import "./HomeCss.css"
import { useNavigate } from "react-router-dom"
function HomePage() {
    const navigate = useNavigate();
    localStorage.removeItem("access_token")
    localStorage.removeItem("studentId")
    localStorage.removeItem("subject")
    localStorage.removeItem("user")
    localStorage.removeItem("userId")
    localStorage.removeItem("teacher")   
    localStorage.removeItem("studentAndSubjectId")
    localStorage.removeItem("teacherSubject")
    localStorage.removeItem("userEmail")
    const handleClick = () => {
        navigate("/Login")
    }
    return (
        <div className="main-container">

            <div>
                <div className="topic">
                    <button onClick={handleClick} className="buttonMenu">
                        MENU
                    </button>
                    <h1 className="scpms">
                        <span>S </span>
                        <span>C </span>
                        <span>P </span>
                        <span>M </span>
                        <span>S </span>
                        <br />
                        <span className="titles">Student Conference Participation Management System </span>    </h1>
                </div>

                <h2 className="objective">Overview</h2>
                <p>The Student Conference Participation Management System allows students to register for an academic conference by choosing a
                    subject and submitting a topic under that subject. Each topic must be verified and approved or rejected by a teacher.
                    The system ensures secure authentication, role-based authorization, and sends email notifications at each stage (submission, approval, rejection).
                </p>

                <video
                    className="videointro"
                    src="/hi.mp4"
                    autoPlay
                    muted
                    loop
                    playsInline
                ></video>

                <ul>
                    <h2 className="objective">Objectives</h2>
                    <li>Centralized Platform – Students can register, submit topics, and track their conference participation all in one place.</li>
                    <li>Efficient Topic Approval – Teachers can easily review, approve, or reject submissions, saving time and effort.</li>
                    <li>Secure Access Control – Role-based authentication ensures students and teachers only access relevant data.</li>
                    <li>Automated Notifications – Students receive instant email updates for submission, approval, or rejection statuses.</li>
                    <li>Record Keeping & Transparency – Maintains a history of approved and rejected topics for reporting and accountability.</li>
                </ul>
                <video
                    className="videointro"
                    src="/v1.mp4"
                    autoPlay
                    muted
                    loop
                    playsInline
                ></video>
                <br />
                <div>
                    <p>
                        Our Student Conference Participation Management System (SCPMS) makes conference participation effortless and rewarding. Students can easily submit topics,
                        track approvals, and receive instant notifications - all in one secure platform. Teachers can quickly review and approve submissions, ensuring a smooth workflow.
                        With transparent record-keeping and role-based access, SCPMS promotes fairness, accountability, and active academic engagement. By simplifying the entire process,
                        we help students focus on learning and presenting their ideas, making conference participation more organized, efficient, and impactful for everyone.
                    </p>

                </div>
            </div>
            <footer className="contacts">
                <div>
                    <h2>Contact Us</h2>
                    <p>
                        <strong>Address:</strong> 12 Universtistsks Street, City Uzhhorod, Country Uzhhorod
                    </p>
                    <p>
                        <strong>Phone:</strong> +1 234 567 890
                    </p>
                    <p>
                        <strong>Email:</strong> info@scpms.edu
                    </p>
                </div>
            </footer>
        </div>
    )
}
export default HomePage;