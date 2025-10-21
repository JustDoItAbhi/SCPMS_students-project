import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom';



import OauthLoginForm from '../oauth/OauthLoginForm';
import SignupForm from '../oauth/SignupForm';
import OauthLogin from '../oauth/OauthLogin';
import Callback from '../oauth/Callback';
import GetList from '../oauth/GetList';
import Dashboard from '../oauth/Dashboard';
import Users from '../user/Users';
import CreatingUsers from '../user/CreatingUsers';
import Login from '../user/Login';
import NavBar from '../user/NavBar';
import GetById from "../user/GetById"
import ResetPassword from '../user/ResetPassword';
import SendOtpToEmail from '../user/SendOtpToEmail';

function AppContent() {
      const location=useLocation();
  const showNavBar=['/USER','/create','/GetList','/GetById','/SENDOTP'].includes(location.pathname);


  return (
    <>
    {showNavBar && <NavBar/>}
     <Routes>
      <Route path='/USER' element={<Users/>}></Route>
      <Route path='/create' element={<CreatingUsers/>}></Route>
      <Route path='/Login' element={<Login/>}></Route>
     <Route path="/" element={<OauthLoginForm />} />
          <Route path="/signup" element={<SignupForm />} />
          <Route path="/login" element={<OauthLogin />} />
          <Route path="/callback" element={<Callback />} />
          <Route path="/GetList" element={<GetList />} />
          <Route path="/dashboard" element={<Dashboard />} /> 
            <Route path="/GetById" element={<GetById/>} /> 
            <Route path='/RESETPASSWORD' element={<ResetPassword/>}/>
              <Route path='/SENDOTP' element={<SendOtpToEmail/>}/>


     </Routes>
    </>
  )
}

export default AppContent;
