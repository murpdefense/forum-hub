package br.com.soupaulodev.forumhub.modules.like.entity;

import br.com.soupaulodev.forumhub.config.UlidGenerator;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a like on a resource.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "resource_type", "resource_id"})
})
public class LikeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @Column(name = "resource_id", nullable = false)
    private UUID resourceId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

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
        this.id = UlidGenerator.generate();
        this.createdAt = Instant.now();
    }

    /**
     * Constructs a new LikeEntity with the specified user and topic.
     * Initializes the id and createdAt fields.
     *
     * @param resourceId the unique identifier of the resource that was liked
     * @param user the user who liked the topic
     */
    public LikeEntity(ResourceType resourceType, UUID resourceId, UserEntity user) {
        this();
        this.resourceId = resourceId;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(ULID id) { this.id = id.toString(); }

    public ResourceType getResourceType() { return resourceType; }

    public void setResourceType(ResourceType resourceType) { this.resourceType = resourceType; }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
