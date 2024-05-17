
export async function getUsers() {
  return await fetch("http://localhost:7070/api/users").then((res) => res.json());
}

export async function getUserRoles() {
  return await fetch("http://localhost:7070/api/roles").then((res) => res.json());
}

export async function updateUserDepartment(user) {
  return await fetch(`http://localhost:7070/api/users/${user.id}/department`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ department: user.department}),
  });
}

export async function updateUserRole(user) {
  return await fetch(`http://localhost:7070/api/users/${user.id}/role`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ role: user.role }),
  });
}

//TODO: setup this endpoint on the backend
export async function updateUser(user) {
  return await fetch(`http://localhost:7070/api/users/${user.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ 
      email: user.email, 
      username: user.username,
     }),
  });
}

export async function resetUserPassword(user) {
  return await fetch(`http://localhost:7070/api/users/${user.id}/reset-password`)
}
