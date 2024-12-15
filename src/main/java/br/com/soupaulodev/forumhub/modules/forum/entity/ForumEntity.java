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
import java.util.*;

/**
 * Represents a forum within the system.
 * <p>
 *     The {@link ForumEntity} class encapsulates the details of a forum, including their
 *     information (name, description) and relationships (owner, participants, topics).
 *     It also tracks the timestamps of forum creation and last update.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_forum")
@Transactional
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
    @ManyToMany(mappedBy = "participatingForums", fetch = FetchType.EAGER)
    private List<UserEntity> participants = new ArrayList<>();

    /**
     * The topics in the forum.
     */
    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TopicEntity> topics = new ArrayList<>();

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
        if (owner != null && !owner.equals(this.owner)) {
            this.owner = owner;
            if (!owner.getOwnedForums().contains(this)) {
                owner.addOwnedForum(this);
            }
        }
    }

    public List<UserEntity> getParticipants() {
        return participants;
    }

    public void addParticipant(UserEntity user) {
        if (!participants.contains(user)) {
            participants.add(user);
            user.addParticipatingForum(this);
        }
    }

    public List<TopicEntity> getTopics() {
        return topics;
    }

    public void addTopic(TopicEntity topic) {
        if (topic != null && !topics.contains(topic)) {
            topics.add(topic);
            if (!this.equals(topic.getForum())) {
                topic.setForum(this);
            }
        }
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ForumEntity that = (ForumEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
