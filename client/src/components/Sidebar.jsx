// import React, { useState, useEffect, useContext } from "react";
// import { useNavigate, useLocation } from "react-router-dom";
// import { Home, Bug, ListCollapseIcon, Menu, Trash2, User, Text } from "lucide-react";
// import { UserContext } from "../context/UserContext"; // Assuming you have this context to provide the user data

// const Sidebar = () => {
//   const navigate = useNavigate();
//   const location = useLocation();
//   const [collapsed, setCollapsed] = useState(true);
//   const [sidebarHeight, setSidebarHeight] = useState(window.innerHeight);

//   // Access the user context (Assuming you have user and role info there)
//   const { user } = useContext(UserContext);

//   useEffect(() => {
//     const handleResize = () => {
//       setSidebarHeight(document.body.scrollHeight); // Update height dynamically
//     };

//     window.addEventListener("resize", handleResize);
//     return () => window.removeEventListener("resize", handleResize);
//   }, []);

//   // Check user role and render items accordingly
//   const renderSidebarItems = () => {
//     if (!user) {
//       return null;
//     }

//     const role = user.role;

//     // Common sidebar items for all roles
//     const commonItems = [
//       {
//         path: "/dashboard",
//         icon: <Home className="w-5 h-5" />,
//         label: "Dashboard",
//       },
//       // {
//       //   path: "/allBugs",
//       //   icon: <ListCollapseIcon className="w-5 h-5" />,
//       //   label: "All Bugs",
//       // },
//       // {
//       //   path: "/addBug",
//       //   icon: <Bug className="w-5 h-5" />,
//       //   label: "Report Bugs",
//       // },
//       // {
//       //   path: "/trashBugs",  // Trash Bugs should be common for all roles
//       //   icon: <Trash2 className="w-5 h-5" />,
//       //   label: "Trash Bugs",
//       // },
//     ];

//     // Role-specific sidebar items
//     let roleSpecificItems = [];

//     if (role === "TESTER") {
//       roleSpecificItems = [
//         {
//           path: "/addBug",  // Developers will also see Trash Bugs (if needed)
//           icon: <Bug className="w-5 h-5" />,
//           label: "Report Bugs",
//         },
//       ];
//     }

//     if (role === "ADMIN" || role === "TESTER") {
//       {
//         roleSpecificItems = [
//           {
//             path: "/allBugs",  // Developers will also see Trash Bugs (if needed)
//             icon: <ListCollapseIcon className="w-5 h-5" />,
//             label: "All Bugs",
//           },
//         ];
//       }

//       if (role === "ADMIN") {
//         roleSpecificItems = [
//           {
//             path: "/trashBugs",  // Developers will also see Trash Bugs (if needed)
//             icon: <Trash2 className="w-5 h-5" />,
//             label: "Trash Bugs",
//           },
//         ];
//       }
//       if (role === "ADMIN") {
//         roleSpecificItems = [
//           {
//             path: "/adminDash",
//             icon: <User className="w-5 h-5" />,
//             label: "Admin Dash",
//           },
//         ];
//       }

//       if (role === "DEVELOPER") {
//         roleSpecificItems = [
//           {
//             path: "/assignedBugs",
//             icon: <Text className="w-5 h-5" />,
//             label: "Assigned Bugs",
//           },
//         ];
//       }


//       // Combine common and role-specific items
//       const allItems = [...commonItems, ...roleSpecificItems];

//       return allItems.map((item, index) => (
//         <li
//           key={ index }
//           className={ `flex items-center gap-2 p-3 rounded ${location.pathname === item.path ? "bg-gray-700" : ""
//             }` }
//           onClick={ () => navigate(item.path) }
//         >
//           { item.icon }
//           { !collapsed && <span>{ item.label }</span> }
//         </li>
//       ));
//     };

//     return (
//       <div
//         className={ `bg-gray-800 text-white ${collapsed ? "w-15" : "w-60"} p-4 transition-all duration-300` }
//         style={ { height: sidebarHeight } }
//       >
//         {/* Toggle Button */ }
//         <button
//           onClick={ () => setCollapsed(!collapsed) }
//           className="mb-4 p-2 bg-gray-700 rounded w-full"
//         >
//           <Menu className="w-6 h-6 mx-auto" />
//         </button>

//         {/* Sidebar Items */ }
//         <ul className="space-y-4">{ renderSidebarItems() }</ul>
//       </div>
//     );
//   };
// }
// export default Sidebar;





// new 
import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Home, Bug, ListCollapseIcon, Menu, Trash2, User, Text } from "lucide-react";
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
    ];

    // Role-specific sidebar items
    let roleSpecificItems = [];

    if (role === "TESTER") {
      roleSpecificItems.push(
        {
          path: "/addBug",
          icon: <Bug className="w-5 h-5" />,
          label: "Report Bugs",
        }
      );
    }

    if (role === "ADMIN" || role === "TESTER") {
      roleSpecificItems.push(
        {
          path: "/allBugs",
          icon: <ListCollapseIcon className="w-5 h-5" />,
          label: "All Bugs",
        }
      );
    }

    if (role === "ADMIN") {
      roleSpecificItems.push(
        {
          path: "/trashBugs",
          icon: <Trash2 className="w-5 h-5" />,
          label: "Trash Bugs",
        },
        {
          path: "/adminDash",
          icon: <User className="w-5 h-5" />,
          label: "Admin Dash",
        }
      );
    }

    if (role === "DEVELOPER") {
      roleSpecificItems.push(
        {
          path: "/assignedBugs",
          icon: <Text className="w-5 h-5" />,
          label: "Assigned Bugs",
        }
      );
    }

    // Combine common and role-specific items
    const allItems = [...commonItems, ...roleSpecificItems];

    return allItems.map((item, index) => (
      <li
        key={ index }
        className={ `flex items-center gap-2 p-3 rounded ${location.pathname === item.path ? "bg-gray-700" : ""}` }
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
