import { Outlet } from 'react-router-dom';
import './App.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

import Navbar from './components/navbar';
import { Toaster } from './components/ui/toaster';

function App() {
  return (
    <>
      <Navbar />
      <Outlet />
      <Toaster />
    </>
  );
}

export default App;
