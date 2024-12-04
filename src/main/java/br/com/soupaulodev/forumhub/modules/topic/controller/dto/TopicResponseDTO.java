package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicState;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for representing a topic response.
 */
public class TopicResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private TopicState state;
    private ForumResponseDTO forum;
    private UserResponseDTO creator;
    private int commentCount;
    private int likeCount;
    private Instant createdAt;
    private Instant updatedAt;

    public TopicResponseDTO(UUID id, String title, String content, TopicState state, ForumResponseDTO forum, UserResponseDTO creator, int commentCount, int likeCount, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.forum = forum;
        this.creator = creator;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TopicState getState() {
        return state;
    }

    public void setState(TopicState state) {
        this.state = state;
    }

    public ForumResponseDTO getForum() {
        return forum;
    }

    public void setForum(ForumResponseDTO forum) {
        this.forum = forum;
    }

    public UserResponseDTO getCreator() {
        return creator;
    }

    public void setCreator(UserResponseDTO creator) {
        this.creator = creator;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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
