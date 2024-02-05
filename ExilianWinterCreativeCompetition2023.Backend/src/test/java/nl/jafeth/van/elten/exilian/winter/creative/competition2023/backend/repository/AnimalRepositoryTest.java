package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import jakarta.validation.ConstraintViolationException;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@TestPropertySource("classpath:application.properties")
@EntityScan("nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto")
@EnableJpaRepositories("nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository")
@ContextConfiguration(classes = {AnimalRepositoryTest.class})
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void findAllAnimalsShouldReturn() {
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
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void findAnimalShouldReturn() {
        Animal animal = animalRepository.findAnimal(1);

        assertAnimal(1, "Test1", "Test1", animal);

        animal = animalRepository.findAnimal(2);

        assertAnimal(2, "Test2", "Test2", animal);
    }

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void saveNewWithCorrectDataShouldSave() {
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

        Animal animal = new Animal(null, "Test3", "Test3");
        animalRepository.save(animal);

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
    void saveNewWithIncorrectDataShouldNotSave() {
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

        Animal animal = new Animal(3, ",", "Test3");
        assertThrows(ConstraintViolationException.class, () -> animalRepository.save(animal));

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
    void saveExistingWithCorrectDataShouldSave() {
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
        Animal existingAnimal = new Animal();

        for (Animal animal : animals) {
            if (animal.getId() == 2) {
                existingAnimal = animal;
            }
        }

        existingAnimal.setName("Test3");
        existingAnimal.setDescription("Test3");

        animalRepository.save(existingAnimal);

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
    void saveExistingWithIncorrectDataShouldNotSave() {
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
        Animal existingAnimal = new Animal();

        for (Animal animal : animals) {
            if (animal.getId() == 2) {
                existingAnimal = animal;
            }
        }

        existingAnimal.setName("Test3");
        existingAnimal.setDescription(",");

        Animal finalExistingAnimal = existingAnimal;
        assertThrows(TransactionSystemException.class, () -> animalRepository.save(finalExistingAnimal));

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
    void deleteShouldDelete() {
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
        Animal existingAnimal = new Animal();

        for (Animal animal : animals) {
            if (animal.getId() == 2) {
                existingAnimal = animal;
            }
        }

        animalRepository.delete(existingAnimal);

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
