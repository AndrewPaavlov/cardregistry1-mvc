package com.andreypaavlov.cardregistry.services;

import com.andreypaavlov.cardregistry.entities.Specialization;
import com.andreypaavlov.cardregistry.entities.User;
import com.andreypaavlov.cardregistry.repositories.SpecializationRepository;
import com.andreypaavlov.cardregistry.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SpecializationRepository specializationRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User setSpecializationToUser(String spec, String email) {
        Specialization specialization = specializationRepository.findSpecializationBySpecialization_name(spec);
        User user = userRepository.findUserByEmailAndRole(email).orElseThrow(() -> new EmptyResultDataAccessException("User not found", 1));
        user.setSpecialization(specialization);
        return userRepository.save(user);
    }
    @Transactional
    public User saveNewUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DataIntegrityViolationException("Пользователь с таким email уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userRepository.save(user);
    }

    @ModelAttribute("doctor")
    public User createUserForSession(Authentication authentication) {
        String doctorEmail = authentication.getName();
        User doctor = userRepository.findByEmail(doctorEmail).get();
        return doctor;
    }

}
