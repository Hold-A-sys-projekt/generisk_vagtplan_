export async function getAllDepartments() {
    return await fetch("http://localhost:7070/api/departments").then((res) => res.json());
}