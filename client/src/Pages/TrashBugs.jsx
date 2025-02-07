import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { UserContext } from "../context/UserContext";
import { USERURL, BUGURL } from "../config";

const bugUrl = BUGURL;
const userUrl = USERURL;

const TrashBugs = () => {
  const { user } = useContext(UserContext);
  const [bugs, setBugs] = useState([]);
  const [developers, setDevelopers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingBug, setEditingBug] = useState(null);
  const [updatingBug, setUpdatingBug] = useState(null);
  const [deletingBug, setDeletingBug] = useState(null);
  const [updateStatus, setUpdateStatus] = useState({});
  const [filters, setFilters] = useState({ priority: "", status: "", assignedTo: "", dateReported: "" });
  const [sortOrder, setSortOrder] = useState("asc");

  useEffect(() => {
    const fetchBugs = async () => {
      try {
        const response = await axios.get(`${bugUrl}/allBugs`);
        setBugs(response.data.bugList);
      } catch (err) {
        setError("Failed to fetch bugs.");
      } finally {
        setLoading(false);
      }
    };

    const fetchDevelopers = async () => {
      try {
        const response = await axios.get(`${userUrl}/developer`);
        setDevelopers(response.data.userList);
      } catch (err) {
        console.error("Failed to fetch developers.");
      }
    };

    fetchBugs();
    if (user.role === "TESTER") fetchDevelopers();
  }, [user.id, user.role]);

  const handleUpdate = async (bugId, updateData) => {
    setUpdatingBug(bugId);
    setUpdateStatus((prev) => ({ ...prev, [bugId]: null }));

    try {
      await axios.patch(`${bugUrl}/${bugId}`, updateData);
      setBugs((prev) =>
        prev.map((bug) => (bug.id === bugId ? { ...bug, ...updateData } : bug))
      );
      setUpdateStatus((prev) => ({ ...prev, [bugId]: "success" }));
    } catch (error) {
      setUpdateStatus((prev) => ({ ...prev, [bugId]: "error" }));
    } finally {
      setUpdatingBug(null);
      setTimeout(() => {
        setUpdateStatus((prev) => ({ ...prev, [bugId]: null }));
      }, 1000);
    }
  };

  const handleDelete = async (bugId) => {
    setDeletingBug(bugId);
    setTimeout(async () => {
      await axios.delete(`${bugUrl}/${bugId}`);
      setBugs((prev) => prev.filter((bug) => bug.id !== bugId));
      setDeletingBug(null);
    }, 600);
  };

  const handleSort = () => {
    const sortedBugs = [...bugs].sort((a, b) => {
      if (sortOrder === "asc") return a.priority.localeCompare(b.priority);
      return b.priority.localeCompare(a.priority);
    });
    setBugs(sortedBugs);
    setSortOrder(sortOrder === "asc" ? "desc" : "asc");
  };

  const filteredBugs = bugs.filter(
    (bug) =>
      bug.isDeleted &&
      (!filters.priority || bug.priority === filters.priority) &&
      (!filters.status || bug.status === filters.status) &&
      (!filters.assignedTo || String(bug.assignedTo) === filters.assignedTo) &&
      (!filters.dateReported || bug.dateReported === filters.dateReported)
  );

  if (loading) return <p>Loading bugs...</p>;
  if (error) return <p className="text-red-500">{ error }</p>;
  // { `p-6 border rounded bg-gray-100 shadow-lg transition-all duration-500` }
  return (
    <div className="p-6 max-w-5xl mx-auto">
      <h2 className="text-2xl font-bold mb-6">Trash Bugs</h2>
      <button onClick={ handleSort } className="mb-4 p-2 bg-blue-500 text-white rounded">
        Sort by Priority ({ sortOrder === "asc" ? "⬆️" : "⬇️" })
      </button>
      <div className="grid grid-cols-1 gap-6">
        { filteredBugs.map((bug) => (
          <div key={ bug.id } className={ `p-6 border rounded bg-gray-100 shadow-lg transition-all duration-500 
            ${updatingBug === bug.id ? "animate-shine" : ""}
            ${deletingBug === bug.id ? "disintegrate" : ""}
            ${updateStatus[bug.id] === "success" ? "success-glow" : ""}
            ${updateStatus[bug.id] === "error" ? "error-glow" : ""}` }>
            { editingBug === bug.id ? (
              <textarea
                className="w-full p-2 border rounded"
                defaultValue={ bug.description }
                onBlur={ (e) => {
                  handleUpdate(bug.id, { description: e.target.value });
                  setEditingBug(null);
                } }
              />
            ) : (
              <p className="font-semibold cursor-pointer" onClick={ () => setEditingBug(bug.id) }>
                { bug.description }
              </p>
            ) }
            <p>Status: { bug.status }</p>
            <p>Priority: { bug.priority }</p>
            <select onChange={ (e) => handleUpdate(bug.id, { assignedTo: e.target.value }) } className="border p-2 rounded">
              <option value="">Assign Developer</option>
              { developers.map((dev) => (
                <option key={ dev.id } value={ dev.id }>{ dev.name }</option>
              )) }
            </select>
            <select onChange={ (e) => handleUpdate(bug.id, { priority: e.target.value }) } className="border p-2 rounded">
              <option>Change Priority</option>
              <option value="LOW">LOW</option>
              <option value="MEDIUM">MEDIUM</option>
              <option value="HIGH">HIGH</option>
            </select>
            <select onChange={ (e) => handleUpdate(bug.id, { status: e.target.value }) } className="border p-2 rounded">
              <option>Change Status</option>
              <option value="OPEN">OPEN</option>
              <option value="IN_PROGRESS">IN PROGRESS</option>
              <option value="RESOLVED">RESOLVED</option>
            </select>
            <button onClick={ () => handleUpdate(bug.id, { isDeleted: !bug.isDeleted }) } className="bg-green-500 text-white px-3 py-2 rounded transition-all duration-300 hover:bg-sky-700">
              Recover
            </button>
          </div>
        )) }
      </div>
    </div>
  );
};

export default TrashBugs;
