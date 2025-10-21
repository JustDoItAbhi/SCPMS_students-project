// src/api/axiosInstance.js
import axios from 'axios';
// import rateLimit from 'axios-interceptor-rate-limit';

// Create axios instance with base configuration
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/user', 
  timeout: 10000, // 10 seconds timeout
  headers: {
    'Content-Type': 'application/json',
  }
});
// rateLimit(axiosInstance, { 
//   maxRequests: 5,     
//   perMilliseconds: 1000, 
// });

// Request interceptor - add auth token to requests
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('access_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }else{
        console.log("Unauthorised request");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor - handle common errors
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Redirect to login if unauthorized
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;