"use client";

import React from "react";
import Link from "next/link"
import { useRouter } from "next/navigation";

const Header = () => {
  const router = useRouter();
  
  const handleLogout =(e) => {
    e.preventDefault();
    localStorage.removeItem('jwt-token');
    router.push("/")
  }

  return (
    <header className="py-8 xl:py-12 text-white">
        <div className="container mx-auto flex justify-between items-center">
          <Link href='/'>
              <h1 className="text-4xl font-semibold">
                  Supermarket Scout 🛒<span className="text-accent"></span>
              </h1>
          </Link>
          <Link
            href="/"
            onClick={handleLogout}
            className="text-white font-medium transition hover:text-[#d15d23]"
          >
            Abmelden
          </Link>
        </div>
    </header>
  )
}

export default Header;