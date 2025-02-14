"use client";

export default function DashboardCard({ item }) {
    return (
        <div className="bg-gray-800 p-4 rounded shadow text-white">
          {Object.entries(item).map(([key, value]) => (
            <p key={key} className="mb-1">
              <span className="font-semibold capitalize">{key}:</span>{" "}
              {String(value)}
            </p>
          ))}
        </div>
      );
}
