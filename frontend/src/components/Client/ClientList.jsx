import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { clientService } from '../../services/api';

export default function ClientList() {
  const [clients, setClients] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadClients();
  }, []);

  const loadClients = async () => {
    try {
      const response = await clientService.getAllClients();
      setClients(response.data);
    } catch (error) {
      console.error('Error loading clients:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center py-12">Loading...</div>;
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-3xl font-bold text-gray-900">Clients</h2>
        <button className="bg-primary-600 text-white px-4 py-2 rounded-md hover:bg-primary-700">
          Add Client
        </button>
      </div>

      <div className="bg-white shadow overflow-hidden sm:rounded-md">
        <ul className="divide-y divide-gray-200">
          {clients.map((client) => (
            <li key={client.clientId}>
              <Link
                to={`/clients/${client.clientId}`}
                className="block hover:bg-gray-50 transition"
              >
                <div className="px-4 py-4 sm:px-6">
                  <div className="flex items-center justify-between">
                    <div className="flex-1">
                      <p className="text-lg font-medium text-primary-600">
                        {client.firstName} {client.lastName}
                      </p>
                      <p className="text-sm text-gray-500">{client.email}</p>
                    </div>
                    <div className="ml-4 flex-shrink-0 text-right">
                      <span className={`px-2 py-1 text-xs rounded-full ${
                        client.segment === 'UHNI' 
                          ? 'bg-gold-100 text-gold-800'
                          : 'bg-blue-100 text-blue-800'
                      }`}>
                        {client.segment}
                      </span>
                      <p className="mt-1 text-sm text-gray-500">
                        {client.riskProfile}
                      </p>
                    </div>
                  </div>
                  <div className="mt-2 flex justify-between">
                    <span className="text-sm text-gray-600">
                      Net Worth: {client.currency} {client.netWorthValue?.toLocaleString()}
                    </span>
                    <span className={`text-xs px-2 py-1 rounded ${
                      client.kycStatus === 'VERIFIED'
                        ? 'bg-green-100 text-green-800'
                        : 'bg-yellow-100 text-yellow-800'
                    }`}>
                      KYC: {client.kycStatus}
                    </span>
                  </div>
                </div>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
