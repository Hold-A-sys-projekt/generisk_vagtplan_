import React, { useEffect, useState } from 'react';
import { Card, CardHeader, CardTitle, CardDescription, CardContent } from '../components/ui/card';
import colors from "tailwindcss/colors.js";

const ShiftsSaleList = () => {

    const [shifts, setShifts] = useState([]);

    useEffect(() => {
        fetch('http://localhost:7070/api/shifts/FOR_SALE')
            .then(response => response.json())
            .then(shiftsData => {
                fetch('http://localhost:7070/api/users')
                    .then(response => response.json())
                    .then(usersData => {
                        const shiftsWithUsername = shiftsData.map(shift => {
                            const user = usersData.find(user => user.id === shift.id);
                            return {...shift, username: user ? user.username : "Unkown"};
                            });
                        setShifts(shiftsWithUsername);
                    })
            })
    }, [])

    return (
        <div className="flex justify-center h-auto" >
            <div className="w-1/2">
                <h2 className="mb-1 text-5xl font-semibold tracking-tight text-gray-900 dark:text-white">Shifts for Swap</h2>
                {shifts.length === 0 ? (
                    <Card className="flex-grow text-center p-10 bg-gray-200 mb-4 mt-4">
                        <CardHeader>
                            <CardTitle>Employee: No shifts for sale</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription>Date: </CardDescription>
                            <CardDescription>Time: </CardDescription>
                        </CardContent>
                    </Card>
                ) : (
                    shifts.map((shift) => (
                           <Card key={shift.id} className="mb-4">
                               <CardHeader>
                                   {console.log("shift: " + JSON.stringify(shift))}
                                      <CardTitle className={"mb-2 text-2xl font-semibold tracking-tight text-gray-900 dark:text-white"}>Seller: {shift.username}</CardTitle>
                               </CardHeader>
                               <CardContent>
                                   <CardDescription>Shift start: {new Date(Date.UTC(...shift.shiftStart)).toLocaleDateString()} {new Date(Date.UTC(...shift.shiftStart)).toLocaleTimeString()}</CardDescription>
                                   <CardDescription>Shift ends: {new Date(Date.UTC(...shift.shiftEnd)).toLocaleDateString()} {new Date(Date.UTC(...shift.shiftEnd)).toLocaleTimeString()}</CardDescription>

                                   <a href="#" className="inline-flex items-center px-3 py-2 mt-4 text-sm font-medium text-center text-white bg-green-900 rounded-lg hover:bg-green-600 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                       Approve
                                       <svg className="rtl:rotate-180 w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="GETURL" fill="none" viewBox="0 0 14 10">
                                           <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                                       </svg>
                                   </a>
                                   <a href="#" className="inline-flex items-center px-3 py-2 mt-4 ml-2 text-sm font-medium text-center text-white bg-red-700 rounded-lg hover:bg-red-500 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                       Decline
                                       <svg className="rtl:rotate-180 w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="GETURL" fill="none" viewBox="0 0 14 10">
                                           <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                                       </svg>
                                   </a>
                               </CardContent>
                            </Card>
                        ))

                )}
            </div>
        </div>
    );
};

export default ShiftsSaleList;