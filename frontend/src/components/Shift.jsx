/* eslint-disable react/prop-types */
import { set } from 'date-fns';
import React, { useEffect, useState } from 'react';

const Shift = ({ id, shiftStart, shiftEnd, punchIn, punchOut, employeeId, status, setShiftText }) => {
    const [punched, setPunched] = useState('in');

    useEffect(() => {
        if (punchIn === null) setPunched('in');
        else if (punchOut === null) setPunched('out');
        else {
            setPunched('none');
            document.querySelector('.punchbutton').style.background = 'green'
            
        }
    }, []);

    function shiftReceived(data) {
        var shiftStartTime = data.shiftStart.splice(3);
        var shiftStartDate = data.shiftStart;
    
        let shiftStart = new Date(shiftStartDate);
        shiftStart.setHours(shiftStartTime[0]);
        shiftStart.setMinutes(shiftStartTime[1]);
        shiftStart.setSeconds(shiftStartTime[2]);
    
        data.shiftStart = shiftStart;
    
        let shiftEndTime = data.shiftEnd.splice(3);
        let shiftEndDate = data.shiftEnd;
    
        let shiftEnd = new Date(shiftEndDate);
        shiftEnd.setHours(shiftEndTime[0]);
        shiftEnd.setMinutes(shiftEndTime[1]);
        shiftEnd.setSeconds(shiftEndTime[2]);
    
        data.shiftEnd = shiftEnd;
    
        setShiftText(
          "Your shift is from " +
            shiftStart.toLocaleDateString() +
            " to " +
            shiftEnd.toLocaleTimeString()
        );
    
        let pin = data.punchIn;
        let pout = data.punchOut;
        if (pin === null) setPunched("in");
        else if (pout === null) setPunched("out");
        else {
          setPunched("none");
          setShiftText("No shift available.");
        }
      }

    const punchHandler = () => {
        
            // if (punched === "out") setPunched("in");
    // else setPunched("out");
    fetch("http://localhost:7070/api/shifts/" + id + "/punch-" + punched, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then(() => {
          fetch(
            "http://localhost:7070/api/employees/" +
              employeeId +
              "/shifts/current"
          )
            .then((res) => {
              if (res.status === 200)
                res.json().then((data) => shiftReceived(data));
              else if (res.status === 500)
                setShiftText("Server error. Please try again later.");
              else res.text().then((data) => setShiftText(data));
            })
            .catch((err) => {
              setShiftText("Server error. Please try again later.");
            });
        })
        .then(() => {
          if (punched === "in") setShiftText("Punched in successfully.");
          else setShiftText("Punched out successfully.");
        })
        .catch((err) => {
          setShiftText("Server error. Please try again later.");
        });
    };

    return (
    <div className="border border-gray-400 rounded-md p-4 mb-4">
        <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: "space-between", gap: "2%", placeContent: "center" }}>
            <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                <div className="border-b px-2 py-1">ID: {id}</div>
            </div>
            <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                <div className="border-b px-2 py-1">Shift Start: {shiftStart}</div>
                <div className="border-b px-2 py-1">Shift End: {shiftEnd}</div>
            </div>
            <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                <div className="border-b px-2 py-1">Punch In: {punchIn}</div>
                <div className="border-b px-2 py-1">Punch Out: {punchOut}</div>
            </div>
            <div className="w-full sm:w-1/6 border-r border-b sm:border-b-0 sm:border-r-0">
                <div className="border-b px-2 py-1">Employee ID: {employeeId}</div>
                <div className="border-b px-2 py-1">Status: {status}</div>
            </div>
            <div className="w-full sm:w-1/6">
                <button 
                    onClick={punchHandler}
                    className="punchbutton bg-blue-500 text-white px-4 py-2 rounded-md mt-2 w-full"
                >
                    {punched === 'in'
                        ? 'Punch In'
                        : punched === 'out'
                        ? 'Punch Out'
                        : 'Shift completed.'}
                </button>
            </div>
        </div>
    </div>
);

};

export default Shift;
