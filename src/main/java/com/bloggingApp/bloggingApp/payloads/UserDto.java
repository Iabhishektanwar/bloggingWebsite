package com.bloggingApp.bloggingApp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotEmpty @NotNull @Size(min = 4, message = "Name should contain 4 characters")
    private String name;

    @Email(message = "Email address not valid")
    private String email;

    @NotNull @NotEmpty @Size(min = 3, max=10, message = "Password must be min of 3 characters and max 10 characters")
    private String password;

    @NotNull
    private String about;
}
