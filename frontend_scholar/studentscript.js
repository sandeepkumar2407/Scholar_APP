document.addEventListener('DOMContentLoaded', function () {
    // Retrieve student ID and name from localStorage
    const studentID = localStorage.getItem('studentID');
    const studentName = localStorage.getItem('studentName');

    // Display student ID and name on the page
    document.getElementById('student-id').textContent = studentID ? studentID : 'N/A';
    document.getElementById('student-name').textContent = studentName ? studentName : 'N/A';
});

function viewAssignments() {
    fetch('http://localhost:8080/student/viewAssignments')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch assignments');
            }
            return response.json();
        })
        .then(data => {
            const assignmentDetails = document.getElementById("assignments");
            assignmentDetails.innerHTML = '';
            data.forEach(assignment => {
                const linkHtml = assignment.fileLink ? `<a href="${assignment.fileLink}" target="_blank">${assignment.fileLink}</a>` : 'N/A';
                const submissionHtml = assignment.submissionLink ? `<a href="${assignment.submissionLink}" target="_blank">${assignment.submissionLink}</a>` : 'N/A';
                assignmentDetails.innerHTML += `
                    <div>
                        <p>ID: ${assignment.assignmentID}</p>
                        <p>Description: ${assignment.description}</p>
                        <p>Deadline: ${assignment.deadline}</p>
                        <p>File Link: ${linkHtml}</p>
                        <p>Submission Link: ${submissionHtml}</p>
                        <p>Section: ${assignment.section}</p>
                        <p>------------------------------</p>
                    </div>`;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

function createSubmission() {
    // Retrieve student ID from localStorage
    const studentID = localStorage.getItem('studentID');
    if (!studentID) {
        console.error('Student ID not found in localStorage');
        return;
    }

    // Retrieve input values
    const assignmentId = document.getElementById('assignment-id').value;
    const submissionFileLink = document.getElementById('submission-file-link').value;

    // Create URL-encoded form data
    const formData = new URLSearchParams();
    formData.append('assignmentId', assignmentId);
    formData.append('studentId', studentID);
    formData.append('submissionFileLink', submissionFileLink);

    // Send a POST request to the backend API to create the submission
    fetch('http://localhost:8080/student/submissions', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create submission');
            }
            return response.json();
        })
        .then(data => {
            console.log('Submission created successfully:', data);
            // You can add further handling here, such as displaying a success message
        })
        .catch(error => {
            console.error('Error creating submission:', error);
            // You can add further error handling here, such as displaying an error message
        });
}

// Function to fetch and display the student's results
function viewStudentResults() {
    // Retrieve the student ID from local storage
    const studentID = localStorage.getItem('studentID');

    // Check if student ID exists in local storage
    if (!studentID) {
        console.error('Student ID not found in local storage.');
        return;
    }

    // Fetch the student's results using the student ID
    fetch(`http://localhost:8080/student/${studentID}/results`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch student results');
            }
            return response.json();
        })
        .then(studentResults => {
            const resultContainer = document.getElementById('student-result-container');
            resultContainer.innerHTML = '';

            // Check if student has any results
            if (Object.keys(studentResults).length === 0) {
                resultContainer.innerHTML = '<p>No results found for the student.</p>';
                return;
            }

            // Iterate through student's results and display them
            Object.entries(studentResults).forEach(([assignmentId, score]) => {
                resultContainer.innerHTML += `
                    <div>
                        <p>Assignment ID: ${assignmentId}</p>
                        <p>Score: ${score}</p>
                    </div>
                    <hr>`;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}


