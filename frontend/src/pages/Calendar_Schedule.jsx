import React, { useState } from 'react';
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import differenceInMinutes from 'date-fns/differenceInMinutes';
import startOfWeek from 'date-fns/startOfWeek';
import getDay from 'date-fns/getDay';
import enUS from 'date-fns/locale/en-US';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import './App.css';
import {addDays} from "date-fns";

const locales = {
    'en-US': enUS,
};

const localizer = dateFnsLocalizer({
    format,
    parse: parseISO,
    startOfWeek: () => startOfWeek(new Date()),
    getDay,
    locales,
});

function Calendar_Schedule() {
    const [events, setEvents] = useState([]);

    const handleSelect = ({ start, end }) => {
        const title = window.prompt('Enter new event name');
        if (!title) {
            toast.error("Event title is required!");
            return;
        }

        let startDate = format(start, 'yyyy-MM-dd');
        let startTime = window.prompt('Enter start time for the event (HH:mm)', '00:00');
        let endTime = window.prompt('Enter end time for the event (HH:mm)', '00:00');

        if (startTime && endTime) {
            let startDateTime = parseISO(`${startDate}T${startTime}`);
            let endDateTime = parseISO(`${startDate}T${endTime}`);

            if (endDateTime <= startDateTime) {
                endDateTime = addDays(endDateTime, 1);
            }

            const shiftType = window.prompt('Enter shift type (Covered, Pending, For Sale):');
            const shiftColors = {
                Covered: '#4CAF50',
                Pending: '#FFC107',
                ForSale: '#F44336'
            };

            const color = shiftColors[shiftType] || '#2196F3';

            const totalMinutes = differenceInMinutes(endDateTime, startDateTime);
            const hours = Math.floor(totalMinutes / 60);
            const minutes = totalMinutes % 60;
            const duration = `${hours}h ${minutes}m`;

            const newEvent = {
                id: events.length + 1,
                title: `${title} - ${shiftType} | ${format(startDateTime, 'HH:mm')} - ${format(endDateTime, 'HH:mm')} (${duration})`,
                start: startDateTime,
                end: endDateTime,
                allDay: false,
                type: shiftType,
                color: color
            };

            setEvents(prevEvents => [...prevEvents, newEvent]);
            toast.success("Event added successfully!");
        } else {
            toast.error("Valid start and end times are required!");
        }
    };

    const handleSelectEvent = (event) => {
        const editChoice = window.prompt('Do you want to edit or delete the event? (edit/delete)', 'edit');
        if (editChoice.toLowerCase() === 'delete') {
            setEvents(events.filter(e => e.id !== event.id));
            toast.success("Event deleted successfully!");
            return;
        } else if (editChoice.toLowerCase() === 'edit') {
            const newTitle = window.prompt('Edit event name:', event.title.split(' - ')[0]);
            const newStartTime = window.prompt('Edit start time (HH:mm)', format(event.start, 'HH:mm'));
            const newEndTime = window.prompt('Edit end time (HH:mm)', format(event.end, 'HH:mm'));
            const newType = window.prompt('Edit shift type (Covered, Pending, For Sale):', event.type);

            if (newStartTime && newEndTime && newType) {
                let startDateTime = parseISO(`${format(event.start, 'yyyy-MM-dd')}T${newStartTime}`);
                let endDateTime = parseISO(`${format(event.end, 'yyyy-MM-dd')}T${newEndTime}`);

                if (endDateTime <= startDateTime) {
                    endDateTime = addDays(endDateTime, 1);
                }

                const shiftColors = {
                    Covered: '#4CAF50',
                    Pending: '#FFC107',
                    For_Sale: '#F44336'
                };

                const color = shiftColors[newType];

                const totalMinutes = differenceInMinutes(endDateTime, startDateTime);
                const hours = Math.floor(totalMinutes / 60);
                const minutes = totalMinutes % 60;
                const duration = `${hours}h ${minutes}m`;

                const updatedEvent = {
                    ...event,
                    title: `${newTitle} - ${newType} | ${format(startDateTime, 'HH:mm')} - ${format(endDateTime, 'HH:mm')} (${duration})`,
                    start: startDateTime,
                    end: endDateTime,
                    type: newType,
                    color: color
                };

                setEvents(events.map(e => e.id === event.id ? updatedEvent : e));
                toast.success("Event updated successfully!");
            } else {
                toast.error("All event details must be provided!");
            }
        }
    };

    const eventStyleGetter = (event) => ({
        style: {
            backgroundColor: event.color,
            color: 'white',
            borderRadius: '0px',
            border: 'none'
        }
    });

    return (
        <div className="calendar-container">
            <Calendar
                localizer={localizer}
                    events={events}
                    startAccessor="start"
                    endAccessor="end"
                    selectable
                    onSelectSlot={handleSelect}
                    onSelectEvent={handleSelectEvent}
                    eventPropGetter={eventStyleGetter}
                    style={{ height: '100%', width: '100%' }}
                    />
                    <ToastContainer position="top-center" autoClose={5000} />
        </div>
    );
}

    export default Calendar_Schedule;
