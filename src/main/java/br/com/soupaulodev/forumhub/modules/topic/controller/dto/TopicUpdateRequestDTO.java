package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicState;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an existing topic.
 */
public class TopicUpdateRequestDTO {

    @Size(max = 50)
    private String title;

    @Size(max = 500)
    private String content;

    private TopicState state;

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
}
