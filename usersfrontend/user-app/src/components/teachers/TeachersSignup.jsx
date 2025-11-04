import React, { useState, useEffect } from 'react';
import { Card, List, Button, Typography, Alert, Spin, Form, Input, message } from 'antd';
import { BookOutlined, CheckCircleOutlined, UserOutlined } from '@ant-design/icons';
import { DeleteTeacherByID, DeleteUser, GetSubjectByYear, getTeacherByID } from "../apis";
import './TeachersSignup.css';

const { Title, Text } = Typography;

function TeachersSignup() {
    const [teacher, setTeacher] = useState(null);
    const [selectedYear, setSelectedYear] = useState('');
    const [subjects, setSubjects] = useState([]);
    const [selectedSubject, setSelectedSubject] = useState('');
    const [loading, setLoading] = useState(false);
    const [messageApi, contextHolder] = message.useMessage();
    const [form] = Form.useForm();
    const userId = localStorage.getItem("userId");

    const years = ['1st', '2nd', '3rd', '4th', '5th', '6th', '7th'];
    
    // Mock subjects data - you can replace this with actual API call
    const yearSubjects = {
        '1st': ['ANATOMY', 'PHYSIOLOGY', 'BIOCHEMISTRY', 'BASIC MEDICAL SCIENCES'],
        '2nd': ['PATHOLOGY', 'PHARMACOLOGY', 'MICROBIOLOGY', 'FORENSIC MEDICINE'],
        '3rd': ['MEDICINE', 'SURGERY', 'PEDIATRICS', 'OBSTETRICS & GYNECOLOGY'],
        '4th': ['PSYCHIATRY', 'DERMATOLOGY', 'OPHTHALMOLOGY', 'ENT'],
        '5th': ['ORTHOPEDICS', 'RADIOLOGY', 'ANESTHESIOLOGY', 'COMMUNITY MEDICINE'],
        '6th': ['INTERNAL MEDICINE', 'GENERAL SURGERY', 'PEDIATRIC MEDICINE', 'EMERGENCY MEDICINE'],
        '7th': ['MEDICAL SPECIALTIES', 'SURGICAL SPECIALTIES', 'RESEARCH METHODOLOGY', 'CLINICAL ELECTIVES']
    };

// Replace the mock yearSubjects and fetchSubjectsByYear function with:
const fetchSubjectsByYear = async (year) => {
    setLoading(true);
    setSelectedYear(year);
    setSelectedSubject('');
    
    try {
        const response = await GetSubjectByYear(year);
        setSubjects(response.subjectsList || []);
        messageApi.success(`Loaded subjects for ${year} year`);
    } catch (error) {
        console.error('Error fetching subjects:', error);
        messageApi.error('Failed to fetch subjects');
        setSubjects([]);
    } finally {
        setLoading(false);
    }
};

const deleteTeacher=async()=>{
    try{
          const deleteFullUser=await DeleteUser(userId);
             console.log("USER DELTED",deleteFullUser);
             if(deleteFullUser){
                localStorage.removeItem("userId");
             }
        const response =await DeleteTeacherByID(userId);
        console.log(response);
        if(!response){
          
        }
    }catch(err){
        console.log(err.message);
    }
}
    // Handle subject selection
    const handleSubjectSelect = (subject) => {
        setSelectedSubject(subject);
        console.log("SUBJECT",subject);
localStorage.setItem('teacherSubject', subject);
        form.setFieldsValue({ teacherSubject: subject });
    };

    // Handle complete signup
    const handleCompleteSignup = async (values) => {
        try {
            console.log("📝 Completing teacher signup with:", values);
            console.log("👤 User ID:", userId);
            
            if (!userId) {
                messageApi.error("User ID not found. Please sign up first.");
                return;
            }

            if (!selectedSubject) {
                messageApi.warning("Please select a subject to teach");
                return;
            }

            setLoading(true);
            const subject=localStorage.getItem("teacherSubject")
            const response = await getTeacherByID(userId,subject);
            console.log("teacher added ", response);
            
            if (response) {
                console.log("✅ TEACHER SIGNUP COMPLETED:", response.data);
                setTeacher(response.data);
                messageApi.success("Teacher profile completed successfully!");
                
                // Store teacher info in localStorage
                localStorage.setItem('teacher', JSON.stringify(response.data));
                
            }
        } catch (err) {
            console.log("❌ Error completing teacher signup:", err.message);
            messageApi.error("Failed to complete teacher signup");
        } finally {
            setLoading(false);
        }
    };

    // Fetch existing teacher data
    const fetchTeacherData = async () => {
        try {
            if (userId) {
                console.log("✅ Ready to complete teacher signup for user:", userId);
                // You can add API call here to check if teacher already exists
            }
        } catch (err) {
            console.log("❌ Error fetching teacher data:", err.message);
        }
    };

    useEffect(() => {
        if (userId) {
            fetchTeacherData();
        } else {
            console.log("❌ No user ID found in localStorage");
            messageApi.error("Please complete user registration first");
        }
    }, [userId]);

    return (
        <div className="teachers-signup-container">
            {contextHolder}
            <Card className="teachers-signup-card">
                <Title level={2} className="page-title">
                    <UserOutlined /> Complete Teacher Profile
                </Title>
                <Button 
                onClick={deleteTeacher}>
                    DELETE PROFILE
                </Button>
                <Text className="subtitle">
                    Select your teaching year and subject to complete your profile
                </Text>

                {/* Year Selection */}
                <div className="year-selection">
                    <Text strong className="section-title">Select Teaching Year:</Text>
                    <div className="year-buttons">
                        {years.map(year => (
                            <Button
                                key={year}
                                type={selectedYear === year ? "primary" : "default"}
                                onClick={() => fetchSubjectsByYear(year)}
                                className="year-button"
                                size="large"
                            >
                                {year} Year
                            </Button>
                        ))}
                    </div>
                </div>

                {/* Subjects Selection */}
                {selectedYear && (
                    <Card 
                        title={
                            <div className="card-title">
                                <span>{selectedYear} Year Teaching Subjects</span>
                                <Text type="secondary">({subjects.length} subjects available)</Text>
                            </div>
                        } 
                        className="subjects-card"
                    >
                        {loading ? (
                            <div className="loading-container">
                                <Spin size="large" />
                                <Text>Loading subjects for {selectedYear} year...</Text>
                            </div>
                        ) : subjects.length > 0 ? (
                            <>
                                <List
                                    dataSource={subjects}
                                    renderItem={(subject, index) => (
                                        <List.Item 
                                            className={`subject-item ${selectedSubject === subject ? 'selected' : ''}`}
                                            onClick={() => handleSubjectSelect(subject)}
                                        >
                                            <div className="subject-content">
                                                <Text strong className="subject-name">
                                                    {index + 1}. {subject}
                                                </Text>
                                            </div>
                                            {selectedSubject === subject && (
                                                <CheckCircleOutlined className="selected-icon" />
                                            )}
                                        </List.Item>
                                    )}
                                    className="subjects-list"
                                />
                                
                                {/* Selected Subject Display */}
                                {selectedSubject && (
                                    <div className="selected-subject-info">
                                        <Alert
                                            message={`Selected: ${selectedSubject} for ${selectedYear} year`}
                                            type="success"
                                            showIcon
                                            className="selection-alert"
                                        />
                                    </div>
                                )}
                            </>
                        ) : (
                            <div className="no-subjects">
                                <Text type="secondary">No subjects available for {selectedYear} year</Text>
                            </div>
                        )}
                    </Card>
                )}

                {/* Additional Information Form */}
                <Form
                    form={form}
                    name="teacherSignup"
                    onFinish={handleCompleteSignup}
                    layout="vertical"
                    className="teacher-form"
                >
                    <Form.Item
                        label="Teaching Subject"
                        name="teacherSubject"
                        rules={[{ required: true, message: "Please select a subject" }]}
                    >
                        <Input 
                            type="text" 
                            placeholder="Select subject from above or enter manually" 
                            value={selectedSubject}
                            onChange={(e) => setSelectedSubject(e.target.value)}
                        />
                    </Form.Item>

                    <Form.Item
                        label="Qualifications"
                        name="qualifications"
                        rules={[{ required: true, message: "Please enter your qualifications" }]}
                    >
                        <Input.TextArea 
                            placeholder="Enter your qualifications (e.g., MD, MBBS, PhD, etc.)"
                            rows={3}
                        />
                    </Form.Item>

                    <Form.Item
                        label="Years of Experience"
                        name="experience"
                        rules={[{ required: true, message: "Please enter years of experience" }]}
                    >
                        <Input 
                            type="number" 
                            placeholder="Enter years of teaching experience" 
                            min="0"
                            max="50"
                        />
                    </Form.Item>

                    <Form.Item
                        label="Specialization"
                        name="specialization"
                    >
                        <Input 
                            type="text" 
                            placeholder="Enter your area of specialization (optional)" 
                        />
                    </Form.Item>

                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            loading={loading}
                            disabled={!selectedSubject}
                            icon={<CheckCircleOutlined />}
                            className="complete-button"
                            size="large"
                        >
                            Complete Teacher Profile
                        </Button>
                    </Form.Item>
                </Form>

                {/* Success Display */}
                {teacher && (
                    <Card 
                        title="Teacher Profile Completed Successfully!" 
                        className="success-card"
                        type="inner"
                    >
                        <div className="teacher-profile">
                            <div className="profile-item">
                                <Text strong>ID:</Text> {teacher.id}
                            </div>
                            <div className="profile-item">
                                <Text strong>Name:</Text> {teacher.name}
                            </div>
                            <div className="profile-item">
                                <Text strong>Email:</Text> {teacher.email}
                            </div>
                            <div className="profile-item">
                                <Text strong>Address:</Text> {teacher.address}
                            </div>
                            <div className="profile-item highlight">
                                <Text strong>Teaching Subject:</Text> {teacher.teacherSubject}
                            </div>
                            <div className="profile-item">
                                <Text strong>Teaching Year:</Text> {selectedYear}
                            </div>
                            <div className="profile-item">
                                <Text strong>Qualifications:</Text> {teacher.qualifications}
                            </div>
                            <div className="profile-item">
                                <Text strong>Experience:</Text> {teacher.experience} years
                            </div>
                            <div className="profile-item">
                                <Text strong>Specialization:</Text> {teacher.specialization || 'Not specified'}
                            </div>
                            <div className="profile-item">
                                <Text strong>Roles:</Text> {teacher.rolesList?.map(role => 
                                    typeof role === 'object' ? role.roles : role
                                ).join(', ')}
                            </div>
                        </div>
                    </Card>
                )}

                {/* Debug Info */}
                <Card title="Debug Information" className="debug-card" size="small">
                    <div className="debug-info">
                        <p><strong>User ID from localStorage:</strong> {userId || 'Not found'}</p>
                        <p><strong>Selected Year:</strong> {selectedYear || 'Not selected'}</p>
                        <p><strong>Selected Subject:</strong> {selectedSubject || 'Not selected'}</p>
                        <p><strong>Teacher data loaded:</strong> {teacher ? 'Yes' : 'No'}</p>
                    </div>
                </Card>
            </Card>
        </div>
    );
}

export default TeachersSignup;