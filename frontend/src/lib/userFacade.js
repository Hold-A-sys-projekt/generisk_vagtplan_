
export async function getUsers() {
  return await fetch("http://localhost:7070/api/users").then((res) => res.json());
}
export async function getUserRoles() {
  return await fetch("http://localhost:7070/api/roles").then((res) => res.json());
}