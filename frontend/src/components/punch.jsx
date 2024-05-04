import { Button } from "@/components/ui/button";
import React, { useState, useEffect } from "react";

function Punch({ shiftId, employeeId }) {
  const [punched, setPunched] = useState("in");

  useEffect(() => {
    fetch("http://localhost:7070/api/employees/" + employeeId + "/shifts")
      .then((res) => res.json())
      .then((data) => {
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
    <div>
      <Button onClick={punchHandler}>Punched-{punched}</Button>
    </div>
  );
}

export default Punch;
