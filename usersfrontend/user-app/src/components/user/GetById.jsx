import { useEffect, useState } from "react";
import { GetUserById } from "../apis";
import { useLocation, useParams, useSearchParams } from "react-router-dom";
import { useAuth } from "../Login Component/UseAuth";

function GetById() {
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");
  const[id,setId]=useState("");
    // const { id } = useParams();
  // const id = useSearchParams.get('id');
  //  const id = location.state?.id;

//  const { user: currentUser } = useAuth();
  
//   useEffect(() => {
//     const handleCurrentUser = async () => {
//       try {
//         // Call endpoint that returns current user without needing ID
//         const response = await axios.get('/api/user/me', {
//           headers: {
//             Authorization: `Bearer ${localStorage.getItem('access_token')}`
//           }
//         });
//         console.log("Current User:", response.data);
//         setUser(response.data);
//       } catch (err) {
//         console.log("Error:", err.message);
//         setError("Failed to fetch user data");
//       }
//     };

//     handleCurrentUser();
//   }, []);


  useEffect(() => {
    const handleUserById = async (Id) => {
      try {
        const response = await GetUserById(Id);
        console.log("USER:", response);
        setId(response.id);
        setUser(response);
      } catch (err) {
        console.log("Error:", err.message);
        setError("User not found or failed to fetch data");
      }
    };

      handleUserById(1);
 
   
  }, []);

  return (
    <>
      <h1 style={{ textAlign: "center" }}>WELCOME</h1>

      {error && (
        <p style={{ color: "red", textAlign: "center", marginTop: 10 }}>
          {error}
        </p>
      )}

      {user && (
        <div
          style={{
            marginTop: 20,
            padding: 10,
            background: "#a79494ff",
            maxWidth: 400,
            margin: "20px auto",
            borderRadius: "8px",
          }}
        >
          <h2>{user.name}</h2>
          <p><strong>Email:</strong> {user.email}</p>
          <p><strong>Address:</strong> {user.address}</p>
          <p>
            <strong>Roles:</strong> {user.rolesList?.join(", ") || "No roles"}
          </p>
        </div>
      )}
    </>
  );
}

export default GetById;
