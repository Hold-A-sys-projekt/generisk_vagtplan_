import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { HoverCard, HoverCardContent, HoverCardTrigger } from '@/components/ui/hover-card';
import { Button } from '@/components/ui/button';
import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar';
import { CalendarDays } from 'lucide-react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function Calendar() {
  const [shifts, setShifts] = useState([]);
  const [clickedShifts, setClickedShifts] = useState([]);
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [selectedUserShifts, setSelectedUserShifts] = useState([]);
  const userId = '1'; // The hardcoded user ID

  useEffect(() => {
    fetchShifts();
  }, [userId]);

  const parseDateArray = (dateArray) => {
    return new Date(
      dateArray[0], // year
      dateArray[1] - 1, // month (0-based index)
      dateArray[2], // day
      dateArray[3], // hour
      dateArray[4] // minute
    );
  };

  const fetchShifts = async () => {
    console.log('Fetching shifts for user:', userId);
    try {
      const response = await fetch(`http://localhost:7070/api/shifts/user/${userId}`);
      console.log('Response:', response);

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      console.log('Data:', data);

      const formattedShifts = data.map(shift => ({
        ...shift,
        title: shift.title || 'Shift',
        start: parseDateArray(shift.shiftStart).toISOString(),
        end: parseDateArray(shift.shiftEnd).toISOString(),
        extendedProps: { shift }
      }));
      console.log('Formatted Shifts:', formattedShifts);

      setShifts(formattedShifts);
    } catch (error) {
      console.error('Error fetching shifts:', error);
    }
  };

  const handleEventClick = async (arg) => {
    const clickedShiftData = arg.event.extendedProps.shift;
    console.log('Clicked shift data:', clickedShiftData);
    setClickedShifts([clickedShiftData]);
    if (clickedShiftData.userRole) {
      fetchUsersWithSameRole(clickedShiftData.userRole);
    } else {
      console.error('No userRole found in clicked shift data');
      setUsers([]);
    }
  };

  const fetchUsersWithSameRole = async (role) => {
    console.log('Fetching users with role:', role);
    try {
      const response = await fetch(`http://localhost:7070/api/users/role?role=${role}`);
      if (!response.ok) {
        throw new Error('Failed to fetch users');
      }
      const data = await response.json();
      console.log('Fetched users:', data);
      setUsers(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error('Error fetching users:', error);
      setUsers([]);
    }
  };

  const handleUserChange = async (event) => {
    const userId = event.target.value;
    setSelectedUser(userId);
    fetchUserShifts(userId);
  };

  const fetchUserShifts = async (userId) => {
    console.log('Fetching shifts for selected user:', userId);
    try {
      const response = await fetch(`http://localhost:7070/api/shifts/user/${userId}`);
      const data = await response.json();
      console.log('Data for selected user:', data);

      const formattedShifts = data.map(shift => ({
        ...shift,
        title: shift.title || 'Shift',
        start: parseDateArray(shift.shiftStart).toISOString(),
        end: parseDateArray(shift.shiftEnd).toISOString(),
        extendedProps: { shift }
      }));
      console.log('Formatted Shifts for selected user:', formattedShifts);

      setSelectedUserShifts(formattedShifts);
    } catch (error) {
      console.error('Error fetching shifts for selected user:', error);
    }
  };

  const handleSelectedUserShiftClick = async (arg) => {
    const clickedShiftData = arg.event.extendedProps.shift;
    console.log('Selected user shift ID:', clickedShiftData.id);
    setClickedShifts(prevShifts => [...prevShifts, clickedShiftData]);
  };

  const handleSelectShiftsClick = async () => {
    if (clickedShifts.length < 2) {
      console.error('Select at least two shifts');
      return;
    }
    const shiftIds = clickedShifts.map(shift => shift.id);
    console.log('Selected shift IDs:', shiftIds);
    try {
      const response = await fetch('http://localhost:7070/api/swaprequests', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          shift1: { id: shiftIds[0] },
          shift2: { id: shiftIds[1] },
          isAccepted: "",
          requestedUserId: 2 // Hardcoded requested user ID
        }),
      });
      if (!response.ok) {
        throw new Error('Failed to create swap request');
      }
      const data = await response.json();
      console.log('Swap request created:', data);
      toast.success('Swap request sent successfully');
    } catch (error) {
      console.error('Error creating swap request:', error);
      toast.error('Failed to send swap request');
    }
  };

  return (
    <div className="relative">
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        weekends={true}
        events={shifts}
        eventClick={handleEventClick}
      />
      {clickedShifts.length > 0 && (
        <div className="absolute top-0 left-0 bg-white p-4 shadow-lg rounded-lg space-y-4">
          {clickedShifts.map((shift, index) => (
            <HoverCardDemo key={index} shift={shift} />
          ))}
          <select onChange={handleUserChange} value={selectedUser || ''}>
            <option value="" disabled>Select a user</option>
            {Array.isArray(users) && users.map(user => (
              <option key={user.id} value={user.id}>{user.username}</option>
            ))}
          </select>
          {clickedShifts.length >= 2 && (
            <Button onClick={handleSelectShiftsClick}>Send Swap Request</Button>
          )}
        </div>
      )}
      {selectedUser && (
        <div className="mt-8">
          <h2 className="text-lg font-bold">
            {users.find(user => user.id === parseInt(selectedUser))?.username}'s Shifts
          </h2>
          <FullCalendar
            plugins={[dayGridPlugin, interactionPlugin]}
            initialView="dayGridMonth"
            weekends={true}
            events={selectedUserShifts}
            eventClick={handleSelectedUserShiftClick}
          />
        </div>
      )}
      <ToastContainer />
    </div>
  );
}

function HoverCardDemo({ shift }) {
  const startDate = new Date(
    shift.shiftStart[0],
    shift.shiftStart[1] - 1,
    shift.shiftStart[2],
    shift.shiftStart[3],
    shift.shiftStart[4]
  ).toLocaleString();

  const endDate = new Date(
    shift.shiftEnd[0],
    shift.shiftEnd[1] - 1,
    shift.shiftEnd[2],
    shift.shiftEnd[3],
    shift.shiftEnd[4]
  ).toLocaleString();

  return (
    <HoverCard open>
      <HoverCardTrigger asChild>
        <Button variant="link">{shift.title}</Button>
      </HoverCardTrigger>
      <HoverCardContent className="w-80">
        <div className="flex justify-between space-x-4">
          <Avatar>
            <AvatarImage src="https://github.com/vercel.png" />
            <AvatarFallback>VC</AvatarFallback>
          </Avatar>
          <div className="space-y-1">
            <h4 className="text-sm font-semibold">{shift.title}</h4>
            <p className="text-sm">
              Employee: {shift.userName || 'Unknown'}
            </p>
            <p className="text-sm">
              Role: {shift.userRole || 'Unknown'}
            </p>
            <p className="text-sm">
              Shift Details
            </p>
            <div className="flex items-center pt-2">
              <CalendarDays className="mr-2 h-4 w-4 opacity-70" />{" "}
              <span className="text-xs text-muted-foreground">
                Start: {startDate}
              </span>
            </div>
            <div className="flex items-center pt-2">
              <CalendarDays className="mr-2 h-4 w-4 opacity-70" />{" "}
              <span className="text-xs text-muted-foreground">
                End: {endDate}
              </span>
            </div>
          </div>
        </div>
      </HoverCardContent>
    </HoverCard>
  );
}
