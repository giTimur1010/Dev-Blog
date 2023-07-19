package ru.imanov.blog.service;

import ru.imanov.blog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imanov.blog.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public void updateTag(Tag tag){
        tagRepository.save(tag);
    }

    /**
     * @param name - tag name
     * @return created tag
     */
    public Tag createTag(String name){
        Tag tag = new Tag();

        tag.setName(name);

        tagRepository.save(tag);

        return tag;
    }

    /**
     * removes a tag from the database
     * @param tag - tag to delete
     */
    public void deleteTag(Tag tag){
        tagRepository.delete(tag);
    }
}
