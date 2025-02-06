import React from "react";
import Sidebar from "./Sidebar";
import Header from "./Header";

const Layout = ({ children }) => {
  return (
    <div className="flex">
      <Sidebar /> {/* Sidebar on the left */ }
      <div className="flex-1">
        <Header />{/* Header at the top  */ }
        <main className="p-6">{ children }</main>
      </div>
    </div>
  );
};

export default Layout;

