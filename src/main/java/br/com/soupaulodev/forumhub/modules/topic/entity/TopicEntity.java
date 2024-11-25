package br.com.soupaulodev.forumhub.modules.topic.entity;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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
    private String message;

    @Column(nullable = false, length = 20)
    private String state;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @Column(nullable = false, length = 50)
    private String course;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private ForumEntity forum;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public TopicEntity() {
        this.id = UUID.randomUUID();
    }

    public TopicEntity(String title, String message, UserEntity creator, ForumEntity forum) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.message = message;
        this.state = state;
        this.course = course;
        this.creator = creator;
        this.forum = forum;
    }
}
