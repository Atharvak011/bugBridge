import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../context/UserContext";

const AddBug = () => {
  const { user } = useContext(UserContext);
  const navigate = useNavigate();
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState("low");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const response = await axios.post("http://localhost:8080/api/bugs/create", {
        reportedBy: 14,
        // reportedBy: user.id,
        description,
        priority,
      });
      if (response.status === 201) {
        navigate("/allBugs");
      }
    } catch (err) {
      setError("Failed to create bug. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 max-w-lg mx-auto">
      <h2 className="text-xl font-bold mb-4">Report a New Bug</h2>
      { error && <p className="text-red-500">{ error }</p> }
      <form onSubmit={ handleSubmit } className="space-y-4">
        <textarea
          className="w-full p-2 border rounded"
          placeholder="Bug Description"
          value={ description }
          onChange={ (e) => setDescription(e.target.value) }
          required
        />
        <select
          className="w-full p-2 border rounded"
          value={ priority }
          onChange={ (e) => setPriority(e.target.value) }
        >
          <option value="low">Low</option>
          <option value="medium">Medium</option>
          <option value="high">High</option>
        </select>
        <button
          type="submit"
          className="bg-blue-500 text-white p-2 rounded w-full"
          disabled={ loading }
        >
          { loading ? "Submitting..." : "Submit Bug" }
        </button>
      </form>
    </div>
  );
};
export default AddBug;