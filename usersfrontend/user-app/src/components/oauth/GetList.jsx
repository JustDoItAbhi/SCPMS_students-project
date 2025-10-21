import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const GetList = () => {
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("access_token");
      console.log("5TH GETLIST REQUEST",token)
    if (!token) {
      navigate("/login");
      return;
    }

    axios
      .get("http://localhost:8080/api/user/", {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      })
      .then((res) => {
        setUsers(res.data);
      })
      .catch((err) => {
        console.error("Error fetching users:", err);
        if (err.response?.status === 401) {
          localStorage.removeItem("access_token");
          navigate("/login");
        }
      });
  }, [navigate]);

  return (
    <div>
      <h2>All Users</h2>
      <ul>
        {users.map((user, index) => (
          <li key={index}>
            {user.Id}: {user.name} ({user.email})
          </li>
        ))}
      </ul>
    </div>
  );
};

export default GetList;