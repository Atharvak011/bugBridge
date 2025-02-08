// import React, { useState, useEffect } from "react";
// import { useNavigate, useLocation } from "react-router-dom";
// import { Home, Bug, ListCollapseIcon, Menu, Trash2, User } from "lucide-react";

// const Sidebar = () => {
//   const navigate = useNavigate();
//   const location = useLocation();
//   const [collapsed, setCollapsed] = useState(true);
//   const [sidebarHeight, setSidebarHeight] = useState(window.innerHeight);

//   useEffect(() => {
//     const handleResize = () => {
//       setSidebarHeight(document.body.scrollHeight); // Update height dynamically
//     };

//     window.addEventListener("scroll", handleResize);
//     return () => window.removeEventListener("scroll", handleResize);
//   }, []);

//   return (
//     <div
//       className={ `bg-gray-800 text-white ${collapsed ? "w-15" : "w-60"} p-4 transition-all duration-300` }
//       style={ { height: sidebarHeight } } // Dynamic height
//     >
//       {/* Toggle Button */ }
//       <button onClick={ () => setCollapsed(!collapsed) } className="mb-4 p-2 bg-gray-700 rounded w-full">
//         <Menu className="w-6 h-6 mx-auto" />
//       </button>

//       <ul className="space-y-4">
//         <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/dashboard" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/dashboard") }>
//           <Home className="w-5 h-5" />
//           { !collapsed && <span>Dashboard</span> }
//         </li>
//         <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/allBugs" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/allBugs") }>
//           <ListCollapseIcon className="w-5 h-5" />
//           { !collapsed && <span>All Bugs</span> }
//         </li>
//         <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/addBug" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/addBug") }>
//           <Bug className="w-5 h-5" />
//           { !collapsed && <span>Report Bugs</span> }
//         </li>
//         <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/trashBugs" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/trashBugs") }>
//           <Trash2 className="w-5 h-5" />
//           { !collapsed && <span>Trash Bugs</span> }
//         </li>
//         <li className={ `flex items-center gap-2 p-3 rounded ${location.pathname === "/adminDash" ? "bg-gray-700" : ""}` } onClick={ () => navigate("/adminDash") }>
//           <User className="w-5 h-5" />
//           { !collapsed && <span>Admin Dash</span> }
//         </li>
//       </ul>
//     </div>
//   );
// };

// export default Sidebar;







import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Home, Bug, ListCollapseIcon, Menu, Trash2, User } from "lucide-react";
import { UserContext } from "../context/UserContext"; // Assuming you have this context to provide the user data

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [collapsed, setCollapsed] = useState(true);
  const [sidebarHeight, setSidebarHeight] = useState(window.innerHeight);

  // Access the user context (Assuming you have user and role info there)
  const { user } = useContext(UserContext);

  useEffect(() => {
    const handleResize = () => {
      setSidebarHeight(document.body.scrollHeight); // Update height dynamically
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  // Check user role and render items accordingly
  const renderSidebarItems = () => {
    if (!user) {
      return null;
    }

    const role = user.role;

    // Common sidebar items for all roles
    const commonItems = [
      {
        path: "/dashboard",
        icon: <Home className="w-5 h-5" />,
        label: "Dashboard",
      },
      {
        path: "/allBugs",
        icon: <ListCollapseIcon className="w-5 h-5" />,
        label: "All Bugs",
      },
      {
        path: "/addBug",
        icon: <Bug className="w-5 h-5" />,
        label: "Report Bugs",
      },
      {
        path: "/trashBugs",  // Trash Bugs should be common for all roles
        icon: <Trash2 className="w-5 h-5" />,
        label: "Trash Bugs",
      },
    ];

    // Role-specific sidebar items
    let roleSpecificItems = [];

    if (role === "ADMIN") {
      roleSpecificItems = [
        {
          path: "/trashBugs",  // Developers will also see Trash Bugs (if needed)
          icon: <Trash2 className="w-5 h-5" />,
          label: "Trash Bugs",
        },
      ];
    } else if (role === "ADMIN" || role === "TESTER") {
      roleSpecificItems = [
        {
          path: "/adminDash",
          icon: <User className="w-5 h-5" />,
          label: "Admin Dash",
        },
      ];
    }

    // Combine common and role-specific items
    const allItems = [...commonItems, ...roleSpecificItems];

    return allItems.map((item, index) => (
      <li
        key={ index }
        className={ `flex items-center gap-2 p-3 rounded ${location.pathname === item.path ? "bg-gray-700" : ""
          }` }
        onClick={ () => navigate(item.path) }
      >
        { item.icon }
        { !collapsed && <span>{ item.label }</span> }
      </li>
    ));
  };

  return (
    <div
      className={ `bg-gray-800 text-white ${collapsed ? "w-15" : "w-60"} p-4 transition-all duration-300` }
      style={ { height: sidebarHeight } }
    >
      {/* Toggle Button */ }
      <button
        onClick={ () => setCollapsed(!collapsed) }
        className="mb-4 p-2 bg-gray-700 rounded w-full"
      >
        <Menu className="w-6 h-6 mx-auto" />
      </button>

      {/* Sidebar Items */ }
      <ul className="space-y-4">{ renderSidebarItems() }</ul>
    </div>
  );
};

export default Sidebar;
