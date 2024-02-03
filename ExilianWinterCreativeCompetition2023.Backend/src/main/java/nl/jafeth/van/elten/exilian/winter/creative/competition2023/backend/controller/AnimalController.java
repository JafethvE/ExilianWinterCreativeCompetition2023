package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.controller;

import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.service.AnimalService;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.db.entity.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnimalController {

    Logger logger = LoggerFactory.getLogger(AnimalController.class);

    @Autowired
    private AnimalService animalService;

    @RequestMapping(method = RequestMethod.GET, value = "/animals", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Animal> findAllAnimals() {
        logger.info("Finding all animals");
        return animalService.findAllAnimals();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/animal/{id}")
    @ResponseBody
    public ResponseEntity<Object> findAnimal(@PathVariable(name = "id") int id) {
        ResponseEntity<Object> responseEntity;
        logger.info("Finding specific animal id: {}", id);
        Animal animal = animalService.findAnimal(id);
        responseEntity = new ResponseEntity<>(animal, HttpStatus.OK);
        if (animal == null) {
            responseEntity = new ResponseEntity<>(String.format("Animal with id %d not found", id), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/animal/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void addAnimal(@RequestBody Animal animal) {
        logger.info("Start adding new animal: {}", animal);
        animalService.addAnimal(animal);
        logger.info("Finished adding new animal: {}", animal);
    }

}
