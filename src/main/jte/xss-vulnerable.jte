@param String name

<!DOCTYPE html>
<html>
<head>
    <title>XSS Vulnerable</title>
</head>
<body>
<h1>Reflected XSS - Vulnerable</h1>
<p>Welcome, $unsafe{name}!</p>
<hr>
<h2>DOM-based XSS - Vulnerable</h2>
<div id="dom-xss"></div>
<script>
    // Vulnerable: reading from hash and writing to innerHTML
    const hashValue = window.location.hash.substring(1);
    if (hashValue) {
        document.getElementById('dom-xss').innerHTML = 'Hello, ' + decodeURIComponent(hashValue);
    }
</script>
<p>Try this: append <code>#&lt;img src=x onerror=alert('DOM-XSS')&gt;</code> to the URL and refresh.</p>
<hr>
<p>Try this in the URL: <code>?name=&lt;script&gt;alert('XSS!')&lt;/script&gt;</code></p>
<a href="/xss/protected">Go to Protected Version</a>
</body>
</html>
