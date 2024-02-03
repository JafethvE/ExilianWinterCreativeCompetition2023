package nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.db.repository;

import nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.db.entity.Animal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends org.springframework.data.repository.CrudRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.id = :id")
    Animal findAnimal(@Param("id") int id);

    @Query("SELECT a FROM Animal a")
    List<Animal> findAllAnimals();

}
