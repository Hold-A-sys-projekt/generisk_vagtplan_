import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import Index from '@/pages/Index.jsx'
import CalendarPage from '@/pages/CalendarPage.jsx'
import Reviews from './components/reviews.jsx'
import Calendar_Schedule from "@/pages/Calendar_Schedule.jsx";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<App />}>
      <Route path="CalendarPage" element={<CalendarPage />} />
      <Route index element={<Index />} />
      <Route path="/reviews" element={<Reviews/>} />
        <Route path="/calendar" element={<Calendar_Schedule />} />
    </Route>
  )
)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)


