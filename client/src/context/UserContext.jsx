// import { createContext, useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";

// export const UserContext = createContext();

// export const UserProvider = ({ children }) => {
//   const [user, setUser] = useState(null);
//   const [loading, setLoading] = useState(true);
//   const navigate = useNavigate();

//   useEffect(() => {
//     const loadUser = async () => {
//       const storedUser = localStorage.getItem("user");

//       if (storedUser) {
//         setUser(JSON.parse(storedUser));
//       } else {
//         // Temporary user object for development
//         const tempUser = {
//           id: 14,
//           email: "ankit@gmail.com",
//           name: "Ankit",
//           role: "TESTER",
//         };
//         setUser(tempUser);
//         localStorage.setItem("user", JSON.stringify(tempUser));
//         // const tempUser2 = {
//         //   id: 15,
//         //   email: "gunnu@gmail.com",
//         //   name: "Gunnu Chaudhary",
//         //   role: "DEVELOPER",
//         // };

//         // setUser(tempUser2);
//         // localStorage.setItem("user", JSON.stringify(tempUser2));
//       }

//       setLoading(false);
//     };

//     loadUser();
//   }, []);

//   // Update user state and local storage
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

      if (storedUser) {
        setUser(JSON.parse(storedUser));
      } else {
        // Temporary user for development
        const tempUser = {
          id: 14,
          email: "ankit@gmail.com",
          name: "Ankit",
          role: "TESTER",
        };
        setUser(tempUser);
        localStorage.setItem("user", JSON.stringify(tempUser));
      }

      setLoading(false);
    };

    loadUser();
  }, []);

  // Log to check if user state is updating properly
  useEffect(() => {
    console.log("User updated:", user);
  }, [user]);

  // Update user state and local storage
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
