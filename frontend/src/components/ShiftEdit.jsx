/* eslint-disable react/prop-types */
import React, { useState } from 'react';

const ShiftEdit = ({ shift, onClose, onSave, refreshShifts}) => {
    const [shiftStart, setShiftStart] = useState(shift.shiftStart);
    const [shiftEnd, setShiftEnd] = useState(shift.shiftEnd);
    const [punchIn, setPunchIn] = useState(shift.punchIn);
    const [punchOut, setPunchOut] = useState(shift.punchOut);

    const handleSubmit = (e) => {
        e.preventDefault();


        if (shiftStart === "" ) {
            setShiftStart(null);
        }
        if (shiftEnd === "" ) {
            setShiftEnd(null);
        }
        if (punchIn === "" ) {
            setPunchIn(null);
        }
        if (punchOut === "" ) {
            setPunchOut(null);
        }

        const updatedShift = {
            ...shift,
            shiftStart: shiftStart || null,
            shiftEnd: shiftEnd || null,
            punchIn: punchIn || null,
            punchOut: punchOut || null,
        };


        console.log('Updated shift IS :', updatedShift);

        // Call the save function passed from parent
        onSave(updatedShift);
        refreshShifts();
        onClose();
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-4 rounded-md w-1/3">
                <h2 className="text-xl font-bold mb-4">Edit Shift</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block mb-1">Shift Start:</label>
                        <input
                            type="text"
                            value={shiftStart}
                            onChange={(e) => setShiftStart(e.target.value)}
                            className="border border-gray-400 rounded-md p-2 w-full"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block mb-1">Shift End:</label>
                        <input
                            type="text"
                            value={shiftEnd}
                            onChange={(e) => setShiftEnd(e.target.value)}
                            className="border border-gray-400 rounded-md p-2 w-full"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block mb-1">Punch In:</label>
                        <input
                            type="text"
                            value={punchIn}
                            onChange={(e) => setPunchIn(e.target.value)}
                            className="border border-gray-400 rounded-md p-2 w-full"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block mb-1">Punch Out:</label>
                        <input
                            type="text"
                            value={punchOut}
                            onChange={(e) => setPunchOut(e.target.value)}
                            className="border border-gray-400 rounded-md p-2 w-full"
                        />
                    </div>
                    <div className="flex justify-end">
                        <button
                            type="button"
                            onClick={onClose}
                            className="bg-gray-500 text-white px-4 py-2 rounded-md mr-2"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            onClick={handleSubmit}
                            className="bg-blue-500 text-white px-4 py-2 rounded-md"
                        >
                            Save
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ShiftEdit;