package br.com.soupaulodev.forumhub.modules.forum.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
 * Represents a forum within the system.
 * <p>
 * This class encapsulates the details of a forum.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_forum")
@Transactional
public class ForumEntity implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier of the forum.
     */
    @Id
    private UUID id;

    /**
     * The name of the forum.
     */
    @Column(nullable = false, length = 50, unique = true)
    private String name;

    /**
     * The description of the forum.
     */
    @Column(nullable = false, length = 50)
    private String description;

    /**
     * The number of highs the forum has received.
     */
    @Column(name = "highs_count", nullable = false)
    private Long highsCount = 0L;

    /**
     * The number of topics in the forum.
     */
    @Column(name = "topics_count", nullable = false)
    private Long topicsCount = 0L;

    /**
     * The owner of the forum.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    /**
     * The participants of the forum.
     */
    @ManyToMany(mappedBy = "participatingForums", fetch = FetchType.EAGER)
    private final List<UserEntity> participants = new ArrayList<>();

    /**
     * The topics in the forum.
     */
    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<TopicEntity> topics = new ArrayList<>();

    /**
     * The timestamp when the forum was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * The timestamp when the forum was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor for initializing the forum with a new UUID and the current timestamp.
     * <p>
     * This constructor assigns the current time to both the `createdAt` and `updatedAt` fields
     * and generates a new UUID for the user ID.
     * </p>
     */
    public ForumEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Contructor to create a forum with specified details.
     * <p>
     * Constructor to create a new forum with the specified name, description, and owner.
     * </p>
     *
     * @param name        the name of the forum
     * @param description the description of the forum
     * @param owner       the user owner of the forum
     */
    public ForumEntity(String name, String description, UserEntity owner) {
        this();
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    /**
     * Gets the unique identifier of the forum.
     *
     * @return the forum's unique identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the forum.
     *
     * @param id the unique identifier of the forum.
     */
    public void setId(UUID id) { this.id = id; }

    /**
     * Gets the name of the forum.
     *
     * @return the name of the forum.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the forum.
     *
     * @param name the name of the forum.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the forum.
     *
     * @return the description of the forum.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the forum.
     *
     * @param description the description of the forum.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the number of highs the forum has received.
     *
     * @return the number of highs the forum has received.
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
     * Gets the number of topics in the forum.
     *
     * @return the number of topics in the forum.
     */
    public Long getTopicsCount() {  return topicsCount; }

    /**
     * Increments the number of topics count.
     */
    public void incrementTopicsCount() { this.topicsCount++; }

    /**
     * Decrements the number of topics count.
     */
    public void decrementTopicsCount() { this.topicsCount--; }

    /**
     * Gets the owner of the forum.
     *
     * @return the owner of the forum.
     */
    public UserEntity getOwner() {
        return owner;
    }

    /**
     * Adds an owner to the forum.
     *
     * @param owner the owner of the forum.
     */
    public void addOwner(UserEntity owner) {
        if (owner != null && !owner.equals(this.owner)) {
            this.owner = owner;
            if (!owner.getOwnedForums().contains(this)) {
                owner.addOwnedForum(this);
            }
        }
    }

    /**
     * Removes the owner of the forum.
     */
    public void removeOwner() {
        if (owner != null) {
            UserEntity oldOwner = owner;
            owner = null;
            oldOwner.removeOwnedForum(this);
        }
    }

    /**
     * Gets the participants of the forum.
     *
     * @return the participants of the forum.
     */
    public List<UserEntity> getParticipants() {
        return participants;
    }

    /**
     * Adds a participant to the forum.
     *
     * @param user the user to add as a participant.
     */
    public void addParticipant(UserEntity user) {
        if (!participants.contains(user)) {
            participants.add(user);
            user.addParticipatingForum(this);
        }
    }

    /**
     * Removes a participant from the forum.
     *
     * @param user the user to remove as a participant.
     */
    public void removeParticipant(UserEntity user) {
        if (participants.contains(user)) {
            participants.remove(user);
            user.removeParticipatingForum(this);
        }
    }

    /**
     * Gets the topics in the forum.
     *
     * @return the topics in the forum.
     */
    public List<TopicEntity> getTopics() {
        return topics;
    }

    /**
     * Adds a topic to the forum.
     *
     * @param topic the topic to add to the forum.
     */
    public void addTopic(TopicEntity topic) {
        if (topic != null && !topics.contains(topic)) {
            topics.add(topic);
            if (!this.equals(topic.getForum())) {
                topic.setForum(this);
            }
        }
    }

    /**
     * Removes a topic from the forum.
     *
     * @param topic the topic to remove from the forum.
     */
    public void removeTopic(TopicEntity topic) {
        if (topic != null && topics.contains(topic)) {
            topics.remove(topic);
            if (this.equals(topic.getForum())) {
                topic.setForum(null);
            }
        }
    }

    /**
     * Gets the timestamp when the forum was created.
     *
     * @return the timestamp when the forum was created.
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the forum was created.
     *
     * @param createdAt the timestamp when the forum was created.
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp when the forum was last updated.
     *
     * @return the timestamp when the forum was last updated.
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the forum was last updated.
     *
     * @param updatedAt the timestamp when the forum was last updated.
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a string representation of the forum.
     *
     * @return a string representation of the forum.
     */
    @Override
    public String toString() {
        return "ForumEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", highsCount=" + highsCount +
                ", topicsCount=" + topicsCount +
                ", owner=" + owner +
                ", participants=" + participants +
                ", topics=" + topics +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compares this forum to the specified object.
     *
     * @param obj the object to compare this forum against.
     * @return {@code true} if the object is equal to this forum, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        ForumEntity that = (ForumEntity) obj;
        return Objects.equals(id, that.id);
    }

    /**
     * Returns the hash code value for this forum.
     * <p>
     *     This method generates a hash code based on the forum's ID, ensuring that equal objects (i.e., with the same ID)
     *     have the same hash code. It is used in hashing data structure such as a HashMap and HashSet.
     * </p>
     *
     * @return the hash code value of the ForumEntity object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
