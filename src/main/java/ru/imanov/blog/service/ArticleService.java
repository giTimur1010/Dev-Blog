package ru.imanov.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author itimur
 */

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    /**
     * updates the article in database
     * @param article - article to update
     */
    public void updateArticle(Article article){
        articleRepository.save(article);
    }

    /**
     * Creates and saves an article in the database
     * @param title - article title
     * @param content - article content
     * @param likesNumber - the number of likes of the article
     * @param thumbnailUrl - the address where the article's t thumbnail is located
     * @param createdDate - date of creation of the article
     * @return created article
     */

    public Article createArticle(String title, String content, Short likesNumber, String thumbnailUrl,
                                LocalDateTime createdDate){
        Article article = new Article();

        article.setTitle(title);
        article.setContent(content);
        article.setLikesNumber(likesNumber);
        article.setThumbnailUrl(thumbnailUrl);
        article.setCreatedDate(createdDate);

        articleRepository.save(article);

        return article;
    }

    /**
     * deletes an article from the database
     * @param article - article to delete
     */
    public void deleteArticle(Article article){
        articleRepository.delete(article);
    }

    /**
     * finds all the author's articles
     * @param author - author of articles
     * @return author's articles
     */
    public List<Article> findAllByAuthor(User author){
        return articleRepository.findAllByAuthor(author);
    }

}
