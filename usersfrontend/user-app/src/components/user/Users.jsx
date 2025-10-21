import { useEffect, useState } from "react";
import { GetAllUsers } from "../apis";
import {Button, Form, message} from "antd"
function Users(){
    const[getUsers,setUsers]=useState([]);


    
const getData=async()=>{
    try{
        const user=await GetAllUsers();
        console.log("ALL USERS FROM USERS",user);
        setUsers(user);
    }catch(err){
        console.log(err.message);
    }
}


useEffect(()=>{
    getData();
},[])


    return(
        <>
        <h1>USERS</h1>
            {
                getUsers.map((items,index)=>{
                    return (
                        <div key={index} >
                            <ul style={{display:"flex",background:"grey", width:200,height:"100px",flexDirection:"column",overflow:"hidden"}}>
                                <li style={{background:"yellow",font:"20px"}}>{items.name}</li>
                                <li>{items.email}</li>
                                <li>{items.password}</li>
                                <li>{items.address}</li>
                            </ul>
                        </div>
                    )
                  
                })
            }
        </>
    )
}
export default Users;
