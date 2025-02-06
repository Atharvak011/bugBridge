import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { UserContext } from "../context/UserContext";

const RoleBasedRoute = ({ children, allowedRoles }) => {
  const { user } = useContext(UserContext);

  // Check if the user's role is allowed
  if (user && allowedRoles.includes(user.role)) {
    return children;
  }

  // Redirect to a fallback route (e.g., dashboard) if the role is not allowed
  return <Navigate to="/dashboard" />;
};

export default RoleBasedRoute;