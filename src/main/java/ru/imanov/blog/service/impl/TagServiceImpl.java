package ru.imanov.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.AbstractEntity;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.Tag;
import ru.imanov.blog.exception.article.ArticleNotFoundException;
import ru.imanov.blog.exception.common.NullIdException;
import ru.imanov.blog.exception.tag.TagFieldsEmptyException;
import ru.imanov.blog.exception.tag.TagNotFoundException;
import ru.imanov.blog.repository.ArticleRepository;
import ru.imanov.blog.repository.TagRepository;
import ru.imanov.blog.rest.dto.request.tag.NewTagRequest;
import ru.imanov.blog.rest.dto.request.tag.UpdateTagRequest;
import ru.imanov.blog.rest.dto.response.tag.NewTagResponse;
import ru.imanov.blog.rest.dto.response.tag.TagAllFields;
import ru.imanov.blog.service.TagService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final ArticleRepository articleRepository;

    /**
     * updates the tag
     * @param request - tag to update
     * @return - updated tag
     * @throws TagNotFoundException - it is thrown out when the parent tag or children tag is not in the database.
     * It is also thrown when the tag with the specified id is not in the database
     * @throws NullIdException - it is thrown out when the tag has id = null
     * @throws ArticleNotFoundException - it is thrown out when the article to which the tag refers is not in the
     * database
     */

    @Override
    public TagAllFields update(UpdateTagRequest request)
            throws TagNotFoundException, NullIdException, ArticleNotFoundException {

        if (request.getId() == null){
            throw new NullIdException("you cannot update a tag with id = null");
        }

        if (!tagRepository.existsById(request.getId())){
            throw new TagNotFoundException(
                    String.format("the tag with id = %d cannot be updated because it is not found", request.getId()),
                    true
            );
        }

        if (request.getParentId() != null && !tagRepository.existsById(request.getParentId())){
            throw new TagNotFoundException("it is not possible to update a tag with a non-existent parent tag");
        }

        if (request.getChildrenIds() != null) {
            for (Long childrenId : request.getChildrenIds()) {
                if (!tagRepository.existsById(childrenId)) {
                    throw new TagNotFoundException("it is not possible to update a tag with a non-existent children tag");
                }
            }
        }

        if (request.getArticleIds() != null) {
            for (Long articleId : request.getArticleIds()) {
                if (!articleRepository.existsById(articleId)) {
                    throw new ArticleNotFoundException("it is not possible to update a tag with a non-existent article");
                }
            }
        }

        Tag tag = Tag.builder()
                .name(request.getName())
                .build();

        if (request.getParentId() != null){
            tag.setParent(tagRepository.findById(request.getParentId()).get());
        }

        if (request.getChildrenIds() != null){
            List<Tag> children = request.getChildrenIds().stream().
                    map(id -> tagRepository.findById(id).get())
                    .collect(Collectors.toList());

            tag.setChildren(children);
        }

        if (request.getArticleIds() != null){
            List<Article> articles = request.getArticleIds().stream()
                    .map(id -> articleRepository.findById(id).get())
                    .collect(Collectors.toList());

            tag.setArticles(articles);
        }

        tag.setId(request.getId());

        checkTag(tag);

        return transform(tagRepository.save(tag));
    }

    /**
     * adds a tag
     * @param request - tag to add
     * @return - added tag
     */
    @Override
    public NewTagResponse add(NewTagRequest request) {

        Tag tag = Tag.builder()
                .name(request.getName())
                .build();

        checkTag(tag);

        Tag addedTag = tagRepository.save(tag);

        return NewTagResponse.builder()
                .id(addedTag.getId())
                .name(addedTag.getName())
                .build();
    }

    /**
     * finds a tag by id
     * @param id - tag id
     * @return - tag found by id
     * @throws TagNotFoundException - it is thrown out when the tag is not in the database
     */
    @Override
    public TagAllFields getById(Long id) throws TagNotFoundException {
        Optional<Tag> tagFromDb = tagRepository.findById(id);

        if (tagFromDb.isEmpty()){
            throw new TagNotFoundException(String.format("tag not found by id = %d", id));
        }

        return transform(tagFromDb.get());
    }

    /**
     * deletes a tag
     * @param id - tag id
     * @throws TagNotFoundException - it is thrown out when there is no tag with the specified id
     */
    @Override
    public void delete(Long id) throws TagNotFoundException {

        if (!tagRepository.existsById(id)){
            throw new TagNotFoundException(String.format(
                    "a tag with id = %d cannot be deleted because he is not in the database",
                    id
                )
            );
        }

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

    /**
     * transforms the Tag type into the TagAllFields type
     * @param tag - tag to transform
     * @return transformed tag
     */

    private TagAllFields transform(Tag tag){
        TagAllFields tagAllFields = TagAllFields.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();

        if (tag.getParent() != null){
            tagAllFields.setParentId(tag.getParent().getId());
        }

        if (tag.getChildren() != null){
            List<Long> childrenIds = tag.getChildren().stream()
                    .map(AbstractEntity::getId)
                    .collect(Collectors.toList());

            tagAllFields.setChildrenIds(childrenIds);
        }

        if (tag.getArticles() != null){
            List<Long> articleIds = tag.getArticles().stream()
                    .map(AbstractEntity::getId)
                    .collect(Collectors.toList());

            tagAllFields.setArticleIds(articleIds);
        }

        return tagAllFields;
    }
}
