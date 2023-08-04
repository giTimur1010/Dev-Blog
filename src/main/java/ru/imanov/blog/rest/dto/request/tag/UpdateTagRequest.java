package ru.imanov.blog.rest.dto.request.tag;

import lombok.Data;

import java.util.List;

@Data
public class UpdateTagRequest {
    private Long id;

    private String name;

    private Long parentId;

    private List<Long> childrenIds;

    private List<Long> articleIds;
}
