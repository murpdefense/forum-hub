package br.com.soupaulodev.forumhub.modules.forum.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class representing a forum.
 */
@Entity
@Table(name = "tb_forum")
public class ForumEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 50)
    private String description;

    /**
     * The owner of the forum.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    /**
     * The participants of the forum.
     */
    @ManyToMany(mappedBy = "participatingForums")
    private Set<UserEntity> participants = new HashSet<>();

    /**
     * The topics in the forum.
     */
    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TopicEntity> topics = new HashSet<>();

    /**
     * The timestamp when the forum was created.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * The timestamp when the forum was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Default constructor initializing the forum with a new UUID and current timestamps.
     */
    public ForumEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new ForumEntity with the specified name, description, and owner.
     *
     * @param name the name of the forum
     * @param description the description of the forum
     * @param owner the owner of the forum
     */
    public ForumEntity(String name, String description, UserEntity owner) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<UserEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserEntity> participants) {
        this.participants = participants;
    }

    public Set<TopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(Set<TopicEntity> topics) {
        this.topics = topics;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
