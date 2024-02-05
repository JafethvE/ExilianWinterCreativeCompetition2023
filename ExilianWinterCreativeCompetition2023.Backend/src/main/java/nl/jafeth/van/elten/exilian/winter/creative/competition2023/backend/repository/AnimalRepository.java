package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository;

import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.dto.Animal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends org.springframework.data.repository.CrudRepository<Animal, Long> {

    /**
     * Finds all data on a specific animal or gives an error if there is a problem.
     * @param id The id of the animal requested.
     * @return An HTTP response containing the data of the animal requested or an error.
     */
    @Query("SELECT a FROM Animal a WHERE a.id = :id")
    Animal findAnimal(@Param("id") int id);

    /**
     * Finds all animals currently in the database or gives an error if there is a problem.
     * @return An HTTP response containing the list of animals currently in the database or an error.
     */
    @Query("SELECT a FROM Animal a")
    List<Animal> findAllAnimals();

}
