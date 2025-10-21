import { useEffect, useState } from "react";
import { GetUserById } from "../apis";

function GetById() {
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const handleUserById = async (id) => {
      try {
        const response = await GetUserById(id);
        console.log("USER:", response.data);
        setUser(response);
      } catch (err) {
        console.log("Error:", err.message);
        setError("User not found or failed to fetch data");
      }
    };

    // ✅ Call with a valid ID (example: 1)
    handleUserById(1);
  }, []);

  return (
    <>
      <h1 style={{ textAlign: "center" }}>USER BY ID</h1>

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
          <h3>User Found</h3>
          <p><strong>Name:</strong> {user.name}</p>
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
