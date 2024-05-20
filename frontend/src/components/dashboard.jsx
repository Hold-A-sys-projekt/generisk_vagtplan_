import {useEffect, useState} from 'react';
import UserAdminPage from "@/pages/UserAdminPage.jsx";
import AssignRole from '@/pages/AssignRole.jsx';
import AddNewEmployee from "@/pages/AddNewEmployee.jsx";
import Punch from "@/components/punch.jsx";
import facade from "@/utils/apiFacade.js";
import {useNavigate} from "react-router-dom";


const Dashboard = () => {

    const [userRole, setUserRole] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const role = localStorage.getItem('userRole');
        setUserRole(role);
    }, []);

    const handleLogout = () => {
        facade.logout(() => navigate("/"));
    };

    return (
        <div>
            <h1>Dashboard</h1>
            {userRole === "administrator" && (
                <div>
                    <p>Welcome Administrator!</p>
                </div>
            )}
            {userRole === "company_admin" && (
                <div>
                    <p>Welcome Company Administrator!</p>
                    <UserAdminPage/>
                </div>
            )}
            {userRole === "manager" && (
                <div>
                    <p>Welcome Manager!</p>
                    <AssignRole/>
                    <AddNewEmployee/>
                </div>

            )}
            {userRole === "employee" && (
                <div>
                    <p>Welcome Employee!</p>
                    <Punch/>
                </div>
            )}
            <button onClick={handleLogout}
                    className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">
                Logout
            </button>
        </div>
    );
};

export default Dashboard;
