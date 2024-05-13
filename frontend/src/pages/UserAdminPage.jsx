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
} from "@/components/ui/table";
import { getUsers, updateUserDepartment } from '@/lib/userFacade';
import { useEffect, useState } from "react";
import Select from "@/components/Select"
import { Button } from '@/components/ui/button';

const UserAdminPage = () => {
  const [users, setUsers] = useState([])
  const [userRoles, setUserRoles] = useState([])

  const loadUserRoles = async () => {
    const roles = await getUserRoles()
    if(!roles) return;
    const userRoles = roles.map((role) => {
      return {
        value: role.name
      }
    })
    setUserRoles(userRoles)
  }


const UserAdminPage = () => {
  const [users, setUsers] = useState([]);

  const loadUsers = async () => {
    setUsers(await getUsers());
  };

  
  useEffect(() => {
    loadUsers()    
    loadUserRoles()
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
                <TableHead>Rolle</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {users.map((user) => (
                <UserRow user={user} key={user.id} roles={userRoles} />
              ))}
            </TableBody>
          </Table>
        </Card>
      </div>
    </div>
  );
};

export default UserAdminPage;

const UserRow = ({ user, roles }) => {

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
        <Button variant="outline" onClick={onSaveNewDepartment}>Gem</Button>
      </TableCell>
      <TableCell> <Select items={roles} defaultValue={user.role} title="Roles" /> </TableCell>
    </TableRow>
  );
};
