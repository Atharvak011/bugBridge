import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { UserProvider } from "./context/UserContext";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import Dashboard from "./Pages/Dashboard";
import Profile from "./Pages/Profile";
import AddBug from "./Pages/AddBug";
import AllBugs from "./Pages/AllBugs";
import Layout from "./components/Layout";
import PrivateRoute from "./components/PrivateRoute";
import RoleBasedRoute from "./components/RoleBasedRoute";
import TrashBugs from "./Pages/TrashBugs";
import AdminDash from "./Pages/AdminDash";

const App = () => {
  return (
    <Router>
      <UserProvider>
        <Routes>
          {/* Public Routes */ }
          <Route path="/login" element={ <Login /> } />
          <Route path="/register" element={ <Register /> } />

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

          {/* Role-Based Routes */ }
          <Route
            path="/addBug"
            element={
              <RoleBasedRoute allowedRoles={ ["TESTER"] }>
                <Layout>
                  <AddBug />
                </Layout>
              </RoleBasedRoute>
            }
          />

          <Route
            path="/allBugs"
            element={
              <RoleBasedRoute allowedRoles={ ["DEVELOPER", "TESTER", "ADMIN"] }>
                <Layout>
                  <AllBugs />
                </Layout>
              </RoleBasedRoute>
            }
          />
          <Route
            path="/trashBugs"
            element={
              <RoleBasedRoute allowedRoles={ ["TESTER", "ADMIN"] }>
                <Layout>
                  <TrashBugs />
                </Layout>
              </RoleBasedRoute>
            }
          />
          <Route
            path="/adminDash"
            element={
              <RoleBasedRoute allowedRoles={ ["TESTER", "ADMIN"] }>
                {/* "TESTER",  */ }
                <Layout>
                  <AdminDash />
                </Layout>
              </RoleBasedRoute>
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