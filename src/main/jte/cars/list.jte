@import org.example.spring26.model.Car
@import java.util.List

@param List<Car> cars

<tbody id="car-list">
@for(Car car : cars)
    @template.cars.row(car = car)
@endfor
</tbody>
