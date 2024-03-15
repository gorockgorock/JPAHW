package com.sparta.travelnewsfeed.repository;

import com.sparta.travelnewsfeed.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryQuery {

  Page<Post> searchByKeyword(String keyword, Pageable pageable);

}
