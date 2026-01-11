import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import { useEffect, useState } from "react";

const SIDEBAR_KEY = "sidebar-collapsed";

const AppLayout = () => {
  const [collapsed, setCollapsed] = useState<boolean>(() => {
    return localStorage.getItem(SIDEBAR_KEY) === "true";
  });

  useEffect(() => {
    localStorage.setItem(SIDEBAR_KEY, String(collapsed));
  }, [collapsed]);

  return (
    <div className="flex min-h-screen">
      <Sidebar collapsed={collapsed} setCollapsed={setCollapsed} />
      <div
        className={`
          transition-all duration-300 ease-in-out
          ${collapsed ? "ml-20" : "ml-64"}
          flex flex-col min-h-screen
        `}
      >
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
};

export default AppLayout;
