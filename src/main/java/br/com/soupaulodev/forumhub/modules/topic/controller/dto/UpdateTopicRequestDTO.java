package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UpdateTopicRequestDTO {

    @Length(min = 3, max = 50)
    private String title;

    @Length(min = 3, max = 500)
    private String message;
}
