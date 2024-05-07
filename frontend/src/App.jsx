import { Outlet } from 'react-router-dom'
import './App.css'
import EmployeeForm from './pages/AddNewEmployee'


function App() {

  return (
    <>
      <Outlet />
      <EmployeeForm />
    </>
  )
}

export default App
