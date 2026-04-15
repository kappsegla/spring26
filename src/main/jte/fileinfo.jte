@import java.util.List
@param String csrfToken
@param boolean isAuthenticated
@param String displayName
@param List<org.example.spring26.service.S3Service.FileInfo> fileInfo

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${csrfToken}">
    <meta name="_csrf_header" content="X-CSRF-TOKEN">
    <title>S3 File Manager</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            padding: 2rem;
        }

        .button {
            display: inline-block;
            padding: 0.5rem 1rem;
            background: #0066ff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            font-size: 1rem;
            margin: 0.2rem;
        }

        .button:hover {
            background: #0053d6;
        }

        .nav {
            margin-top: 1rem;
            display: flex;
            gap: 0.5rem;
            align-items: center;
        }

        .file-list {
            margin-top: 2rem;
            border-collapse: collapse;
            width: 100%;
        }

        .file-list th, .file-list td {
            text-align: left;
            padding: 0.5rem;
            border-bottom: 1px solid #ccc;
        }

        #uploadSection {
            margin-top: 2rem;
            padding: 1rem;
            border: 1px solid #eee;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<h1>S3 File Manager</h1>

<h3>Files in S3</h3>
<table class="file-list">
    <thead>
    <tr>
        <th>File Name</th>
        <th>Last Modified</th>
    </tr>
    </thead>

    <tbody>
    @for(var info : fileInfo)
        <tr>
            <td>${info.key()}</td>
            <td><span class="local-time"
                      data-utc="${info.lastModified().toString()}">${info.lastModified().toString()}</span></td>
        </tr>
    @endfor
    </tbody>
</table>
<script>
    @raw
    document.querySelectorAll('.local-time').forEach(el => {
        const utcDateStr = el.getAttribute('data-utc');
        if (utcDateStr) {
            const localDate = new Date(utcDateStr);

            // Formatera snyggt enligt användarens språkinställning
            // el.textContent = localDate.toLocaleString(undefined, {
            //     year: 'numeric',
            //     month: 'short',
            //     day: 'numeric',
            //     hour: '2-digit',
            //     minute: '2-digit'
            // });
            const year = localDate.getFullYear();
            const month = String(localDate.getMonth() + 1).padStart(2, '0');
            const day = String(localDate.getDate()).padStart(2, '0');
            const hour = String(localDate.getHours()).padStart(2, '0');
            const minute = String(localDate.getMinutes()).padStart(2, '0');

            el.textContent = `${year}-${month}-${day} ${hour}:${minute}`;

        }
    });
    @endraw
</script>
</body>
</html>
