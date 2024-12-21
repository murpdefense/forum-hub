package br.com.soupaulodev.forumhub.modules.user.entity;

import br.com.soupaulodev.forumhub.config.UlidGenerator;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a "high" interaction between users within the system.
 * <p>
 *  * The {@link UserHighsEntity} class stores information about which user high another user.
 *  * </p>
 *  * <p>
 *  * The "highingUser" is the user who performed the "high," and the "highedUser" is the recipient.
 *  * </p>
 *  * <p>
 *  * This entity is tracked with creation and update timestamps for auditing purposes.
 *  * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_user_highs")
public class UserHighsEntity {

    @Id
    private String id;


    @ManyToOne
    @JoinColumn(name = "highed_user_id", nullable = false)
    private UserEntity highedUser;

    @ManyToOne
    @JoinColumn(name = "highing_user_id", nullable = false)
    private UserEntity highingUser;


    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor.
     * Initializes the id, createdAt and updatedAt fields.
     */
    public UserHighsEntity() {
        Instant now = Instant.now();
        this.id = UlidGenerator.generate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new UserHighsEntity with the specified highedUser and highingUser.
     * Initializes the id, createdAt and updatedAt fields.
     *
     * @param highedUser
     * @param highingUser
     */
    public UserHighsEntity(UserEntity highedUser, UserEntity highingUser) {
        this();
        this.highedUser = highedUser;
        this.highingUser = highingUser;
    }

    /**
     * Gets the unique identifier of the userHighed high.

     * @return the unique identifier of the userHighed high
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the userHighed high.
     *
     * @param id the unique identifier of the userHighed high
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user who highed the userHighed.
     *
     * @return the user who highed the userHighed
     */
    public UserEntity getHighedUser() {
        return highedUser;
    }

    /**
     * Sets the user who highed the userHighed.
     *
     * @param highedUser the user who highed the userHighed
     */
    public void setHighedUser(UserEntity highedUser) {
        this.highedUser = highedUser;
    }

    /**
     * Gets the user who liked the user.
     *
     * @return the user who liked the user
     */
    public UserEntity getHighingUser() {
        return highingUser;
    }

    /**
     * Sets the user who liked the user.
     *
     * @param highingUser the user who liked the user
     */
    public void setHighingUser(UserEntity highingUser) {
        this.highingUser = highingUser;
    }

    /**
     * Gets the date and time when the user was liked.
     *
     * @return the date and time when the user was liked
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when the user was liked.
     *
     * @param createdAt the date and time when the user was liked
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the user was last updated.
     *
     * @return the date and time when the user was last updated
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the user was last updated.
     *
     * @param updatedAt the date and time when the user was last updated
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns the string representation of the user high.
     *
     * @return the string representation of the user high
     */
    @Override
    public String toString() {
        return "UserHighsEntity{" +
                "id='" + id + '\'' +
                ", highedUser=" + highedUser +
                ", highingUser=" + highingUser +
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

        UserHighsEntity that = (UserHighsEntity) obj;
        return Objects.equals(this.id, that.id);
    }

    /**
     * Returns the hash code value for this user high.
     *
     * @return the hash code value for this user high
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
