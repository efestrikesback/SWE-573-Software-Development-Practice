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

function createProfile() {
    var bio = document.getElementById('bio').value;
    var avatarUrl = document.getElementById('avatarUrl').value;

    // Retrieve the access token from local storage
    var accessToken = localStorage.getItem('access_token');

    // Ensure there is an access token before sending the request
    if (!accessToken) {
        alert('No access token found. Please log in first.');
        return;
    }

    fetch('http://localhost:8080/api/v1/userProfile/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // Include the access token in the Authorization header
            'Authorization': 'Bearer ' + accessToken
        },
        body: JSON.stringify({bio, avatarUrl
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Profile created:', data);
        alert('Profile created successfully: ' + JSON.stringify(data));
    })
    .catch(error => {
        console.error('Error creating profile:', error);
        alert('Error creating profile: ' + error.message);
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

    function joinCommunity() {
        var communityId = prompt("Enter the ID of the community you wish to join:");

        if (!communityId) {
            alert('Community ID is required!');
            return;
        }

        // Retrieve the access token from local storage
        var accessToken = localStorage.getItem('access_token');

        // Ensure there is an access token before sending the request
        if (!accessToken) {
            alert('No access token found. Please log in first.');
            return;
        }

        fetch(`http://localhost:8080/api/v1/community/join/${communityId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                // Include the access token in the Authorization header
                'Authorization': 'Bearer ' + accessToken
            },
            body: JSON.stringify({
                userId: userId, // The user ID should be known to the client, possibly stored after login
                communityId: communityId // This is obtained from the user input above
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Joined community:', data);
            alert('Successfully joined community ID: ' + communityId);
        })
        .catch(error => {
            console.error('Error joining community:', error);
            alert('Error joining community: ' + error.message);
        });
    }

    function joinCommunity() {
        var communityId = document.getElementById('communityId').value;

        if (!communityId) {
            alert('Community ID is required!');
            return;
        }

        var accessToken = localStorage.getItem('access_token');
        if (!accessToken) {
            alert('No access token found. Please log in first.');
            return;
        }

        fetch(`http://localhost:8080/api/v1/community/join/${communityId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            },
            // Assuming the backend does not need a body for a join operation,
            // as the communityId is in the URL and the userId can be inferred from the token
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            alert('Successfully joined community ID: ' + communityId);
        })
        .catch(error => {
            alert('Error joining community: ' + error.message);
        });
    }

}


