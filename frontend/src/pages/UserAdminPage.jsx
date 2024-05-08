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
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

import { getUsers } from '@/lib/userFacade';
import { useEffect, useState } from 'react';

const UserAdminPage = () => {
  const [users, setUsers] = useState([]);
  const [locations, setLocations] = useState([]);

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
                    <DropdownMenu>
                      <DropdownMenuTrigger>{user.locations} example </DropdownMenuTrigger>
                      <DropdownMenuContent>
                        <DropdownMenuLabel>My Account</DropdownMenuLabel>
                        <DropdownMenuSeparator />
                        {locations.map((lokation) => (
                          <DropdownMenuItem key={lokation.id}>
                            {lokation.name}
                          </DropdownMenuItem>
                        ))}
                      </DropdownMenuContent>
                    </DropdownMenu>
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
