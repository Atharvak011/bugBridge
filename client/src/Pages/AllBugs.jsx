import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { UserContext } from "../context/UserContext";

const AllBugs = () => {
  const { user } = useContext(UserContext);
  const [bugs, setBugs] = useState([]);
  const [developers, setDevelopers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBugs = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/bugs/allBugs?user_id=${user.id}`);
        setBugs(response.data.bugList);
      } catch (err) {
        setError("Failed to fetch bugs.");
      } finally {
        setLoading(false);
      }
    };

    const fetchDevelopers = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/users/developer");
        setDevelopers(response.data.userList);

      } catch (err) {
        console.error("Failed to fetch developers.");
      }
    };

    fetchBugs();
    if (user.role === "TESTER") fetchDevelopers();
  }, [user.id, user.role]);

  const handleDelete = async (bugId) => {
    // softDelete in server side
    await axios.put(`http://localhost:8080/api/bugs/delete/${bugId}`);
    setBugs(bugs.map(bug => bug.id === bugId ? { ...bug, isDeleted: true } : bug));
  };

  const handleAssign = async (bugId, developerId) => {
    try {
      const response = await axios.patch(`http://localhost:8080/api/bugs/${bugId}`, {
        assignedTo: developerId,
      });

      // Update state to reflect the new assignment
      setBugs(bugs.map(bug => bug.id === bugId ? { ...bug, assignedTo: developerId } : bug));

      alert("Bug assigned successfully!");
    } catch (error) {
      console.error("Failed to assign bug:", error);
      alert("Failed to assign bug.");
    }
  };

  const handlePriorityChange = async (bugId, priority) => {
    await axios.patch(`http://localhost:8080/api/bugs/${bugId}`, { priority });
  };

  const handleStatusChange = async (bugId, status) => {
    const resolvedAt = status === "RESOLVED" ? new Date().toISOString() : null;
    await axios.patch(`http://localhost:8080/api/bugs/${bugId}`, { status, resolvedAt });
  };

  if (loading) return <p>Loading bugs...</p>;
  if (error) return <p className="text-red-500">{ error }</p>;

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h2 className="text-xl font-bold mb-4">Bugs</h2>
      { bugs.length === 0 ? (
        <p>No bugs found.</p>
      ) : (
        <ul className="space-y-4">
          { bugs.map((bug) => (
            <li key={ bug.id } className="p-4 border rounded bg-gray-100">
              <p className="font-semibold">{ bug.description }</p>
              <p>Status: { bug.status }</p>
              <p>Priority: { bug.priority }</p>

              { user.role === "TESTER" && (
                <div className="mt-2">
                  <button onClick={ () => handleDelete(bug.id) } className="bg-red-500 text-white px-2 py-1 rounded mr-2">Delete</button>
                  <select onChange={ (e) => handleAssign(bug.id, e.target.value) }>
                    <option value="">Assign Developer</option>
                    { developers.map(dev => (
                      <option key={ dev.id } value={ dev.id }>
                        { dev.name }
                      </option>
                    )) }
                  </select>
                  <select onChange={ (e) => handlePriorityChange(bug.id, e.target.value) } className="border p-1 ml-2">
                    <option>Change Priority</option>
                    <option value="LOW">LOW</option>
                    <option value="MEDIUM">MEDIUM</option>
                    <option value="HIGH">HIGH</option>
                  </select>
                </div>
              ) }

              { user.role === "DEVELOPER" && (
                <div className="mt-2">
                  <textarea placeholder="Write resolution report..." className="border p-1 w-full"></textarea>
                  <select onChange={ (e) => handleStatusChange(bug.id, e.target.value) } className="border p-1 mt-2">
                    <option>Change Status</option>
                    <option value="OPEN">OPEN</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="RESOLVED">RESOLVED</option>
                  </select>
                </div>
              ) }
            </li>
          )) }
        </ul>
      ) }
    </div>
  );
};

export default AllBugs;