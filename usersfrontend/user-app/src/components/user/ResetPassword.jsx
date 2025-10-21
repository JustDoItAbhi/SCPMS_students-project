import { useState } from "react";
import { ResetPass, SendOTP } from "../apis";
import {userNavigate} from "react-router-dom"

function ResetPassword(email, setEmail){
    const[pass,setPass]=useState([]);
    const [message, setMessage] = useState("");
    
    const handleRest=async(value)=>{
        try{
            const response=await ResetPass(value);
            console.log(response);
            if(response){
                console.log("RES ",response);
            }
        }catch(err){
         console.log(err.message);
        }
    }


    return(
   <div style={{ maxWidth: 400, margin: "50px auto", textAlign: "center" }}>
      <h2>ENTER YOUR DETAILS</h2>
      <input
        type="email"
        placeholder="Enter your email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        style={{ padding: "8px", width: "100%", marginBottom: "10px" }}
      />
        <input
        type="text"
        placeholder="Enter your OTP"
        value={otp}
        onChange={(e) => setPass(e.target.value)}
        style={{ padding: "8px", width: "100%", marginBottom: "10px" }}
      />
      <button
        onClick={handleSendOtp}
        disabled={loading}
        style={{ padding: "8px 15px", cursor: "pointer" }}
      >
        {loading ? "Sending..." : "Send OTP"}
      </button>
      {message && <p style={{ marginTop: 10 }}>{message}</p>}
    </div>
    )
}
export default ResetPassword;