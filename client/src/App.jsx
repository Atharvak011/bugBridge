// // import React, { useContext } from 'react';
// // import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
// // import { UserContext, UserProvider } from './context/UserContext';
// // import Header from './components/Header';
// // import Login from './Pages/Login';
// // import Profile from './Pages/Profile';
// // import Dashboard from './Pages/Dashboard'
// // import Register from './Pages/Register';

// // // PrivateRoute component to protect routes
// // const PrivateRoute = ({ children }) => {
// //   const { user, loading } = useContext(UserContext);

// //   // While user data is being fetched, show loading
// //   if (loading) {
// //     return <div className="flex items-center justify-center h-screen text-xl">Loading...</div>;
// //   }

// //   // If no user, redirect to login
// //   return user ? children : <Navigate to="/login" />;
// // };

// // const App = () => {
// //   return (
// //     <UserProvider>
// //       <Router>
// //         <Routes>
// //           {/* Public Routes */ }
// //           <Route path="/login" element={ <Login /> } />
// //           <Route path="/register" element={ <Register /> } /> {/* Register route */ }
// //           {/* Protected Routes */ }
// //           <Route
// //             path="/dashboard"
// //             element={
// //               <PrivateRoute>
// //                 <Header /> {/* Header rendered only if the user is logged in */ }
// //                 <Dashboard />
// //               </PrivateRoute>
// //             }
// //           />
// //           <Route
// //             path="/profile"
// //             element={
// //               <PrivateRoute>
// //                 <Header /> {/* Header rendered only if the user is logged in */ }
// //                 <Profile />
// //               </PrivateRoute>
// //             }
// //           />
// //         </Routes>
// //       </Router>
// //     </UserProvider>
// //   );
// // };




// // export default App;


// //--------------------------------------------------------------

// import React, { useContext } from "react";
// import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
// import { UserContext, UserProvider } from "./context/UserContext";
// import Header from "./components/Header";
// import Login from "./Pages/Login";
// import Profile from "./Pages/Profile";
// import Dashboard from "./Pages/Dashboard";
// import Register from "./Pages/Register";
// import Sidebar from "./components/Sidebar";
// import AddBug from "./Pages/AddBug";
// import AllBugs from "./Pages/AllBugs";


// // PrivateRoute component (Modified to remove `loading`)
// const PrivateRoute = ({ children }) => {
//   const { user } = useContext(UserContext);

//   // If no user is logged in, redirect to login
//   return user ? children : <Navigate to="/login" />;
// };

// // const App = () => {
// //   return (
// //     <UserProvider>
// //       <Router>
// //         <Header /> {/* Ensure Header is always visible */ }
// //         <Routes>
// //           {/* Public Routes */ }
// //           <Route path="/login" element={ <Login /> } />
// //           <Route path="/register" element={ <Register /> } />

// //           {/* Protected Routes (still keeping them private for future JWT auth) */ }
// //           <Route
// //             path="/dashboard"
// //             element={
// //               <PrivateRoute>
// //                 <Dashboard />
// //               </PrivateRoute>
// //             }
// //           />
// //           <Route
// //             path="/profile"
// //             element={
// //               <PrivateRoute>
// //                 <Profile />
// //               </PrivateRoute>
// //             }
// //           />
// //         </Routes>
// //       </Router>
// //     </UserProvider>
// //   );
// // };
// // ---------------------------------------------------------------------
// // Layout Component that wraps protected pages
// const Layout = ({ children }) => {
//   return (
//     <div className="flex">
//       <Sidebar /> {/* Sidebar on the left */ }
//       <div className="flex-1">
//         <Header /> {/* Header at the top */ }
//         <main className="p-6">{ children }</main>
//       </div>
//     </div>
//   );
// };


// const App = () => {
//   return (
//     <Router>
//       <UserProvider>
//         <Routes>
//           {/* Public Routes (No Header/Sidebar) */ }
//           <Route path="/login" element={ <Login /> } />
//           <Route path="/registeration" element={ <Register /> } />

//           {/* Protected Routes (Wrapped with Layout) */ }
//           <Route
//             path="/dashboard"
//             element={
//               <Layout>
//                 <Dashboard />
//               </Layout>
//             }
//           />
//           <Route
//             path="/profile"
//             element={
//               <Layout>
//                 <Profile />
//               </Layout>
//             }
//           />

//           {/* Protected Routes (Wrapped with Layout) */ }
//           <Route
//             path="/addBug"
//             element={
//               <Layout>
//                 <AddBug />
//               </Layout>
//             }
//           />
//           {/* Protected Routes (Wrapped with Layout) */ }
//           <Route
//             path="/allBugs"
//             element={
//               <Layout>
//                 <AllBugs />
//               </Layout>
//             }
//           />

//         </Routes>
//       </UserProvider>
//     </Router>
//   );
// };
// export default App;
// {/* <Route path="/dashboard" element={ <PrivateRoute><Dashboard /></PrivateRoute> } /> */ }
// {/* <Route path="/profile" element={ <PrivateRoute><Profile /></PrivateRoute> } /> */ }










import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { UserProvider } from "./context/UserContext"; // Import UserProvider
import Header from "./components/Header";
import Sidebar from "./components/Sidebar";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import Dashboard from "./Pages/Dashboard";
import Profile from "./Pages/Profile";
import AddBug from "./Pages/AddBug";
import AllBugs from "./Pages/AllBugs";

// Layout Component for Protected Pages
const Layout = ({ children }) => {
  return (
    <div className="flex">
      <Sidebar />
      <div className="flex-1">
        <Header />
        <main className="p-6">{ children }</main>
      </div>
    </div>
  );
};

// PrivateRoute Component to Protect Routes
import { UserContext } from "./context/UserContext";
import { useContext } from "react";

const PrivateRoute = ({ children }) => {
  const { user, loading } = useContext(UserContext);

  if (loading) {
    return <div className="flex items-center justify-center h-screen text-xl">Loading...</div>;
  }

  return user ? children : <Navigate to="/login" />;
};

// App Component
const App = () => {
  return (
    <Router>
      <UserProvider> {/* Wrap inside Router to fix useNavigate error */ }
        <Routes>
          {/* Public Routes */ }
          <Route path="/login" element={ <Login /> } />
          <Route path="/registeration" element={ <Register /> } />

          {/* Protected Routes */ }
          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <Layout>
                  <Dashboard />
                </Layout>
              </PrivateRoute>
            }
          />
          <Route
            path="/profile"
            element={
              <PrivateRoute>
                <Layout>
                  <Profile />
                </Layout>
              </PrivateRoute>
            }
          />
          <Route
            path="/addBug"
            element={
              <PrivateRoute>
                <Layout>
                  <AddBug />
                </Layout>
              </PrivateRoute>
            }
          />
          <Route
            path="/allBugs"
            element={
              <PrivateRoute>
                <Layout>
                  <AllBugs />
                </Layout>
              </PrivateRoute>
            }
          />

          {/* Redirect unknown routes to Dashboard if logged in, else Login */ }
          <Route path="*" element={ <Navigate to="/dashboard" /> } />
        </Routes>
      </UserProvider>
    </Router>
  );
};

export default App;
