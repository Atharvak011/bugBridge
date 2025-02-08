// import { createContext, useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";

// // Dummy user data
// const dummyUser = {
//   id: 14,
//   email: "ankit@gmail.com",
//   name: "Ankit Chaudhary",
//   role: "TESTER",
//   // role: "DEVELOPER",
// };

// // Create and export UserContext
// export const UserContext = createContext();

// export const UserProvider = ({ children }) => {
//   const [user, setUser] = useState(null);
//   const [loading, setLoading] = useState(true);
//   const navigate = useNavigate();

//   useEffect(() => {
//     const loadUser = () => {
//       const storedUser = localStorage.getItem("user");

//       try {
//         if (storedUser) {
//           // Attempt to parse the stored user data
//           const parsedUser = JSON.parse(storedUser);
//           setUser(parsedUser);
//         }
//         else {
//           // If no user is stored, set the dummy user
//           setUser(dummyUser);
//           localStorage.setItem("user", JSON.stringify(dummyUser));
//         }
//       } catch (error) {
//         console.error("Failed to parse user data from localStorage:", error);
//         // Clear corrupted data and set the dummy user
//         localStorage.removeItem("user");
//         setUser(dummyUser);
//         localStorage.setItem("user", JSON.stringify(dummyUser));
//       }
//       setLoading(false);
//     };
//     loadUser();
//   }, []);

//   // Log user updates for debugging
//   useEffect(() => {
//     console.log("User updated:", user);
//   }, [user]);

//   // Update user state and local storage
//   const updateUser = (userData) => {
//     setUser(userData);
//     localStorage.setItem("user", JSON.stringify(userData));
//   };

//   // Handle user logout
//   const handleLogout = () => {
//     localStorage.removeItem("user");
//     setUser(null);
//     navigate("/login");
//   };

//   // Provide user context to children
//   return (
//     <UserContext.Provider value={ { user, updateUser, logout: handleLogout, loading } }>
//       { children }
//     </UserContext.Provider>
//   );
// };


// ABOVE WORKING




// import { createContext, useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";

// // Create UserContext
// export const UserContext = createContext(null);

// const UserProvider = ({ children }) => {
//   const [user, setUser] = useState(null);
//   const [loading, setLoading] = useState(true);
//   const navigate = useNavigate();

//   useEffect(() => {
//     const loadUser = () => {
//       const storedUser = localStorage.getItem("user");
//       if (storedUser) {
//         setUser(JSON.parse(storedUser));
//       }
//       setLoading(false);
//     };
//     loadUser();
//   }, []);

//   useEffect(() => {
//     console.log("User updated:", user);
//   }, [user]);

//   const updateUser = (userData) => {
//     setUser(userData);
//     localStorage.setItem("user", JSON.stringify(userData));
//   };

//   const handleLogout = () => {
//     localStorage.removeItem("user");
//     setUser(null);
//     navigate("/login");
//   };

//   return (
//     <UserContext.Provider value={ { user, updateUser, logout: handleLogout, loading } }>
//       { children }
//     </UserContext.Provider>
//   );
// };

// export default UserProvider;




// NEW WORKING

import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

// Create and export UserContext
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
          // Attempt to parse the stored user data
          const parsedUser = JSON.parse(storedUser);
          setUser(parsedUser);
        }
      } catch (error) {
        console.error("Failed to parse user data from localStorage:", error);
        // Clear corrupted data if parsing fails
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

  // Update user state and local storage
  const updateUser = (userData) => {
    setUser(userData);
    localStorage.setItem("user", JSON.stringify(userData));
  };

  // Handle user logout
  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
  };

  // Provide user context to children
  return (
    <UserContext.Provider value={ { user, updateUser, logout: handleLogout, loading } }>
      { children }
    </UserContext.Provider>
  );
};
