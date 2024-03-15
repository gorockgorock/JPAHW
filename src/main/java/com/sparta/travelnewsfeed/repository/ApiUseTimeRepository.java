package com.sparta.travelnewsfeed.repository;

import com.sparta.travelnewsfeed.entity.ApiUseTime;
import com.sparta.travelnewsfeed.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
  Optional<ApiUseTime> findByUser(User user);
}