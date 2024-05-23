import DepartmentsDropdown from "@/components/departmentsDropdown";
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
import {
  getUserRoles,
  getUsers,
  resetUserPassword,
  updateUserDepartment,
  updateUserRole,
} from "@/lib/userFacade";
import { useEffect, useState } from "react";
import Select from "@/components/Select";
import { Button } from "@/components/ui/button";
import { useToast } from "@/components/ui/use-toast";
import EditUserModal from "@/components/admin/EditUserModal";

const UserAdminPage = () => {
  const [users, setUsers] = useState([]);
  const [userRoles, setUserRoles] = useState([]);

  const loadUserRoles = async () => {
    const roles = await getUserRoles();
    if (!roles) return;
    const userRoles = roles.map((role) => {
      return {
        value: role.name,
      };
    });
    setUserRoles(userRoles);
  };

  const loadUsers = async () => {
    setUsers(await getUsers());
  };

  useEffect(() => {
    loadUsers();
    loadUserRoles();
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
                <TableHead>Afdeling</TableHead>
                <TableHead>Rolle</TableHead>
                <TableHead>Funktionalitet</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody className="text-left">
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

const UserRow = ({ user, roles }) => {
  const [selectedDepartment, setSelectedDepartment] = useState(user.department);
  const { toast } = useToast();

  const handleSelectRole = async (role) => {
    const result = await updateUserRole({ ...user, role: role });
    if (result.ok) {
      toast({
        variant: "success",
        title: "Success!",
        description: "The user's role was successfully updated.",
      });
    } else {
      toast({
        variant: "destructive",
        title: "Uh oh! Something went wrong.",
        description: "There was a problem with updating user's role",
      });
    }
  };

  const onSaveNewDepartment = async () => {
    user = { ...user, department: selectedDepartment };
    const result = await updateUserDepartment(user);
    if (result.ok) {
      toast({
        variant: "success",
        title: "Success!",
        description: "The user's department was successfully updated.",
      });
    } else {
      toast({
        variant: "destructive",
        title: "Uh oh! Something went wrong.",
        description: "There was a problem with updating the user's department",
      });
    }
  };

  const handleResetPassword = async () => {
    const result = await resetUserPassword(user)
    if (result.ok) {
      toast({
        variant: "success",
        title: "Success!",
        description: "The user's password was successfully reset. A new password has been sent to the user's email.",
      });
    } else {
      toast({
        variant: "destructive",
        title: "Uh oh! Something went wrong.",
        description: "There was a problem with resetting the user's password",
      });
    }
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
        <Button
          variant="outline"
          className="ml-2"
          onClick={onSaveNewDepartment}
        >
          Gem
        </Button>
      </TableCell>
      <TableCell>
        {" "}
        <Select
          items={roles}
          defaultValue={user.role.name}
          title="Roles"
          onSelect={handleSelectRole}
        />{" "}
      </TableCell>
      <TableCell>
        <EditUserModal user={user} />
        <Button variant="outline" className="ml-2" onClick={handleResetPassword}>
          Reset Password
        </Button>
      </TableCell>
    </TableRow>
  );
};

export default UserAdminPage;
