function login() {
    const role = document.getElementById("role").value;
    const id = document.getElementById("id").value;
    const password = document.getElementById("password").value;

    // Define attribute names based on the selected role
    let idAttributeName, passwordAttributeName, nameAttributeName, nameElementId;

    switch (role) {
        case 'admin':
            idAttributeName = 'id';
            passwordAttributeName = 'password';
            nameAttributeName = 'name'; // Add name attribute
            nameElementId = 'admin-name'; // Element ID to display name
            break;
        case 'teacher':
            idAttributeName = 'teacherID';
            passwordAttributeName = 'teacherPassword';
            nameAttributeName = 'teacherName';
            nameElementId = 'teacher-name';
            break;
        case 'student':
            idAttributeName = 'studentID';
            passwordAttributeName = 'studentPassword';
            nameAttributeName = 'studentName';
            nameElementId = 'student-name';
            break;
        default:
            // Handle invalid role
            document.getElementById('error-message').textContent = 'Invalid role';
            return;
    }

    // Construct request payload
    const requestBody = {
        [idAttributeName]: id,
        [passwordAttributeName]: password
    };

    // Send a POST request to the appropriate endpoint based on the selected role
    fetch(`http://localhost:8080/${role}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Display success message or navigate to the appropriate page based on the response
            switch (role) {
                case 'admin':
                    // Open admin page and display name if available
                    window.open('admin.html', '_blank');
                    const adminNameElement = document.getElementById(nameElementId);
                    if (adminNameElement) {
                        adminNameElement.textContent = data.name ? data.name : '';
                    }
                    break;
                case 'teacher':
                    // Save teacher ID and name to local storage
                    localStorage.setItem('teacherID', data.id);
                    localStorage.setItem('teacherName', data.name);
                    window.open('teacher.html', '_blank');
                    break;
                case 'student':
                    // Save student ID and name to local storage
                    localStorage.setItem('studentID', data.id);
                    localStorage.setItem('studentName', data.name);
                    window.open('student.html', '_blank');
                    break;
                default:
                    document.getElementById('error-message').textContent = 'Invalid role';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('error-message').textContent = 'An error occurred. Please try again later.';
        });
}
