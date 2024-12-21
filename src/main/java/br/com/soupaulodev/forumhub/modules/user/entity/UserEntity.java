package br.com.soupaulodev.forumhub.modules.user.entity;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentHighsEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumHighsEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicHighsEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * Represents a user within the system.
 * <p>
 * The {@link UserEntity} class encapsulates the details of a user.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_user")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "highs_count")
    private Long highsCount = 0L;


    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<ForumEntity> ownedForums = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "forum_participants", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "forum_id"))
    private final List<ForumEntity> participatingForums = new ArrayList<>();

    @OneToMany(mappedBy = "highingUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<UserHighsEntity> highsInUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<ForumHighsEntity> highsInForums = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<TopicHighsEntity> highsInTopics = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<CommentHighsEntity> highsInComments = new ArrayList<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<TopicEntity> topics = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<CommentEntity> comments = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor for initializing the user with a unique ID and current timestamps.
     * <p>
     * This constructor assigns the current time to both the `createdAt` and `updatedAt` fields
     * and generates a new UUID for the user ID.
     * </p>
     */
    public UserEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructor to create a user with the specified details.
     * <p>
     * This constructor initializes a user entity with the specified name, username, email, and password.
     * </p>
     *
     * @param name     the full name of the user
     * @param username the username of the user
     * @param email    the email address of the user
     * @param password the password of the user
     */
    public UserEntity(String name, String username, String email, String password) {
        this();
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user's unique identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the new unique identifier to set.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the full name of the user.
     *
     * @return the user's full name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the user.
     *
     * @param name the new full name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the username of the user.
     *
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the number of highs count.
     *
     * @return the number of highs count.
     */
    public Long getHighsCount() { return highsCount; }

    /**
     * Increments the number of highs count.
     */
    public void incrementHighs() { this.highsCount++; }

    /**
     * Decrements the number of highs count.
     */
    public void decrementHighs() { this.highsCount--; }

    /**
     * Gets the list of forums owned by the user.
     *
     * @return {@link List} of {@link ForumEntity} the list of forums owned by the user.
     */
    public List<ForumEntity> getOwnedForums() {
        return ownedForums;
    }

    /**
     * Adds a forum to the user's owned forums collection.
     *
     * @param forum the forum to add to the user's owned forums.
     */
    public void addOwnedForum(ForumEntity forum) {
        if (!ownedForums.contains(forum)) {
            ownedForums.add(forum);
            if (forum.getOwner() != this) {
                forum.addOwner(this);
            }
        }
    }

    /**
     * Removes a forum from the user's owned forums collection.
     *
     * @param forum the forum to remove from the user's owned forums.
     */
    public void removeOwnedForum(ForumEntity forum) {
        if (ownedForums.contains(forum)) {
            ownedForums.remove(forum);
            if (forum.getOwner() == this) {
                forum.addOwner(null);
            }
        }
    }

    /**
     * Checks if the user owns a specific forum.
     *
     * @param forum the forum to check for ownership.
     * @return true if the user owns the forum, false otherwise.
     */
    public boolean participatingInForum(ForumEntity forum) { return !participatingForums.contains(forum); }

    /**
     * Gets the list of forums in which the user is a participant.
     *
     * @return {@link List} of {@link ForumEntity} the list of forums in which the user is a participant.
     */
    public List<ForumEntity> getParticipatingForums() { return participatingForums; }

    /**
     * Adds a forum to the user's participating forums collection.
     *
     * @param forum {@link ForumEntity} the forum to add to the user's participating forums.
     */
    public void addParticipatingForum(ForumEntity forum) {
        if (!participatingForums.contains(forum)) {
            participatingForums.add(forum);
            forum.addParticipant(this);
        }
    }

    /**
     * Removes a forum from the user's participating forums collection.
     *
     * @param forum {@link ForumEntity} the forum to remove from the user's participating forums.
     */
    public void removeParticipatingForum(ForumEntity forum) {
        if (participatingForums.contains(forum)) {
            participatingForums.remove(forum);
            forum.removeParticipant(this);
        }
    }

    /**
     * Gets the list of highs made by the user in other users.
     *
     * @return the list of highs made by the user in other users.
     */
    public List<UserHighsEntity> getHighsInUsers() {
        return highsInUsers;
    }

    /**
     * Gets the list of highs made by the user in forums.
     *
     * @return the list of highs made by the user in forums.
     */
    public List<ForumHighsEntity> getHighsInForums() {
        return highsInForums;
    }

    /**
     * Gets the list of highs made by the user in topics.
     *
     * @return the list of highs made by the user in topics.
     */
    public List<TopicHighsEntity> getHighsInTopics() {
        return highsInTopics;
    }

    /**
     * Gets the list of highs made by the user in comments.
     *
     * @return the list of highs made by the user in comments.
     */
    public List<CommentHighsEntity> getHighsInComments() {
        return highsInComments;
    }

    /**
     * Gets the list of topics created by the user.
     *
     * @return {@link List} of {@link TopicEntity} the list of topics created by the user.
     */
    public List<TopicEntity> getTopics() {
        return topics;
    }

    /**
     * Adds a topic to the user's created topics collection.
     *
     * @param topic {@link TopicEntity} the topic to add to the user's created topics.
     */
    public void addTopic(TopicEntity topic) {
        if (!topics.contains(topic)) {
            topics.add(topic);
            if (topic.getCreator() != this) {
                topic.setCreator(this);
            }
        }
    }

    /**
     * Gets the list of comments made by the user.
     *
     * @return {@link List} of {@link CommentEntity} the list of comments made by the user.
     */
    public List<CommentEntity> getComments() {
        return comments;
    }

    /**
     * Gets the timestamp when the user was created.
     *
     * @return the timestamp when the user was created.
     */
    public Instant getCreatedAt() { return createdAt; }

    /**
     * Sets the timestamp when the user was created.
     *
     * @param createdAt the new timestamp to set for user creation.
     */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    /**
     * Gets the timestamp when the user was last updated.
     *
     * @return the timestamp when the user was last updated.
     */
    public Instant getUpdatedAt() { return updatedAt; }

    /**
     * Sets the timestamp when the user was last updated.
     *
     * @param updatedAt the new timestamp to set for the last update of the user.
     */
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compares this user to the specified object.
     *
     * @param obj the object to compare this user against.
     * @return {@code true} if the object is equal to this user, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        UserEntity that = (UserEntity) obj;
        return Objects.equals(this.id, that.id);
    }

    /**
     * Returns a hash code value for the UserEntity object.
     * <p>
     * This method generates a hash code based on the user's ID, ensuring that equal objects (i.e., with the same ID)
     * have the same hash code. It is used in hashing data structures such as HashMap and HashSet.
     * </p>
     *
     * @return the hash code value of the UserEntity object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
