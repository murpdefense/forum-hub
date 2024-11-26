package br.com.soupaulodev.forumhub.modules.user.entity;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ForumEntity> ownedForums = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "forum_participants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "forum_id")
    )
    private Set<ForumEntity> participatingForums = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TopicEntity> topics = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikeEntity> likes = new HashSet<>();

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
