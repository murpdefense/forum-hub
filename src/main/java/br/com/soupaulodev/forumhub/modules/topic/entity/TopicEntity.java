package br.com.soupaulodev.forumhub.modules.topic.entity;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_topic")
@Getter
@Setter
public class TopicEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicState state;

    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private ForumEntity forum;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserEntity creator;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikeEntity> likes = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public TopicEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public TopicEntity(String title, String content, TopicState state) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.state = state;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public TopicEntity(String title, String content, UserEntity creator, ForumEntity forum) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.state = TopicState.OPEN;
        this.forum = forum;
        this.creator = creator;
        this.createdAt = now;
        this.updatedAt = now;
    }
}
