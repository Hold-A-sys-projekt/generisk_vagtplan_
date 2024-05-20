import {useState} from "react";
import {useNavigate} from "react-router-dom";
import facade from "@/utils/apiFacade.js";

const Login = () => {

    const [loginCredentials, setLoginCredentials] = useState({
        username: "",
        password: "",
    });
    const [loginError, setLoginError] = useState("");
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setLoginCredentials({...loginCredentials, [name]: value});
    };

    const performLogin = (e) => {
        e.preventDefault();
        facade.login(
            loginCredentials.username,
            loginCredentials.password,
            (loggedIn) => {
                if (loggedIn) {
                    console.log("Login success");



                    // get user

                    // get use role

                    // get user ID?




                    navigate("/dashboard");
                } else {
                    console.log("Login failed");
                    setLoginError("Invalid username or password.");
                }
            }
        );
    };


    return (
        <div className="flex justify-center items-center h-screen">
            <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-full max-w-md">
                <form onSubmit={performLogin}>
                    {loginError && (
                        <p className="text-red-500 text-xs italic mb-2">{loginError}</p>
                    )}
                    <div className="mb-4">
                        <label
                            className="block text-gray-700 text-sm font-bold mb-2"
                            htmlFor="username"
                        >
                            Username
                        </label>
                        <input
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            id="username"
                            name="username"
                            type="text"
                            placeholder="Username"
                            value={loginCredentials.username}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="mb-6">
                        <label
                            className="block text-gray-700 text-sm font-bold mb-2"
                            htmlFor="password"
                        >
                            Password
                        </label>
                        <input
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                            id="password"
                            name="password"
                            type="password"
                            placeholder="******************"
                            value={loginCredentials.password}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="flex items-center justify-center">
                        <button
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                            type="submit"
                        >
                            Login
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;

