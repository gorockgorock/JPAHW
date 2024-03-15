package com.sparta.travelnewsfeed.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.travelnewsfeed.entity.Post;
import com.sparta.travelnewsfeed.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Post> searchByKeyword(String keyword, Pageable pageable) {
    QPost post = QPost.post;
    QueryResults<Post> results = jpaQueryFactory
        .selectFrom(post)
        .where(post.title.contains(keyword)
            .or(post.content.contains(keyword)))
        .offset(pageable.getOffset()) // 페이지 시작
        .limit(pageable.getPageSize()) // 한 페이지의 크기
        .orderBy(post.postId.desc()) // 정렬 조건 (postId 내림차순)
        .fetchResults(); //

    long total = results.getTotal();
    List<Post> posts = results.getResults();
    return new PageImpl<>(posts, pageable, total);


  }
}