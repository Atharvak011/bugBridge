import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../context/UserContext";

const AllBugs = () => {
  const { user } = useContext(UserContext);
  const [bugs, setBugs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBugs = async () => {
      try {
        let id = 14;
        const response = await axios.get(
          // `http://localhost:8080/api/bugs/allBugs?user_id=${user.id}`
          `http://localhost:8080/api/bugs/allBugs?user_id=${id}`
        );
        setBugs(response.data.bugList);
      } catch (err) {
        setError("Failed to fetch bugs.");
      } finally {
        setLoading(false);
      }
    };
    fetchBugs();
    // }, [user.id]);
  }, [id]);

  if (loading) return <p>Loading bugs...</p>;
  if (error) return <p className="text-red-500">{ error }</p>;

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <h2 className="text-xl font-bold mb-4">All Bugs Assigned to You</h2>
      { bugs.length === 0 ? (
        <p>No bugs assigned to you.</p>
      ) : (
        <ul className="space-y-2">
          { bugs.map((bug) => (
            <li
              key={ bug.id }
              className="p-4 border rounded bg-gray-100 hover:bg-gray-200"
            >
              <p className="font-semibold">{ bug.description }</p>
              <p>Status: { bug.status }</p>
              <p>Priority: { bug.priority }</p>
            </li>
          )) }
        </ul>
      ) }
    </div>
  );
};

export default AllBugs;