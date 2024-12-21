package br.com.soupaulodev.forumhub.modules.comment.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a comment in the forum.
 * <p>
 *     This class encapsulates the details of a comment.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
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

    @Column(name = "highs_count", nullable = false)
    private Long highsCount = 0L;



    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentHighsEntity> highs = new ArrayList<>();

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentEntity> replies = new ArrayList<>();



    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor. Initializes the id, createdAt, and updatedAt fields.
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
     * @param user    the user who made the comment
     * @param topic   the topic to which the comment belongs
     */
    public CommentEntity(String content, UserEntity user, TopicEntity topic) {
        this();
        this.content = content;
        this.user = user;
        this.topic = topic;
    }

    /**
     * Constructs a new CommentEntity with the specified content, user, topic, and parent comment.
     *
     * @param content       the content of the comment
     * @param user          the user who made the comment
     * @param topic         the topic to which the comment belongs
     * @param parentComment the parent comment if this comment is a reply
     */
    public CommentEntity(String content, UserEntity user, TopicEntity topic, CommentEntity parentComment) {
        this(content, user, topic);
        this.setParentComment(parentComment);
    }

    /**
     * Gets the unique identifier of the comment.
     *
     * @return the unique identifier of the comment
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the comment.
     *
     * @param id the unique identifier of the comment
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the content of the comment.
     *
     * @return the content of the comment
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the comment.
     *
     * @param content the content of the comment
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the number of highs count.
     *
     * @return the number of highs count.
     */
    public Long getHighsCount() {
        return highsCount;
    }

    /**
     * Increments the number of highs count.
     */
    public void incrementHighs() {
        this.highsCount++;
    }

    /**
     * Decrements the number of highs count.
     */
    public void decrementHighs() {
        this.highsCount--;
    }

    /**
     * Gets the user who made the comment.
     *
     * @return the user who made the comment
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Sets the user who made the comment.
     *
     * @param user the user who made the comment
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Gets the topic to which the comment belongs.
     *
     * @return the topic to which the comment belongs
     */
    public TopicEntity getTopic() {
        return topic;
    }

    /**
     * Sets the topic to which the comment belongs.
     *
     * @param topic the topic to which the comment belongs
     */
    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    /**
     * Gets the parent comment if this comment is a reply.
     *
     * @return the parent comment if this comment is a reply
     */
    public CommentEntity getParentComment() {
        return parentComment;
    }

    /**
     * Sets the parent comment if this comment is a reply.
     *
     * @param parentComment the parent comment if this comment is a reply
     */
    public void setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
    }

    /**
     * Gets the list of highs of the comment.
     *
     * @return the list of highs of the comment
     */
    public List<CommentHighsEntity> getHighs() {
        return highs;
    }

    /**
     * Gets the list of replies to the comment.
     *
     * @return the list of replies to the comment
     */
    public List<CommentEntity> getReplies() {
        return replies;
    }

    /**
     * Adds a reply to the comment.
     *
     * @param reply the reply to be added
     */
    public void addReplies(CommentEntity reply) {
        if (reply != null && !this.replies.contains(reply)) {
            this.replies.add(reply);
            reply.parentComment = this;
        }
    }

    /**
     * Removes a reply from the comment.
     *
     * @param reply the reply to be removed
     */
    public void removeReplies(CommentEntity reply) {
        if (reply != null && this.replies.contains(reply)) {
            this.replies.remove(reply);
            reply.parentComment = null;
        }
    }

    /**
     * Gets the list of highs of the comment.
     *
     * @return the list of highs of the comment
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when the comment was created.
     *
     * @param createdAt the date and time when the comment was created
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the comment was last updated.
     *
     * @return the date and time when the comment was last updated
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the comment was last updated.
     *
     * @param updatedAt the date and time when the comment was last updated
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
