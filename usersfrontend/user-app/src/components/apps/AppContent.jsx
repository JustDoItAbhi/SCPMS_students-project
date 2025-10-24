import { Routes, Route, useLocation } from 'react-router-dom';
import OauthLoginForm from '../oauth/OauthLoginForm';
import SignupForm from '../oauth/SignupForm';
import Callback from '../oauth/Callback';
import GetList from '../oauth/GetList';
import Dashboard from '../oauth/Dashboard';
import Users from '../user/Users';
import CreatingUsers from '../user/CreatingUsers';
import NavBar from '../user/NavBar';
import GetById from "../user/GetById";
import ResetPassword from '../user/ResetPassword';
import SendOtpToEmail from '../user/SendOtpToEmail';
import ProtectedRoute from '../Login Component/ProtectedRoute'; 
import AuthLogin from '../Login Component/AuthLogin';

function AppContent() {
  const location = useLocation();
  const showNavBar = ['/USER', '/create', '/GetList', '/GetById', '/SENDOTP', '/dashboard'].includes(location.pathname);

  return (
    <>
      {showNavBar && (
        <div style={{ position: "relative", zIndex: 2 }}>
          <NavBar />
        </div>
      )}

      <Routes>
        {/* Public routes */}
        <Route path="/" element={<OauthLoginForm />} />
        <Route path="/login" element={<AuthLogin />} />
        <Route path="/signup" element={<SignupForm />} />
        <Route path="/callback" element={<Callback />} />
        <Route path="/RESETPASSWORD" element={<ResetPassword />} />
        <Route path="/SENDOTP" element={<SendOtpToEmail />} />
        <Route path="/GetById" element={<GetById />} />

        {/* Protected routes */}
        <Route path="/USER" element={<ProtectedRoute><Users /></ProtectedRoute>} />
        <Route path="/create" element={<ProtectedRoute><CreatingUsers /></ProtectedRoute>} />
        <Route path="/GetList" element={<ProtectedRoute><GetList /></ProtectedRoute>} />
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
       {/* <Route path="/GetById/:id" element={<ProtectedRoute><GetById /></ProtectedRoute>} /> */}
      </Routes>
    </>
  )
}

export default AppContent;