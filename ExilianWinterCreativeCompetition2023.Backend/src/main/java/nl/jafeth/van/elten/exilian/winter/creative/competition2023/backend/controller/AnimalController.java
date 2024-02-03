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
    public ResponseEntity<Object> findAllAnimals() {
        ResponseEntity<Object> responseEntity;
        logger.info("Finding all animals");
        try {
            List<Animal> animals = animalService.findAllAnimals();
            responseEntity = new ResponseEntity<>(animals, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/animal/{id}")
    @ResponseBody
    public ResponseEntity<Object> findAnimal(@PathVariable(name = "id") int id) {
        ResponseEntity<Object> responseEntity;
        logger.info("Finding specific animal id: {}", id);
        try {
            Animal animal = animalService.findAnimal(id);
            responseEntity = new ResponseEntity<>(animal, HttpStatus.OK);
            if (animal == null) {
                responseEntity = new ResponseEntity<>(String.format("Animal with id %d not found", id), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/animal/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addAnimal(@RequestBody Animal animal) {
        ResponseEntity<Object> responseEntity;
        logger.info("Start adding new animal: {}", animal);
        try {
            animalService.addAnimal(animal);
            responseEntity = new ResponseEntity<>("Animal saved", HttpStatus.OK);
            logger.info("Finished adding new animal: {}", animal);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/animal/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> updateAnimal(@RequestBody Animal animal) {
        ResponseEntity<Object> responseEntity;
        logger.info("Start updating animal: {}", animal);
        try {
            animalService.updateAnimal(animal);
            responseEntity = new ResponseEntity<>("Animal updated", HttpStatus.OK);
            logger.info("Finished updating animal: {}", animal);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/animal/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> deleteAnimal(@RequestBody Animal animal) {
        ResponseEntity<Object> responseEntity;
        logger.info("Start deleting animal: {}", animal);
        try {
            animalService.deleteAnimal(animal);
            responseEntity = new ResponseEntity<>("Animal deleted", HttpStatus.OK);
            logger.info("Finished deleting animal: {}", animal);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
