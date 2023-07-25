package ru.imanov.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.Tag;
import ru.imanov.blog.exception.tag.TagAlreadyExistsException;
import ru.imanov.blog.exception.tag.TagFieldsEmptyException;
import ru.imanov.blog.exception.tag.TagNotFoundException;
import ru.imanov.blog.repository.TagRepository;
import ru.imanov.blog.service.TagService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * updates the tag
     * @param tag - tag to update
     * @return - updated tag
     * @throws TagNotFoundException - it is thrown out when the user is not in the database
     */
    @Override
    public Tag update(Tag tag) throws TagNotFoundException {
        if (tagRepository.existsById(tag.getId())){
            throw new TagNotFoundException("the tag cannot be updated because it is not found", true);
        }

        checkTag(tag);

        return tagRepository.save(tag);
    }

    /**
     * adds a tag
     * @param tag - tag to add
     * @return - added tag
     * @throws TagAlreadyExistsException - it is thrown out when the tag is already in the database
     */
    @Override
    public Tag add(Tag tag) throws TagAlreadyExistsException {
        if (tag.getId() != null && tagRepository.existsById(tag.getId())){
            throw new TagAlreadyExistsException("the tag cannot be added as it already exists", true);
        }

        checkTag(tag);

        return tagRepository.save(tag);
    }

    /**
     * finds a tag by id
     * @param id - tag id
     * @return - tag found by id
     * @throws TagNotFoundException - it is thrown out when the tag is not in the database
     */
    @Override
    public Tag getById(Long id) throws TagNotFoundException {
        Optional<Tag> tagFromDb = tagRepository.findById(id);

        if (tagFromDb.isEmpty()){
            throw new TagNotFoundException("tag not found by id");
        }

        return tagFromDb.get();
    }

    /**
     * deletes a tag
     * @param id - tag id
     */
    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    /**
     *  checks that all necessary fields are filled in
     * @param tag - tag to check
     * @throws TagFieldsEmptyException - it is thrown out when not all required tag fields are filled in
     */
    private void checkTag(Tag tag) throws TagFieldsEmptyException {
        if (StringUtils.isEmpty(tag.getName())){
            throw new TagFieldsEmptyException("Tag name is empty", true);
        }
    }
}
