package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicState;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicUpdateRequestDTO {

    @Size(max = 50)
    private String title;

    @Size(max = 500)
    private String content;

    private TopicState state;
}
