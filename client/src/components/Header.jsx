import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { UserContext } from '../context/UserContext';

const Header = () => {
  const { user, logout } = useContext(UserContext); // Access user context
  const navigate = useNavigate();
  const [darkMode, setDarkMode] = useState(localStorage.getItem('theme') === 'dark');
  const [notifications, setNotifications] = useState([]);
  const [showNotifications, setShowNotifications] = useState(false);

  // Toggle dark mode
  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
  };

  // Update theme in localStorage and apply dark mode styles
  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add('dark');
      localStorage.setItem('theme', 'dark');
    } else {
      document.documentElement.classList.remove('dark');
      localStorage.setItem('theme', 'light');
    }
  }, [darkMode]);

  // useEffect(() => {
  //   const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
  //   setDarkMode(localStorage.getItem('theme') === 'dark' || prefersDark);
  // }, []);


  // Fetch notifications
  useEffect(() => {
    axios
      .get('http://localhost:8080/api/notifications', {
        // EDIT HERE UNCOMMENT THIS CODE  ---------------------------------------------------------------
        // headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      })
      .then((res) => setNotifications(res.data))
      .catch((err) => console.error('Error fetching notifications', err));
  }, []);

  // Handle logout
  const handleLogout = () => {
    logout(); // Clear user context
    // EDIT HERE UNCOMMENT THIS CODE  ---------------------------------------------------------------
    // localStorage.removeItem('token'); // Clear JWT Token
    navigate('/login'); // Redirect to login page
  };

  // const handleDashboard = () => {
  //   navigate('/dashboard');
  // }

  return (
    <header className="bg-gray-200 dark:bg-gray-900 text-gray-900 dark:text-white px-6 py-4 flex justify-between items-center shadow-md">
      {/* Logo / Brand */ }
      <h1 className="text-xl font-bold">BugBridge</h1>
      {/* Logout Button */ }

      {/* Navigation Icons */ }
      <div className="flex items-center space-x-6">
        {/* Dark Mode Toggle */ }
        <button
          onClick={ toggleDarkMode }
          className="p-2 bg-gray-300 dark:bg-gray-700 rounded-lg"
        >
          { darkMode ? '🌙' : '☀️' }
        </button>

        {/* Notification Icon (Popup Trigger) */ }
        <div className="relative">
          <button
            onClick={ () => setShowNotifications(!showNotifications) }
            className="relative p-2 bg-gray-300 dark:bg-gray-700 rounded-lg"
          >
            🔔
            { notifications.length > 0 && (
              <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs w-4 h-4 rounded-full flex items-center justify-center">
                { notifications.length }
              </span>
            ) }
          </button>

          {/* Notifications Popup */ }
          { showNotifications && (
            <div className="absolute right-0 mt-2 w-64 bg-white dark:bg-gray-800 border border-gray-300 dark:border-gray-600 rounded-lg shadow-lg p-4">
              <h3 className="text-sm font-semibold">Notifications</h3>
              { notifications.length === 0 ? (
                <p className="text-xs text-gray-500">No new notifications</p>
              ) : (
                notifications.map((notif, index) => (
                  <div key={ index } className="text-sm py-1 border-b last:border-none">
                    { notif.message }
                  </div>
                ))
              ) }
            </div>
          ) }
        </div>


        {/* Profile Button */ }
        <button
          onClick={ () => navigate('/profile') }
          className="p-2 bg-gray-300 dark:bg-gray-700 rounded-lg"
        >
          👤
        </button>

        {/* Logout Button */ }
        <button
          onClick={ handleLogout }
          className="px-4 py-2 bg-red-500 text-white rounded-lg"
        >
          Logout
        </button>
      </div>
    </header>
  );
};

export default Header;
