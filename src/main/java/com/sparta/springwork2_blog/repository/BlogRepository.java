package com.sparta.springwork2_blog.repository;

import com.sparta.springwork2_blog.entity.Blog;
import com.sparta.springwork2_blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByOrderByCreatedAtDesc();

    Optional<Blog> findByIdAndUser(Long id, User user);
}
