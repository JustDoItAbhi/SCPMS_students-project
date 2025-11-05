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
                const userId=userData?.id
                localStorage.setItem("userId",userId);
                if (userData) {

                    // const user = JSON.parse(userData);
                    // navigate(`/GetById/${user.id}`);
                    const userId = localStorage.getItem("userId");
                    const getUser = await GetUserById(userId);
                    console.log("userId",userId)
                    // console.log("get user from local , ", getUser.data.rolesList[0]);
                    const role = getUser.data.rolesList[0];
                    const userRole = role?.roles || role?.[0];
                    console.log("USER ROLE ", userRole);
                    const studentId = localStorage.getItem("studentId");
                    if (!studentId && studentId==="undefined") {
                        navigate("/STUDENTSIGNUP")
                    } else if(studentId){
                        navigate("/Student-dashboard")
                    }
                    else {
                        if (userRole === "STUDENT") {
                            navigate("/STUDENTSIGNUP")
                        } else if (userRole === "TEACHER") {
                            const userEmail=localStorage.getItem("userEmail");
                            const getteacherbyemail=await GetTeacherByUserEmail(userEmail);
                            if(getteacherbyemail){
                localStorage.setItem("teacherId",getteacherbyemail)
                                console.log(getteacherbyemail);

                              navigate("/TEACHERSIGNUP")
                            }
          
                        }
                    }


                } else {
                    navigate('/profile');
                }
            } else {
                setError('Login successful but no token received');
            }

        } catch (err) {
            console.error("Login error:", err);
            setError(err.response?.data?.error || err.message || 'Login failed');
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
                    <a href="/SENDOTP">Forgot Password?</a>
                    <a href="/create">Create Account</a>
                    {/* <a href="/">Or login with OAuth2</a> */}
                </div>
            </div>
        </div>
    );
};

export default AuthLogin;