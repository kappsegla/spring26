@import org.example.spring26.model.Car
@import java.util.List

@param List<Car> cars

@for(Car car : cars)
    @template.cars.row(car = car)
@endfor
