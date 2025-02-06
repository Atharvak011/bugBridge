import React, { useEffect, useState, useContext } from "react";
import axios from "axios";
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from "recharts";
import { UserContext } from "../context/UserContext";

const Dashboard = () => {
  const { user } = useContext(UserContext);
  const [bugs, setBugs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchBugs = async () => {
    // if (!user) return;
    setLoading(true);
    try {
      // const response = await axios.get(`http://localhost:8080/api/bugs/allBugs?user_id=${user.id}`);
      const response = await axios.get(`http://localhost:8080/api/bugs/allBugs?user_id=14`);
      setBugs(response.data.bugList || []);
      setError(null);
    } catch (err) {
      setError("Failed to fetch bugs");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBugs();
  }, [user]);

  if (loading) return <div className="text-center mt-10">Loading bugs...</div>;
  if (error) return <div className="text-center mt-10 text-red-500">{ error }</div>;

  // Process data for Pie Charts
  const statusCount = bugs.reduce((acc, bug) => {
    acc[bug.status] = (acc[bug.status] || 0) + 1;
    return acc;
  }, {});

  const priorityCount = bugs.reduce((acc, bug) => {
    acc[bug.priority] = (acc[bug.priority] || 0) + 1;
    return acc;
  }, {});

  const statusData = Object.entries(statusCount).map(([key, value]) => ({ name: key, value }));
  const priorityData = Object.entries(priorityCount).map(([key, value]) => ({ name: key, value }));

  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#A28DFF"];

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold text-center mb-6">Dashboard</h1>

      <div className="flex justify-center mb-6">
        <button
          onClick={ fetchBugs }
          className="bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600 transition"
        >
          Refresh Data
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Pie Chart - Bugs by Status */ }
        <div className="bg-white p-4 shadow-md rounded-lg">
          <h2 className="text-xl font-semibold text-center mb-4">Bugs by Status</h2>
          <ResponsiveContainer width="100%" height={ 300 }>
            <PieChart>
              <Pie data={ statusData } cx="50%" cy="50%" outerRadius={ 100 } fill="#8884d8" label>
                { statusData.map((entry, index) => (
                  <Cell key={ `cell-${index}` } fill={ COLORS[index % COLORS.length] } />
                )) }
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>

        {/* Pie Chart - Bugs by Priority */ }
        <div className="bg-white p-4 shadow-md rounded-lg">
          <h2 className="text-xl font-semibold text-center mb-4">Bugs by Priority</h2>
          <ResponsiveContainer width="100%" height={ 300 }>
            <PieChart>
              <Pie data={ priorityData } cx="50%" cy="50%" outerRadius={ 100 } fill="#82ca9d" label>
                { priorityData.map((entry, index) => (
                  <Cell key={ `cell-${index}` } fill={ COLORS[index % COLORS.length] } />
                )) }
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
