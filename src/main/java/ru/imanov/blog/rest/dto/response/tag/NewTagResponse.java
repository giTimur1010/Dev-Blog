package ru.imanov.blog.rest.dto.response.tag;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewTagResponse {
    private Long id;
    private String name;
}
