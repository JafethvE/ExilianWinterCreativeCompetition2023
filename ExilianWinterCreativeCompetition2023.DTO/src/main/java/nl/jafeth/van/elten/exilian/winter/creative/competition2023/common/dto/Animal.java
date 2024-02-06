package nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entity definition for the Animal entity.
 */
@Entity
@Table
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Max(value = Integer.MAX_VALUE, message = "ID must fit in an integer")
    @Min(value = 0, message = "ID must start at 0")
    private Integer id;

    @Column
    @NotBlank(message = "Animal can't have an empty name")
    @NotNull(message = "Animal must have a name")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{1,255}$", message = "Name can only have letters, numbers, and spaces")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;

    @Column
    @NotBlank(message = "Animal can't have an empty description")
    @NotNull(message = "Animal must have a description")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{1,255}$", message = "Description contains illegal characters")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String description;

    /**
     * Default constructor.
     */
    public Animal() {

    }

    /**
     * Overloaded constructor.
     * @param id The database ID for this animal.
     * @param name The name of this animal.
     * @param description The description of this animal.
     */
    public Animal(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the ID of this animal.
     * @return The ID of this animal.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the name of this animal.
     * @return The name of this animal.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of this animal.
     * @return The description of this animal.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Id: %s\nName: %s\nDescription: %s", id, name, description);
    }
}