package nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnimalTest {

    @Test
    void defaultConstructorShouldReturn() {
        Animal animal = new Animal();
        assertEquals(0, animal.getId());
        assertNull(animal.getName());
        assertNull(animal.getDescription());
    }

    @Test
    void overloadedConstructorShouldReturn() {
        Animal animal = new Animal(1, "Test1", "Test1");
        assertAnimal(1, "Test1", "Test1", animal);

        animal = new Animal(2, "Test2", "Test2");
        assertAnimal(2, "Test2", "Test2", animal);
    }

    @Test
    void getIdShouldReturn() {
        Animal animal = new Animal();
        assertEquals(0, animal.getId());

        animal = new Animal(1, "Test1", "Test1");
        assertEquals(1, animal.getId());

        animal = new Animal(2, "Test2", "Test2");
        assertEquals(2, animal.getId());
    }

    @Test
    void getNameShouldReturn() {
        Animal animal = new Animal();
        assertNull(animal.getName());

        animal = new Animal(1, "Test1", "Test1");
        assertEquals("Test1", animal.getName());

        animal = new Animal(2, "Test2", "Test2");
        assertEquals("Test2", animal.getName());
    }

    @Test
    void getDescriptionShouldReturn() {
        Animal animal = new Animal();
        assertNull(animal.getDescription());

        animal = new Animal(1, "Test1", "Test1");
        assertEquals("Test1", animal.getDescription());

        animal = new Animal(2, "Test2", "Test2");
        assertEquals("Test2", animal.getDescription());
    }

    @Test
    void setNameShouldSet() {
        Animal animal = new Animal();
        assertNull(animal.getName());

        animal.setName("Test1");
        assertEquals("Test1", animal.getName());

        animal.setName("Test2");
        assertEquals("Test2", animal.getName());
    }

    @Test
    void setDescriptionShouldSet() {
        Animal animal = new Animal();
        assertNull(animal.getDescription());

        animal.setDescription("Test1");
        assertEquals("Test1", animal.getDescription());

        animal.setDescription("Test2");
        assertEquals("Test2", animal.getDescription());
    }

    @Test
    void toStringShouldReturn() {
        Animal animal = new Animal();
        assertEquals(String.format("Id: %s\nName: %s\nDescription: %s", "0", "null", "null"), animal.toString());

        animal = new Animal(1, "Test1", "Test1");
        assertEquals(String.format("Id: %s\nName: %s\nDescription: %s", "1", "Test1", "Test1"), animal.toString());

        animal = new Animal(2, "Test2", "Test2");
        assertEquals(String.format("Id: %s\nName: %s\nDescription: %s", "2", "Test2", "Test2"), animal.toString());
    }

    void assertAnimal(int id, String name, String description, Animal animal) {
        assertEquals(id, animal.getId());
        assertEquals(name, animal.getName());
        assertEquals(description, animal.getDescription());
    }
}
