package ru.imanov.blog.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.repository.ArticleRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteSmallArticlesJob implements Runnable {

    private final ArticleRepository articleRepository;

    /**
     * minimum article length
     */
    private static final int minLength = 5;

    /**
     * removes articles with small content
     */
    @Override
    public void run() {
        List<Article> allArticles = articleRepository.findAll();

        allArticles.forEach(article -> {
            if (article.getContent().length() < minLength){
                articleRepository.deleteById(article.getId());
            }
        });
    }
}
