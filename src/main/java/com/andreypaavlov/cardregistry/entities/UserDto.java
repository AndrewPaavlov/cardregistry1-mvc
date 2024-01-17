package com.andreypaavlov.cardregistry.entities;

import lombok.*;

import java.util.Comparator;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String email;

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setLastname(user.getLastname());
        dto.setFirstname(user.getFirstname());
        dto.setPatronymic(user.getPatronymic());
        dto.setEmail(user.getEmail());
        return dto;
    }
}