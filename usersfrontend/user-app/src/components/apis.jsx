import axios from "axios"
import axiosInstance from "../auth/AuthMiddleWear"
const BASE_URL="http://localhost:8080/api/user"

export const GetAllUsers=async()=>{
    try{
        const users=await axiosInstance.get(`${BASE_URL}/`)
        console.log("ALL USERS ",users.data)
        return users.data
    }catch(err){
        console.log(err.message);
    }
}
export const CreateUser=async(values)=>{
    try{
        const create=await axios.post(`${BASE_URL}/createUser`,values);
        console.log(create)
        return create
    }catch(err){
        console.log(err.message);
    }
}
export const LoginUser=async(value)=>{
    try{
        const login=await axiosInstance.post(`${BASE_URL}/Login`,value);
        console.log("LOGIN USERS",login.data)
        return login.data
    }catch(err){
        console.log(err.message);
    }
}
export const DeleteUser=async(value)=>{
    try{
        const login=await axiosInstance.get(`${BASE_URL}/Login`,value);
        console.log("LOGIN USERS",login.data)
        return login.data
    }catch(err){
        console.log(err.message);
    }
}

export const GetUserById=async(id)=>{
    try{
        const response=await axiosInstance.get(`${BASE_URL}/getUserById`,{params: { id: id }});
        console.log(" USERS BY ID",response.data)
        return response.data
    }catch(err){
        console.log(err.message);
    }
}

 export const SendOTP=async(email)=>{
    try{
        const response=await axiosInstance.get(`${BASE_URL}/sendOtp/${email}`);
        console.log(" OTP SENT ",response.data)
        return response.data
    }catch(err){
        console.log(err.message);
    }
}
 export const ResetPass=async(email,otp,pass)=>{
    try{
        const response=await axiosInstance.post(`${BASE_URL}/resetPassword`,{email:email,otp:otp,password:pass});
        console.log(" RESET BY ",response.data)
        return response.data
    }catch(err){
        console.log(err.message);
    }
}
