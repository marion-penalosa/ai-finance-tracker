import LogoutButton from "../features/auth/LogoutButton";

interface SidebarProps {
  collapsed: boolean;
  setCollapsed: React.Dispatch<React.SetStateAction<boolean>>;
}

const Sidebar = ({ collapsed, setCollapsed }: SidebarProps) => {
  return (
    <div
      className={`
        fixed top-0 left-0 h-screen
        bg-white shadow-lg
        transition-all duration-300 ease-in-out
        shrink-0
        ${collapsed ? "w-20" : "w-64"}
        flex flex-col items-center
      `}
    >
      <button
        onClick={() => setCollapsed((prev) => !prev)}
        className="mt-4 bg-amber-500 hover:bg-amber-600 text-white px-3 py-1 rounded"
      >
        Toggle
      </button>
      <LogoutButton />
    </div>
  );
};

export default Sidebar;
