import { useState, useEffect, createContext, useContext } from 'react';
import axios from 'axios';
import decodeJWT from "../apps/decodeJWT"

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
            const userData = localStorage.getItem('user');
            
            if (token && userData) {
                // Set the authorization header
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                setUser(JSON.parse(userData));
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
    console.log("AUTH LOGIN PAGE ")
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
        
        console.log("AUTH RESPONSE ", response.data);
        const { token, user: userData } = response.data;
        
        localStorage.setItem('access_token', token);
        console.log("Token stored in localStorage:", token ? "Yes" : "No");
        
        // Decode the token to get the roles
        const decodedToken = decodeJWT(token);
        console.log("Decoded token:", decodedToken);
           const fullEmail = decodedToken.email || "";
           const username = fullEmail.split('@')[0] || "User";
        console.log("Extracted username:", username);

        const completeUserData = {
            ...userData,
             username: username,
            roles: decodedToken.roles || [],
            grantAuthority: decodedToken.roles?.[0] || 'USER'
        };
        
        console.log("Complete user data to store:", completeUserData);
        
        // Store user data
        localStorage.setItem('user', JSON.stringify(completeUserData));
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        setUser(completeUserData);
        
        return response.data;
        
    } catch (error) {
        console.log("Login error:", error);
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
            setUser(null);
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