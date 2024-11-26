package br.com.soupaulodev.forumhub.modules.like.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "likes")
@Data
@AllArgsConstructor
public class LikeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(name = "resource_id", nullable = false)
    private UUID resourceId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public LikeEntity() {
        this.id = UUID.randomUUID();
    }

    public LikeEntity(UUID resourceId, UUID userId) {
        this.id = UUID.randomUUID();
        this.resourceId = resourceId;
        this.userId = userId;
    }
}
