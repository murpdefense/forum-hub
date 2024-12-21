package br.com.soupaulodev.forumhub.modules.topic.entity;

import br.com.soupaulodev.forumhub.config.UlidGenerator;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a topic high within the system.
 * <p>
 * The {@link TopicHighsEntity} class encapsulates the details of a topic high.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_topic_highs")
public class TopicHighsEntity {

    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor.
     * Initializes the id
     */
    public TopicHighsEntity() {
        Instant now = Instant.now();
        this.id = UlidGenerator.generate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new TopicHighsEntity with the specified user and topic.
     * Initializes the id, createdAt and updatedAt fields.
     *
     * @param topic      the topic that was liked
     * @param user       the user who liked the topic
     */
    public TopicHighsEntity(TopicEntity topic, UserEntity user) {
        this();
        this.topic = topic;
        this.user = user;
    }

    /**
     * Gets the unique identifier of the topic high.

     * @return the unique identifier of the topic high
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the topic high.
     *
     * @param id the unique identifier of the topic high
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user who liked the topic.
     *
     * @return the user who liked the topic
     */
    public TopicEntity getTopic() {
        return topic;
    }

    /**
     * Sets the user who liked the topic.
     *
     * @param topic the user who liked the topic
     */
    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    /**
     * Gets the user who liked the topic.
     *
     * @return the user who liked the topic
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Sets the user who liked the topic.
     *
     * @param user the user who liked the topic
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Gets the date and time when the topic was liked.
     *
     * @return the date and time when the topic was liked
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when the topic was liked.
     *
     * @param createdAt the date and time when the topic was liked
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the topic was last updated.
     *
     * @return the date and time when the topic was last updated
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the topic was last updated.
     *
     * @param updatedAt the date and time when the topic was last updated
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the string representation of the topic high.
     *
     * @return the string representation of the topic high
     */
    @Override
    public String toString() {
        return "TopicHighsEntity{" +
                "id='" + id + '\'' +
                ", topic=" + topic +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TopicHighsEntity that = (TopicHighsEntity) obj;
        return Objects.equals(this.id, that.id);
    }

    /**
     * Returns the hash code value for this topic high.
     *
     * @return the hash code value for this topic high
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
