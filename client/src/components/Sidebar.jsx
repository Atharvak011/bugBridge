import React, { useContext, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { UserContext } from "../context/UserContext";
import { Home, User, Bug, LogOut, Menu } from "lucide-react";

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { logout } = useContext(UserContext);
  const [collapsed, setCollapsed] = useState(false);

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className={ `bg-gray-800 text-white ${collapsed ? "w-16" : "w-60"} p-4 h-screen transition-all duration-300` }>
      {/* Toggle Button */ }
      <button onClick={ () => setCollapsed(!collapsed) } className="mb-4 p-2 bg-gray-700 rounded w-full">
        <Menu className="w-6 h-6 mx-auto" />
      </button>

      <ul className="space-y-4">
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/dashboard" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/") }>
          <Home className="w-5 h-5" />
          { !collapsed && <span>Dashboard</span> }
        </li>
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/profile" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/profile") }>
          <User className="w-5 h-5" />
          { !collapsed && <span>Profile</span> }
        </li>
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/bugs" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/bugs") }>
          <Bug className="w-5 h-5" />
          { !collapsed && <span>Report Bugs</span> }
        </li>
        {/* <li>
          <button onClick={ handleLogout } className="w-full flex items-center gap-2 text-left py-2 px-3 bg-red-500 rounded">
            <LogOut className="w-5 h-5" />
            { !collapsed && <span>Logout</span> }
          </button>
        </li> */}
      </ul>
    </div>
  );
};

export default Sidebar;
