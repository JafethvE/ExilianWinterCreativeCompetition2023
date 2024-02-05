package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.service;

import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository.AnimalRepository;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        List<Animal> animals = new ArrayList<>();
        animals.add(animal1);
        animals.add(animal2);

        when(animalRepository.findAllAnimals()).thenReturn(animals);
        when(animalRepository.findAnimal(1)).thenReturn(animal1);
        when(animalRepository.findAnimal(2)).thenReturn(animal2);
    }

    @Test
    void findAllAnimalsShouldReturn() {
        List<Animal> animals = animalService.findAllAnimals();
        verify(animalRepository).findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("id", is(2)),
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));
    }

    @Test
    void findAnimalShouldReturn() {
        Animal animal = animalService.findAnimal(1);
        verify(animalRepository).findAnimal(1);
        assertAnimal(1, "Test1", "Test1", animal);

        animal = animalService.findAnimal(2);
        verify(animalRepository).findAnimal(2);
        assertAnimal(2, "Test2", "Test2", animal);
    }

    @Test
    void addAnimalShouldSave() {
        Animal animal = new Animal(1, "Test1", "Test1");
        animalService.addAnimal(animal);
        verify(animalRepository).save(animal);
    }

    @Test
    void updateAnimalShouldSave() {
        Animal animal = new Animal(1, "Test1", "Test1");
        animalService.updateAnimal(animal);
        verify(animalRepository).save(animal);
    }

    @Test
    void deleteAnimalShouldDelete() {
        Animal animal = new Animal(1, "Test1", "Test1");
        animalService.deleteAnimal(animal);
        verify(animalRepository).delete(animal);
    }

    void assertAnimal(Integer id, String name, String description, Animal animal) {
        assertEquals(id, animal.getId());
        assertEquals(name, animal.getName());
        assertEquals(description, animal.getDescription());
    }
}
