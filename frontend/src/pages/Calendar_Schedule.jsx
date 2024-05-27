import React, { useState, useEffect } from "react";
import { Calendar, dateFnsLocalizer } from "react-big-calendar";
import format from "date-fns/format";
import parseISO from "date-fns/parseISO";
import startOfWeek from "date-fns/startOfWeek";
import getDay from "date-fns/getDay";
import enUS from "date-fns/locale/en-US";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-big-calendar/lib/css/react-big-calendar.css";
import "../App.css";
import { getAllShiftsFromAUser, updateShiftStatus } from "../lib/shiftFacade";
import { useParams } from "react-router-dom";

const locales = { "en-US": enUS };

const localizer = dateFnsLocalizer({
  format,
  parse: parseISO,
  startOfWeek: () => startOfWeek(new Date()),
  getDay,
  locales,
});

function CalendarSchedule() {
  const [events, setEvents] = useState([]);
  const [selectedDateEvents, setSelectedDateEvents] = useState([]);
  const [userId, setUserId] = useState(0); // Hardcoded user id for now

  useEffect(() => {
    const fetchShifts = async () => {
      try {
        console.log(`Fetching shifts for user: ${userId}`);
        const data = await getAllShiftsFromAUser(userId);
        console.log("Fetched shifts data:", data);
        const formattedEvents = data.map((shift) => {
          const startDateTime = new Date(
            shift.shiftStart[0],
            shift.shiftStart[1] - 1,
            shift.shiftStart[2],
            shift.shiftStart[3],
            shift.shiftStart[4]
          ).toISOString();
          const endDateTime = new Date(
            shift.shiftEnd[0],
            shift.shiftEnd[1] - 1,
            shift.shiftEnd[2],
            shift.shiftEnd[3],
            shift.shiftEnd[4]
          ).toISOString();

          const shiftColors = {
            COVERED: "#4CAF50",
            PENDING: "#FFC107",
            FOR_SALE: "#F44336",
          };

          const color = shiftColors[shift.status] || "#2196F3";

          return {
            id: shift.id,
            title: `Arbejde: ${shift.status}`,
            start: parseISO(startDateTime),
            end: parseISO(endDateTime),
            allDay: false,
            type: shift.status,
            color,
          };
        });
        console.log("Formatted events:", formattedEvents);
        setEvents(formattedEvents);
      } catch (error) {
        toast.error("Failed to fetch events");
        console.error("Failed to fetch events:", error);
      }
    };

    fetchShifts();
  }, [userId]);

  const handleSelect = ({ start }) => {
    const selectedDate = format(start, "yyyy-MM-dd");
    const filteredEvents = events.filter(
      (event) => format(event.start, "yyyy-MM-dd") === selectedDate
    );
    setSelectedDateEvents(filteredEvents);
  };

  const handleChangeShiftStatus = async (eventId, newStatus) => {
    const event = events.find((e) => e.id === eventId);
    if (event) {
      try {
        await updateShiftStatus(eventId, newStatus);
        const shiftColors = {
          COVERED: "#4CAF50",
          PENDING: "#FFC107",
          FOR_SALE: "#F44336",
        };

        setEvents((prevEvents) =>
          prevEvents.map((e) =>
            e.id === eventId
              ? { ...e, type: newStatus, color: shiftColors[newStatus] }
              : e
          )
        );
        toast.success(`Shift status changed to '${newStatus}' successfully!`);
      } catch (error) {
        toast.error("Failed to change shift status");
        console.error("Failed to change shift status:", error);
      }
    }
  };

  const eventStyleGetter = (event) => ({
    style: {
      backgroundColor: event.color,
      color: "white",
      borderRadius: "0px",
      border: "none",
    },
  });

  return (
    <div className="calendar-container flex">
      <div style={{ flex: 2 }}>
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          selectable
          onSelectSlot={handleSelect}
          eventPropGetter={eventStyleGetter}
          style={{ height: "100vh", width: "100%" }}
          views={["month", "week", "day", "agenda"]}
        />
      </div>
      <div className="event-list mt-4" style={{ flex: 1, padding: "0 20px" }}>
        <div>
          <h2>All Shifts</h2>
          <ul>
            {events.map((event) => (
              <li
                key={event.id}
                className="mb-4 p-4 border rounded-lg shadow-lg"
              >
                <div className="flex justify-between items-center">
                  <span className="font-semibold">{event.title}</span>
                  <button
                    style={{ backgroundColor: event.color, color: "white" }}
                    onClick={() =>
                      handleChangeShiftStatus(
                        event.id,
                        event.type === "FOR_SALE" ? "COVERED" : "FOR_SALE"
                      )
                    }
                  >
                    {event.type === "FOR_SALE" ? "Cancel" : "For Sale"}
                  </button>
                </div>
                <div>
                  <span>Start: {format(event.start, "Pp")}</span>
                  <br />
                  <span>End: {format(event.end, "Pp")}</span>
                </div>
              </li>
            ))}
          </ul>
        </div>
        <div>
          <h2>Selected Date Events</h2>
          <ul>
            {selectedDateEvents.map((event) => (
              <li
                key={event.id}
                className="mb-4 p-4 border rounded-lg shadow-lg"
              >
                <div className="flex justify-between items-center">
                  <span className="font-semibold">{event.title}</span>
                  <button style={{ backgroundColor: "red", color: "white" }}>
                    William
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>
      <ToastContainer position="top-center" autoClose={5000} />
    </div>
  );
}

export default CalendarSchedule;
