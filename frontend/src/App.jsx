import { Outlet } from 'react-router-dom'
import './App.css'

import Navbar from './components/navbar'

import Calendar from './components/shiftCalender'

function App() {

  return (
    <>
      <Navbar />
      <Outlet />
      <Calendar />
    </>
  )
}

export default App
