import { useState } from "react";
import { useAuth } from "./UseAuth";
import { useNavigate } from "react-router-dom";
import axios from "axios"; 
import "./AuthLoginCss.css"

const AuthLogin = () => {
    const [formData, setFormData] = useState({
        email: "", 
        password: ""
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
        setError('');
    };

   const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
        const response = await login(formData.email, formData.password);
        console.log("RESPONSE ", response.token);
        
        if (response) {
                    console.log("RESPONSE ", response.token);
            // Store token in localStorage
            localStorage.setItem('access_token', response.token);
            localStorage.setItem('user', JSON.stringify(response.user));
            
            // Set default authorization header
            axios.defaults.headers.common['Authorization'] = `Bearer ${response.token}`;
            
            // Manually update auth context if needed
            // This depends on your UseAuth implementation
            // await checkAuthStatus(); // If you export this from UseAuth
            
            console.log("Login successful, redirecting...");
            navigate('/GetById');
        } else {
            setError('Login successful but no token received');
        }
        
    } catch (err) {
        setError(err.response?.data?.error || 'Login failed');
    } finally {
        setLoading(false);
    }
};

    return (
        <div className="login-container">
            <div className="login-form">
                <h2>Welcome Back</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            placeholder="Enter your email"
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Enter your password"
                        />
                    </div>

                    {error && <div className="error-message">{error}</div>}

                    <button 
                        type="submit" 
                        className="login-button"
                        disabled={loading}
                    >
                        {loading ? 'Signing in...' : 'Sign In'}
                    </button>
                </form>
                
                <div className="login-links">
                    <a href="/RESETPASSWORD">Forgot Password?</a>
                    <a href="/signup">Create Account</a>
                    <a href="/">Or login with OAuth2</a>
                </div>
            </div>
        </div>
    );
};

export default AuthLogin;