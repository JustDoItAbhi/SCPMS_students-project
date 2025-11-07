import axios from 'axios';
import validateToken from "../components/apps/validateToken";

// Create axios instance
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// ✅ Single unified request interceptor
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('access_token');
    // console.log("🚀 REQUEST INTERCEPTOR - Token:", token ? "Present" : "Missing");
    // console.log("🚀 Request URL:", config.url);

    if (token) {
      // ✅ Validate token before attaching
      const validation = validateToken(token);
      // console.log("🔍 Token validation result:", validation);

      if (validation.valid) {
        config.headers.Authorization = `Bearer ${token}`;
        // console.log("✅ Authorization header set with valid token");
      } else {
        console.log("❌ Token invalid:", validation.reason);
      }
    }

    return config;
  },
  (error) => {
    console.log("❌ Request interceptor error:", error);
    return Promise.reject(error);
  }
);

// ✅ Response interceptor - handle common errors
axiosInstance.interceptors.response.use(
  (response) => {
    // console.log("✅ RESPONSE INTERCEPTOR - Success:", response.status, response.config.url);
    return response;
  },
  (error) => {
    console.log("❌ RESPONSE INTERCEPTOR - Error:", {
      status: error.response?.status,
      url: error.config?.url,
      data: error.response?.data,
      headers: error.response?.headers,
    });

    if (error.response?.status === 401) {
      console.log("🛑 401 Unauthorized detected");
      console.log("🛑 Current token before removal:", localStorage.getItem('access_token'));

      // Don't remove token immediately - let's debug first
      // localStorage.removeItem('access_token');
      // localStorage.removeItem('user');
      // window.location.href = '/login';

      console.log("🛑 Token would be removed here (commented out for debugging)");
    }

    return Promise.reject(error);
  }
);

export default axiosInstance;
