import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { UserContext } from '../context/UserContext';
import PropTypes from 'prop-types';

const RoleBasedRoute = ({ children, allowedRoles }) => {
  const { user } = useContext(UserContext);

  // Check if the user's role is allowed
  if (user && allowedRoles.includes(user.role)) {
    return children;
  }

  // Redirect to a fallback route (e.g., dashboard) if the role is not allowed
  return <Navigate to="/dashboard" />;
};
RoleBasedRoute.propTypes = {
  children: PropTypes.node.isRequired,
  allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default RoleBasedRoute;
// export default RoleBasedRoute;