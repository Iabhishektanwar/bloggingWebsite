package com.bloggingApp.bloggingApp.repositories;

import com.bloggingApp.bloggingApp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
