@param org.example.spring26.HomePage page
@param boolean visible
@param java.util.List<String> tasks
@param org.example.spring26.JteLocalizer localizer

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${page.title()}</title>
</head>
<body>
<h1>Home page</h1>
@if( visible )
    <div>${page.message()}</div>
@endif
<p>${localizer.localize("hello")}</p>
<p>${localizer.localize("animal")}</p>
<ul>
    @for(var task  : tasks)
        <li>${task}</li>
    @endfor
</ul>
<hr>
<h2>XSS Demos</h2>
<ul>
    <li><a href="/xss/vulnerable?name=Guest">Vulnerable XSS Page</a></li>
    <li><a href="/xss/protected?name=Guest">Protected XSS Page</a></li>
</ul>
@template.footer(company = "fungover", year = 2026)
</body>
</html>
