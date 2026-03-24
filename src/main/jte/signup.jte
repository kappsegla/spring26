@param String csrfToken
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Account</title>
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

        h1, h2 {
            margin-top: 0;
            font-size: 1.5rem;
            text-align: center;
        }

        h2 {
            font-size: 1.1rem;
            margin-top: 1.5rem;
            color: #666;
        }

        .form-group {
            margin-bottom: 1rem;
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

        button:disabled {
            background: #ccc;
            cursor: not-allowed;
        }

        .hidden {
            display: none;
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
    <div id="step-1">
        <h1>Create Account</h1>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" placeholder="Choose a username">
        </div>
        <div class="form-group">
            <label for="displayName">Display Name</label>
            <input type="text" id="displayName" placeholder="How others see you">
        </div>
        <button id="create-btn">Continue</button>
        <div class="link">
            Already have an account? <a href="/login">Sign in</a>
        </div>
    </div>

    <div id="step-2" class="hidden">
        <h1>Secure Account</h1>
        <h2>Register your first passkey</h2>
        <div class="form-group">
            <label for="passkeyLabel">Passkey Name</label>
            <input type="text" id="passkeyLabel" placeholder="e.g. My MacBook">
        </div>
        <button id="register-btn">Register Passkey</button>
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
        return "My Device";
    }

    document.getElementById("passkeyLabel").value = getDefaultLabel();

    document.getElementById("create-btn").onclick = async () => {
        const username = document.getElementById("username").value;
        const displayName = document.getElementById("displayName").value;

        if (!username || !displayName) {
            alert("Please fill in all fields");
            return;
        }

        const res = await fetch("/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrf
            },
            body: JSON.stringify({username, displayName})
        });

        if (res.ok) {
            document.getElementById("step-1").classList.add("hidden");
            document.getElementById("step-2").classList.remove("hidden");
        } else {
            const errorMsg = await res.text();
            alert(errorMsg || "Could not create account. Username might be taken.");
        }
    };

    document.getElementById("register-btn").onclick = async () => {
        const label = document.getElementById("passkeyLabel").value || "My Passkey";

        const res = await fetch("/webauthn/register/options", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrf
            },
            body: JSON.stringify({})
        });

        if (!res.ok) {
            alert("Failed to get registration options.");
            return;
        }

        const options = await res.json();
        options.challenge = base64urlToBuffer(options.challenge);
        options.user.id = base64urlToBuffer(options.user.id);

        if (options.excludeCredentials) {
            for (let cred of options.excludeCredentials) {
                cred.id = base64urlToBuffer(cred.id);
            }
        }

        try {
            const cred = await navigator.credentials.create({publicKey: options});
            const registerRes = await fetch("/webauthn/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRF-TOKEN": csrf
                },
                body: JSON.stringify(toRegistrationJSON(cred, label))
            });

            if (registerRes.ok) {
                alert("Passkey registered!");
                window.location.href = "/profile";
            } else {
                alert("Failed to save passkey on server.");
            }
        } catch (e) {
            console.error(e);
            alert("Passkey registration cancelled or failed.");
        }
    };

    function bytesToBase64Url(bytes) {
        const binary = new Uint8Array(bytes);
        let str = "";
        for (let i = 0; i < binary.length; i++) str += String.fromCharCode(binary[i]);
        return btoa(str).replaceAll("+", "-").replaceAll("/", "_").replace(/=+$/, "");
    }

    function base64urlToBuffer(base64url) {
        const base64 = base64url.replace(/-/g, "+").replace(/_/g, "/").padEnd(base64url.length + (4 - base64url.length % 4) % 4, "=");
        const binary = atob(base64);
        const bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) bytes[i] = binary.charCodeAt(i);
        return bytes.buffer;
    }

    function toRegistrationJSON(cred, label) {
        return {
            publicKey: {
                label: label,
                credential: {
                    id: cred.id,
                    rawId: bytesToBase64Url(cred.rawId),
                    type: cred.type,
                    response: {
                        attestationObject: bytesToBase64Url(cred.response.attestationObject),
                        clientDataJSON: bytesToBase64Url(cred.response.clientDataJSON),
                        transports: cred.response.getTransports ? cred.response.getTransports() : []
                    },
                    clientExtensionResults: cred.getClientExtensionResults(),
                    authenticatorAttachment: cred.authenticatorAttachment
                }
            }
        };
    }
</script>
</body>
</html>
