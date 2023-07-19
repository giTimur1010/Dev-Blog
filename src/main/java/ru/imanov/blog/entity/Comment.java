package ru.imanov.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @author itimur
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractEntity{

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_user")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_article")
    private Article article;

    @Column(name = "content", nullable = false, length = 240)
    private String content;

    @Column(name = "likes_number")
    private Short likesNumber;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
