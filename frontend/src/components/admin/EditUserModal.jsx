import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogFooter,
  DialogTitle,
  DialogTrigger,
  DialogClose,
} from "@/components/ui/dialog";
import { Label } from "../ui/label";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { useRef } from "react";
import { updateUser } from "@/lib/userFacade";

export default function EditUserModal({ user }) {
    const usernameRef = useRef(user.username)
    const emailRef = useRef(user.email)

    const submitHandler = () => {
        user = { 
            ...user,
            username: usernameRef,
            email: emailRef,
        }
        updateUser(user)
    }


  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant="outline">Edit</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            Edit {user.username}'s information Information
          </DialogTitle>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="username" className="text-right">
                Username
              </Label>
              <Input
                id="username"
                defaultValue={user.username}
                className="col-span-3"
                ref={usernameRef}
              />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="email" className="text-right">
                Email
              </Label>
              <Input
                id="email"
                defaultValue={user.email}
                className="col-span-3"
                ref={emailRef}
              />
            </div>
          </div>
          <DialogDescription>
            This action cannot be undone. This will permanently delete your
            account and remove your data from our servers.
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="sm:justify-start">
          <DialogClose asChild>
            <Button type="button" variant="secondary">
              Close
            </Button>
          </DialogClose>
          <Button onClick={() => submitHandler}>Save</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
