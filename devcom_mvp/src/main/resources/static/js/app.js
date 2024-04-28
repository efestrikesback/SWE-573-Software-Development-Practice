function registerUser() {
    var firstname = document.getElementById('firstname').value;
    var lastname = document.getElementById('lastname').value;
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/v1/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ firstname, lastname, email, password })
    }).then(response => response.json())
      .then(data => alert('User registered: ' + JSON.stringify(data)))
      .catch(error => alert('Error registering user: ' + error));
}


