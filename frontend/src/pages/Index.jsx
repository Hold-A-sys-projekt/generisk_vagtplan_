import { Link } from 'react-router-dom';
import AssignRole from '@/pages/AssignRole.jsx';
import AddNewEmployee from '@/pages/AddNewEmployee.jsx';

const Index = () => {
  return (
    <div className="h-screen p-4 pt-0">


      <div className="border-t border-gray-400 my-2"></div>

      <div className="flex-grow text-center p-10 h-80 bg-gray-200 mb-4 mt-4">
        <h1 className='mb-4'>Welcome to GVS</h1>
        <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit.
           Vestibulum sed augue est. Maecenas fringilla aliquam dui,
            vel lobortis nunc euismod nec. Suspendisse quis tortor ipsum.
            Nunc quis imperdiet lectus. Donec tincidunt finibus lacus scelerisque rutrum.
            Curabitur purus turpis, semper non ex eu, dignissim facilisis justo.</p>
      </div>

      <div className="flex items-center justify-center text-center p-4 h-60 bg-gray-200">
        <Link to="/reviews" className="text-blue-500 underline">
          Reviews
        </Link>
      </div>
<div>
    <AssignRole />
    <AddNewEmployee />
</div>

    </div>


  )
}





export default Index;
