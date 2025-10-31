import { useEffect, useState } from "react";
import { getStudentByID } from "../apis";
import { Button, Form, Input, message } from "antd";
import { useNavigate } from "react-router-dom";

function StudentsSignup() {
    const [student, setStudent] = useState(null);
    const [basicForm] = Form.useForm(); // Fixed typo: basicFrom -> basicForm
    const navigate = useNavigate();

    const studentDetails = localStorage.getItem("userId");

    const getData = async (values) => { // Changed parameter name to 'values'
        try {
            if (!studentDetails) {
                console.log("ERROR user id not defined", studentDetails);
                message.error("User ID not found");
                return;
            }
            
            const findStudent = await getStudentByID(studentDetails, values);
            console.log("STUDENT RESPONSE ", findStudent.data);
            
            if (findStudent && findStudent.data) {
                console.log("STUDENT FOUND ", findStudent.data);
                const studentData = findStudent.data; // Fixed: use findStudent.data, not fetchStudentData.data
                setStudent(studentData);


                console.log("STUDENT ID ", studentData.id);
                localStorage.setItem("studentId",studentData.studentId);
 


                if (studentData.passportNumber && studentData.studentIdCardNumber) {
                    message.success("Student profile completed successfully!");
                    // navigate("/student-dashboard");
                } else {
                    message.warning("Some fields may not have been saved correctly");
                }
                
                // Check if student data is complete
                if (studentData.error) {
                    navigate("/DELETESTUDENT");
                } else if (
                    !studentData.currentYear || 
                    !studentData.groupNumber || 
                    !studentData.subGroupNumber || 
                    !studentData.monitorName || 
                    !studentData.studentIdCardNumber || 
                    !studentData.passportNumber
                ) {
                    // If any required field is missing, show message but don't navigate
                    message.warning("Please complete all student information");
                } else {
                    message.success("Student profile completed successfully!");
                }
            }

        } catch (err) {
            console.log(err.message);
            message.error("Failed to save student data");
        }
    }

    const fetchStudentData = async () => {
        if (studentDetails) {
            try {
                // If you have a GET endpoint to fetch existing student data
                // const existingData = await getStudentByID(studentDetails);
                // setStudent(existingData.data);
                console.log("Student ID available:", studentDetails);
            } catch (err) {
                console.log("Error fetching student data:", err.message);
            }
        } else {
            console.log("UNABLE TO FETCH STUDENT DATA - No user ID");
            message.error("Please complete user registration first");
        }
    }

    useEffect(() => {
        if (studentDetails) {
            fetchStudentData();
        } else {
            console.log("❌ No user ID found in localStorage");
            message.error("Please complete user registration first");
        }
    }, [studentDetails]);

    return (
        <div style={{height: "100%", background:"content-box radial-gradient(rgba(124, 108, 111, 1), rgb(39, 40, 41))"}}>
            <Form
                form={basicForm} // Fixed: basicFrom -> basicForm
                name="studentSignup"
                onFinish={getData}
                layout="vertical"
                style={{ maxWidth: 400, margin: '0 auto', padding: '20px' }}
            >
                <Form.Item
                    label="YOUR PASSPORT NUMBER"
                    name="passportNumber"
                    rules={[{ required: true, message: "Please enter your PASSPORT NUMBER" }]}
                >
                    <Input type="text" placeholder="Enter your PASSPORT NUMBER (e.g., A123456)" />
                </Form.Item>
                
                <Form.Item
                    label="YOUR STUDENT ID NUMBER"
                    name="studentIdCardNumber"
                    rules={[{ required: true, message: "Please enter your STUDENT ID NUMBER" }]}
                >
                    <Input type="text" placeholder="Enter your STUDENT ID NUMBER (e.g., AO21345)" />
                </Form.Item>

                <Form.Item
                    label="YOUR CURRENT YEAR OF STUDY"
                    name="currentYear"
                    rules={[{ required: true, message: "Please enter your YEAR" }]}
                >
                    <Input type="text" placeholder="Enter your YEAR (e.g., 4TH,5TH,6TH)" />
                </Form.Item>

                <Form.Item
                    label="YOUR CURRENTLY IN GROUP NUMBER"
                    name="groupNumber"
                    rules={[{ required: true, message: "Please enter your GROUP NUMBER" }]}
                >
                    <Input type="text" placeholder="Enter your GROUP NUMBER (e.g., 1,2,3)" />
                </Form.Item>

                <Form.Item
                    label="YOUR CURRENTLY IN SUBGROUP NUMBER"
                    name="subGroupNumber"
                    rules={[{ required: true, message: "Please enter your SUBGROUP NUMBER" }]}
                >
                    <Input type="text" placeholder="Enter your SUBGROUP NUMBER (e.g., 1,2,3)" />
                </Form.Item>

                <Form.Item
                    label="YOUR GROUP MONITOR NAME"
                    name="monitorName"
                    rules={[{ required: true, message: "Please enter your GROUP MONITOR NAME" }]}
                >
                    <Input type="text" placeholder="Enter your GROUP MONITOR NAME (e.g., JOHN)" />
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
                        COMPLETE STUDENTS SIGNUP
                    </Button>
                </Form.Item>
            </Form>
            
            {student && (
                <div style={{
                    marginTop: 20,
                    padding: 10,
                    background: '#1d1a1aff',
                    maxWidth: 400,
                    margin: '20px auto'
                }}>
                    <h3>Student Profile Completed Successfully!</h3>
                    <p><strong>ID:</strong> {student.id}</p>
                    <p><strong>Name:</strong> {student.name}</p>
                    <p><strong>Email:</strong> {student.email}</p>
                    <p><strong>Address:</strong> {student.address}</p>
                    <p><strong>Passport Number:</strong> {student.passportNumber || 'Not provided'}</p>
                    <p><strong>Student ID:</strong> {student.studentIdCardNumber || 'Not provided'}</p>
                    <p><strong>Current Year:</strong> {student.currentYear || 'Not provided'}</p>
                    <p><strong>Group Number:</strong> {student.groupNumber || 'Not provided'}</p>
                    <p><strong>Subgroup Number:</strong> {student.subGroupNumber || 'Not provided'}</p>
                    <p><strong>Monitor Name:</strong> {student.monitorName || 'Not provided'}</p>
                    <p><strong>Roles:</strong> {student.rolesList?.map(role => 
                        typeof role === 'object' ? role.roles : role
                    ).join(', ')}</p>
                </div>
            )}
            
            <div style={{ 
                marginTop: 20, 
                padding: 10, 
                background: '#fff3cd',
                maxWidth: 400,
                margin: '20px auto',
                fontSize: '12px'
            }}>
                <h4>Debug Info:</h4>
                <p><strong>User ID from localStorage:</strong> {studentDetails || 'Not found'}</p>
                <p><strong>Student data loaded:</strong> {student ? 'Yes' : 'No'}</p>
                {student && <p><strong>Student ID:</strong> {student.id}</p>}
            </div>
        </div>
    )
}

export default StudentsSignup;