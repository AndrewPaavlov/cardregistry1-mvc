package com.andreypaavlov.cardregistry.repositories;

import com.andreypaavlov.cardregistry.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {


    @Query("SELECT s FROM Specialization s LEFT JOIN s.doctors d ON s.specializationName= d.specialization.specializationName")
    List<Specialization> findAllWithDoctors();

    @Query("SELECT s FROM Specialization s where s.specializationName = :name")
    Specialization findSpecializationBySpecialization_name(@Param("name") String name);

    @Query("select s.specializationName from  Specialization s order by s.specializationName asc")
    List<String> findAllSpecializationNames();
    @Query("select s from Specialization s order by s.specializationName asc")
    List<Specialization> findAllSpecializations();
    Specialization getSpecializationBySpecializationName(String name);


}
