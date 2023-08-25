package ru.imanov.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author itimur
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "usr", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity implements UserDetails {
    @Column(name = "avatar", length = 50)
    private String avatarUrl;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "full_name", length = 250)
    private String fullName;

    @Column(name = "email", length = 250, nullable = false)
    private String email;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "author", fetch = FetchType.LAZY)
    private List<Article> articles;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usr_roles",
            inverseJoinColumns = @JoinColumn(name="id_role"),
            joinColumns = @JoinColumn(name="id_usr")
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            roles = new HashSet<>();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
