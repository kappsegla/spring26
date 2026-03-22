@param String csrfToken
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login with Passkey</title>

    <meta name="csrf-token" content="${csrfToken}">

    <style>
        body {
            font-family: system-ui, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background: #f5f5f5;
        }

        .login-box {
            background: white;
            padding: 2rem 2.5rem;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 320px;
        }

        h1 {
            margin-top: 0;
            font-size: 1.6rem;
        }

        button {
            width: 100%;
            padding: 0.8rem;
            font-size: 1rem;
            border: none;
            border-radius: 8px;
            background: #0066ff;
            color: white;
            cursor: pointer;
        }

        button:hover {
            background: #0053d6;
        }

        .signup {
            margin-top: 1rem;
            font-size: 0.9rem;
        }

        .signup a {
            color: #0066ff;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="login-box">
    <h1>Sign in</h1>

    <button id="passkey-login">Sign in with Passkey</button>

    <div class="signup">
        No account yet?
        <a href="/signup">Create one</a>
    </div>
</div>

<script>
    const csrf = document.querySelector('meta[name="csrf-token"]').content;

    function base64urlToBuffer(base64url) {
        const base64 = base64url
            .replace(/-/g, "+")
            .replace(/_/g, "/")
            .padEnd(base64url.length + (4 - base64url.length % 4) % 4, "=");

        const binary = atob(base64);
        const bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) {
            bytes[i] = binary.charCodeAt(i);
        }
        return bytes.buffer;
    }

    function bufToBase64url(buf) {
        const bytes = new Uint8Array(buf);
        let str = "";
        for (let i = 0; i < bytes.length; i++) str += String.fromCharCode(bytes[i]);
        return btoa(str)
            .replaceAll("+", "-")
            .replaceAll("/", "_")
            .replace(/=+$/, "");
    }

    document.getElementById("passkey-login").addEventListener("click", async () => {
        try {
            // 1. Get authentication options
            const optionsRes = await fetch("/webauthn/authenticate/options", {
                method: "POST",
                headers: {
                    "X-CSRF-TOKEN": csrf
                }
            });

            if (!optionsRes.ok) {
                alert("Could not get authentication options");
                return;
            }

            const options = await optionsRes.json();

            // Base64URL → ArrayBuffer for WebAuthn
            options.challenge = base64urlToBuffer(options.challenge);

            if (options.allowCredentials) {
                for (let cred of options.allowCredentials) {
                    cred.id = base64urlToBuffer(cred.id);
                }
            }

            // 2. WebAuthn assertion
            const assertion = await navigator.credentials.get({publicKey: options});

            // 3. Build payload manually with safe direct ArrayBuffer encoding.
            //    Do NOT use assertion.toJSON() — it produces a format that
            //    Spring Security's CBOR parser (Webauthn4J) cannot deserialize.
            //    Do NOT wrap response fields with toArrayBuffer() — they are
            //    already proper ArrayBuffers from the browser API.
            const resp = assertion.response;

            const payload = {
                id: assertion.id,
                rawId: bufToBase64url(assertion.rawId),
                type: assertion.type,
                authenticatorAttachment: assertion.authenticatorAttachment ?? null,
                response: {
                    clientDataJSON: bufToBase64url(resp.clientDataJSON),
                    authenticatorData: bufToBase64url(resp.authenticatorData),
                    signature: bufToBase64url(resp.signature),
                    userHandle: resp.userHandle ? bufToBase64url(resp.userHandle) : null
                },
                clientExtensionResults: assertion.getClientExtensionResults?.() ?? {}
            };

            const authDataBytes = new Uint8Array(resp.authenticatorData);
            console.log("authenticatorData length:", authDataBytes.length);
            console.log("authenticatorData hex:", Array.from(authDataBytes.slice(0, 10))
                .map(b => b.toString(16).padStart(2, '0')).join(' '));
            const loginRes = await fetch("/login/webauthn", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRF-TOKEN": csrf
                },
                body: JSON.stringify(payload)
            });

            if (loginRes.ok) {
                const {redirectUrl} = await loginRes.json();
                window.location = redirectUrl || "/";
            } else {
                alert("Authentication failed");
            }
        } catch (e) {
            console.error(e);
            alert("Something went wrong during passkey login");
        }
    });
</script>

</body>
</html>
