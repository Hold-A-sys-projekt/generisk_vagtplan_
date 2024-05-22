import { useState } from 'react';

const AddNewEmployee = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        role: '',
        department: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:7070/api/employees', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });
            if (!response.ok) {
                throw new Error('Failed to save employee data');
            }
            console.log('Employee data saved successfully');

            setFormData({
                username: '',
                password: '',
                email: '',
                role: '',
                department: ''
            });
        } catch (error) {
            console.error('Error saving employee data:', error);
        }
    };

    return (
        <div className="container mx-auto px-4">
            <div className="p-4 bg-white rounded-lg shadow-md">
                <form onSubmit={handleSubmit} className="max-w-md mx-auto">
                    <div className="mb-4">
                        <label htmlFor="username" className="block text-sm font-medium text-gray-700">Username</label>
                        <input
                            id="username"
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                            className="mt-1 p-2 block w-full shadow-sm border border-gray-300 rounded-md"
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password</label>
                        <input
                            id="password"
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            className="mt-1 p-2 block w-full shadow-sm border border-gray-300 rounded-md"
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700">Email</label>
                        <input
                            id="email"
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            className="mt-1 p-2 block w-full shadow-sm border border-gray-300 rounded-md"
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="role" className="block text-sm font-medium text-gray-700">Role</label>
                        <input
                            id="role"
                            type="text"
                            name="role"
                            placeholder="Role"
                            value={formData.role}
                            onChange={handleChange}
                            required
                            className="mt-1 p-2 block w-full shadow-sm border border-gray-300 rounded-md"
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="department" className="block text-sm font-medium text-gray-700">Department</label>
                        <input
                            id="department"
                            type="number"
                            name="department"
                            placeholder="Department ID"
                            value={formData.department}
                            onChange={handleChange}
                            required
                            className="mt-1 p-2 block w-full shadow-sm border border-gray-300 rounded-md"
                        />
                    </div>

                    <div>
                        <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                            Add New Employee
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default AddNewEmployee;
