"use client";

import React, {useState} from 'react'
import Link from 'next/link';
import Image from "next/image";
import { useRouter } from 'next/navigation';

import { Input } from '../../components/ui/input'

const Auth = () => {
  const [loading, setLoading] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const router = useRouter();

      const handleChange = (e) => {
        const { name, value } = e.target;

        if(name === 'username') {
          setUsername(value);
        } else if(name === 'password') {
          setPassword(value);
        }
      };

    const handleSubmit = async (e) => {
      setLoading(true);

      try {
        const baseUrl = 'http://localhost:80/api/v1/user-service/login';
        const queryParams = new URLSearchParams({
          username: username,
          password: password
        });
        const url = `${baseUrl}?${queryParams.toString()}`;

        const response = await fetch(url, {
          method: "GET",
          headers: {
            'Content-Type': 'application/json'
          },
        });

        if(!response.ok) {
          const errData = await response.json();
          throw new Error(errData.errorMessage || "Authentifizierung fehlgeschlagen");
        }

        const data = await response.json();
        if(data.errorMessage !== "") {
          throw new Error(data.errorMessage);
        }

        localStorage.setItem('jwt-token', data.token);
        localStorage.setItem('user', JSON.stringify(data));
        console.log(data);
        router.push('/dashboard');
      } catch (err) {
        alert(err.message)
      } finally {
        setLoading(false);
      }
    };

    return (
        <section className=" flex items-center justify-center">
          <div className="max-w-md w-full space-y-8 p-8 bg-gray-800 rounded-lg shadow-lg">
            <div className="flex flex-col items-center">
              <Image
                src="/logo.webp"
                alt="Supermarket Scout Logo"
                width={100}
                height={100}
                className="rounded-lg shadow-lg object-cover mb-4"
              />
              <h2 className="mt-6 text-center text-3xl font-extrabold text-white">
                Authentifizieren
              </h2>
              <p className="mt-2 text-center text-sm text-gray-300">
                Oder{' '}
                <Link href="/" className="font-medium text-[#ea6b28] hover:text-[#d15d23]">
                  zurück zur Startseite
                </Link>
              </p>
            </div>
            
            {/* Submit Formular */}
            <div className="mt-8 space-y-6" onSubmit={handleSubmit}>
              <div className="rounded-md shadow-sm -space-y-px">
                <Input name="username" type="text" onChange={handleChange} placeholder="Benutzername" />
                <Input name="password" type="password" onChange={handleChange} placeholder="Passwort" />
              </div>
  
              {/* Submit Button */}
              <div>
                <button
                  type="submit"
                  disabled={loading}
                  onClick={handleSubmit}
                  className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-[#ea6b28] hover:bg-[#d15d23] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#ea6b28] transition-colors duration-300"
                >
                  {loading ? 'Authentifizierung läuft...' : 'Anmelden'}
                </button>
              </div>
            </div>
          </div>
        </section>
      );
    };
export default Auth;