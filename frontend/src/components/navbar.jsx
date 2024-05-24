import {Link} from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className='mr-4 mt-4 ml-4 mb-4'>
            <div className="w-full h-10 flex items-center justify-center text-center p-10 bg-gray-200">
                <ul>
                    <li><Link to="/">Home</Link></li>
                    <li><Link to="/login">Login</Link></li>
                    <li><Link to="/companysignup">Company Signup</Link></li>
                </ul>
            </div>
        </nav>

    );
}

export default Navbar;


