@param String title
@param String message
@param org.example.spring26.JteLocalizer localizer

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
</head>
<body>
<h1>${message}</h1>
<p>${localizer.localize("hello")}</p>
<p>${localizer.localize("animal")}</p>
</body>
</html>
