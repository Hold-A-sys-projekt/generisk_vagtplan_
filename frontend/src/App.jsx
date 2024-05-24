import { Outlet } from "react-router-dom";
import "./App.css";

import Navbar from "./components/navbar";
import { Toaster } from "./components/ui/toaster";

function App() {
  return (
    <>
      <Navbar />
      <Outlet />
      <Toaster />
    </>
  );
}

export default App;
