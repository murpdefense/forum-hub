package br.com.soupaulodev.forumhub.modules.comment.entity;

import br.com.soupaulodev.forumhub.config.UlidGenerator;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a comment high within the system.
 * <p>
 * The {@link CommentHighsEntity} class encapsulates the details of a comment high.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_comment_highs")
public class CommentHighsEntity {

    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity comment;

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
    public CommentHighsEntity() {
        Instant now = Instant.now();
        this.id = UlidGenerator.generate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new CommentHighsEntity with the specified user and comment.
     * Initializes the id, createdAt and updatedAt fields.
     *
     * @param comment      the comment that was liked
     * @param user       the user who liked the comment
     */
    public CommentHighsEntity(CommentEntity comment, UserEntity user) {
        this();
        this.comment = comment;
        this.user = user;
    }

    /**
     * Gets the unique identifier of the comment high.

     * @return the unique identifier of the comment high
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the comment high.
     *
     * @param id the unique identifier of the comment high
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user who liked the comment.
     *
     * @return the user who liked the comment
     */
    public CommentEntity getComment() {
        return comment;
    }

    /**
     * Sets the user who liked the comment.
     *
     * @param comment the user who liked the comment
     */
    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    /**
     * Gets the user who liked the comment.
     *
     * @return the user who liked the comment
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Sets the user who liked the comment.
     *
     * @param user the user who liked the comment
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Gets the date and time when the comment was liked.
     *
     * @return the date and time when the comment was liked
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when the comment was liked.
     *
     * @param createdAt the date and time when the comment was liked
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the comment was last updated.
     *
     * @return the date and time when the comment was last updated
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the comment was last updated.
     *
     * @param updatedAt the date and time when the comment was last updated
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the string representation of the comment high.
     *
     * @return the string representation of the comment high
     */
    @Override
    public String toString() {
        return "CommentHighsEntity{" +
                "id='" + id + '\'' +
                ", comment=" + comment +
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

        CommentHighsEntity that = (CommentHighsEntity) obj;
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
