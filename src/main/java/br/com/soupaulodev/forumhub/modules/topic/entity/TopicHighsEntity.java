package br.com.soupaulodev.forumhub.modules.topic.entity;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_topic_highs")
public class TopicHighsEntity {

    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private Long createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private Long updatedAt;

}
