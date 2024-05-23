import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import {
    Route,
    RouterProvider,
    createBrowserRouter,
    createRoutesFromElements,
} from "react-router-dom";
import Index from "@/pages/Index.jsx";
import UserAdminPage from "./pages/UserAdminPage.jsx";
import Reviews from "./components/reviews.jsx";
import Calendar_Schedule from "@/pages/Calendar_Schedule.jsx";

import { ToastProvider } from "@radix-ui/react-toast";
import ShiftSwapsManager from "@/pages/ShiftSwapManager.jsx";
import ManageSwapRequests from "./components/ManageSwapRequests.jsx";

import ShiftOverview from "./pages/ShiftOverview.jsx";
import EmployeeAdminPage from "./pages/EmployeeAdminPage.jsx";
import Login from "@/components/login.jsx";
import Dashboard from "@/components/dashboard.jsx";
import Companysignup from "@/components/companysignup.jsx";
import ManagerOverview from "@/pages/ManagerOverview.jsx";


const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<App />}>
      <Route index element={<Index />} />
      <Route path="admin/">
        <Route path="users" element={<UserAdminPage />} />
        <Route path="employees" element={<EmployeeAdminPage />} />
      </Route>
      <Route path="/reviews" element={<Reviews />} />
      <Route path="/calendar" element={<Calendar_Schedule />} />
      <Route path="/swapshifts" element={<ShiftSwapsManager />} />
      <Route path="/manage-swap-requests" element={<ManageSwapRequests userId={1} />} /> {/* Add the new route */}
    </Route>
  )
);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ToastProvider>
      <RouterProvider router={router} />
    </ToastProvider>
  </React.StrictMode>

);
