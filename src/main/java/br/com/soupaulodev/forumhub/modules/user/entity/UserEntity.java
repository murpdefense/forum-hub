package br.com.soupaulodev.forumhub.modules.user.entity;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany
    @JoinTable(
            name = "user_forum",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "forum_id")
    )
    private Set<ForumEntity> forums;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public UserEntity() {
        this.id = UUID.randomUUID();
    }

    public UserEntity(String name, String username, String email, String password, UserRole role) {
        this();
        this.name = name;
        this.username = username.toLowerCase().replace(" ", "_");
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void validate() {
        if (this.name == null || this.name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (this.email == null || this.email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (this.password == null || this.password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (this.role == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }
}
