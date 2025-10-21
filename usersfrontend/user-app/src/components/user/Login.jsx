import { useEffect, useState } from "react";
import { LoginUser } from "../apis";
import { Form, Input, Button } from "antd";
import { useNavigate } from "react-router-dom";

function Login() {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
    const [advancedForm] = Form.useForm();

  const handleLogin = async (values) => {
    try {
      setLoading(true);
      const userData = await LoginUser(values);
      console.log("from login page ", userData.token);
      
      if (userData) {
        // Store user data in state/localStorage/context as needed
        localStorage.setItem("user", JSON.stringify(userData));
        console.log("login successfully ")
        navigate("/GetById");
      }
    } catch (err) {
      console.log(err.message);
    } 
    finally {
      setLoading(false);
    }
  };




  return (
    <>
    
      <Form
        name="basic"
        form={advancedForm}
        labelCol={{ span: 8 }}
        wrapperCol={{ span: 16 }}
        style={{ maxWidth: 600 }}
        initialValues={{ remember: true }}
        onFinish={handleLogin}

      >
        <h2 style={{marginLeft:"80px"}}>LOGIN FORM</h2>
        <Form.Item
          label="Email"
          name="userEmail"
          rules={[{ required: true, message: "Please enter your email" }]}
        >
          <Input type="email" placeholder="Please enter your email" />
        </Form.Item>
        <Form.Item
          label="Password"
          name="password"
          rules={[{ required: true, message: "Please enter your password" }]}
        >
          <Input type="password" placeholder="Please enter your password" />
        </Form.Item>
        <Form.Item label={null}>
          <Button type="primary" htmlType="submit" loading={loading}>
            Submit
          </Button>
          <hr/>
          <Button type="primary" htmlType="submit" loading={loading}>
            Reset Password
          </Button>
        </Form.Item>
      </Form>
    </>
  );
}

export default Login;