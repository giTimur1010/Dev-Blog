package ru.imanov.blog.service;

import ru.imanov.blog.rest.dto.request.tag.NewTagRequest;
import ru.imanov.blog.rest.dto.request.tag.UpdateTagRequest;
import ru.imanov.blog.rest.dto.response.tag.NewTagResponse;
import ru.imanov.blog.rest.dto.response.tag.TagAllFields;

public interface TagService {
    TagAllFields update(UpdateTagRequest request);

    NewTagResponse add(NewTagRequest request);

    TagAllFields getById(Long id);

    void delete(Long id);

}
