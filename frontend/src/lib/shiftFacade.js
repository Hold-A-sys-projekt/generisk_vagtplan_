export async function getAllShiftsFromAUser(userId) {
    console.log(`Fetching shifts for user: ${userId}`);
    try {
        const response = await fetch(`http://localhost:7070/api/shifts/${userId}/userid`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("Failed to fetch shifts for user:", error);
        throw error;
    }
}

export async function updateShiftStatus(shiftId, status) {
    console.log(`Updating status of shift ${shiftId} to ${status}`);
    try {
        const response = await fetch(`http://localhost:7070/api/shifts/${shiftId}/status?status=${status}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Failed to update shift status for shift ${shiftId}:`, error);
        throw error;
    }
}
