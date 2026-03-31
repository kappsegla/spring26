package org.example.spring26.controller;

import org.example.spring26.model.Car;
import org.example.spring26.repository.CarRepository;
import org.springframework.security.web.csrf.CsrfToken;
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
        model.addAttribute("request", request);
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.addAttribute("_csrf", token);
        return "cars/index";
    }

    @PostMapping
    public String addCar(@ModelAttribute Car car, Model model, @RequestHeader(value = "HX-Request", required = false) String htmxRequest, jakarta.servlet.http.HttpServletResponse response) {
        carRepository.save(car);
        if ("true".equals(htmxRequest)) {
            response.setHeader("HX-Trigger", "carAdded");
            return null; // Return empty body, HTMX will handle it via trigger
        }
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteCar(@PathVariable Long id, @RequestHeader(value = "HX-Request", required = false) String htmxRequest, jakarta.servlet.http.HttpServletResponse response) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        }
        if ("true".equals(htmxRequest)) {
            response.setHeader("HX-Trigger", "carDeleted");
        }
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
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) return "";
        model.addAttribute("car", car);
        return "cars/row";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "cars/list";
    }
}
