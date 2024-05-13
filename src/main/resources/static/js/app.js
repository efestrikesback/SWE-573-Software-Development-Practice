function validateRegistrationInput(firstname, lastname, username, email, password) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!firstname.trim() || !lastname.trim() || !username.trim()) {
        alert('Please fill out all fields.');
        return false;
    }
    if (!emailRegex.test(email)) {
        alert('Please enter a valid email address.');
        return false;
    }
    if (password.length < 8) {
        alert('Password must be at least 8 characters long.');
        return false;
    }
    return true;
}

function setLoading(isLoading) {
    document.getElementById('registerBtn').disabled = isLoading; // Assuming the button has an ID 'registerBtn'
    document.getElementById('loadingIndicator').style.display = isLoading ? 'block' : 'none';
}

function clearForm() {
    document.getElementById('registrationForm').reset(); // Assuming the form has an ID 'registrationForm'
}

function registerUser() {
    var firstname = document.getElementById('firstname').value;
    var lastname = document.getElementById('lastname').value;
    var username = document.getElementById('username').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    if (!validateRegistrationInput(firstname, lastname, username, email, password)) return;

    setLoading(true);

    fetch('http://13.60.66.40:8080/api/v1/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ firstname, lastname, username, email, password })
    }).then(response => {
        setLoading(false);
        if (!response.ok) {
            return response.json().then(err => throw new Error(err.message || 'Failed to register'));
        }
        return response.json();
    })
    .then(data => {
        alert('User registered: ' + JSON.stringify(data));
        clearForm();
    })
    .catch(error => {
        alert('Error registering user: ' + error.message);
    });
}


function login() {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    if (!validateInput(email, password)) return; // Stop if validation fails

    setLoading(true); // Show loading indicator and disable button

    fetch('http://13.60.66.40:8080/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        setLoading(false); // Hide loading indicator and enable button
        if (!response.ok) {
            return response.json().then(err => throw new Error(err.message || 'Failed to login'));
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('access_token', data.access_token);
        console.log('User authenticated and token stored:', data.access_token);
        clearForm(); // Clear the form fields
    })
    .catch(error => {
        console.error('Error logging in:', error);
        alert('Login failed: ' + error.message);
    });
}




function createCommunity() {
    var name = document.getElementById('name').value;
    var description = document.getElementById('description').value;
    var isPrivate = false;
    var isArchived = false;
//        var isPrivate = document.getElementById('isPrivate').value;
//        var isArchived = document.getElementById('isArchived').value;

    // Retrieve the access token from local storage
    var accessToken = localStorage.getItem('access_token');

    // Ensure there is an access token before sending the request
    if (!accessToken) {
        alert('No access token found. Please log in first.');
        return;
    }

    fetch('http://13.60.66.40:8080/api/v1/community/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // Include the access token in the Authorization header
            'Authorization': 'Bearer ' + accessToken
        },
        body: JSON.stringify({
            name,
            description,
            isPrivate,
            isArchived
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Community created:', data);
        alert('Community created successfully: ' + data.name);
    })
    .catch(error => {
        console.error('Error creating community:', error);
        alert('Error creating community: ' + error.message);
    });
}


