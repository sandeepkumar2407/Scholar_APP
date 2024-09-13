document.addEventListener('DOMContentLoaded', function () {
    // Retrieve teacher ID and name from localStorage
    const teacherID = localStorage.getItem('teacherID');
    const teacherName = localStorage.getItem('teacherName');

    // Display teacher ID and name on the page
    document.getElementById('teacher-id').textContent = teacherID;
    document.getElementById('teacher-name').textContent = teacherName;

    // Add event listener to the form for creating assignments
    const createAssignmentForm = document.getElementById('create-assignment-form');
    createAssignmentForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent form submission

        // Get values from form fields
        let description = document.getElementById('description').value;
        let fileLink = document.getElementById('file-link').value;
        const section = document.getElementById('section').value;
        const deadline = document.getElementById('deadline').value;
        const submissionLink = document.getElementById('submission-link').value; // Add this line

        if (fileLink === '') {
            fileLink = null;
        }

        if (description === '') {
            description = null;
        }

        // Log the values to ensure they are correctly retrieved
        console.log('Description:', description);
        console.log('File Link:', fileLink);
        console.log('Section:', section);
        console.log('Deadline:', deadline);
        console.log('Submission Link:', submissionLink); // Add this line

        // Call the createAssignment function with the form values
        createAssignment(description, fileLink, section, deadline, submissionLink);
    });
});

// Function to create an assignment
function createAssignment(description, fileLink, section, deadline, submissionLink) {
    // Create URL-encoded form data
    const formData = new URLSearchParams();
    if (description !== null) {
        formData.append('description', description);
    }
    if (fileLink !== null) {
        formData.append('fileLink', fileLink);
    }
    formData.append('section', section);
    formData.append('deadline', deadline);
    formData.append('submissionLink', submissionLink); // Add this line

    // Log form data before sending the request
    console.log('Form Data:', formData.toString());

    // Send a POST request to the backend API to create the assignment
    fetch('http://localhost:8080/teacher/createAssignment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Check if response body is empty
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return response.json(); // Parse JSON response if available
            } else {
                return null; // Return null if response body is empty
            }
        })
        .then(data => {
            if (data) {
                // Handle successful creation of assignment
                console.log('Assignment created successfully:', data);
                // You can add further handling here, such as displaying a success message
            } else {
                // Handle empty response body
                console.log('Response body is empty.');
            }
        })
        .catch(error => {
            console.error('Error creating assignment:', error);
            // You can add further error handling here, such as displaying an error message
        });
}

// Function to fetch and display assignments
function viewAssignments() {
    fetch('http://localhost:8080/teacher/viewAssignments')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch assignments');
            }
            return response.json();
        })
        .then(data => {
            const assignmentDetails = document.getElementById("assignment-details");
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

function viewSubmissions() {
    const assignmentId = document.getElementById('assignment-id').value;
    console.log(assignmentId);

    fetch(`http://localhost:8080/teacher/assignments/${assignmentId}/submissions`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch submissions');
            }
            return response.json();
        })
        .then(data => {
            const submissionDetails = document.getElementById("submission-details");
            submissionDetails.innerHTML = '';
            data.forEach(submission => {
                const submissionFileLink = submission.submissionFileLink ? `<a href="${submission.submissionFileLink}" target="_blank">${submission.submissionFileLink}</a>` : 'N/A';
                submissionDetails.innerHTML += `
                    <div>
                        <p>Submission ID: ${submission.submissionID}</p>
                        <p>Assignment ID: ${submission.assignment.assignmentID}</p>
                        <p>Student ID: ${submission.student.studentID}</p>
                        <p>Submission File Link: ${submissionFileLink}</p>
                        <p>Submission Status: ${submission.submissionStatus}</p>
                        <p>Submission Time: ${submission.dateOfSubmission}</p>
                        <p>------------------------------</p>
                    </div>`;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to create or update a result list
function createOrUpdateResultList() {
    // Get values from input fields
    var assignmentID = document.getElementById("result-assignment-id").value;
    var studentID = document.getElementById("student-id").value;
    var score = document.getElementById("score").value;

    // Construct the request body as a JSON object
    var requestBody = {
        "assignmentID": assignmentID,
        "studentID": studentID,
        "score": score
    };

    // Send POST request to the backend
    fetch("http://localhost:8080/teacher/resultlists", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json' // Set content type to JSON
        },
        body: JSON.stringify(requestBody) // Convert request body to JSON string
    })
        .then(response => {
            if (response.ok) {
                console.log("Result list created or updated successfully.");
            } else {
                console.error("Failed to create or update result list.");
            }
        })
        .catch(error => {
            console.error("Error creating or updating result list:", error);
        });
}

// Function for viewing resultlists of assignments
function viewResultList() {
    fetch('http://localhost:8080/teacher/resultlist')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch result lists');
            }
            return response.json();
        })
        .then(resultLists => {
            const resultListDetails = document.getElementById('result-list-details');
            resultListDetails.innerHTML = '';

            if (resultLists.length === 0) {
                resultListDetails.innerHTML = '<p>No result lists found.</p>';
                return;
            }

            resultLists.forEach(resultList => {
                resultListDetails.innerHTML += `
                    <div>
                        <h3>Assignment ID: ${resultList.assignment.assignmentID}</h3>
                        <ul>
                            ${Object.entries(resultList.scoreList).map(([studentID, score]) => `
                                <li>Student ID: ${studentID}, Score: ${score}</li>
                            `).join('')}
                        </ul>
                    </div>
                    <hr>`;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

