import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { useEffect, useState } from 'react';
import { Button } from './ui/button';

const DepartmentsDropdown = ({ defaultValue }) => {
  const [departments, setDepartments] = useState([]);

  const loadDepartments = async () => {

  }

  useEffect(() => {
    loadDepartments()
  })

  return (
    <DropdownMenu>
      <DropdownMenuTrigger>
        <Button>{defaultValue ? defaultValue : "Vælg Lokation"}</Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <DropdownMenuSeparator />
        {departments.map((department) => (
          <DropdownMenuItem key={department.id}>{department.name}</DropdownMenuItem>
        ))}
      </DropdownMenuContent>
    </DropdownMenu>
  );
};

export default DepartmentsDropdown;
