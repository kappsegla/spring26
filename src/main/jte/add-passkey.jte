@param String csrfToken
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Passkey</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background: #f5f5f5;
        }

        .box {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 350px;
        }

        h1 {
            margin-top: 0;
            font-size: 1.5rem;
            text-align: center;
        }

        .form-group {
            margin-bottom: 1rem;
            margin-top: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.3rem;
            font-weight: 500;
            font-size: 0.9rem;
        }

        input {
            width: 100%;
            padding: 0.6rem;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 0.8rem;
            background: #0066ff;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            margin-top: 0.5rem;
        }

        button:hover {
            background: #0053d6;
        }

        .link {
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }

        .link a {
            color: #0066ff;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="box">
    <h1>Add New Passkey</h1>
    <div class="form-group">
        <label for="passkeyLabel">Device Name</label>
        <input type="text" id="passkeyLabel" placeholder="e.g. Work Laptop">
    </div>
    <button id="register">Register Passkey</button>
    <div class="link">
        <a href="/profile">Cancel</a>
    </div>
</div>

<script>
    const csrf = "${csrfToken}";

    function getDefaultLabel() {
        const ua = navigator.userAgent;
        if (ua.includes("iPhone")) return "iPhone";
        if (ua.includes("Android")) return "Android Device";
        if (ua.includes("Windows")) return "Windows PC";
        if (ua.includes("Macintosh")) return "MacBook";
        return "New Device";
    }

    document.getElementById("passkeyLabel").value = getDefaultLabel();

    document.getElementById("register").onclick = async () => {
        const label = document.getElementById("passkeyLabel").value || "New Passkey";
        try {
            const res = await fetch("/webauthn/register/options", {
                method: "POST",
                headers: {
                    "X-CSRF-TOKEN": csrf
                }
            });
            if (!res.ok) throw new Error("Could not get registration options");

            const options = await res.json();
            options.challenge = base64urlToBuffer(options.challenge);
            options.userEntity.id = base64urlToBuffer(options.userEntity.id);
            if (options.excludeCredentials) {
                for (let cred of options.excludeCredentials) {
                    cred.id = base64urlToBuffer(cred.id);
                }
            }

            const cred = await navigator.credentials.create({publicKey: options});

            const saveRes = await fetch("/webauthn/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRF-TOKEN": csrf
                },
                body: JSON.stringify(toRegistrationJSON(cred, label))
            });

            if (saveRes.ok) {
                alert("Passkey registered!");
                window.location = "/profile";
            } else {
                alert("Failed to save passkey");
            }
        } catch (e) {
            console.error(e);
            alert("Error: " + e.message);
        }
    };

    function base64urlToBuffer(base64url) {
        const base64 = base64url.replace(/-/g, "+").replace(/_/g, "/").padEnd(base64url.length + (4 - base64url.length % 4) % 4, "=");
        const binary = atob(base64);
        const bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) bytes[i] = binary.charCodeAt(i);
        return bytes.buffer;
    }

    function bufferToBase64url(buffer) {
        const bytes = new Uint8Array(buffer);
        let str = "";
        for (let i = 0; i < bytes.length; i++) str += String.fromCharCode(bytes[i]);
        return btoa(str).replace(/\+/g, "-").replace(/\//g, "_").replace(/=/g, "");
    }

    function toRegistrationJSON(cred, label) {
        return {
            publicKey: {
                label: label,
                credential: {
                    id: cred.id,
                    rawId: bufferToBase64url(cred.rawId),
                    type: cred.type,
                    response: {
                        attestationObject: bufferToBase64url(cred.response.attestationObject),
                        clientDataJSON: bufferToBase64url(cred.response.clientDataJSON),
                        transports: cred.response.getTransports ? cred.response.getTransports() : []
                    },
                    clientExtensionResults: cred.getClientExtensionResults?.() ?? {}
                }
            }
        };
    }
</script>
</body>
</html>
