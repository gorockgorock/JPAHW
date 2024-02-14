package com.sparta.travelnewsfeed.entity;

import com.sparta.travelnewsfeed.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    // 댓글과 게시물 간의 다대일 관계를 정의한다.
    @JoinColumn(name = "post_id", nullable = false)
    // 관계의 외래 키를 지정한다.
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "created_at", nullable = false, updatable = false)
    // 이 필드가 "created_at"에 맵핑되고, null이 불가능하며, 업데이트가 불가능하다.
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    //엔티티가 생성될 때 실행되는 로직을 구현할때 사용하는 어노테이션
    protected void onCreate() {
        // 엔티티를 현재 날짜와 시간을 값으로 설정한다.
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    //엔티티가 업데이트될 때 실행되는 로직을 구현할때 사용하는 어노테이션
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }


    public Comment(String text, Post post) {
        this.text = text;
        this.post = post;
    }
}