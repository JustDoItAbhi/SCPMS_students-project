import { useState, useEffect, createContext, useContext } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const useAuth = () => {
    return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        checkAuthStatus();
    }, []);

    const checkAuthStatus = async () => {
        try {
            const token = localStorage.getItem('access_token');
            if (token) {
                // Set the authorization header
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                
                // Verify token by calling user endpoint
                const response = await axios.get('http://localhost:8080/api/user/me');
                setUser(response.data);
            } else {
                setUser(null);
            }
        } catch (error) {
            console.error('Auth check failed:', error);
            localStorage.removeItem('access_token');
            localStorage.removeItem('user');
            delete axios.defaults.headers.common['Authorization'];
            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    const login = async (email, password) => {
        try {
            const formData = new URLSearchParams();
            formData.append('email', email);
            formData.append('password', password);

            const response = await axios.post('http://localhost:8080/api/auth/login', formData, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                withCredentials: true
            });

            const { token, user: userData } = response.data;
            
            // Store token and update user state
            localStorage.setItem('access_token', token);
            localStorage.setItem('user', JSON.stringify(userData));
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            setUser(userData); 
            
            return response.data;
            
        } catch (error) {
            throw error;
        }
    };

    const logout = async () => {
        try {
            await axios.post('http://localhost:8080/api/auth/logout', {}, {
                withCredentials: true
            });
        } catch (error) {
            console.error('Logout error:', error);
        } finally {
            localStorage.removeItem('access_token');
            localStorage.removeItem('user');
            delete axios.defaults.headers.common['Authorization'];
            setUser(null); // ← Clear user state
            window.location.href = '/login';
        }
    };

    const value = {
        user,
        login,
        logout,
        checkAuthStatus,
        loading
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};