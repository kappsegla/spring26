@import org.example.spring26.model.Car

@param Car car

<tr id="car-row-${car.getId()}">
    <td>
        <input type="text"
               name="make"
               value="${car.getMake()}"
               class="oat-input">
    </td>

    <td>
        <input type="text"
               name="model"
               value="${car.getModel()}"
               class="oat-input">
    </td>

    <td>
        <input type="number"
               name="year"
               value="${car.getYear()}"
               class="oat-input">
    </td>

    <td class="oat-flex oat-gap-2">
        <button class="oat-btn oat-btn-s oat-btn-success"
                hx-put="/cars/${car.getId()}"
                hx-include="closest tr"
                hx-target="#car-row-${car.getId()}"
                hx-swap="outerHTML">
            Save
        </button>

        <button class="oat-btn oat-btn-s oat-btn-secondary"
                hx-get="/cars/${car.getId()}"
                hx-target="#car-row-${car.getId()}"
                hx-swap="outerHTML">
            Cancel
        </button>
    </td>
</tr>
