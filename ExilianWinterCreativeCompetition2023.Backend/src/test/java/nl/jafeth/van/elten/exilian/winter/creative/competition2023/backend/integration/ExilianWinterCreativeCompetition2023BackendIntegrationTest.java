package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.AnimalBackendApplication;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository.AnimalRepository;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AnimalBackendApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Configuration
public class ExilianWinterCreativeCompetition2023BackendIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AnimalRepository animalRepository;

    private static final String TOO_LONG_STRING = "Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur Excepteur sint occaecat cupidatat non proident sunt in culpa qui officia deserunt mollit anim id est laborum";

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void findAllAnimalsShouldReturn() {
        ResponseEntity<Animal[]> response = restTemplate.getForEntity("http://localhost:" + port + "/animals", Animal[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Animal[].class, response.getBody());

        List<Animal> animals = List.of(response.getBody());

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void findAnimalWithCorrectDataShouldReturn() {
        ResponseEntity<Animal> response = restTemplate.getForEntity("http://localhost:" + port + "/animal/1", Animal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Animal animal = response.getBody();

        assertAnimal(1, "Test1", "Test1", animal);

        response = restTemplate.getForEntity("http://localhost:" + port + "/animal/2", Animal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Animal.class, response.getBody());

        animal = response.getBody();

        assertAnimal(2, "Test2", "Test2", animal);
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void findAnimalWithIncorrectDataShouldReturn() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/animal/@", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertThat( response.getBody(), containsString(",\"status\":404,\"error\":\"Not Found\",\"path\":\"/animal/@\""));

        response = restTemplate.getForEntity("http://localhost:" + port + "/animal/T", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertThat( response.getBody(), containsString(",\"status\":404,\"error\":\"Not Found\",\"path\":\"/animal/T\""));
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void addAnimalWithCorrectDataShouldReturn() {
        List<Animal> animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, "Test3", "Test3"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal saved", response.getBody());

        animals = animalRepository.findAllAnimals();

        assertEquals(3, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test3")),
                        hasProperty("description", is("Test3"))
                ))
        ));
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void addAnimalWithIncorrectDataShouldReturn() {
        List<Animal> animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, "@", "Test3"), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Validation failed for classes [nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal] during persist time for groups [jakarta.validation.groups.Default, ]\n" +
                "List of constraint violations:[\n" +
                "\tConstraintViolationImpl{interpolatedMessage='Name can only have letters, numbers, and spaces', propertyPath=name, rootBeanClass=class nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal, messageTemplate='Name can only have letters, numbers, and spaces'}\n" +
                "]", response.getBody());

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, "", "Test3"), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Validation failed for classes [nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal] during persist time for groups [jakarta.validation.groups.Default, ]\n" +
                "List of constraint violations:[\n" +
                "\tConstraintViolationImpl{interpolatedMessage='Animal can't have an empty name', propertyPath=name, rootBeanClass=class nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal, messageTemplate='Animal can't have an empty name'}\n" +
                "]", response.getBody());

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, TOO_LONG_STRING, "Test3"), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Validation failed for classes [nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal] during persist time for groups [jakarta.validation.groups.Default, ]\n" +
                "List of constraint violations:[\n" +
                "\tConstraintViolationImpl{interpolatedMessage='Name cannot be longer than 255 characters', propertyPath=name, rootBeanClass=class nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal, messageTemplate='Name cannot be longer than 255 characters'}\n" +
                "]", response.getBody());

        animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, "Test3", "@"), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Validation failed for classes [nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal] during persist time for groups [jakarta.validation.groups.Default, ]\n" +
                "List of constraint violations:[\n" +
                "\tConstraintViolationImpl{interpolatedMessage='Description contains illegal characters', propertyPath=description, rootBeanClass=class nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal, messageTemplate='Description contains illegal characters'}\n" +
                "]", response.getBody());

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, "Test3", ""), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Validation failed for classes [nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal] during persist time for groups [jakarta.validation.groups.Default, ]\n" +
                "List of constraint violations:[\n" +
                "\tConstraintViolationImpl{interpolatedMessage='Animal can't have an empty description', propertyPath=description, rootBeanClass=class nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal, messageTemplate='Animal can't have an empty description'}\n" +
                "]", response.getBody());

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/add", new Animal(3, "Test3", TOO_LONG_STRING), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Validation failed for classes [nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal] during persist time for groups [jakarta.validation.groups.Default, ]\n" +
                "List of constraint violations:[\n" +
                "\tConstraintViolationImpl{interpolatedMessage='Description cannot be longer than 255 characters', propertyPath=description, rootBeanClass=class nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal, messageTemplate='Description cannot be longer than 255 characters'}\n" +
                "]", response.getBody());

        animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void updateAnimalWithCorrectDataShouldReturn() {
        List<Animal> animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));

        Animal animal = animalRepository.findAnimal(2);
        animal.setName("Test3");
        animal.setDescription("Test3");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal updated", response.getBody());

        animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test3")),
                        hasProperty("description", is("Test3"))
                ))
        ));
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void updateAnimalWithIncorrectDataShouldReturn() {
        List<Animal> animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));

        Animal animal = animalRepository.findAnimal(2);
        animal.setName("@");
        animal.setDescription("Test3");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Could not commit JPA transaction", response.getBody());

        animal.setName("");
        animal.setDescription("Test3");

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Could not commit JPA transaction", response.getBody());

        animal.setName(TOO_LONG_STRING);
        animal.setDescription("Test3");

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Could not commit JPA transaction", response.getBody());

        animal.setName("Test3");
        animal.setDescription("@");

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Could not commit JPA transaction", response.getBody());

        animal.setName("Test3");
        animal.setDescription("");

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Could not commit JPA transaction", response.getBody());

        animal.setName("Test3");
        animal.setDescription(TOO_LONG_STRING);

        response = restTemplate.postForEntity("http://localhost:" + port + "/animal/update", animal, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Could not commit JPA transaction", response.getBody());

        animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void deleteAnimalShouldDelete() {
        List<Animal> animals = animalRepository.findAllAnimals();

        assertEquals(2, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));

        Animal animal = animalRepository.findAnimal(2);

        HttpEntity<Animal> request = new HttpEntity<>(animal);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/animal/delete", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(String.class, response.getBody());
        assertEquals("Animal deleted", response.getBody());

        animals = animalRepository.findAllAnimals();

        assertEquals(1, animals.size());
        assertThat(animals, allOf(
                hasItem(allOf(
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                ))
        ));
    }

    void assertAnimal(Integer id, String name, String description, Animal animal) {
        assertEquals(id, animal.getId());
        assertEquals(name, animal.getName());
        assertEquals(description, animal.getDescription());
    }
}
