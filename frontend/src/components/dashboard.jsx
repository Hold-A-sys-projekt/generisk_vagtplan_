import {useEffect, useState} from 'react';
import UserAdminPage from "@/pages/UserAdminPage.jsx";
import AssignRole from '@/pages/AssignRole.jsx';
import AddNewEmployee from "@/pages/AddNewEmployee.jsx";
import Punch from "@/components/punch.jsx";


const Dashboard = () => {

    const [userRole, setUserRole] = useState('');

    useEffect(() => {
        const role = sessionStorage.getItem('userRole');
        setUserRole(role);
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            {userRole === "administrator" && (
                <div>
                    <p>Welcome Administrator!</p>
                </div>
            )}
            {userRole === "companyAdmin" && (
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
        </div>
    );
};

export default Dashboard;
