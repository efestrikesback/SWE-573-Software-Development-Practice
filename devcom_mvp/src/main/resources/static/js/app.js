function registerUser() {
    var firstname = document.getElementById('firstname').value;
    var lastname = document.getElementById('lastname').value;
    var username = document.getElementById('username').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/v1/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ firstname, lastname, username, email, password })
    }).then(response => response.json())
      .then(data => alert('User registered: ' + JSON.stringify(data)))
      .catch(error => alert('Error registering user: ' + error));
}

function login() {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        // Store the access token in local storage
        localStorage.setItem('access_token', data.access_token);
        // Alert or log the success and stored token
        console.log('User authenticated and token stored:', data.access_token);
    })
    .catch(error => {
        // Handle errors, such as network issues or JSON parsing issues
        console.error('Error logging in:', error);
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

    fetch('http://localhost:8080/api/v1/community/create', {
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


