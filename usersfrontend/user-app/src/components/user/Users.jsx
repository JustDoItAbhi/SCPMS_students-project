import { useEffect, useState } from "react";
import { GetAllUsers } from "../apis";

function Users() {
  const [getUsers, setUsers] = useState([]);

  const getData = async () => {
    try {
      const user = await GetAllUsers();
      console.log("ALL USERS FROM USERS", user);
      setUsers(user);
    } catch (err) {
      console.log(err.message);
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div style={{ position: "relative", zIndex: 10, color: "white", padding: "20px" }}>
      <h1>USERS</h1>
      <div
        style={{
          display: "flex",
          gap: "10px",
          flexWrap: "wrap",
        }}
      >
        {getUsers.map((items, index) => (
          <div key={index}>
            <ul
              style={{
                display: "flex",
                flexDirection: "column",
                width: 200,
                height: "120px",
                overflow: "hidden",
                background: "rgba(128,128,128,0.5)", // semi-transparent grey
                padding: "10px",
                borderRadius: "8px",
              }}
            >
              <li
                style={{
                  background: "hsla(120, 100%, 50%, 0.3)", // semi-transparent green
                  fontSize: "18px",
                  padding: "2px 5px",
                  borderRadius: "4px",
                }}
              >
                {items.name}
              </li>
              <li>{items.email}</li>
              <li>{items.password}</li>
              <li>{items.address}</li>
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Users;
