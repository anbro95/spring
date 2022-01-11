package com.example.Springpractice.repo;

import com.example.Springpractice.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
