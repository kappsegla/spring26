@import org.example.spring26.model.Car

@param Car car

<tr id="car-row-${car.getId()}">
    <td>${car.getMake()}</td>
    <td>${car.getModel()}</td>
    <td>${car.getYear()}</td>

    <td class="oat-flex oat-gap-2">
        <button class="oat-btn oat-btn-s oat-btn-primary"
                hx-get="/cars/${car.getId()}/edit"
                hx-target="#car-row-${car.getId()}"
                hx-swap="outerHTML">
            Edit
        </button>

        <button class="oat-btn oat-btn-s oat-btn-danger"
                hx-delete="/cars/${car.getId()}"
                hx-target="#car-list"
                hx-swap="innerHTML"
                hx-confirm="Are you sure?">
            Remove
        </button>
    </td>
</tr>
