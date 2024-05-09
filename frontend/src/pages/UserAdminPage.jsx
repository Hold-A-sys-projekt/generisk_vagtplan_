import DepartmentsDropdown from '@/components/departmentsDropdown';
import { Card } from '@/components/ui/card';
import {
  Table,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
  TableBody,
} from '@/components/ui/table';
import { Button } from '@/components/ui/button';

import { getUsers, updateUserDepartment } from '@/lib/userFacade';
import { useEffect, useState } from 'react';

const UserAdminPage = () => {
  const [users, setUsers] = useState([]);

  const loadUsers = async () => {
    setUsers(await getUsers());
  };

  useEffect(() => {
    loadUsers();
  }, []);

  return (
    <div>
      <h1>User Admin Page</h1>
      <div className="container my-4">
        <Card>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Email</TableHead>
                <TableHead>Navn</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {users.map((user) => (
                <UserRow user={user} key={user.id} />
              ))}
            </TableBody>
          </Table>
        </Card>
      </div>
    </div>
  );
};

export default UserAdminPage;

const UserRow = ({ user }) => {

  const [selectedDepartment, setSelectedDepartment] = useState(user.department);
  
  const onSaveNewDepartment = () => {

    user = { ...user, department: selectedDepartment };

    updateUserDepartment(user);

  }

  return (
    <TableRow>
      <TableCell>{user.email}</TableCell>
      <TableCell>{user.username}</TableCell>
      <TableCell>
        <DepartmentsDropdown
          selectedDepartment={selectedDepartment}
          setSelectedDepartment={setSelectedDepartment}
        />
      </TableCell>
      <TableCell>
        <Button variant="outline" onClick={onSaveNewDepartment}>Gem</Button>
      </TableCell>
    </TableRow>
  );
};
