package br.com.soupaulodev.forumhub.modules.forum.entity;

import br.com.soupaulodev.forumhub.config.UlidGenerator;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a forum high within the system.
 * <p>
 * The {@link ForumHighsEntity} class encapsulates the details of a forum high.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_forum_highs")
public class ForumHighsEntity {

    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private ForumEntity forum;

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
    public ForumHighsEntity() {
        Instant now = Instant.now();
        this.id = UlidGenerator.generate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new ForumHighsEntity with the specified user and forum.
     * Initializes the id, createdAt and updatedAt fields.
     *
     * @param forum      the forum that was liked
     * @param user       the user who liked the forum
     */
    public ForumHighsEntity(ForumEntity forum, UserEntity user) {
        this();
        this.forum = forum;
        this.user = user;
    }

    /**
     * Gets the unique identifier of the forum high.

     * @return the unique identifier of the forum high
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the forum high.
     *
     * @param id the unique identifier of the forum high
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user who liked the forum.
     *
     * @return the user who liked the forum
     */
    public ForumEntity getForum() {
        return forum;
    }

    /**
     * Sets the user who liked the forum.
     *
     * @param forum the user who liked the forum
     */
    public void setForum(ForumEntity forum) {
        this.forum = forum;
    }

    /**
     * Gets the user who liked the forum.
     *
     * @return the user who liked the forum
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Sets the user who liked the forum.
     *
     * @param user the user who liked the forum
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Gets the date and time when the forum was liked.
     *
     * @return the date and time when the forum was liked
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when the forum was liked.
     *
     * @param createdAt the date and time when the forum was liked
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the forum was last updated.
     *
     * @return the date and time when the forum was last updated
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the forum was last updated.
     *
     * @param updatedAt the date and time when the forum was last updated
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the string representation of the forum high.
     *
     * @return the string representation of the forum high
     */
    @Override
    public String toString() {
        return "ForumHighsEntity{" +
                "id='" + id + '\'' +
                ", forum=" + forum +
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

        ForumHighsEntity that = (ForumHighsEntity) obj;
        return Objects.equals(this.id, that.id);
    }

    /**
     * Returns the hash code value for this forum high.
     *
     * @return the hash code value for this forum high
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
