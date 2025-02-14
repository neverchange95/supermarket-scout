"use client";

import React from 'react'
import { useEffect, useState } from "react";

import ProtectedRoute from '../../components/ProtectedRoute';
import DashboardCard from "../../components/DashboardCard";

const Dashboard = () => {
  const [initialData, setInitialData] = useState([]);
  const [searchData, setSearchData] = useState(null); 
  const [productName, setProductName] = useState("");
  const [location, setLocation] = useState("");
  const [userObj, setUserObj] = useState(null);

  useEffect(() => {
    const obj = JSON.parse(localStorage.getItem('user'));
    if(obj !== null) {
      setUserObj(obj.user);

      fetch(`http://localhost:80/api/v1/price-service/cheapest?productCategory=${obj.user.preferredCategory}&location=${obj.user.location}`)
      .then((res) => res.json())
      .then((json) => {
        setInitialData(json)
      })
      .catch((err) => console.error(err))
    }
  }, []);

  const handleSearch = () => {
    fetch(`http://localhost:80/api/v1/price-service/compare?productName=${productName}&location=${location}`)
    .then((res) => res.json())
    .then((json) => {
      if (json && json.length > 0) {
        setSearchData(json);
      } else {
        setSearchData([]);
      }
    })
    .catch((err) => console.error(err));
  }

  return (
    <ProtectedRoute>
      <main className="p-4 md:p-8 text-white bg-gray-900 min-h-screen">
        <h1 className="text-3xl font-bold mb-6">Dashboard</h1>

        <div className="flex flex-col md:flex-row items-start md:items-end gap-2 mb-6">
          <div>
            <label className="block text-sm font-medium mb-1">Produkt</label>
            <input
              className="bg-gray-800 border border-gray-700 text-white p-2 rounded w-full"
              placeholder="z.B. Chips"
              value={productName}
              onChange={(e) => setProductName(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">Standort</label>
            <input
              className="bg-gray-800 border border-gray-700 text-white p-2 rounded w-full"
              placeholder="z.B. Ingolstadt"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
          </div>
          <button
            onClick={handleSearch}
            className="bg-[#ea6b28] hover:bg-[#d15d23] transition text-white px-4 py-2 rounded mt-4 md:mt-0"
          >
            Suchen
          </button>
        </div>

        {
          searchData ? (
            searchData.map((item, index) => (
              <div key={index} className="mb-6">
                <div className="mb-4 p-4 bg-gray-800 rounded shadow">
                  <p className="mb-1">
                    <span className="font-semibold">Produkt:</span> {item.productName}
                  </p>
                  <p className="mb-1">
                    <span className="font-semibold">Kategorie:</span>{" "}
                    {item.productCategory}
                  </p>
                  <p className="mb-1">
                    <span className="font-semibold">Beschreibung:</span>{" "}
                    {item.productDescription}
                  </p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {item.supermarketPrices.map((priceItem, idx) => (
                    <DashboardCard key={idx} item={priceItem} />
                  ))}
                </div>
              </div>
            ))
          ) : (
          <>
          <div className="mb-4 p-4 bg-gray-800 rounded shadow">
              <p className="mb-1">
                <span className="font-semibold">Bevorzugte Produktkategorie:</span>{" "}
                {userObj ? userObj.preferredCategory : ""}
              </p>
              <p className="mb-1">
                <span className="font-semibold">Wohnort:</span>{" "}
                {userObj ? userObj.location : ""}
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {initialData.map((item, idx) => (
                <DashboardCard key={idx} item={item} />
              ))}
            </div>
          </>

        )}
      </main>
    </ProtectedRoute>
  );
}

export default Dashboard;