import {Link} from "react-router-dom";
import ShiftsSaleList from "../components/shiftsSaleList";
import React from "react";


const ShiftSale = () => {

    const user = {
        role_name: "employee"
        // implement logic to get the user role
    }

    return (
        <div className="flex flex-col h-screen p-4 pt-0">

            <div className="border-t border-gray-400 my-2" ></div>
gi
            <h2 className="mb-5 mt-4 text-5xl font-semibold tracking-tight text-gray-900 dark:text-white">Shifts for Sale</h2>

            <div className="flex-grow text-center p-10 bg-gray-200 mb-4 mt-4">
                <ShiftsSaleList  user={user} />
            </div>

            <div className="flex items-center justify-center text-center p-4 h-60 bg-gray-200">
                <Link to="" className="text-blue-500 underline">
                    <p>TEST</p>
                </Link>
            </div>

        </div>


    )
}





export default ShiftSale;