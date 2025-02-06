import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Home, Bug, ListCollapseIcon, Menu, Trash2 } from "lucide-react";

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [collapsed, setCollapsed] = useState(true);
  const [sidebarHeight, setSidebarHeight] = useState(window.innerHeight);

  useEffect(() => {
    const handleResize = () => {
      setSidebarHeight(document.body.scrollHeight); // Update height dynamically
    };

    window.addEventListener("scroll", handleResize);
    return () => window.removeEventListener("scroll", handleResize);
  }, []);

  return (
    <div
      className={ `bg-gray-800 text-white ${collapsed ? "w-15" : "w-60"} p-4 transition-all duration-300` }
      style={ { height: sidebarHeight } } // Dynamic height
    >
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
        <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/trashBugs" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/trashBugs") }>
          <Trash2 className="w-5 h-5" />
          { !collapsed && <span>Trash Bugs</span> }
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
