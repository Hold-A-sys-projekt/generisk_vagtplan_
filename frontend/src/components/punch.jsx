import { Button } from "@/components/ui/button";
import React, { useState, useEffect } from "react";

function Punch({ shiftId, employeeId }) {
  const [punched, setPunched] = useState("in");
  const [shiftText, setShiftText] = useState("");

  useEffect(() => {
    fetch(
      "http://localhost:7070/api/employees/" + employeeId + "/shifts/current"
    )
      .then((res) => {
        if (res.status === 200) res.json().then((data) => shiftReceived(data));
        else if (res.status === 500)
          setShiftText("Server error. Please try again later.");
        else res.text().then((data) => setShiftText(data));
      })
      .catch((err) => {
        setShiftText("Server error. Please try again later.");
      });
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
        shiftStart.toLocaleTimeString() +
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

  function punchHandler() {
    // if (punched === "out") setPunched("in");
    // else setPunched("out");
    fetch("http://localhost:7070/api/shifts/" + shiftId + "/punch-" + punched, {
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
  }

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-2xl font-bold">Punch</h1>
      <p className="text-xl font-semibold pl-2 pr-2 text-center">{shiftText}</p>
      <Button
        className="px-4 py-2 bg-blue-500 text-white rounded-md"
        onClick={punchHandler}
        disabled={punched === "none"}
      >
        {punched === "in"
          ? "Punch In"
          : punched === "out"
          ? "Punch Out"
          : "No Shift Available"}
      </Button>
    </div>
  );
}

export default Punch;
