import { useEffect, useState } from "react";
import { getTeacherByID } from "../apis";
import { Button, Form, Input, message } from "antd";

function TeachersSignup() {
    const [teacher, setTeacher] = useState(null);
    const [basicForm] = Form.useForm();
    const userId = localStorage.getItem("userId");

    const handleCompleteSignup = async (values) => {
        try {
            console.log("📝 Completing teacher signup with:", values);
            console.log("👤 User ID:", userId);
            
            if (!userId) {
                message.error("User ID not found. Please sign up first.");
                return;
            }
            const response = await getTeacherByID(userId, values);
            
            if (response) {
                console.log("✅ TEACHER SIGNUP COMPLETED:", response.data);
                setTeacher(response.data);
            }
        } catch (err) {
            console.log("❌ Error completing teacher signup:", err.message);
            message.error("Failed to complete teacher signup");
        }
    }

    // Optional: Fetch existing teacher data on component mount
    const fetchTeacherData = async () => {
        try {
            if (userId) {

                console.log("✅ Ready to complete teacher signup for user:", userId);
            }
        } catch (err) {
            console.log("❌ Error fetching teacher data:", err.message);
        }
    }

    useEffect(() => {
        if (userId) {
            fetchTeacherData();
        } else {
            console.log("❌ No user ID found in localStorage");
            message.error("Please complete user registration first");
        }
    }, [userId]);

    return (
        <>
            <Form
                form={basicForm}
                name="teacherSignup"
                onFinish={handleCompleteSignup}
                layout="vertical"
                style={{ maxWidth: 400, margin: '0 auto', padding: '20px' }}
            >
                <Form.Item
                    label="Teacher Subject"
                    name="teacherSubject"
                    rules={[{ required: true, message: "Please enter your subject" }]}
                >
                    <Input type="text" placeholder="Enter your subject (e.g., ANATOMY)" />
                </Form.Item>

                           <Form.Item>
                    <Button
                        type="primary"
                        htmlType="submit"
                        style={{
                            background: '#1890ff',
                            color: 'white',
                            border: 'none',
                            padding: '8px 16px',
                            borderRadius: '4px',
                            cursor: 'pointer',
                            width: '100%'
                        }}
                    >
                        Complete Teacher Sign Up
                    </Button>
                </Form.Item>
            </Form>

            {teacher && (
                <div style={{
                    marginTop: 20,
                    padding: 10,
                    background: '#f0f0f0',
                    maxWidth: 400,
                    margin: '20px auto'
                }}>
                    <h3>Teacher Profile Completed Successfully!</h3>
                    <p><strong>ID:</strong> {teacher.id}</p>
                    <p><strong>Name:</strong> {teacher.name}</p>
                    <p><strong>Email:</strong> {teacher.email}</p>
                    <p><strong>Address:</strong> {teacher.address}</p>
                    <p><strong>Subject:</strong> {teacher.teacherSubject}</p>
                    <p><strong>Qualifications:</strong> {teacher.qualifications}</p>
                    <p><strong>Experience:</strong> {teacher.experience} years</p>
                    <p><strong>Roles:</strong> {teacher.rolesList?.map(role => 
                        typeof role === 'object' ? role.roles : role
                    ).join(', ')}</p>
                </div>
            )}

            {/* Debug info */}
            <div style={{ 
                marginTop: 20, 
                padding: 10, 
                background: '#fff3cd',
                maxWidth: 400,
                margin: '20px auto',
                fontSize: '12px'
            }}>
                <h4>Debug Info:</h4>
                <p><strong>User ID from localStorage:</strong> {userId || 'Not found'}</p>
                <p><strong>Teacher data loaded:</strong> {teacher ? 'Yes' : 'No'}</p>
            </div>
        </>
    )
}

export default TeachersSignup;