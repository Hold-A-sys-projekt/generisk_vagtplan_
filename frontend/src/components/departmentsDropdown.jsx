import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
  DropdownMenuRadioGroup, DropdownMenuRadioItem
} from '@/components/ui/dropdown-menu';
import { useEffect, useState } from 'react';
import { Button } from './ui/button';
import { getAllDepartments } from '@/lib/departmentFacade';

const DepartmentsDropdown = ({ defaultValue }) => {
  const [departments, setDepartments] = useState([]);

  const [selectedDepartment, setSelectedDepartment] = useState(defaultValue);

  const loadDepartments = async () => {
    setDepartments(await getAllDepartments());
  }

  const tests = [
    {id: 1, name: "Marketing"}
    , {id:2, name: "Finance"}
    ,{id:3 ,name: "Human Resources"}];

  useEffect(() => {
    //loadDepartments()
    setDepartments(tests)
    
  }, [])

  return (
    <DropdownMenu>
      <DropdownMenuTrigger>
        <Button variant="secondary">{defaultValue ? defaultValue : "Vælg Lokation"}</Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup value={selectedDepartment} onValueChange={setSelectedDepartment}>
        {departments.map((department) => (
          <DropdownMenuRadioItem key={department.id}>{department.name}</DropdownMenuRadioItem>
        ))}
        </DropdownMenuRadioGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export default DepartmentsDropdown;
