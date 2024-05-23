import React, { useEffect, useState } from 'react';
import facade from "@/utils/apiFacade";

const AssignModal = ({ isOpen, onClose, employees, selectedDay }) => {
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [selectedEmployee, setSelectedEmployee] = useState('');
    const [payload, setPayload] = useState('')

    const nextDay = new Date(selectedDay);
    nextDay.setDate(selectedDay.getDate() + 1);

    const handleStartTimeChange = (event) => {
        setStartTime(event.target.value);
    };

    const handleEndTimeChange = (event) => {
        setEndTime(event.target.value);
    };

    const handleEmployeeChange = (event) => {
        setSelectedEmployee(event.target.value);
    };

    const handleSaveShift = () => {
        const formattedStartTime = startTime ? `${nextDay.toISOString().split('T')[0]}T${startTime}:00` : '';
        const formattedEndTime = endTime ? `${nextDay.toISOString().split('T')[0]}T${endTime}:00` : '';

        setPayload({
            shiftStart: formattedStartTime,
            shiftEnd: formattedEndTime
        });
        alert(`Shift assigned successfully`)
    };

    useEffect(() => {
        if (payload) {
            facade
                .fetchData('shifts?user_id=' + selectedEmployee, 'POST', payload)
                .then((response) => {
                    console.log("Response:", response);
                    onClose();
                })
                .catch((error) => {
                    console.error("Error:", error);
                });
        }
    }, [payload, onClose]);



    return (
        <div className={`fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50 ${isOpen ? 'block' : 'hidden'}`}>
            <div className="bg-white p-6 rounded-lg shadow-lg">
                <h2>Assign Employee - {nextDay.toDateString()}</h2>
                <div className="flex flex-col mb-4">
                    <label htmlFor="startTime" className="mb-1">Start Time:</label>
                    <input
                        type="time"
                        id="startTime"
                        className="outline-none border border-gray-300 px-3 py-2 rounded-md focus:border-blue-500"
                        value={startTime}
                        onChange={handleStartTimeChange}
                    />
                </div>
                <div className="flex flex-col mb-4">
                    <label htmlFor="endTime" className="mb-1">End Time:</label>
                    <input
                        type="time"
                        id="endTime"
                        className="outline-none border border-gray-300 px-3 py-2 rounded-md focus:border-blue-500"
                        value={endTime}
                        onChange={handleEndTimeChange}
                    />
                </div>
                <div className="flex flex-col">
                    <label htmlFor="worker" className="mb-1">Select Employee:</label>
                    <select
                        id="worker"
                        className="outline-none border border-gray-300 px-3 py-2 rounded-md focus:border-blue-500"
                        value={selectedEmployee}
                        onChange={handleEmployeeChange}
                    >
                        <option value="">Select Employee</option>
                        {employees.map((employee, index) => (
                            <option key={index} value={employee.id}>{employee.name}</option>
                        ))}
                    </select>

                </div>
                <br />
                <div className="flex justify-between mb-4">
                    <button
                        className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-2 px-4 rounded"
                        onClick={onClose}
                    >
                        Close
                    </button>
                    <button
                        className="bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded"
                        onClick={handleSaveShift}
                    >
                        Save Shift
                    </button>
                </div>
            </div>
        </div>
    );
};

export default AssignModal;
