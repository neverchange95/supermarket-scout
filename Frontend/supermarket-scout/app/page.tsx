"use client";

import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <section className="h-full">
      <div className="container mx-auto h-full px-4 py-10">
        <div className="flex flex-col items-center gap-8">

          <div>
            <Image
              src="/logo.webp"
              alt="Supermarket Scout Logo"
              width={200}
              height={200}
              className="rounded-lg shadow-lg object-cover"
              priority
            />
          </div>

          <div className="text-center max-w-[500px]">
            <span className="text-xl block mb-4 font-semibold text-white/90">
              THI Clound-native-Projekt
            </span>
            <p className="mb-6 text-white/80 leading-relaxed">
              Supermarket Scout
            </p>

            <div className="flex flex-col items-center gap-4 sm:flex-row sm:justify-center">
            <Link 
                href="/register" 
                className="px-6 py-2 rounded border border-[#ea6b28] text-[#ea6b28] hover:bg-[#ea6b28] hover:text-white transition-colors duration-300 text-center"
              >
                Account anlegen
              </Link>
              <Link 
                href="/auth" 
                className="px-6 py-2 rounded border border-[#ea6b28] text-[#ea6b28] hover:bg-[#ea6b28] hover:text-white transition-colors duration-300 text-center"
              >
                Anmelden
              </Link>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
