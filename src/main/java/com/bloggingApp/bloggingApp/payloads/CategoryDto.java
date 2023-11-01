package com.bloggingApp.bloggingApp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CategoryDto {

    private Integer categoryId;

    @NotEmpty @NotNull @NotBlank
    private String categoryTitle;

    @NotEmpty @NotNull @NotBlank
    private String categoryDescription;
}
