package ru.imanov.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(schema="public", name="role")
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usr_roles",
            inverseJoinColumns = @JoinColumn(name="id_role"),
            joinColumns = @JoinColumn(name="id_usr")
    )
    private List<User> users;

}
