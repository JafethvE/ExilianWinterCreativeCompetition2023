package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.controller;

import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.service.AnimalService;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnimalControllerTest {

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalController animalController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    void setupCorrectDataForFindAllAnimals() {
        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        List<Animal> animals = new ArrayList<>();
        animals.add(animal1);
        animals.add(animal2);

        when(animalService.findAllAnimals()).thenReturn(animals);
    }

    void setupEmptyDataForFindAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        when(animalService.findAllAnimals()).thenReturn(animals);
    }

    void setupExceptionForFindAllAnimals() {
        when(animalService.findAllAnimals()).thenThrow(new RuntimeException());
    }

    void setupCorrectDataForFindAnimal() {
        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        when(animalService.findAnimal(1)).thenReturn(animal1);
        when(animalService.findAnimal(2)).thenReturn(animal2);
    }

    void setupEmptyDataForFindAnimal() {
        when(animalService.findAnimal(anyInt())).thenReturn(null);
    }

    void setupExceptionForFindAnimal() {
        when(animalService.findAnimal(anyInt())).thenThrow(new RuntimeException());
    }

    void setupExceptionForAddAnimal() {
        doThrow(new RuntimeException()).when(animalService).addAnimal(any(Animal.class));
    }

    void setupExceptionForUpdateAnimal() {
        doThrow(new RuntimeException()).when(animalService).updateAnimal(any(Animal.class));
    }

    void setupExceptionForDeleteAnimal() {
        doThrow(new RuntimeException()).when(animalService).deleteAnimal(any(Animal.class));
    }

    @Test
    void findAllAnimalsWithCorrectDataShouldReturn() {
        setupCorrectDataForFindAllAnimals();

        ResponseEntity<Object> response = animalController.findAllAnimals();

        verify(animalService).findAllAnimals();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());

        List<Animal> animals = (List<Animal>) response.getBody();

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
    void findAllAnimalsWithEmptyDataShouldReturn() {
        setupEmptyDataForFindAllAnimals();

        ResponseEntity<Object> response = animalController.findAllAnimals();

        verify(animalService).findAllAnimals();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());

        List<Animal> animals = (List<Animal>) response.getBody();

        assertEquals(0, animals.size());
    }

    @Test
    void findAllAnimalsWithExceptionShouldReturn() {
        setupExceptionForFindAllAnimals();

        ResponseEntity<Object> response = animalController.findAllAnimals();

        verify(animalService).findAllAnimals();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());
    }

    @Test
    void findAnimalWithCorrectDataShouldReturn() {
        setupCorrectDataForFindAnimal();

        ResponseEntity<Object> response = animalController.findAnimal(1);
        verify(animalService).findAnimal(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Animal.class, response.getBody());

        Animal animal = (Animal) response.getBody();

        assertNotNull(animal);
        assertAnimal(1, "Test1", "Test1", animal);

        response = animalController.findAnimal(2);
        verify(animalService).findAnimal(2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Animal.class, response.getBody());

        animal = (Animal) response.getBody();

        assertNotNull(animal);
        assertAnimal(2, "Test2", "Test2", animal);
    }

    @Test
    void findAnimalWithEmptyDataShouldReturn() {
        setupEmptyDataForFindAnimal();

        ResponseEntity<Object> response = animalController.findAnimal(1);
        verify(animalService).findAnimal(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal with id 1 not found", response.getBody());

        response = animalController.findAnimal(2);
        verify(animalService).findAnimal(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());

        assertEquals("Animal with id 2 not found", response.getBody());
    }

    @Test
    void findAnimalWithExceptionShouldReturn() {
        setupExceptionForFindAnimal();

        ResponseEntity<Object> response = animalController.findAnimal(1);

        verify(animalService).findAnimal(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        response = animalController.findAnimal(2);

        verify(animalService).findAnimal(2);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());
    }

    @Test
    void addAnimalCorrectlyShouldReturn() {
        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ResponseEntity<Object> response = animalController.addAnimal(animal1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal saved", response.getBody());

        response = animalController.addAnimal(animal2);

        verify(animalService, Mockito.times(2)).addAnimal(argumentCaptor.capture());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal saved", response.getBody());

        assertThat(argumentCaptor.getAllValues(), allOf(
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
    void addAnimalWithExceptionShouldReturn() {
        setupExceptionForAddAnimal();

        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ResponseEntity<Object> response = animalController.addAnimal(animal1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        response = animalController.addAnimal(animal2);

        verify(animalService, Mockito.times(2)).addAnimal(argumentCaptor.capture());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        assertThat(argumentCaptor.getAllValues(), allOf(
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
    void updateAnimalCorrectlyShouldReturn() {
        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ResponseEntity<Object> response = animalController.updateAnimal(animal1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal updated", response.getBody());

        response = animalController.updateAnimal(animal2);

        verify(animalService, Mockito.times(2)).updateAnimal(argumentCaptor.capture());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal updated", response.getBody());

        assertThat(argumentCaptor.getAllValues(), allOf(
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
    void updateAnimalWithExceptionShouldReturn() {
        setupExceptionForUpdateAnimal();

        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ResponseEntity<Object> response = animalController.updateAnimal(animal1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        response = animalController.updateAnimal(animal2);

        verify(animalService, Mockito.times(2)).updateAnimal(argumentCaptor.capture());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        assertThat(argumentCaptor.getAllValues(), allOf(
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
    void deleteAnimalCorrectlyShouldReturn() {
        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ResponseEntity<Object> response = animalController.deleteAnimal(animal1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal deleted", response.getBody());

        response = animalController.deleteAnimal(animal2);

        verify(animalService, Mockito.times(2)).deleteAnimal(argumentCaptor.capture());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal deleted", response.getBody());

        assertThat(argumentCaptor.getAllValues(), allOf(
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
    void deleteAnimalWithExceptionShouldReturn() {
        setupExceptionForDeleteAnimal();

        Animal animal1 = new Animal(1, "Test1", "Test1");
        Animal animal2 = new Animal(2, "Test2", "Test2");

        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ResponseEntity<Object> response = animalController.deleteAnimal(animal1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        response = animalController.deleteAnimal(animal2);

        verify(animalService, Mockito.times(2)).deleteAnimal(argumentCaptor.capture());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Internal server error", response.getBody());

        assertThat(argumentCaptor.getAllValues(), allOf(
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

    void assertAnimal(int id, String name, String description, Animal animal) {
        assertEquals(id, animal.getId());
        assertEquals(name, animal.getName());
        assertEquals(description, animal.getDescription());
    }
}
