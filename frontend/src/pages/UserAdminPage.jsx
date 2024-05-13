import { Card } from "@/components/ui/card";
import {
  Table,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
  TableBody,
} from "@/components/ui/table";
import { getUsers } from "@/lib/userFacade";
import { getUserRoles } from "@/lib/userFacade";
import { useEffect, useState } from "react";
import Select from "@/components/Select"

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



  const loadUsers = async () => {
    setUsers(await getUsers())
  }

  
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
              <TableRow key={user.id}>
                <TableCell>{user.email}</TableCell>
                <TableCell>{user.username}</TableCell>
                <TableCell> <Select items={userRoles} defaultValue={user.role} title="Roles" /> </TableCell>
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
