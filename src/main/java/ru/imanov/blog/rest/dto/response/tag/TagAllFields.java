package ru.imanov.blog.rest.dto.response.tag;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class TagAllFields {
    private Long id;

    private String name;

    private Long parentId;

    private List<Long> childrenIds;

    private List<Long> articleIds;
}
