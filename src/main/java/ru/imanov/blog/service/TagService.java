package ru.imanov.blog.service;

import ru.imanov.blog.entity.Tag;

public interface TagService {
    Tag update(Tag tag);
    Tag add(Tag tag);

    Tag getById(Long id);
    void delete(Long id);

}
