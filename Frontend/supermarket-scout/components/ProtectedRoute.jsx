"use client";

import { useRouter } from 'next/navigation';
import { useEffect } from "react"

const ProtectedRoute = ({ children }) => {
    const router = useRouter();
    
    useEffect(() => {
        const token = localStorage.getItem('jwt-token');
        if(!token) {
            router.replace('/auth');
        }
    }, [router]);

    return children;
}

export default ProtectedRoute;