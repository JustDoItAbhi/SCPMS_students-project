import axios from "axios"
import axiosInstance from "../auth/AuthMiddleWear"
const BASE_URL="http://localhost:8080/api/user"

export const GetAllUsers = async () => {
    try {
        console.log("🔍 Making GetAllUsers API call...");
        const response = await axiosInstance.get("/api/user/");
        console.log("✅ GetAllUsers success:", response.data);
        return response.data;
    } catch (err) {
        console.log("❌ GetAllUsers error details:", {
            message: err.message,
            status: err.response?.status,
            statusText: err.response?.statusText,
            data: err.response?.data,
            headers: err.response?.headers
        });
        throw err;
    }
}
export const CreateUser = async (values) => {
    try {
        const create = await axios.post("http://localhost:8080/api/user/createUser", values, {
            headers: {
                Authorization: undefined, // Explicitly remove
                'Content-Type': 'application/json'
            },
            withCredentials: false // Important: disable credentials
        });
        console.log("✅ CreateUser success:", create);
        return create;
    } catch (err) {
        console.log("❌ CreateUser error:", err.message);
        throw err;
    }
}
export const getTeacherByID=async(userId,teacherData)=>{

    try {
        const teacherSignup = await axiosInstance.post(`/api/teachers/finishSignUP/${userId}`, {user:teacherData});
        console.log("✅ CompleteTeacherSignup success:", teacherSignup);
        return teacherSignup;
    } catch (err) {
        console.log("❌ CompleteTeacherSignup error:", err.message);
        throw err;
    }
}

export const getStudentByID=async(userId,studentData)=>{

    try {
        const studentSignup = await axiosInstance.post(`/api/students/completeStundentSignUp/${userId}`, studentData);
        console.log("✅ Complete STUDENT Signup success:", studentSignup);
        return studentSignup;
    } catch (err) {
        console.log("❌ Complete STUDENT Signup error:", err.message);
        throw err;
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
        const response=await axiosInstance.get(`${BASE_URL}/getUserById/${id}`);
        console.log(" USERS BY ID",response)
        return response
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
 export const DeleteStudentDataFrom=async(studentId)=>{
    try{
        const response=await axiosInstance.delete(`api/students/delete/${studentId}`);
        console.log(" deleted student BY ",response.data)
        return response.data
    }catch(err){
        console.log(err.message);
    }
}

