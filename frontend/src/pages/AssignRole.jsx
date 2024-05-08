import React, { useState } from 'react';

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
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Employee ID</label>
                    <input
                        id="employeeID"
                        type="text"
                        name="employeeID"
                        placeholder="Employee ID"
                        value={formData.employeeID}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div>
                    <label>New Role</label>
                    <input
                        id="newRole"
                        type="text"
                        name="newRole"
                        placeholder="New Role"
                        value={formData.newRole}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <button type="submit">Update Role</button>
                </div>
            </form>
        </div>
    );
};

export default AssignRole;
