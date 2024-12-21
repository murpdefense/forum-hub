package br.com.soupaulodev.forumhub.modules.topic.entity;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
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
 * Entity representing a topic in the forum.
 * <p>
 * The {@link TopicEntity} class encapsulates the details of a topic.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Entity
@Table(name = "tb_topic")
public class TopicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(name = "highs_count")
    private Long highsCount = 0L;

    @Column(name = "comments_count")
    private Long commentsCount = 0L;



    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private ForumEntity forum;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserEntity creator;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TopicHighsEntity> highs = new ArrayList<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentEntity> comments = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Default constructor.
     * Initializes the id, createdAt and updatedAt fields.
     */
    public TopicEntity() {
        Instant now = Instant.now();

        this.id = UUID.randomUUID();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Constructs a new TopicEntity with the specified title and content.
     * Initializes the id, createdAt and updatedAt fields.
     *
     * @param title   the title of the topic
     * @param content the content of the topic
     */
    public TopicEntity(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    /**
     * Constructs a new TopicEntity with the specified title, content, creator and forum.
     * Initializes the id, createdAt and updatedAt fields.
     *
     * @param title   the title of the topic
     * @param content the content of the topic
     * @param creator the creator of the topic
     * @param forum   the forum where the topic was created
     */
    public TopicEntity(String title, String content, UserEntity creator, ForumEntity forum) {
        this(title, content);
        this.forum = forum;
        this.creator = creator;
    }

    /**
     * Gets the unique identifier of the topic.
     *
     * @return the unique identifier of the topic
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the topic.
     *
     * @param id the unique identifier of the topic
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the title of the topic.
     *
     * @return the title of the topic
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the topic.
     *
     * @param title the title of the topic
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content of the topic.
     *
     * @return the content of the topic
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the topic.
     *
     * @param content the content of the topic
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
     * Gets the number of comments count.
     *
     * @return the number of comments count.
     */
    public Long getCommentsCount() {
        return commentsCount;
    }

    /**
     * Increments the number of comments count.
     */
    public void incrementComments() {
        this.commentsCount++;
    }

    /**
     * Decrements the number of comments count.
     */
    public void decrementComments() {
        this.commentsCount--;
    }

    /**
     * Gets the forum where the topic was created.
     *
     * @return the forum where the topic was created
     */
    public ForumEntity getForum() {
        return forum;
    }

    /**
     * Sets the forum where the topic was created.
     *
     * @param forum the forum where the topic was created
     */
    public void setForum(ForumEntity forum) {
        if (forum != null && !forum.equals(this.forum)) {
            this.forum = forum;
            if (!forum.getTopics().contains(this)) {
                forum.addTopic(this);
            }
        }
    }

    /**
     * Gets the creator of the topic.
     *
     * @return the creator of the topic
     */
    public UserEntity getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the topic.
     *
     * @param creator the creator of the topic
     */
    public void setCreator(UserEntity creator) {
        if (creator != null && !creator.equals(this.creator)) {
            this.creator = creator;
            if (!creator.getTopics().contains(this)) {
                creator.addTopic(this);
            }
        }
    }

    /**
     * Gets the list of highs data of the topic.
     *
     * @return the highs of the topic
     */
    public List<TopicHighsEntity> getHighs() {
        return highs;
    }

    /**
     * Gets the list of comments data of the topic.
     *
     * @return the comments of the topic
     */
    public List<CommentEntity> getComments() {
        return comments;
    }

    /**
     * Gets the creation date of the topic.
     *
     * @return the creation date of the topic
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the topic.
     *
     * @param createdAt the creation date of the topic
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date and time when the topic was last updated.
     *
     * @return the date and time when the topic was last updated
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date and time when the topic was last updated.
     *
     * @param updatedAt the date and time when the topic was last updated
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a string representation of the topic.
     *
     * @return a string representation of the topic.
     */
    @Override
    public String toString() {
        return "TopicEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", highsCount=" + highsCount +
                ", commentsCount=" + commentsCount +
                ", forum=" + forum +
                ", creator=" + creator +
                ", highs=" + highs +
                ", comments=" + comments +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Compares this topic to the specified object.
     *
     * @param obj the object to compare this topic against.
     * @return {@code true} if the object is equal to this topic, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TopicEntity that)) return false;

        return Objects.equals(this.id, that.id);
    }

    /**
     * Returns a hash code value for the TopicEntity object.
     * <p>
     * This method generates a hash code based on the topic's ID, ensuring that equal objects (i.e., with the same ID)
     * have the same hash code. It is used in hashing data structures such as HashMap and HashSet.
     * </p>
     *
     * @return the hash code value of the TopicEntity object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
