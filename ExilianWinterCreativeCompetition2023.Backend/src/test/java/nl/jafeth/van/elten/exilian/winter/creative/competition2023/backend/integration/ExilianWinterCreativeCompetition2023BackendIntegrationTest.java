package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.AnimalBackendApplication;
import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DatabaseSetup(value = "/FindAllAnimalsTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @DatabaseTearDown(value = "/EmptyAllTables.xml", type = DatabaseOperation.DELETE_ALL)
    void findAllAnimalsShouldReturn() {
        ResponseEntity<Animal[]> animalHttpResponse = restTemplate.getForEntity("http://localhost:" + port + "/animals", Animal[].class);

        assertEquals(HttpStatus.OK, animalHttpResponse.getStatusCode());

        List<Animal> animals = List.of(animalHttpResponse.getBody());

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
