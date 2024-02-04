package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.service;

import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository.AnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    Logger logger = LoggerFactory.getLogger(AnimalService.class);

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> findAllAnimals() {
        logger.info("Start finding all animals");
        List<Animal> animals = animalRepository.findAllAnimals();
        logger.info("Finish finding all {} animals", animals.size());
        return animals;
    }

    public Animal findAnimal(int id) {
        logger.info("Start finding specific animal id: {}", id);
        Animal animal = animalRepository.findAnimal(id);
        logger.info("Finished finding animal id: {}\nAnimal found: {}", id, animal);
        return animal;
    }

    public void addAnimal(Animal animal) {
        logger.info("Start adding new animal: {}", animal);
        animalRepository.save(animal);
        logger.info("Finished adding new animal: {}", animal);
    }

    public void updateAnimal(Animal animal) {
        logger.info("Start updating animal: {}", animal);
        animalRepository.save(animal);
        logger.info("Finished updating animal: {}", animal);
    }

    public void deleteAnimal(Animal animal) {
        logger.info("Start deleting animal: {}", animal);
        animalRepository.delete(animal);
        logger.info("Finished deleting animal: {}", animal);
    }
}
