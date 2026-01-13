import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8081';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const clientService = {
  getAllClients: () => apiClient.get('/api/v1/clients'),
  getClient: (clientId) => apiClient.get(`/api/v1/clients/${clientId}`),
  createClient: (data) => apiClient.post('/api/v1/clients', data),
  updateKYC: (clientId, data) => apiClient.put(`/api/v1/clients/${clientId}/kyc`, data),
  getNetWorth: (clientId) => apiClient.get(`/api/v1/clients/${clientId}/net-worth`),
};

export const portfolioService = {
  getPortfoliosByClient: (clientId) => 
    axios.get(`http://localhost:8082/api/v1/portfolios/client/${clientId}`),
  getPortfolio: (portfolioId) => 
    axios.get(`http://localhost:8082/api/v1/portfolios/${portfolioId}`),
};

export const advisoryService = {
  generateRecommendation: (data) => 
    axios.post('http://localhost:8083/api/v1/advisory/recommendations', data),
};

export default apiClient;
