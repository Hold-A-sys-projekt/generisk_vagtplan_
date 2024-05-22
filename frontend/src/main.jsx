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
import CalendarPage from "@/pages/CalendarPage.jsx";
import Login from "@/components/login.jsx";
import Dashboard from "@/components/dashboard.jsx";
import Companysignup from "@/components/companysignup.jsx";
import EmployeeAdminPage from "./pages/EmployeeAdminPage.jsx";
import ShiftOverview from "@/components/ShiftOverview.jsx";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<App />}>
            <Route index element={<Index />} />
            <Route path="admin/">
                <Route path="users" element={<UserAdminPage />} />
                <Route path="employees" element={<EmployeeAdminPage />} />
            </Route>
            <Route path="reviews" element={<Reviews />} />
            <Route path="employee/">
                <Route path="my-shifts" element={<ShiftOverview />} />
            </Route>
            <Route path="calendar" element={<Calendar_Schedule />} />
            <Route path="CalendarPage" element={<CalendarPage />} />
            <Route path="login" element={<Login />} />
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="companysignup" element={<Companysignup />} />
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