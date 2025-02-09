import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";


export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const loadUser = () => {
      const storedUser = localStorage.getItem("user");

      try {
        if (storedUser) {

          const parsedUser = JSON.parse(storedUser);
          setUser(parsedUser);
        }
      } catch (error) {
        console.error("Failed to parse user data from localStorage:", error);

        localStorage.removeItem("user");
      }
      setLoading(false);
    };
    loadUser();
  }, []);

  useEffect(() => {
    if (user) {
      console.log("User updated:", user);
    }
  }, [user]);


  const updateUser = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
  };


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
