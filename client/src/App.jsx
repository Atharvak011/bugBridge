// import React, { useContext } from 'react';
// import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
// import { UserContext, UserProvider } from './context/UserContext';
// import Header from './components/Header';
// import Login from './Pages/Login';
// import Profile from './Pages/Profile';
// import Dashboard from './Pages/Dashboard'
// import Register from './Pages/Register';

// // PrivateRoute component to protect routes
// const PrivateRoute = ({ children }) => {
//   const { user, loading } = useContext(UserContext);

//   // While user data is being fetched, show loading
//   if (loading) {
//     return <div className="flex items-center justify-center h-screen text-xl">Loading...</div>;
//   }

//   // If no user, redirect to login
//   return user ? children : <Navigate to="/login" />;
// };

// const App = () => {
//   return (
//     <UserProvider>
//       <Router>
//         <Routes>
//           {/* Public Routes */ }
//           <Route path="/login" element={ <Login /> } />
//           <Route path="/register" element={ <Register /> } /> {/* Register route */ }
//           {/* Protected Routes */ }
//           <Route
//             path="/dashboard"
//             element={
//               <PrivateRoute>
//                 <Header /> {/* Header rendered only if the user is logged in */ }
//                 <Dashboard />
//               </PrivateRoute>
//             }
//           />
//           <Route
//             path="/profile"
//             element={
//               <PrivateRoute>
//                 <Header /> {/* Header rendered only if the user is logged in */ }
//                 <Profile />
//               </PrivateRoute>
//             }
//           />
//         </Routes>
//       </Router>
//     </UserProvider>
//   );
// };




// export default App;


//--------------------------------------------------------------

import React, { useContext } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { UserContext, UserProvider } from "./context/UserContext";
import Header from "./components/Header";
import Login from "./Pages/Login";
import Profile from "./Pages/Profile";
import Dashboard from "./Pages/Dashboard";
import Register from "./Pages/Register";

// PrivateRoute component (Modified to remove `loading`)
const PrivateRoute = ({ children }) => {
  const { user } = useContext(UserContext);

  // If no user is logged in, redirect to login
  return user ? children : <Navigate to="/login" />;
};

// const App = () => {
//   return (
//     <UserProvider>
//       <Router>
//         <Header /> {/* Ensure Header is always visible */ }
//         <Routes>
//           {/* Public Routes */ }
//           <Route path="/login" element={ <Login /> } />
//           <Route path="/register" element={ <Register /> } />

//           {/* Protected Routes (still keeping them private for future JWT auth) */ }
//           <Route
//             path="/dashboard"
//             element={
//               <PrivateRoute>
//                 <Dashboard />
//               </PrivateRoute>
//             }
//           />
//           <Route
//             path="/profile"
//             element={
//               <PrivateRoute>
//                 <Profile />
//               </PrivateRoute>
//             }
//           />
//         </Routes>
//       </Router>
//     </UserProvider>
//   );
// };


const App = () => {
  return (
    <Router> {/* Router must wrap UserProvider */ }
      <UserProvider>
        <Header /> {/* Ensure Header is always visible */ }
        <Routes>
          <Route path="/login" element={ <Login /> } />
          <Route path="/register" element={ <Register /> } />
          <Route path="/dashboard" element={ <PrivateRoute><Dashboard /></PrivateRoute> } />
          <Route path="/profile" element={ <PrivateRoute><Profile /></PrivateRoute> } />
        </Routes>
      </UserProvider>
    </Router>
  );
};

export default App;
