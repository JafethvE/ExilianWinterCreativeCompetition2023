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
    void findAnimalShouldReturn() {
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
    void addAnimalShouldReturn() {
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
    void updateAnimalShouldReturn() {
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

    void assertAnimal(int id, String name, String description, Animal animal) {
        assertEquals(id, animal.getId());
        assertEquals(name, animal.getName());
        assertEquals(description, animal.getDescription());
    }
}
