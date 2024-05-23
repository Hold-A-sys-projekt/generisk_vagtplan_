import React, { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import NotificationButton from './NotificationButton';
import './ManageSwapRequests.css'; // Import the CSS file

export default function ManageSwapRequests({ userId }) {
  const [requests, setRequests] = useState([]);
  const [error, setError] = useState(null);
  const [showRequests, setShowRequests] = useState(false);

  useEffect(() => {
    fetchPendingRequests();
  }, []);

  const fetchPendingRequests = async () => {
    try {
      const response = await fetch(`http://localhost:7070/api/swaprequests/user/${userId}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      console.log('Fetched pending requests:', data);
      if (Array.isArray(data)) {
        setRequests(data);
      } else {
        throw new Error('Response data is not an array');
      }
    } catch (error) {
      console.error('Error fetching pending requests:', error);
      setError(error.message);
    }
  };

  const handleRequestAction = async (requestId, status) => {
    try {
      await fetch(`http://localhost:7070/api/swaprequests/${requestId}/approve?accepted=${status === 'Approved'}`, {
        method: 'POST',
      });
      fetchPendingRequests();
    } catch (error) {
      console.error(`Error updating request status to ${status}:`, error);
    }
  };

  const handleButtonClick = () => {
    setShowRequests(!showRequests);
  };

  if (error) {
    return <div className="error">Error: {error}</div>;
  }

  const formatDate = (dateArray) => {
    const date = new Date(
      dateArray[0], dateArray[1] - 1, dateArray[2],
      dateArray[3], dateArray[4]
    );
    const dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
    const timeOptions = { hour: '2-digit', minute: '2-digit', hour12: false };
    const formattedDate = date.toLocaleDateString(undefined, dateOptions);
    const formattedTime = date.toLocaleTimeString(undefined, timeOptions);
    return { formattedDate, formattedTime };
  };

  const formatShift = (shift) => {
    const { formattedDate: startDate, formattedTime: startTime } = formatDate(shift.shiftStart);
    const { formattedDate: endDate, formattedTime: endTime } = formatDate(shift.shiftEnd);

    if (startDate === endDate) {
      return `${startDate}, ${startTime} - ${endTime}`;
    }
    return `${startDate}, ${startTime} - ${endDate}, ${endTime}`;
  };

  return (
    <div className="manage-swap-requests-container">
      <NotificationButton count={requests.length} onClick={handleButtonClick} />
      {showRequests && (
        <div className="manage-swap-requests">
          <h2 className="title">Pending Swap Requests</h2>
          {requests.length === 0 ? (
            <p className="no-requests">No pending requests</p>
          ) : (
            <ul className="requests-list">
              {requests.map((request) => (
                request && request.id && request.shift1 && request.shift2 ? (
                  <li key={request.id} className="request-item">
                    <div className="request-info">
                      <span><strong>Shift 1:</strong> {formatShift(request.shift1)}</span>
                      <span><strong>Shift 2:</strong> {formatShift(request.shift2)}</span>
                      <span><strong>Status:</strong> {request.status}</span>
                    </div>
                    <div className="request-actions">
                      <Button className="approve-button" onClick={() => handleRequestAction(request.id, 'Approved')}>Approve</Button>
                      <Button className="decline-button" onClick={() => handleRequestAction(request.id, 'Declined')}>Decline</Button>
                    </div>
                  </li>
                ) : <li key={request.id || 'unknown'}>Invalid request data</li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
}
