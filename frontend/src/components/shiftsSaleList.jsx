import React, { useEffect, useState } from 'react';
import { Card, CardHeader, CardTitle, CardDescription, CardContent } from '../components/ui/card';
import colors from "tailwindcss/colors.js";

const ShiftsSaleList = () => {

    const [shifts, setShifts] = useState([]);

    useEffect(() => {
        fetch('http://localhost:7070/api/users/')
            .then(response => response.json())
            .then(data => {
                console.log("LOOOOOG" + JSON.stringify(data))
                setShifts(data)
            });
    }, [])

    return (
        <div className="flex justify-center, h-auto" style={{backgroundColor: "red"}}>
            <div className="w-1/2">
                <h2 className="text-center mb-4">Shifts for Swap</h2>
                {shifts.length === 0 ? (
                    <Card className="mb-4">
                        <CardHeader>
                            <CardTitle>Seller: No shifts for sale</CardTitle>
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
                                      <CardTitle>Seller: {shift.username}</CardTitle>
                               </CardHeader>
                               <CardContent>
                                   <CardDescription>Date: {shift.date}</CardDescription>
                                   <CardDescription>Time: {shift.time}</CardDescription>
                               </CardContent>
                            </Card>
                        ))

                )}
            </div>
        </div>
    );
};

export default ShiftsSaleList;