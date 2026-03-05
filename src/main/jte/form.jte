@param org.example.spring26.books.UserForm userForm
@param String nameError
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Form demo</title>
</head>
<body>
<h1>Form demo</h1>
<form method="POST" action="/books/form">
    <label for="name">Name:</label><br>
    <input id="name" type="text" name="name" placeholder="name" value="${userForm.name()}"><br>
    @if(nameError != null)
        <div style="color:red">${nameError}</div><br>
    @endif
    <label for="email">Email:</label><br>
    <input id="email" type="email" name="email" placeholder="email" value="${userForm.email()}"><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
