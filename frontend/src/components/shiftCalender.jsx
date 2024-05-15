import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';


export default function Calendar() {
  const [shifts, setShifts] = useState([]);
  const employeeId = '1'; // The hardcoded employee ID

  useEffect(() => {
    const parseDateArray = (dateArray) => {
      return new Date(
        dateArray[0], // år
        dateArray[1] - 1, // moåned
        dateArray[2], // dag
        dateArray[3], // timer
        dateArray[4], // minuter
        dateArray[5] || 0, // sek 
        dateArray[6] || 0 // millisek
      ).toISOString();
    };

    const fetchShifts = async () => {
      try {
        const response = await fetch(`http://localhost:7070/api/shifts/employee/${employeeId}`);
        const data = await response.json();

        console.log('Fetched shifts:', data);

        const formattedShifts = data.map(shift => ({
          title: shift.title || 'Shift',
          start: parseDateArray(shift.shiftStart),
          end: parseDateArray(shift.shiftEnd)
        }));

        console.log('Formatted shifts:', formattedShifts);

        setShifts(formattedShifts);
      } catch (error) {
        console.error('Error fetching shifts:', error);
      }
    };

    fetchShifts();
  }, [employeeId]);

  const handleDateClick = (arg) => {
    alert(`Clicked on date: ${arg.date}`);
  };

  return (
    <FullCalendar
      plugins={[dayGridPlugin, interactionPlugin]}
      initialView="dayGridMonth"
      weekends={true}
      events={shifts}
      dateClick={handleDateClick}
    />
  );
}
