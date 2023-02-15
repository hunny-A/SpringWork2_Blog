package com.sparta.springwork2_blog.repository;

import com.sparta.springwork2_blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByOrderByCreatedAtDesc();
}
