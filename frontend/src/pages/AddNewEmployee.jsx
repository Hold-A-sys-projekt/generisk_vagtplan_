import { useState } from 'react';

const AddNewEmployee = () => {
  const [formData, setFormData] = useState({
    employeename: '',
    role: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:7070/api/managers/employees', {
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
        employeename: '',
        role: '',
      });
    } catch (error) {
      console.error('Error saving employee data:', error);
    }
  };

 
  return (
    <div className="p-4 bg-white rounded-lg shadow-md">
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="employeename" className="block text-sm font-medium text-gray-700">Employee Name</label>
          <input
            id="employeename"
            type="text"
            name="employeename"
            placeholder="Employee Name"
            value={formData.employeename}
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
        <div>
          <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            Add New Employee
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddNewEmployee;