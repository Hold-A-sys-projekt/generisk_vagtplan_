// companysignup.js
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import compFacade from "@libs/companyfacade.js"

const Companysignup = () => {
    const [signupCredentials, setSignupCredentials] = useState({
      companyName: "",
      admin: "",
    });
    const [signupError, setSignupError] = useState("");
    const navigate = useNavigate();
  
    const handleInputChange = (e) => {
      const { name, value } = e.target;
      setSignupCredentials({ ...signupCredentials, [name]: value });
    };
  
    const performSignup = (e) => {
      e.preventDefault();
      compFacade.signup(
        signupCredentials.companyName,
        signupCredentials.admin,
        (signedUp) => {
          if (signedUp) {
            navigate("/dashboard");
          } else {
            setSignupError("Signup failed. Please try again.");
          }
        }
      );
    };
  
 
  
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-full max-w-md">
          <form onSubmit={performSignup}>
            {signupError && (
              <p className="text-red-500 text-xs italic mb-2">{signupError}</p>
            )}
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="companyName"
              >
                Company Name
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="companyName"
                name="companyName"
                type="text"
                placeholder="Company Name"
                value={signupCredentials.companyName}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="admin"
              >
                Admin
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                id="admin"
                name="admin"
                type="text"
                placeholder="Admin"
                value={signupCredentials.admin}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="flex items-center justify-center">
              <button
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Sign Up
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  };
  
  export default Companysignup;