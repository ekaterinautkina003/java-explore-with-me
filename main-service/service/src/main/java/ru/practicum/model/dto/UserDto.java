package ru.practicum.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    Long id;

    @Email
    @NotEmpty
    @NotNull
    @Length(min = 6, max = 254)
    String email;

    @NotBlank
    @Length(min = 2, max = 250)
    String name;
}
