package br.com.soupaulodev.forumhub.modules.comment.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_comment")
@Getter
@Setter
public class CommentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> replies = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public CommentEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public CommentEntity(String content, UserEntity user, TopicEntity topic) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.content = content;
        this.user = user;
        this.topic = topic;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public CommentEntity(String content, UserEntity user, TopicEntity topic, CommentEntity parentComment) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.content = content;
        this.user = user;
        this.topic = topic;
        this.createdAt = now;
        this.updatedAt = now;
    }
}
