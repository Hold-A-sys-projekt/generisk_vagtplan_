/* eslint-disable react/prop-types */
import React from 'react';

const EmployeeDetails = ({ employee, setEmployeeId }) => {
    const { id, email, username, role, createdOn, updatedOn, department, deleted } = employee;

    const formatDate = (dateArray) => {
        const [year, month, day, hour, minute, second, nanosecond] = dateArray;
        return new Date(year, month - 1, day, hour, minute, second, nanosecond / 1000000).toLocaleString();
    };

    return (
        <div className="border border-gray-400 rounded-md p-4 mb-4">
            <h2 className="text-xl font-bold mb-4">Employee Details</h2>
            <div className="grid grid-flow-col grid-rows-2 gap-4">
                <div className="mb-2"><strong>Employee ID:</strong> {id}</div>
                <div className="mb-2"><strong>Email:</strong> {email}</div>
                <div className="mb-2"><strong>Username:</strong> {username}</div>
                <div className="mb-2"><strong>Role:</strong> {role.name}</div>
                <div className="mb-2"><strong>Created On:</strong> {formatDate(createdOn)}</div>
                <div className="mb-2"><strong>Updated On:</strong> {formatDate(updatedOn)}</div>
                <div className="mb-2"><strong>Department:</strong> {department.name}</div>
                <div className="mb-2"><strong>Company ID:</strong> {department.company.id}</div>
                <div className="mb-2"><strong>Deleted:</strong> {deleted ? 'Yes' : 'No'}</div>
                <div className="mb-2 border border-gray-400 rounded-md p-2 w-full mt-4">
                    <button onClick={() => setEmployeeId(id)}>Select</button>
                </div>
            </div>
        </div>
    );
};

export default EmployeeDetails;
