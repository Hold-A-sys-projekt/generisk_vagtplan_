import React, { useEffect, useState } from "react";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "../components/ui/card";
import { Button } from "../components/ui/button";
import colors from "tailwindcss/colors.js";

const ShiftsSaleList = ({ user }) => {
  const [shifts, setShifts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:7070/api/shifts/status/FOR_SALE")
      .then((response) => response.json())
      .then((shiftsData) => {
        fetch("http://localhost:7070/api/users")
          .then((response) => response.json())
          .then((usersData) => {
            const shiftsWithUsername = shiftsData.map((shift) => {
              const user = usersData.find((user) => user.id === shift.id);
              return { ...shift, username: user ? user.username : "Unkown" };
            });
            setShifts(shiftsWithUsername);
          });
      });
  }, []);

  const approveShift = (shift) => {
    const confirm = window.confirm(
      "Are you sure you want to approve shift with id " + shift.id + " ?"
    );
    if (confirm) {
      console.log("You approved shift sale with id: ");
      //  Add logic to approve shift sale
    }
  };

  const declineShift = (shift) => {
    const confirm = window.confirm(
      "Are you sure you want to decline shift with id" + shift.id + " ?"
    );
    if (confirm) {
      console.log("You declined shift sale with id: ");
      //  Add logic to decline shift sale
    }
  };

  const bidOnShift = (shift) => {
    const confirm = window.confirm(
      "Are you sure you want to bid on this shift?"
    );
    if (confirm) {
      console.log("You made a bid on shift sale with id: " + shift.id);
      //  Add logic to bid and confirm
    }
  };

  if (user.role_name === "manager" || user.role_name === "company_admin") {
    return (
      <div className="flex justify-center items-center h-auto">
        <div className="flex flex-wrap justify-center">
          {shifts.length === 0 ? (
            <Card className="flex-grow text-center p-10 bg-white mb-4 mt-4 ml-4">
              <CardHeader>
                <CardTitle>No shifts for approval</CardTitle>
              </CardHeader>
              <CardContent>
                <CardDescription>Check again later...</CardDescription>
              </CardContent>
            </Card>
          ) : (
            shifts.map((shift) => (
              <Card key={shift.id} className="mb-4 ml-1.5">
                <CardHeader>
                  {console.log("shift: " + JSON.stringify(shift))}
                  <CardTitle
                    className={
                      "mb-2 text-2xl font-semibold tracking-tight text-gray-900 dark:text-white"
                    }
                  >
                    <span className="font-bold">Seller:</span> {shift.username}
                  </CardTitle>
                </CardHeader>
                <CardContent className="mt-5">
                  <CardDescription>
                    <span className="font-bold">Shift start:</span>{" "}
                    {new Date(
                      Date.UTC(...shift.shiftStart)
                    ).toLocaleDateString()}{" "}
                    {new Date(
                      Date.UTC(...shift.shiftStart)
                    ).toLocaleTimeString()}
                  </CardDescription>
                  <CardDescription>
                    <span className="font-bold">Shift ends:</span>{" "}
                    {new Date(Date.UTC(...shift.shiftEnd)).toLocaleDateString()}{" "}
                    {new Date(Date.UTC(...shift.shiftEnd)).toLocaleTimeString()}
                  </CardDescription>

                  <Button
                    className="inline-flex items-center px-3 py-2 mt-4 ml-4 text-sm font-medium text-center text-white bg-green-900 rounded-lg hover:bg-green-600 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    onClick={() => approveShift(shift)}
                  >
                    Approve
                    <svg
                      className="rtl:rotate-180 w-3.5 h-3.5 ms-2"
                      aria-hidden="true"
                      xmlns="GETURL"
                      fill="none"
                      viewBox="0 0 14 10"
                    >
                      <path
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M1 5h12m0 0L9 1m4 4L9 9"
                      />
                    </svg>
                  </Button>
                  <Button
                    className="inline-flex items-center px-3 py-2 mt-4 ml-3 text-sm font-medium text-center text-white bg-red-600 rounded-lg hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    onClick={() => declineShift(shift)}
                  >
                    Decline
                    <svg
                      className="rtl:rotate-180 w-3.5 h-3.5 ms-2"
                      aria-hidden="true"
                      xmlns="GETURL"
                      fill="none"
                      viewBox="0 0 14 10"
                    >
                      <path
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M1 5h12m0 0L9 1m4 4L9 9"
                      />
                    </svg>
                  </Button>
                </CardContent>
              </Card>
            ))
          )}
        </div>
      </div>
    );
  } else {
    return (
      <div className="flex justify-center items-center h-auto">
        <div className="flex flex-wrap justify-center">
          {shifts.length === 0 ? (
            <Card className="flex-grow text-center p-10 bg-white mb-4 mt-4">
              <CardHeader>
                <CardTitle>No shifts for sale</CardTitle>
              </CardHeader>
              <CardContent>
                <CardDescription>Check again later...</CardDescription>
              </CardContent>
            </Card>
          ) : (
            shifts.map((shift) => (
              <Card key={shift.id} className="mb-4 ml-1.5">
                <CardHeader>
                  {console.log("shift: " + JSON.stringify(shift))}
                  <CardTitle
                    className={
                      "mb-2 text-2xl font-semibold tracking-tight text-gray-900 dark:text-white"
                    }
                  >
                    <span className="font-bold">Seller:</span> {shift.username}
                  </CardTitle>
                </CardHeader>
                <CardContent className="mt-5">
                  <CardDescription>
                    <span className="font-bold">Shift start:</span>{" "}
                    {new Date(
                      Date.UTC(...shift.shiftStart)
                    ).toLocaleDateString()}{" "}
                    {new Date(
                      Date.UTC(...shift.shiftStart)
                    ).toLocaleTimeString()}
                  </CardDescription>
                  <CardDescription>
                    <span className="font-bold">Shift ends:</span>{" "}
                    {new Date(Date.UTC(...shift.shiftEnd)).toLocaleDateString()}{" "}
                    {new Date(Date.UTC(...shift.shiftEnd)).toLocaleTimeString()}
                  </CardDescription>

                  <Button
                    className="inline-flex items-center px-3 py-2 mt-4 ml-4 text-sm font-medium text-center text-white bg-green-900 rounded-lg hover:bg-green-600 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    onClick={() => bidOnShift(shift)}
                  >
                    Bid
                    <svg
                      className="rtl:rotate-180 w-3.5 h-3.5 ms-2"
                      aria-hidden="true"
                      xmlns="GETURL"
                      fill="none"
                      viewBox="0 0 14 10"
                    >
                      <path
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M1 5h12m0 0L9 1m4 4L9 9"
                      />
                    </svg>
                  </Button>
                </CardContent>
              </Card>
            ))
          )}
        </div>
      </div>
    );
  }
};

export default ShiftsSaleList;
