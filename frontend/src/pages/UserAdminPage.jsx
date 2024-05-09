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


import { getUsers } from '@/lib/userFacade';
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
                <TableRow key={user.id}>
                  <TableCell>{user.email}</TableCell>
                  <TableCell>{user.username}</TableCell>
                  <TableCell>
                    <DepartmentsDropdown defaultValue={user.deparment}/>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Card>
      </div>
    </div>
  );
};

export default UserAdminPage;
