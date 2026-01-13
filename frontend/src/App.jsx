import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard/Dashboard';
import ClientList from './components/Client/ClientList';
import ClientDetail from './components/Client/ClientDetail';
import PortfolioView from './components/Portfolio/PortfolioView';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <nav className="bg-white shadow-sm border-b border-gray-200">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex justify-between h-16">
              <div className="flex items-center">
                <h1 className="text-2xl font-bold text-primary-600">
                  🏦 AurumOne
                </h1>
                <span className="ml-3 text-sm text-gray-500">
                  HNI Wealth Management
                </span>
              </div>
            </div>
          </div>
        </nav>

        <main className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/clients" element={<ClientList />} />
            <Route path="/clients/:clientId" element={<ClientDetail />} />
            <Route path="/portfolio/:clientId" element={<PortfolioView />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
