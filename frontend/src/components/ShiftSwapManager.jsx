import React, { useState, useEffect } from 'react';
import axios from 'axios';

const formatDate = (dateArray) => {
    const [year, month, day] = dateArray;
    return `${day}/${month}/${year}`;
};

const formatTime = (dateArray) => {
    const hour = dateArray[3];
    const minute = dateArray[4];
    return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
};

const ShiftSwapManager = () => {
    const [shiftSwaps, setShiftSwaps] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:7070/api/swapshifts')
            .then(response => {
                const sortedData = response.data.sort((a, b) => {
                    if (a.isAccepted === 'Pending' && b.isAccepted !== 'Pending') return -1;
                    if (a.isAccepted !== 'Pending' && b.isAccepted === 'Pending') return 1;
                    return 0;
                });
                setShiftSwaps(sortedData.map(swap => ({
                    id: swap.shiftId,
                    shift1Day: formatDate(swap.shift1.startTime),
                    shift1User: swap.shift1.user.username,
                    shift1Time: `${formatTime(swap.shift1.startTime)} - ${formatTime(swap.shift1.endTime)}`,
                    shift2Day: formatDate(swap.shift2.startTime),
                    shift2User: swap.shift2.user.username,
                    shift2Time: `${formatTime(swap.shift2.startTime)} - ${formatTime(swap.shift2.endTime)}`,
                    isAccepted: swap.isAccepted || 'Pending'
                })));
            })
            .catch(error => console.error('There was an error fetching the shift swaps', error));
    }, []);

    const handleSwapApproval = (index, isApproved) => {
        axios.post(`http://localhost:7070/api/swapshifts/${shiftSwaps[index].id}/approve`, { isAccepted: isApproved ? 'Approved' : 'Not Approved' })
            .then(() => {
                setShiftSwaps(prevSwaps => {
                    const newSwaps = [...prevSwaps];
                    newSwaps[index].isAccepted = isApproved ? 'Approved' : 'Not Approved';
                    return newSwaps.sort((a, b) => {
                        if (a.isAccepted === 'Pending' && b.isAccepted !== 'Pending') return -1;
                        if (a.isAccepted !== 'Pending' && b.isAccepted === 'Pending') return 1;
                        return 0;
                    });
                });
            })
            .catch(error => {
                console.error(`Failed to ${isApproved ? 'approve' : 'decline'} shift swap`, error);
            });
    };

    return (
        <div className="max-w-6xl mx-auto mt-10">
            <h1 className="text-2xl font-bold text-center mb-6">Shift Swap Requests</h1>
            <table className="min-w-full divide-y divide-gray-200">
                <thead>
                <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift 1 Day</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift 1 User</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift 1 Time</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift 2 Day</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift 2 User</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift 2 Time</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {shiftSwaps.map((swap, index) => (
                    <tr key={index} className={`${swap.isAccepted !== 'Pending' ? 'bg-gray-200' : ''}`}>
                        <td className="px-6 py-4 whitespace-nowrap">{swap.shift1Day}</td>
                        <td className="px-6 py-4 whitespace-nowrap">{swap.shift1User}</td>
                        <td className="px-6 py-4 whitespace-nowrap">{swap.shift1Time}</td>
                        <td className="px-6 py-4 whitespace-nowrap">{swap.shift2Day}</td>
                        <td className="px-6 py-4 whitespace-nowrap">{swap.shift2User}</td>
                        <td className="px-6 py-4 whitespace-nowrap">{swap.shift2Time}</td>
                        <td className="px-6 py-4 whitespace-nowrap">
                <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${swap.isAccepted === 'Approved' ? 'bg-green-100 text-green-800' : (swap.isAccepted === 'Not Approved' ? 'bg-red-100 text-red-800' : 'bg-gray-100 text-gray-500')}`}>
                  {swap.isAccepted}
                </span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                            <button onClick={() => handleSwapApproval(index, true)} disabled={swap.isAccepted !== 'Pending'} className={`text-white bg-green-500 hover:bg-green-600 font-medium rounded-lg text-sm px-3 py-1 mr-2 ${swap.isAccepted !== 'Pending' ? 'opacity-50 cursor-not-allowed' : ''}`}>Approve</button>
                            <button onClick={() => handleSwapApproval(index, false)} disabled={swap.isAccepted !== 'Pending'} className={`text-white bg-red-500 hover:bg-red-600 font-medium rounded-lg text-sm px-3 py-1 ${swap.isAccepted !== 'Pending' ? 'opacity-50 cursor-not-allowed' : ''}`}>Decline</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShiftSwapManager;
