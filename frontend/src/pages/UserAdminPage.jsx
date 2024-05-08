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

const UserAdminPage = () => {
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
              <TableRow>
                <TableCell>email@gmail.com</TableCell>
                <TableCell>Navn Navnesen</TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </Card>
      </div>
    </div>
  );
};

export default UserAdminPage;
