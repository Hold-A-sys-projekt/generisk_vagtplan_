/* eslint-disable react/prop-types */
import React, {useEffect, useState} from 'react';
import ShiftEdit from './ShiftEdit';

const ManageShift = ({ id, shiftStart, shiftEnd, punchIn, punchOut, userId, status, setShiftText, refreshShifts }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [punched, setPunched] = useState('in');
    const [completed, setCompleted] = useState(false);

    useEffect(() => {
        if (punchIn === null) {
            setPunched('in');
        } else if (punchOut === null) {
            setPunched('out');
        } else {
            setPunched('none');
            setCompleted(true);
        }
    }, [punchIn, punchOut]);

    const punchHandler = () => {
        fetch(`http://localhost:7070/api/shifts/${id}/punch-${punched}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
        }).then((res) => {
            setShiftText("Punching in/out...");
            refreshShifts();
        });
    };

    const handleSave = (updatedShift) => {
        // API call to save the updated shift
        console.log('Updated shift IS NOW IN THE MANAGER :', updatedShift)
        fetch(`http://localhost:7070/api/managers/employees/${userId}/shifts/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedShift)
        })
            .then((res) => {
                setShiftText("Saving...");
                refreshShifts();
            })
            .catch((err) => {
                setShiftText("Server error. Please try again later.");
            });
    };

    return (
        <div className={`border border-gray-400 rounded-md p-4 mb-4`}>
            <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: "space-between", gap: "2%", placeContent: "center" }}>
                <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                    <div className="border-b px-2 py-1">ID: {id}</div>
                </div>
                <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                    <div className="border-b px-2 py-1">Shift Start: {shiftStart}</div>
                    <div className="border-b px-2 py-1">Shift End: {shiftEnd}</div>
                </div>
                <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                    <div className="border-b px-2 py-1">Punch In: {punchIn}</div>
                    <div className="border-b px-2 py-1">Punch Out: {punchOut}</div>
                </div>
                <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                    <div className="border-b px-2 py-1">Employee ID: {userId}</div>
                    <div className="border-b px-2 py-1">Status: {status}</div>
                </div>
                <div className="w-full sm:w-1/6">
                    <button
                        onClick={punchHandler}
                        className={`punchbutton bg-blue-500 text-white px-4 py-2 rounded-md mt-2 w-full ${completed ? 'bg-green-500' : ''}`}
                    >
                        {completed ? 'Completed' : `Punch ${punched}`}
                    </button>
                        <button
                            onClick={() => setIsEditing(true)}
                            className="editbutton bg-gray-500 text-white px-4 py-2 rounded-md mt-2 w-full"
                        >
                            Edit
                        </button>
                </div>
            </div>
            {isEditing && (
                <ShiftEdit
                    shift={{ id, shiftStart, shiftEnd, punchIn, punchOut, userId, status }}
                    onClose={() => setIsEditing(false)}
                    onSave={handleSave}
                    refreshShifts={refreshShifts}
                />
            )}
        </div>
    );
};

export default ManageShift;
