package com.sparta.travelnewsfeed.repository;

import com.sparta.travelnewsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>,PostRepositoryQuery {
    List<Post> findAllByOrderByCreatedAtDesc();
}


