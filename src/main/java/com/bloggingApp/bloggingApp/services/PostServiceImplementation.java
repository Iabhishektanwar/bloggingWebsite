package com.bloggingApp.bloggingApp.services;

import com.bloggingApp.bloggingApp.entities.Category;
import com.bloggingApp.bloggingApp.entities.Post;
import com.bloggingApp.bloggingApp.entities.User;
import com.bloggingApp.bloggingApp.exceptions.ResourceNotFoundException;
import com.bloggingApp.bloggingApp.payloads.PostDto;
import com.bloggingApp.bloggingApp.payloads.PostResponse;
import com.bloggingApp.bloggingApp.repositories.CategoryRepository;
import com.bloggingApp.bloggingApp.repositories.PostRepository;
import com.bloggingApp.bloggingApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new
                ResourceNotFoundException("Post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new
                ResourceNotFoundException("Post", "post id", postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
        Sort sort = orderBy.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = (Pageable) PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> posts = this.postRepository.findAll(pageable);
        List<Post> allPosts = posts.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setLastPage(posts.isLast());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new
                ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post, PostDto.class);

    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new
                ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);
        return posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new
                ResourceNotFoundException("User", "userId", userId));
        List<Post> posts = this.postRepository.findByUser(user);
        return posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(Integer userId) {
        return null;
    }
}
