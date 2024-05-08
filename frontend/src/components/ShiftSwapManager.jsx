

 
 import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ShiftSwapManager = () => {
  const [shiftSwaps, setShiftSwaps] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:7070/api/swapshift') // CHANGE TO CORRECT ENDPOINT FETCH
      .then(response => setShiftSwaps(response.data))
      .catch(error => console.error('There was an error fetching the shift swaps', error));
  }, []);

  const handleSwapApproval = (index, isApproved) => {
    const swap = shiftSwaps[index];
    const updatedSwaps = [...shiftSwaps];
    updatedSwaps[index].approved = isApproved.toString(); // Optimistic update

    axios.post(`Correct_endpoint/${swap.id}`, { approved: isApproved })
      .then(() => {
        setShiftSwaps(updatedSwaps); // Confirm update
      })
      .catch(error => {
        console.error(`Failed to ${isApproved ? 'approve' : 'decline'} shift swap`, error);
        updatedSwaps[index].approved = swap.approved; // Revert on error
        setShiftSwaps(updatedSwaps);
      });
  };

  
  return (
    <div className="max-w-4xl mx-auto mt-10">
      <h1 className="text-2xl font-bold text-center mb-6">Shift Swap Requests</h1>
      <table className="table-auto w-full text-left whitespace-no-wrap">
        <thead>
          <tr className="text-xs font-semibold tracking-wide text-gray-500 uppercase border-b bg-gray-50">
            <th className="px-4 py-3">Day</th>
            <th className="px-4 py-3">From Employee ID</th>
            <th className="px-4 py-3">To Employee ID</th>
            <th className="px-4 py-3">Approved</th>
            <th className="px-4 py-3">Actions</th>
          </tr>
        </thead>
        <tbody>
          {shiftSwaps.map((swap, index) => (
            <tr key={index} className="bg-white border-b">
              <td className="px-4 py-3">{swap.day_request_swap}</td>
              <td className="px-4 py-3">{swap.employee_requested_from_id}</td>
              <td className="px-4 py-3">{swap.employee_requested_to_id}</td>
              <td className={`px-4 py-3 ${swap.approved === 'true' ? 'text-green-500' : 'text-red-500'}`}>
                {swap.approved === 'true' ? 'Approved' : 'Not approved'}
              </td>
              <td className="px-4 py-3 flex justify-start space-x-2">
                <button onClick={() => handleSwapApproval(index, true)} className="text-white bg-green-500 hover:bg-green-600 font-medium rounded-lg text-sm px-3 py-1 text-center">Approve</button>
                <button onClick={() => handleSwapApproval(index, false)} className="text-white bg-red-500 hover:bg-red-600 font-medium rounded-lg text-sm px-3 py-1 text-center">Decline</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ShiftSwapManager; 