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

/**
 * Forums the user is participating in.
 */
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

    /**
     * Role of the user.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /**
     * Forums owned by the user.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ForumEntity> ownedForums = new HashSet<>();

    /**
     * Forums the user is participating in.
     */
    @ManyToMany
    @JoinTable(
            name = "forum_participants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "forum_id")
    )
    private Set<ForumEntity> participatingForums = new HashSet<>();

    /**
     * Topics created by the user.
     */
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TopicEntity> topics = new HashSet<>();

    /**
     * Comments made by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments = new HashSet<>();

    /**
     * Likes given by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikeEntity> likes = new HashSet<>();

    /**
     * Timestamp of when the user was created.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * Timestamp of when the user was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Default constructor that initializes the user ID and timestamps.
     */
    public UserEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();

        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructor to create a user with the specified details.
     *
     * @param name the name of the user
     * @param username the username of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param role the role of the user
     */
    public UserEntity(String name, String username, String email, String password, UserRole role) {
        this();
        this.name = name;
        this.username = username.toLowerCase().replace(" ", "_");
        this.email = email;
        this.password = password;
        this.role = role;

        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
