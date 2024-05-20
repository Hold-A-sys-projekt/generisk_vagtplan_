import React, { useEffect, useState } from "react";
import Shift from "@/components/Shift";

const ShiftOverview = () => {
    const [shifts, setShifts] = useState([]);
    const [shiftText, setShiftText] = useState("");
    const [userId, setUserId] = useState('');

    useEffect(() => {
        if (isNaN(userId)) {
            return;
        }
        fetchShifts();
    }, []);

    useEffect(() => {

        console.log("shiftText", shiftText);
        fetchShifts();
    }, [shiftText]);

    const fetchShifts = () => {
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
                <h1 className="text-2xl font-bold">My shifts</h1>
            </div>
            <div>
                <input
                    type="text"
                    value={userId}
                    onChange={(e) => setUserId(Number(e.target.value))}
                    className="border border-gray-400 rounded-md p-2 w-full mt-4"
                    placeholder="Enter your user ID"
                />
                <button
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    onClick={fetchShifts}
                >
                    Reload
                </button>
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
                        userId={shift.userId}
                        status={shift.status}
                        setShiftText={setShiftText} // Pass the refresh function
                        refreshShifts={fetchShifts} // Pass the refresh
                    />
                ))}
            </div>
        </div>
    );
};

export default ShiftOverview;
