package com.bloggingApp.bloggingApp.services;

import com.bloggingApp.bloggingApp.entities.Category;
import com.bloggingApp.bloggingApp.entities.Post;
import com.bloggingApp.bloggingApp.entities.User;
import com.bloggingApp.bloggingApp.exceptions.ResourceNotFoundException;
import com.bloggingApp.bloggingApp.payloads.PostDto;
import com.bloggingApp.bloggingApp.repositories.CategoryRepository;
import com.bloggingApp.bloggingApp.repositories.PostRepository;
import com.bloggingApp.bloggingApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer usrId, Integer categoryId) {

        User user = this.userRepository.findById(usrId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", usrId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepository.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<PostDto> getAllPost() {
        return null;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        return null;
    }

    @Override
    public List<PostDto> searchPost(Integer userId) {
        return null;
    }
}
