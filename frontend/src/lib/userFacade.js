
export async function getUsers() {
  return await fetch("http://localhost:7070/api/users").then((res) => res.json());
}

export async function updateUserDepartment(user) {

  return await fetch(`http://localhost:7070/api/users/${user.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ user }),
  });
}