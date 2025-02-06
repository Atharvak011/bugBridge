import React, { useContext, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Home, Bug, ListCollapseIcon, Menu } from "lucide-react";

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [collapsed, setCollapsed] = useState(true);

  return (
    <div className={ `bg-gray-800 text-white ${collapsed ? "w-16" : "w-60"} p-4 h-screen transition-all duration-300` }>
      {/* Toggle Button */ }
      <button onClick={ () => setCollapsed(!collapsed) } className="mb-4 p-2 bg-gray-700 rounded w-full">
        <Menu className="w-6 h-6 mx-auto" />
      </button>

      <ul className="space-y-4">
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/dashboard" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/dashboard") }>
          <Home className="w-5 h-5" />
          { !collapsed && <span>Dashboard</span> }
        </li>
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/allBugs" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/allBugs") }>
          <ListCollapseIcon className="w-5 h-5" />
          { !collapsed && <span>All Bugs</span> }
        </li>
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/addBug" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/addBug") }>
          <Bug className="w-5 h-5" />
          { !collapsed && <span>Report Bugs</span> }
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
