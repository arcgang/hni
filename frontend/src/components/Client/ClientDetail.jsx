import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { clientService } from '../../services/api';

export default function ClientDetail() {
  const { clientId } = useParams();
  const [client, setClient] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadClient();
  }, [clientId]);

  const loadClient = async () => {
    try {
      const response = await clientService.getClient(clientId);
      setClient(response.data);
    } catch (error) {
      console.error('Error loading client:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center py-12">Loading...</div>;
  }

  if (!client) {
    return <div className="text-center py-12">Client not found</div>;
  }

  return (
    <div className="space-y-6">
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-2xl font-bold text-gray-900 mb-4">
          {client.firstName} {client.lastName}
        </h2>
        
        <div className="grid grid-cols-2 gap-4">
          <InfoItem label="Email" value={client.email} />
          <InfoItem label="Phone" value={client.phone || 'N/A'} />
          <InfoItem label="Segment" value={client.segment} />
          <InfoItem label="Risk Profile" value={client.riskProfile} />
          <InfoItem label="KYC Status" value={client.kycStatus} />
          <InfoItem 
            label="Net Worth" 
            value={`${client.currency} ${client.netWorthValue?.toLocaleString()}`} 
          />
          <InfoItem label="Family Office" value={client.familyOffice ? 'Yes' : 'No'} />
          <InfoItem label="Domicile" value={client.domicileCountry || 'N/A'} />
        </div>
      </div>

      <div className="bg-white shadow rounded-lg p-6">
        <h3 className="text-lg font-semibold mb-4">AI Insights</h3>
        <p className="text-gray-600">
          Client health score: <span className="font-bold text-green-600">Excellent</span>
        </p>
        <p className="text-gray-600 mt-2">
          Recommended actions: Portfolio rebalancing, Tax optimization
        </p>
      </div>
    </div>
  );
}

function InfoItem({ label, value }) {
  return (
    <div>
      <dt className="text-sm font-medium text-gray-500">{label}</dt>
      <dd className="mt-1 text-sm text-gray-900">{value}</dd>
    </div>
  );
}
