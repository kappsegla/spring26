@import org.example.spring26.model.Car
@import java.util.List
@import jakarta.servlet.http.HttpServletRequest
@import org.springframework.security.web.csrf.CsrfToken

@param List<Car> cars
@param HttpServletRequest request
@param CsrfToken _csrf

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.getToken()}">
    <meta name="_csrf_header" content="${_csrf.getHeaderName()}">

    <title>Car Fleet Management</title>

    <link rel="stylesheet" href="./oat.min.css">
    <script src="./oat.min.js" defer></script>
    <script src="/htmx.min.js"></script>

    <script>
        document.addEventListener('htmx:configRequest', (event) => {
            let token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            let header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
            event.detail.headers[header] = token;
        });
    </script>
</head>

<body class="oat-bg-1 oat-p-6">

<div class="oat-container oat-max-w-3xl oat-mx-auto">

    <div class="oat-card oat-shadow-m">
        <div class="oat-card-header oat-bg-primary oat-text-invert">
            <h3 class="oat-m-0">Car Fleet</h3>
        </div>

        <div class="oat-card-body oat-space-y-4">

            <!-- Form -->
            <form
                    hx-post="/cars"
                    hx-target="#car-list"
                    hx-swap="beforeend"
                    hx-on::after-request="this.reset()"
                    class="oat-grid oat-grid-cols-4 oat-gap-3"
            >
                <input type="text" name="make" class="oat-input" placeholder="Make" required>
                <input type="text" name="model" class="oat-input" placeholder="Model" required>
                <input type="number" name="year" class="oat-input" placeholder="Year" required>

                <button type="submit" class="oat-btn oat-btn-success oat-w-full">
                    Add Car
                </button>
            </form>

            <!-- Table -->
            <table class="oat-table oat-table-hover">
                <thead>
                <tr class="oat-bg-3">
                    <th>Make</th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody id="car-list"
                       hx-get="/cars/list"
                       hx-trigger="load"
                       hx-target="#car-list"
                       hx-swap="innerHTML">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
