package ru.imanov.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author itimur
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tag", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Tag parent;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Tag> children;

    @ManyToMany()
    @JoinTable(
            name = "article_tag",
            inverseJoinColumns =  @JoinColumn(name = "id_article"),
            joinColumns = @JoinColumn(name = "id_tag")
    )
    private List<Article> articles;
}
