import { Outlet } from 'react-router-dom'
import './App.css'

import Navbar from './components/navbar'
import { Toaster } from './components/ui/toaster'

import ShiftCalender from './components/shiftCalender'
function App() {

  return (
    <>
      <Navbar />
      <Outlet />
      <Toaster />
      <ShiftCalender/>
    </>
  )
}

export default App
