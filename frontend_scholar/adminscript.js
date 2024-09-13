function createTeacher() {
    const teacherName = document.getElementById("teacherName").value;
    const teacherPassword = document.getElementById("teacherPassword").value;

    fetch('http://localhost:8080/admin/createTeacher', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            teacherName: teacherName, // Update key to match backend expectation
            teacherPassword: teacherPassword // Update key to match backend expectation
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create teacher');
            }
            alert('Teacher created successfully');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

function createStudent() {
    const studentName = document.getElementById("studentName").value;
    const studentSection = document.getElementById("studentSection").value;
    const studentPassword = document.getElementById("studentPassword").value;

    fetch('http://localhost:8080/admin/createStudent', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            studentName: studentName,
            studentSection: studentSection,
            studentPassword: studentPassword
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create student');
            }
            alert('Student created successfully');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

function viewTeachers() {
    fetch('http://localhost:8080/admin/viewTeachers')
        .then(response => response.json())
        .then(data => {
            const teacherDetails = document.getElementById("teacherDetails");
            teacherDetails.innerHTML = '';
            data.forEach(teacher => {
                teacherDetails.innerHTML += `<p>ID: ${teacher.teacherID}, Name: ${teacher.teacherName}, Password: ${teacher.teacherPassword}</p>`;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

function viewStudents() {
    fetch('http://localhost:8080/admin/viewStudents')
        .then(response => response.json())
        .then(data => {
            const studentDetails = document.getElementById("studentDetails");
            studentDetails.innerHTML = '';
            data.forEach(student => {
                studentDetails.innerHTML += `<p>ID: ${student.studentID}, Name: ${student.studentName}, Section: ${student.studentSection}, Password: ${student.studentPassword}</p>`;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}


// Function to delete a teacher
function deleteTeacher() {
    const teacherId = document.getElementById("teacherId").value;

    fetch(`http://localhost:8080/admin/deleteTeacher/${teacherId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete teacher');
            }
            alert('Teacher deleted successfully');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to delete a student
function deleteStudent() {
    const studentId = document.getElementById("studentId").value;

    fetch(`http://localhost:8080/admin/deleteStudent/${studentId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete student');
            }
            alert('Student deleted successfully');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

