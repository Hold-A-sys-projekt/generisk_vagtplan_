import React from "react";
import { useEffect, useState } from "react";
import { Card } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useToast } from "@/components/ui/use-toast";
import facade from "@/utils/apiFacade";
import ShiftOverview from "./ShiftOverview";

function EmployeeAdminPage() {
  const [employees, setEmployees] = useState([]);
  const [currentEmployee, setCurrentEmployee] = useState(null);

  const loadEmployeees = async () => {
    setEmployees(await facade.fetchData("employees", "GET"));
  };

  useEffect(() => {
    loadEmployeees();
  }, []);

  return (
    <div>
      <h1>User Admin Page</h1>

      <div className="container my-4">
        {(currentEmployee && (
          <>
            <div>
              <button
                className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
                onClick={() => setCurrentEmployee(null)}
              >
                Luk ansat editor
              </button>
            </div>
            <ShiftOverview
              employeeId={currentEmployee.id}
              employee={currentEmployee}
            />
          </>
        )) ||
          (!currentEmployee && (
            <Card>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Email</TableHead>
                    <TableHead>Navn</TableHead>
                    <TableHead>Shifts</TableHead>
                    <TableHead>Sidste shift</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody className="text-left">
                  {employees.map((employee) => (
                    <EmployeeRow
                      employee={employee}
                      setEmployee={setCurrentEmployee}
                      key={employee.id}
                    />
                  ))}
                </TableBody>
              </Table>
            </Card>
          ))}
      </div>
    </div>
  );
}

export default EmployeeAdminPage;

const EmployeeRow = ({ employee, setEmployee }) => {
  const [shifts, setShifts] = useState([]);

  const loadShifts = async () => {
    setShifts(await facade.fetchData(`employees/${employee.id}/shifts`, "GET"));
  };

  useEffect(() => {
    loadShifts();
  }, []);

  return (
    <TableRow onClick={() => setEmployee(employee)}>
      <TableCell>{employee.email}</TableCell>
      <TableCell>{employee.username}</TableCell>
      <TableCell>{shifts.length}</TableCell>
      <TableCell>{shifts[0]?.shiftStart ?? "Ingen shifts"}</TableCell>
    </TableRow>
  );
};
