package br.com.soupaulodev.forumhub.modules.like.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a like on a topic.
 */
@Entity
@Table(name = "tb_likes")
public class LikeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    /**
     * The user who liked the topic.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The topic that was liked.
     */
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    /**
     * The timestamp when the like was created.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * Default constructor.
     * Initializes the id and createdAt fields.
     */
    public LikeEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }

    /**
     * Constructs a new LikeEntity with the specified user and topic.
     * Initializes the id and createdAt fields.
     *
     * @param user the user who liked the topic
     * @param topic the topic that was liked
     */
    public LikeEntity(UserEntity user, TopicEntity topic) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.topic = topic;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
