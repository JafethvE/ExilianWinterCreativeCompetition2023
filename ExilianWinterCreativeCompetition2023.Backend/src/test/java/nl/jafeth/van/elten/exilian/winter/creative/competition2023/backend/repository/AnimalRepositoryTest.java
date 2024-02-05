package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                        hasProperty("id", is(-1)),
                        hasProperty("name", is("Test1")),
                        hasProperty("description", is("Test1"))
                )),
                hasItem(allOf(
                        hasProperty("id", is(-2)),
                        hasProperty("name", is("Test2")),
                        hasProperty("description", is("Test2"))
                ))
        ));
    }
}
