import  { useState } from 'react';

const AssignRole = () => {
    const [formData, setFormData] = useState({
        employeeID: '',
        newRole: '',
    });

const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:7070/api/managers/employees/${formData.employeeID}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ role: formData.newRole }),
            });
            if (!response.ok) {
                throw new Error('Failed to update employee role');
            }
            console.log('Employee role updated successfully');

            setFormData({
                employeeID: '',
                newRole: '',
            });
        } catch (error) {
            console.error('Error updating employee role:', error);
        }
    };

    return (
        <div className="container mx-auto px-4">
            <form onSubmit={handleSubmit} className="max-w-md mx-auto">
                <div className="mb-4">
                    <label htmlFor="employeeID" className="block">Employee ID</label>
                    <input
                        id="employeeID"
                        type="text"
                        name="employeeID"
                        placeholder="Employee ID"
                        value={formData.employeeID}
                        onChange={handleChange}
                        required
                        className="border border-gray-400 rounded-md p-2 w-full"
                    />
                </div>

                <div className="mb-4">
                    <label htmlFor="newRole" className="block">New Role</label>
                    <input
                        id="newRole"
                        type="text"
                        name="newRole"
                        placeholder="New Role"
                        value={formData.newRole}
                        onChange={handleChange}
                        required
                        className="border border-gray-400 rounded-md p-2 w-full"
                    />
                </div>
                <div>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">Update Role</button>
                </div>
            </form>
        </div>
    );
};

export default AssignRole;
