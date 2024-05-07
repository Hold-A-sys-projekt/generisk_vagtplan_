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
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Employee Name</label>
          <input
            id="employeename"
            type="text"
            name="employeename"
            placeholder="Employee Name"
            value={formData.employeename}
            onChange={handleChange}
            required
          />
        </div>
        
        <div>
          <label>Role</label>
          <input
            id="role"
            type="text"
            name="role"
            placeholder="Role"
            value={formData.role}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <button type="submit">Save</button>
        </div>
      </form>
    </div>
  );
};

export default AddNewEmployee;