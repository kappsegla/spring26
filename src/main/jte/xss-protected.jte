@param String name

<!DOCTYPE html>
<html>
<head>
    <title>XSS Protected</title>
</head>
<body>
<h1>Reflected XSS - Protected</h1>
<p>Welcome, ${name}!</p>
<hr>
<h2>DOM-based XSS - Protected</h2>
<div id="dom-xss"></div>
<script>
    // Protected: using textContent instead of innerHTML
    const hashValue = window.location.hash.substring(1);
    if (hashValue) {
        document.getElementById('dom-xss').textContent = 'Hello, ' + decodeURIComponent(hashValue);
    }
</script>
<p>Notice how even if you use <code>#&lt;img src=x onerror=alert('DOM-XSS')&gt;</code>, it is treated as plain text.</p>
<hr>
<p>Try this in the URL: <code>?name=&lt;script&gt;alert('XSS!')&lt;/script&gt;</code></p>
<p>Notice how JTE automatically escapes the input.</p>
<a href="/xss/vulnerable">Go to Vulnerable Version</a>
</body>
</html>
