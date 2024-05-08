import React, { useState } from "react";

function Modal({ isOpen, onClose, selectedDay }) {
  const [schedule, setSchedule] = useState([
    { hour: "10:00 - 11:00", worker: "Peter" },
    { hour: "11:00 - 12:00", worker: "Brian" },
    { hour: "12:00 - 13:00", worker: "Anne" },
    { hour: "13:00 - 14:00", worker: "Charlie" },
    { hour: "14:00 - 15:00", worker: "Maria" },
    { hour: "15:00 - 16:00", worker: "Bastian" },
    // This is just dummy data for now
  ]);
  const [isEditMode, setIsEditMode] = useState(false);
  const [removedIndices, setRemovedIndices] = useState([]);

  const handleEdit = () => {
    setIsEditMode(true);
  };

  const handleBack = () => {
    setIsEditMode(false);
    setRemovedIndices([]);
  };

  const handleRemoveWorker = (index) => {
    const updatedSchedule = [...schedule];
    updatedSchedule[index].worker = "";
    setSchedule(updatedSchedule);
    setRemovedIndices([...removedIndices, index]);
  };

  const handleSwapWorker = (index) => {
    // Implement swap functionality here TBD
    alert("Swap functionality to be implemented");
  };

  const handleAssignWorker = (index) => {
    // Implement assign functionality here TBD
    alert("Assign functionality to be implemented");
  };

  const getShiftClasses = (index) => {
    const hasWorker = schedule[index].worker !== "";
    const isRemoved = removedIndices.includes(index);
    return hasWorker ? "bg-white" : isRemoved ? "bg-red-200" : "bg-red-200";
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-900 bg-opacity-50">
      <div className="bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-lg font-semibold mb-4">
          Shifts for {selectedDay.toLocaleDateString()}
        </h2>
        <div className="space-y-4">
          {schedule.map((shift, index) => (
            <div
              key={index}
              className={`border border-gray-300 p-2 rounded-md ${getShiftClasses(
                index
              )}`}
            >
              <p className="text-sm font-semibold">{shift.hour}</p>
              {isEditMode ? (
                <div className="flex justify-between items-center">
                  <p className="text-sm">Worker: {shift.worker}</p>
                  <div className="space-x-2">
                    {!removedIndices.includes(index) && (
                      <button
                        onClick={() => handleRemoveWorker(index)}
                        className="bg-red-500 hover:bg-red-600 text-white font-semibold py-1 px-2 rounded"
                      >
                        Remove
                      </button>
                    )}
                    {removedIndices.includes(index) && (
                      <button
                        onClick={() => handleAssignWorker(index)}
                        className="bg-green-500 hover:bg-green-600 text-white font-semibold py-1 px-2 rounded"
                      >
                        Assign
                      </button>
                    )}
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
        <div className="mt-4 flex justify-between">
          {isEditMode ? (
            <button
              onClick={handleBack}
              className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-2 px-4 rounded"
            >
              Back
            </button>
          ) : (
            <>
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
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default Modal;
