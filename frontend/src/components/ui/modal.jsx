import React, { useState } from "react";

function Modal({ isOpen, onClose, selectedDay }) {
  if (!isOpen) return null;

  // Dummy data for demonstration
  const initialSchedule = [
    { hour: "10:00 - 11:00", worker: "Peter" },
    { hour: "11:00 - 12:00", worker: "Brian" },
    { hour: "12:00 - 13:00", worker: "Anne" },
    { hour: "13:00 - 14:00", worker: "Charlie" },
    { hour: "14:00 - 15:00", worker: "Maria" },
    { hour: "15:00 - 16:00", worker: "Bastian" },
    // Add more shift data as needed
  ];

  const [schedule, setSchedule] = useState(initialSchedule);
  const [isEditMode, setIsEditMode] = useState(false);

  const handleEdit = () => {
    setIsEditMode(true);
  };

  const handleRemoveWorker = (index) => {
    const updatedSchedule = [...schedule];
    updatedSchedule[index].worker = ""; // Remove worker from shift
    setSchedule(updatedSchedule);
  };

  const handleSwapWorker = (index) => {
    // Implement swap functionality here
    alert("Swap functionality to be implemented");
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50">
      <div className="bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-lg font-semibold mb-4">
          Shifts for {selectedDay.toLocaleDateString()}
        </h2>
        <div className="space-y-4">
          {schedule.map((shift, index) => (
            <div key={index} className="border border-gray-300 p-2 rounded-md">
              <p className="text-sm font-semibold">{shift.hour}</p>
              {isEditMode ? (
                <div className="flex justify-between">
                  <p className="text-sm">Worker: {shift.worker}</p>
                  <div>
                    <button
                      onClick={() => handleRemoveWorker(index)}
                      className="mr-2 bg-red-500 hover:bg-red-600 text-white font-semibold py-1 px-2 rounded"
                    >
                      Remove
                    </button>
                    <button
                      onClick={() => handleSwapWorker(index)}
                      className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-1 px-2 rounded"
                    >
                      Swap
                    </button>
                  </div>
                </div>
              ) : (
                <p className="text-sm">Worker: {shift.worker}</p>
              )}
            </div>
          ))}
        </div>
        <div className="flex justify-between mt-4">
          <button
            onClick={onClose}
            className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded"
          >
            Close
          </button>
          <button
            onClick={handleEdit}
            className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-2 px-4 rounded"
          >
            Edit
          </button>
        </div>
      </div>
    </div>
  );
}

export default Modal;
