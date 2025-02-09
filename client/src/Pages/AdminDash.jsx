import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { PieChart, Pie, Cell, ResponsiveContainer } from 'recharts';


const AdminDash = () => {
  const [userList, setUserList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    const fetchUserList = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/users/admin/allUsers');
        setUserList(response.data.userList);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching user list', error);
      }
    };

    fetchUserList();
  }, []);


  const handleDeleteUser = async (userId) => {
    try {
      const response = await axios.delete(`http://localhost:8080/api/users/admin/deleteUser/${userId}`);
      if (response.status === 200) {
        setUserList(userList.filter(user => user.id !== userId));
        alert('User deleted successfully');
      }
    } catch (error) {
      console.error('Error deleting user', error);
      alert('Error deleting user');
    }
  };


  const getRoleStats = () => {
    const roleCounts = { DEVELOPER: 0, TESTER: 0 };

    userList.forEach(user => {
      if (user.role === 'DEVELOPER') {
        roleCounts.DEVELOPER++;
      } else if (user.role === 'TESTER') {
        roleCounts.TESTER++;
      }
    });

    return [
      { name: 'Developers', value: roleCounts.DEVELOPER },
      { name: 'Testers', value: roleCounts.TESTER }
    ];
  };

  // Chart colors
  const COLORS = ['#0088FE', '#00C49F'];

  const roleCounts = getRoleStats();

  return (
    <div className="dashboard-container">
      { loading ? (
        <p>Loading...</p>
      ) : (
        <div className="dashboard-content">
          {/* Pie chart for Developer vs Tester */ }
          <div className="chart-container">
            <h3 className="text-2xl font-semibold text-gray-800 mb-4 mt-6">
              Admin Dashboard - User Roles
            </h3>

            <ResponsiveContainer width="100%" height={ 300 }>
              <PieChart>
                <Pie
                  data={ getRoleStats() }
                  dataKey="value"
                  nameKey="name"
                  outerRadius={ 120 }
                  fill="#8884d8"
                  label
                >
                  { getRoleStats().map((entry, index) => (
                    <Cell key={ `cell-${index}` } fill={ COLORS[index % COLORS.length] } />
                  )) }
                </Pie>
              </PieChart>
            </ResponsiveContainer>

            {/* Role counts below the chart */ }
            <div className="role-counts">
              <p><strong>Developers:</strong> { roleCounts[0].value }</p>
              <p><strong>Testers:</strong> { roleCounts[1].value }</p>
            </div>
          </div>

          {/* User Cards */ }
          <div className="user-cards">
            { userList.map((user) => (
              <div key={ user.id } className="user-card">
                <h4>{ user.name }</h4>
                <p>Email: { user.email }</p>
                <p>Role: { user.role }</p>
                <button onClick={ () => handleDeleteUser(user.id) }>Delete User</button>
              </div>
            )) }
          </div>
        </div>
      ) }
    </div>
  );
};

export default AdminDash;
