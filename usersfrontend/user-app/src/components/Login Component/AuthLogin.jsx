import { useId, useState } from "react";
import { useAuth } from "./UseAuth";
import { useNavigate } from "react-router-dom";
import "./AuthLoginCss.css"
import { GetTeacherByUserEmail, GetUserById } from "../apis";

const AuthLogin = () => {
    const [formData, setFormData] = useState({
        email: "",
        password: ""
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const { login } = useAuth();
    const navigate = useNavigate();
    //     localStorage.removeItem("access_token")
    // localStorage.removeItem("studentId")
    // localStorage.removeItem("subject")
    // localStorage.removeItem("user")
    // localStorage.removeItem("userId")
    // localStorage.removeItem("studentAndSubjectId")

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
            console.log("LOGIN RESPONSE ", response);

            if (response && response.token) {
                console.log("Login successful, redirecting...", response);

                // Get user ID from the stored user data
                const userData = JSON.parse(localStorage.getItem('user'));
                console.log("USER ID", userData?.id)
                const userId = userData?.id
                localStorage.setItem("userId", userId);
                if (userData) {
                    const userId = localStorage.getItem("userId");
                    const getUser = await GetUserById(userId);
                    console.log("userId", userId)
                    // console.log("get user from local , ", getUser.data.rolesList[0]);
                    const role = getUser.data.rolesList[0];
                    const userRole = role?.roles || role?.[0];
                    console.log("USER ROLE ", userRole);
                    const studentId = localStorage.getItem("studentId");
                    if (!studentId && studentId === "undefined") {
                        navigate("/STUDENTSIGNUP")
                    } else if (studentId) {
                        navigate("/Student-dashboard")
                    }
                    else {
                        if (userRole === "STUDENT") {
                            navigate("/STUDENTSIGNUP");
                        } else if (userRole === "TEACHER") {
                            try {
                                const teacherId = localStorage.getItem("teacherId");
                                const userEmail = localStorage.getItem("userEmail");

                                // If we already have teacherId, navigate to profile
                                if (teacherId) {
                                    navigate("/TEACHER-PROFILE");
                                    return; // Important to prevent further execution
                                }

                                // If no teacherId but we have email, try to get teacher data
                                if (userEmail) {
                                    const getteacherbyemail = await GetTeacherByUserEmail(userEmail);

                                    if (getteacherbyemail) {
                                        // Store the teacher ID properly
                                        localStorage.setItem("teacherId", getteacherbyemail);
                                        navigate("/TEACHER-PROFILE");
                                    } else {
                                        // Teacher not found, need to sign up
                                        navigate("/TEACHERSIGNUP");
                                    }
                                } else {
                                    // No email found, need to sign up
                                    navigate("/TEACHERSIGNUP");
                                }
                            } catch (error) {
                                console.error("Error fetching teacher data:", error);
                                // On error, redirect to signup
                                navigate("/TEACHERSIGNUP");
                            }
                        }
                    }
                }
            }

        } catch (err) {
                        console.error("Login error:", err);
                        setError(err.response?.data?.error || err.message || 'Login failed');
                    } finally {
                        setLoading(false);
                    }
                
            }

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
                                <a href="/SENDOTP">Forgot Password?</a>
                                <a href="/create">Create Account</a>
                                {/* <a href="/">Or login with OAuth2</a> */}
                            </div>
                        </div>
                    </div>
                );
            };

            export default AuthLogin;