package br.com.soupaulodev.forumhub.modules.comment.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity representing a comment in the forum.
 */
@Entity
@Table(name = "tb_comment")
public class CommentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 500)
    private String content;

    /**
     * User who made the comment.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Topic to which the comment belongs.
     */
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    /**
     * Parent comment if this comment is a reply.
     */
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentComment;

    /**
     * Replies to this comment.
     */
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> replies = new HashSet<>();

    /**
     * Timestamp when the comment was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /**
     * Timestamp when the comment was last updated.
     */
    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor initializing the comment with current timestamps and a new UUID.
     */
    public CommentEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new CommentEntity with the specified content, user, and topic.
     *
     * @param content the content of the comment
     * @param user the user who made the comment
     * @param topic the topic to which the comment belongs
     */
    public CommentEntity(String content, UserEntity user, TopicEntity topic) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.content = content;
        this.user = user;
        this.topic = topic;
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new CommentEntity with the specified content, user, topic, and parent comment.
     *
     * @param content the content of the comment
     * @param user the user who made the comment
     * @param topic the topic to which the comment belongs
     * @param parentComment the parent comment if this comment is a reply
     */
    public CommentEntity(String content, UserEntity user, TopicEntity topic, CommentEntity parentComment) {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.content = content;
        this.user = user;
        this.topic = topic;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public CommentEntity getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
    }

    public Set<CommentEntity> getReplies() {
        return replies;
    }

    public void setReplies(Set<CommentEntity> replies) {
        this.replies = replies;
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
}
