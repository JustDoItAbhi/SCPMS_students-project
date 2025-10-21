import { Link, useLocation } from "react-router-dom";

function NavBar(){
    const location = useLocation();
    
    return(
        <nav style={{
            background: "#1890ff", 
            padding: "15px 20px",
            display: "flex",
            gap: "30px",
            alignItems: "center",
            boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
        }}>
            <Link to="/USER" style={{color:"white"}}>USERS</Link>
            <Link to="/GetById"  style={{color:"white"}}>GET USER BY ID</Link>
            <Link to="/create"  style={{color:"white"}} >SIGN UP</Link>
            <Link to="/Login"style={{color:"white"}}  >LOGIN</Link>
            <Link to="/SENDOTP" style={{color:"white"}}>SEND OTP</Link>
       
        </nav>
    )
}

export default NavBar;