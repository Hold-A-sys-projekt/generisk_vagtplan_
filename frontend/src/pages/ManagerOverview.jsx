import React, { useEffect, useState } from "react";
import Shift from "@/components/Shift";
import EmployeeDetails from "@/components/EmployeeDetails.jsx";
import ManageShift from "@/components/manageShift.jsx";

const ManagerOverview = () => {
    const [employees, setEmployees] = useState([]);
    const [selectedEmployee, setSelectedEmployee] = useState(null);
    const [shifts, setShifts] = useState([]);
    const [shiftText, setShiftText] = useState("");
    const [employeeId, setEmployeeId] = useState(null);

    useEffect(() => {
        fetchEmployees();
    }, []);

    useEffect(() => {
        const fetchEmployeeData = async () => {
            if (employeeId) {
                const user = await fetchSingleEmployee(employeeId);
                console.log("user", user);
                setSelectedEmployee(user);
                console.log("the sel em: ", selectedEmployee)
                console.log(employeeId);
                fetchShifts(employeeId);
            }
        };
        fetchEmployeeData();
    }, [employeeId]);

    useEffect(() => {

        if (employeeId === null) {
            return;
        }
        console.log("shiftText", shiftText);

        fetchShifts(employeeId);
    }, [shiftText]);

    async function fetchSingleEmployee(id) {
        return fetch(`http://localhost:7070/api/employees/${id}`)
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Failed to fetch employees');
                }
                return res.json();
            })
            .catch((error) => {
                console.error('Error fetching employees:', error);
                setShiftText('Error fetching employees. Please try again.');
            });
    }

    const fetchEmployees = () => {
        fetch('http://localhost:7070/api/managers/employees')
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Failed to fetch employees');
                }
                return res.json();
            })
            .then((data) => {
                console.log('Employees:', data);
                setEmployees(data);
            })
            .catch((error) => {
                console.error('Error fetching employees:', error);
                setShiftText('Error fetching employees. Please try again.');
            });
    };

    const fetchShifts = (userId) => {
        fetch(`http://localhost:7070/api/shifts/users/${userId}`)
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Failed to fetch shifts');
                }
                return res.json();
            })
            .then((data) => {
                console.log('Shifts:', data);
                setShifts(data);
            })
            .catch((error) => {
                console.error('Error fetching shifts:', error);
                setShiftText('Error fetching shifts. Please try again.');
            });
    };

    return (
        <div className="container mx-auto px-4">
            <div className="flex justify-between items-center">
                <h1 className="text-2xl font-bold">Manage Employees' Shifts</h1>
            </div>
            <div className="mt-4">
                <h2 className="text-xl mb-4">Employees</h2>
                <ul>
                    {employees.map((employee, index) => (
                        <EmployeeDetails key={index} employee={employee} setEmployeeId={setEmployeeId}/>
                    ))}
                </ul>
            </div>
            {selectedEmployee && (
                <div className="mt-4">
                    <h2 className="text-xl mb-4">Shifts for {selectedEmployee.username}</h2>
                    {shifts.map((shift) => (
                        <ManageShift
                            key={shift.id}
                            id={shift.id}
                            shiftStart={shift.shiftStart}
                            shiftEnd={shift.shiftEnd}
                            punchIn={shift.punchIn}
                            punchOut={shift.punchOut}
                            userId={shift.userId}
                            status={shift.status}
                            setShiftText={setShiftText}
                            refreshShifts={() => fetchShifts(selectedEmployee.id)}
                            isManager={true}
                        />
                    ))}
                </div>
            )}
            <div className="mt-4 text-red-500">{shiftText}</div>
        </div>
    );
};

export default ManagerOverview;
