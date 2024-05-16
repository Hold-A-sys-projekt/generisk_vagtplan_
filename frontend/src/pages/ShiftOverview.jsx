import Shift from "@/components/Shift";
import { useEffect, useState } from "react";

const ShiftOverview = ({employeeId}) => {
    const [shifts, setShifts] = useState([]);
    const [shiftText, setShiftText] = useState("");

    useEffect(() => {
        fetch('http://localhost:7070/api/employees/' + employeeId + '/shifts')
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Failed to fetch shifts');
                }
                return res.json();
            })
            .then((data) => {
                setShifts(data);
            })
            .catch((error) => {
                console.error('Error fetching shifts:', error);
            });
    }, []);
    
    return (
        <div className="container mx-auto px-4">
            <div className="flex justify-between items-center">
                <h1 className="text-2xl font-bold">My Tasks</h1>
            </div>
            <div className="mt-4">
                {shifts.map((shift) => (
                    <Shift
                        key={shift.id}
                        id={shift.id}
                        shiftStart={shift.shiftStart}
                        shiftEnd={shift.shiftEnd}
                        punchIn={shift.punchIn}
                        punchOut={shift.punchOut}
                        userId={employeeId}
                        status={shift.status}
                        setShiftText={setShiftText}
                    />
                ))}
            </div>
        </div>
    );
};

export default ShiftOverview;
