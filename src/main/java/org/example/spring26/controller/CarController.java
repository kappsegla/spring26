package org.example.spring26.controller;

import org.example.spring26.model.Car;
import org.example.spring26.repository.CarRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public String index(Model model, jakarta.servlet.http.HttpServletRequest request) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("request", request);
        return "cars/index";
    }

    @PostMapping
    public String addCar(@ModelAttribute Car car, Model model) {
        Car savedCar = carRepository.save(car);
        model.addAttribute("car", savedCar);
        return "cars/row";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);
        return ""; // HTMX will remove the target element because we return empty response and swap is outerHTML
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id).orElseThrow();
        model.addAttribute("car", car);
        return "cars/edit";
    }

    @PutMapping("/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute Car carUpdates, Model model) {
        Car car = carRepository.findById(id).orElseThrow();
        car.setMake(carUpdates.getMake());
        car.setModel(carUpdates.getModel());
        car.setYear(carUpdates.getYear());
        carRepository.save(car);
        model.addAttribute("car", car);
        return "cars/row";
    }

    @GetMapping("/{id}")
    public String getCar(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id).orElseThrow();
        model.addAttribute("car", car);
        return "cars/row";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "cars/list";
    }
}
