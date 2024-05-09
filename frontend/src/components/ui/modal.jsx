import React, { useState, useEffect } from "react";
import facade from "@/util/apiFacade";

function Modal({ isOpen, onClose, selectedDay }) {
  const [shifts, setShifts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [schedule, setSchedule] = useState([]);
  const [isEditMode, setIsEditMode] = useState(false);
  const [removedIndices, setRemovedIndices] = useState([]);
  const [workers, setWorkers] = useState([])

  useEffect(() => {
    const fetchData = () => {
      facade.fetchData('shifts', 'GET')
        .then((response) => {
          setShifts(response);
        })
        .finally(() => {
          setLoading(false);
        });
    };

    fetchData();
  }, []);

  const selectedDayArray = [
    selectedDay.getFullYear(),
    selectedDay.getMonth() + 1,
    selectedDay.getDate()
  ];

  useEffect(() => {
    if (shifts.length > 0) {
      console.log(shifts[0].shiftStart);
      console.log(selectedDayArray);

      const shiftStartDate = new Date(
        shifts[0].shiftStart[0],
        shifts[0].shiftStart[1] - 1,
        shifts[0].shiftStart[2]
      );
      shiftStartDate.setHours(0, 0, 0, 0);

      const selectedDayDate = new Date(selectedDay);
      selectedDayDate.setHours(0, 0, 0, 0);

      if (shiftStartDate.getTime() === selectedDayDate.getTime()) {
        setWorkers([...workers, shifts[0].employeeId]);
      }

      const updatedSchedule = [
        { hour: "10:00 - 11:00", worker: "Peter" },
        { hour: "11:00 - 12:00", worker: "Brian" },
        { hour: "12:00 - 13:00", worker: "Anne" },
        { hour: "13:00 - 14:00", worker: "Charlie" },
        { hour: "14:00 - 15:00", worker: "Maria" },
        { hour: "15:00 - 16:00", worker: "Bastian" },
        { hour: "10", worker: workers[0]}
      ];
      setSchedule(updatedSchedule);
    }
  }, [shifts, selectedDayArray]);

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
    alert("Swap functionality to be implemented");
  };

  const handleAssignWorker = (index) => {
    alert("Assign functionality to be implemented");
  };

  const getShiftClasses = (index) => {
    const hasWorker = schedule[index].worker !== "";
    const isRemoved = removedIndices.includes(index);
    return hasWorker ? "bg-white" : isRemoved ? "bg-red-200" : "bg-red-200";
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (shifts.length === 0) {
    return <div>No shifts available.</div>;
  }

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
