package com.yenda.api.domain.dto;

import com.yenda.api.domain.entities.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String isbn;
    private String title;
    private AuthorEntity author;
}
