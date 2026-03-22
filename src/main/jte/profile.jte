@import org.springframework.security.web.webauthn.api.CredentialRecord
@import java.util.List

@param String username
@param String csrfToken
@param List<CredentialRecord> keys

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <style>
        body {
            font-family: sans-serif;
            padding: 2rem;
        }

        .key-card {
            border: 1px solid #ccc;
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<h1>Welcome, ${username}!</h1>

<h2>Your Registered Passkeys</h2>

@if(keys.isEmpty())
    <p>You have no passkeys registered yet.</p>
@else
    @for(var key : keys)
        <div class="key-card">
            <strong>Label:</strong> ${key.getLabel()}<br>
            <strong>Created:</strong> ${key.getCreated().toString()}<br>
            <strong>Credential ID:</strong> <small>${key.getCredentialId().toString()}</small>
        </div>
    @endfor
@endif

<hr>
<div style="display: flex; gap: 10px; align-items: center; margin-bottom: 20px;">
    <a href="/" style="text-decoration: none; color: #0066ff;">Back to Home</a>
    <button onclick="createDeviceLink()"
            style="display: inline-block; padding: 0.5rem 1rem; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">
        Link New Device
    </button>
    <form action="/logout" method="post" style="display:inline;">
        <input type="hidden" name="_csrf" value="${csrfToken}">
        <button type="submit"
                style="padding: 0.5rem 1rem; background: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">
            Logout
        </button>
    </form>
</div>

<div id="link-container"
     style="margin-top: 10px; display: none; padding: 1rem; border: 1px solid #28a745; border-radius: 8px; background: #f0fff0;">
    <p style="margin-top: 0;"><strong>Device Link Created!</strong></p>
    <p>Use this link on your other device or browser to add a passkey:</p>
    <code id="device-link-url"
          style="display: block; background: #fff; padding: 10px; border: 1px solid #ddd; margin-bottom: 10px; word-break: break-all;"></code>
    <a id="device-link-href" href="#"
       style="display: inline-block; padding: 0.4rem 0.8rem; background: #007bff; color: white; text-decoration: none; border-radius: 4px;">Open
        link here</a>
</div>

<script>
    async function createDeviceLink() {
        const response = await fetch('/device-link/create', {
            method: 'POST',
            headers: {
                'X-CSRF-TOKEN': '${csrfToken}'
            }
        });
        if (response.ok) {
            const data = await response.json();
            document.getElementById('device-link-url').innerText = data.url;
            document.getElementById('device-link-href').href = data.url;
            document.getElementById('link-container').style.display = 'block';
        } else {
            alert('Failed to create device link');
        }
    }
</script>
</body>
</html>
