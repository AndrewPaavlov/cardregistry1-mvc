package com.andreypaavlov.cardregistry.services;

import com.andreypaavlov.cardregistry.entities.Role;
import com.andreypaavlov.cardregistry.entities.Specialization;
import com.andreypaavlov.cardregistry.entities.User;
import com.andreypaavlov.cardregistry.entities.UserDto;
import com.andreypaavlov.cardregistry.repositories.SpecializationRepository;
import com.andreypaavlov.cardregistry.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpecializationService {

    private SpecializationRepository specializationRepository;

    @Transactional
    @Cacheable(value = "specs", key = "'all'")
    public Map<Specialization, List<UserDto>> getAllSpec() {

        Map<Specialization, List<UserDto>> map = new LinkedHashMap<>();
        List<Specialization> specializations = specializationRepository.findAllWithDoctors();
        specializations.forEach(specialization -> {
            Set<User> users = specialization.getDoctors();
            List<UserDto> userDTOs = users.stream()
                    .map(user -> UserDto.fromUser(user)).sorted(Comparator.comparing(UserDto::getLastname)
                            .thenComparing(UserDto::getFirstname)
                            .thenComparing(UserDto::getPatronymic))
                    .collect(Collectors.toList());
            map.put(specialization, userDTOs);
        });
        return map;
    }

    @Transactional
    @CacheEvict(value = "specs", key = "'all'")
    public Specialization addSpec(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

//    public List<String> getSpecializationNames() {
//        return specializationRepository.findAllSpecializationNames();
//    }
    public List<String> getAllSpecNames(){
        return specializationRepository.findAllSpecializationNames();
    }

    public Specialization getSpecializationByName(String name) {
        return specializationRepository.getSpecializationBySpecializationName(name);
    }


}
