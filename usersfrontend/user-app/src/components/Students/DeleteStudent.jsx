import { useState } from "react"
import { DeleteStudentDataFrom } from "../apis";
import { Button, Form } from "antd";
import { useForm } from "antd/es/form/Form";

function DeleteStudent(){
    const[deleteStudent,setDeleteStudent]=useState(null);
    const[basicForm]=useForm();
    const userId=localStorage.getItem("studentId");
    console.log("user id",userId);
const delData=async()=>{
    try{
        const deleteStudent=await DeleteStudentDataFrom(userId);
        if(deleteStudent){
            console.log("STUDENT DELETED")
            localStorage.removeItem("studentId");
        }

    }catch(err){
        console.log(err.message);
    }
}


    return(
        <div style={{ height:"100vh",background:"content-box radial-gradient(rgb(124, 108, 111), rgb(39, 40, 41))"}}>

        <Form
        form={basicForm}
        name="deleteStudentData"
        onFinish={delData}
        layout="vertical"
        style={{ maxWidth: 400, margin: '0 auto', padding: '20px'}}
>
    <Form.Item>
             <Button
             type="primery"
             htmlType="submit"
             style={{
                            background: '#1890ff',
                            color: 'white',
                            border: 'none',
                            padding: '40px 16px',
                            borderRadius: '10px',
                            cursor: 'pointer',
                            width: '100%',
                           marginLeft:"0vh",
                           marginRight:"0vh"

                        }}>
                               DELETE STUDENT FORM DATA
                </Button>

    </Form.Item>
   
</Form>
        </div>
    )
}
export default DeleteStudent;