@param String csrfToken
@param boolean isAuthenticated
@param String displayName

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot Passkey Demo</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            padding: 2rem;
        }

        .button {
            display: inline-block;
            padding: 0.5rem 1rem;
            background: #0066ff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            font-size: 1rem;
        }

        .button:hover {
            background: #0053d6;
        }

        .nav {
            margin-top: 1rem;
            display: flex;
            gap: 0.5rem;
            align-items: center;
        }
    </style>
</head>
<body>
<h1>Spring Boot Passkey Demo</h1>

@if(isAuthenticated)
    <p>Welcome, ${displayName}!</p>
    <div class="nav">
        <a href="/profile" class="button">My Profile</a>
        <a href="/add-passkey" class="button">Add Passkey</a>
        <form action="/logout" method="post" style="display:inline;">
            <input type="hidden" name="_csrf" value="${csrfToken}">
            <button type="submit" class="button">Logout</button>
        </form>
    </div>
@else
    <p>Welcome to the WebAuthn / Passkey demonstration.</p>
    <div class="nav">
        <a href="/login" class="button">Login</a>
        <a href="/signup" class="button">Sign Up</a>
    </div>
@endif

</body>
</html>
