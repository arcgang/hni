import React from 'react';
import { useParams } from 'react-router-dom';

export default function PortfolioView() {
  const { clientId } = useParams();

  return (
    <div className="space-y-6">
      <h2 className="text-3xl font-bold text-gray-900">Portfolio Analytics</h2>
      
      <div className="bg-white shadow rounded-lg p-6">
        <h3 className="text-lg font-semibold mb-4">Asset Allocation</h3>
        <div className="grid grid-cols-2 gap-4">
          <AssetClassCard name="Equity" percentage={40} value="$960K" />
          <AssetClassCard name="Fixed Income" percentage={30} value="$720K" />
          <AssetClassCard name="Alternatives" percentage={20} value="$480K" />
          <AssetClassCard name="Cash" percentage={10} value="$240K" />
        </div>
      </div>

      <div className="bg-white shadow rounded-lg p-6">
        <h3 className="text-lg font-semibold mb-4">Performance</h3>
        <p className="text-gray-600">YTD Return: <span className="font-bold text-green-600">+12.5%</span></p>
        <p className="text-gray-600">Benchmark: <span className="text-gray-900">+10.2%</span></p>
      </div>
    </div>
  );
}

function AssetClassCard({ name, percentage, value }) {
  return (
    <div className="border border-gray-200 rounded-lg p-4">
      <h4 className="font-medium text-gray-900">{name}</h4>
      <p className="text-2xl font-bold text-primary-600">{percentage}%</p>
      <p className="text-sm text-gray-500">{value}</p>
    </div>
  );
}
