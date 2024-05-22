import { useState } from 'react';

const UpdateShift = () => {
    const [formData, setFormData] = useState({
        id: '',
        newShiftStart: '',
        newShiftEnd: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:7070/api/shifts/${formData.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    shiftStart: formData.newShiftStart,
                    shiftEnd: formData.newShiftEnd
                }),
            });
            if (!response.ok) {
                throw new Error('Failed to update shift dates');
            }
            console.log('Shift dates updated successfully');

            setFormData({
                id: '',
                newShiftStart: '',
                newShiftEnd: '',
            });
        } catch (error) {
            console.error('Error updating shift dates:', error);
        }
    };

    return (
        <div className="container mx-auto px-4">
            <form onSubmit={handleSubmit} className="max-w-md mx-auto">
                <div className="mb-4">
                    <label htmlFor="id" className="block">Shift ID</label>
                    <input
                        id="id"
                        type="text"
                        name="id"
                        placeholder="Shift ID"
                        value={formData.id}
                        onChange={handleChange}
                        required
                        className="border border-gray-400 rounded-md p-2 w-full"
                    />
                </div>

                <div className="mb-4">
                    <label htmlFor="newShiftStart" className="block">New Start Date and Time (GMT)</label>
                    <input
                        id="newShiftStart"
                        type="datetime-local"
                        name="newShiftStart"
                        value={formData.newShiftStart}
                        onChange={handleChange}
                        required
                        className="border border-gray-400 rounded-md p-2 w-full"
                    />
                </div>

                <div className="mb-4">
                    <label htmlFor="newShiftEnd" className="block">New End Date and Time (GMT)</label>
                    <input
                        id="newShiftEnd"
                        type="datetime-local"
                        name="newShiftEnd"
                        value={formData.newShiftEnd}
                        onChange={handleChange}
                        required
                        className="border border-gray-400 rounded-md p-2 w-full"
                    />
                </div>

                <div>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">Update Shift Dates</button>
                </div>
            </form>
        </div>
    );
};

export default UpdateShift;
