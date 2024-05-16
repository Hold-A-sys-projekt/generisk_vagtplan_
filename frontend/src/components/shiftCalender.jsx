import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

import { HoverCard, HoverCardContent, HoverCardTrigger } from '@/components/ui/hover-card';
import { Button } from '@/components/ui/button';
import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar';
import { CalendarDays } from 'lucide-react';

export default function Calendar() {
  const [shifts, setShifts] = useState([]);
  const [clickedShift, setClickedShift] = useState(null);
  const employeeId = '1'; // The hardcoded employee ID

  useEffect(() => {
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
      try {
        const response = await fetch(`http://localhost:7070/api/shifts/employee/${employeeId}`);
        const data = await response.json();

        console.log('Fetched shifts:', data);

        const formattedShifts = data.map(shift => ({
          ...shift,
          title: shift.title || 'Shift',
          start: parseDateArray(shift.shiftStart).toISOString(),
          end: parseDateArray(shift.shiftEnd).toISOString(),
          extendedProps: {
            shift
          }
        }));

        console.log('Formatted shifts:', formattedShifts);

        setShifts(formattedShifts);
      } catch (error) {
        console.error('Error fetching shifts:', error);
      }
    };

    fetchShifts();
  }, [employeeId]);

  const handleEventClick = (arg) => {
    console.log('Event clicked:', arg);
    const clickedShiftData = arg.event.extendedProps.shift;
    console.log('Clicked shift:', clickedShiftData);
    setClickedShift(clickedShiftData);
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
      {clickedShift && (
        <div className="absolute top-0 left-0">
          <HoverCardDemo shift={clickedShift} />
        </div>
      )}
    </div>
  );
}

function HoverCardDemo({ shift }) {
  console.log('Rendering HoverCardDemo with shift:', shift);

  // Parse the dates from the shift object
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
