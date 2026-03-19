@param org.springframework.security.web.csrf.CsrfToken csrf
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Transfer funds</title>
    <style>
        body {
            font-family: sans-serif;
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            background: #f7f7f7;
        }

        h1 {
            text-align: center;
        }

        form {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        button {
            margin-top: 20px;
            width: 100%;
            padding: 12px;
            background: #0066cc;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #004999;
        }
    </style>
</head>
<body>
<h1>Transfer funds</h1>
<form action="/transfer" method="POST">
    <input type="hidden" name="${csrf.getParameterName()}" value="${csrf.getToken()}">
    <label for="toAccount">To account</label>
    <input type="text" id="toAccount" name="toAccount" required>
    <label for="amount">Amount</label>
    <input type="number" id="amount" min="1" name="amount" required>
    <button type="submit">Transfer</button>
</form>
</body>
</html>
