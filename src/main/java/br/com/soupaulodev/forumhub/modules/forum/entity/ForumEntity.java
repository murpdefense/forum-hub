package br.com.soupaulodev.forumhub.modules.forum.entity;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
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
@Table(name = "tb_forum")
@Getter
@Setter
public class ForumEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    private Set<TopicEntity> topics;

    @ManyToMany(mappedBy = "forums")
    private Set<UserEntity> users;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public ForumEntity() {
        this.id = UUID.randomUUID();
    }

    public ForumEntity(String name, String description, UserEntity owner) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.owner = owner;
    }
}
