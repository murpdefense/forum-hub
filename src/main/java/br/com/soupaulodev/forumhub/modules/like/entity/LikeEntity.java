package br.com.soupaulodev.forumhub.modules.like.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    public LikeEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }

    public LikeEntity(UserEntity user, TopicEntity topic) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.topic = topic;
        this.createdAt = Instant.now();
    }
}
