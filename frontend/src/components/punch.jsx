import { Button } from "@/components/ui/button";
import React, { useState, useEffect } from "react";

function Punch({ shiftId, employeeId }) {
  const [punched, setPunched] = useState("in");
  const [shift, setShift] = useState({ shiftStart: new Date() });

  useEffect(() => {
    fetch("http://localhost:7070/api/employees/" + employeeId + "/shifts")
      .then((res) => res.json())
      .then((data) => {
        var shiftStartTime = data[0].shiftStart.splice(3);
        var shiftStartDate = data[0].shiftStart;

        let shiftStart = new Date(shiftStartDate);
        shiftStart.setHours(shiftStartTime[0]);
        shiftStart.setMinutes(shiftStartTime[1]);
        shiftStart.setSeconds(shiftStartTime[2]);

        data[0].shiftStart = shiftStart;

        let shiftEndTime = data[0].shiftEnd.splice(3);
        let shiftEndDate = data[0].shiftEnd;

        let shiftEnd = new Date(shiftEndDate);
        shiftEnd.setHours(shiftEndTime[0]);
        shiftEnd.setMinutes(shiftEndTime[1]);
        shiftEnd.setSeconds(shiftEndTime[2]);

        data[0].shiftEnd = shiftEnd;

        setShift(date[0]);

        let pout = data[0].punchOut;
        if (pout === null) setPunched("out");
        else setPunched("in");
      });
  }, []);

  function punchHandler() {
    if (punched === "out") setPunched("in");
    else setPunched("out");
    fetch("http://localhost:7070/api/shifts/" + shiftId + "/punch-" + punched, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });
  }

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-2xl font-bold">Punch</h1>
      <p className="text-xl font-semibold pl-2 pr-2 text-center">
        You are currently punched-{punched} for shift {shiftId} which started on{" "}
        {shift?.shiftStart?.toDateString().toLocaleString() || ""}
      </p>
      <Button
        className="px-4 py-2 bg-blue-500 text-white rounded-md"
        onClick={punchHandler}
      >
        Punch-{punched == "in" ? "out" : "in"}
      </Button>
    </div>
  );
}

export default Punch;
