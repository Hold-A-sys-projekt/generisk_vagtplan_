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

const DepartmentsDropdown = ({ selectedDepartment, setSelectedDepartment }) => {
  const [departments, setDepartments] = useState([]);

  const loadDepartments = async () => {
    setDepartments(await getAllDepartments());
  }

  useEffect(() => {
    loadDepartments()
  }, [])


  function updateDepartment(selectedId) {
    setSelectedDepartment(departments.find(department => department.id == selectedId))
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="secondary">{selectedDepartment.name ? selectedDepartment.name : "Vælg afdeling"}</Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup value={selectedDepartment.id} onValueChange={updateDepartment}>
        {departments.map((department) => (
          <DropdownMenuRadioItem value={department.id} key={department.id}>{department.name}</DropdownMenuRadioItem>
        ))}
        </DropdownMenuRadioGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export default DepartmentsDropdown;
