package co.develhope.crud.controller;

import co.develhope.crud.entities.Car;
import co.develhope.crud.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    CarRepository carRepository;
    /*create a new Car
return a list of all the Cars
return a single Car - if the id is not in the db (use existsById()), returns an empty Car
updates the type of a specific Car, identified by id and passing a query param - if not present in the db,
returns an empty Car
deletes a specific Car - if absent, the response will have a Conflict HTTP status
deletes all the Cars in the db
*/

    @PostMapping("")
    public Car createCare(@RequestBody Car car){
        Car carsSaved = carRepository.saveAndFlush(car);
        return carsSaved;
    }

    @GetMapping
    public List<Car> get(){
          List<Car> cars = carRepository.findAll();
          return cars;
    }

    @GetMapping("/{id}")
    public Car getSingle(@PathVariable long id){
        Car car = carRepository.getReferenceById(id);
        boolean carExist = carRepository.existsById(id);
        if(carExist) {
            return car;
        }
        return new Car();
    }

    @PutMapping("/{id}")
    public Car updateSingle (@PathVariable long id, @RequestBody Car car) {
        car.setId(id);
        Car car1 = carRepository.saveAndFlush(car);
        boolean carExist = carRepository.existsById(id);
        if (carExist) {
            return car1;
        }
        return new Car();
    }

    @DeleteMapping("/{id}")
    //anche se faccio Delete di un ID inesistente, mi da OK come risposta.
    public void deleteSingle(@PathVariable long id, HttpServletResponse response){
        boolean carExist = carRepository.existsById(id);
        if(carExist) {
            carRepository.deleteById(id);
        }else{
            new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("")
    public void deleteAll(){
        carRepository.deleteAll();
    }

}

