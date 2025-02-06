/*
import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate(); // Redirect users on logout

  useEffect(() => {
    const loadUser = async () => {
      const token = localStorage.getItem("token");
      const storedUser = localStorage.getItem("user");

      if (token && storedUser) {
        try {
          const parsedUser = JSON.parse(storedUser);

          // Validate the token by making an API request
          const res = await axios.get("http://localhost:8080/api/users/profile", {
            headers: { Authorization: `Bearer ${token}` },
          });

          if (res.data) {
            setUser(parsedUser);
          } else {
            handleLogout(); // Clear session if invalid
          }
        } catch (error) {
          console.error("Session expired or invalid token.");
          handleLogout();
        }
      }

      setLoading(false);
    };

    loadUser();
  }, []);

  // Update user and store in localStorage
  const updateUser = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
  };

  // Logout function
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login"); // Redirect to login on logout
  };

  return (
    <UserContext.Provider value={ { user, updateUser, logout: handleLogout, loading } }>
      { children }
    </UserContext.Provider>
  );
};
*/

//--------------------------------------------------------------


import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate(); // Redirect users on logout

  useEffect(() => {
    const loadUser = async () => {
      // Since there's no JWT auth, just check local storage for user
      const storedUser = localStorage.getItem("user");

      if (storedUser) {
        setUser(JSON.parse(storedUser));
      }

      setLoading(false);
    };

    loadUser();
  }, []);

  // Update user and store in localStorage
  const updateUser = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
  };

  // Logout function (Remove only user, since token isn't used)
  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
  };

  return (
    <UserContext.Provider value={ { user, updateUser, logout: handleLogout, loading } }>
      { children }
    </UserContext.Provider>
  );
};
