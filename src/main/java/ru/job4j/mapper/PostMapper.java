package ru.job4j.mapper;

import org.mapstruct.Mapper;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface PostMapper {

    default PostDto getModelFromEntityCustom(Post post) {
        PostDto postDto = new PostDto();
        if (post != null) {
            postDto.setId(post.getId() == null ? 0 : post.getId());
            postDto.setDescription(post.getDescription());
            postDto.setCreated(post.getCreated());
            postDto.setDone(post.isDone());
            postDto.setCar(post.getCar());
            postDto.setUser(post.getUser());
            postDto.setPhotos(post.getPhotos());
        } else {
            postDto = null;
        }
        return postDto;
    }

    default Post getEntityFromModelCustom(PostDto postDto) {
        Post post = new Post();
        if (postDto != null) {
            post.setId(postDto.getId() == 0 ? null : postDto.getId());
            post.setDescription(postDto.getDescription());
            post.setCreated(postDto.getCreated());
            post.setDone(postDto.isDone());
            post.setCar(postDto.getCar());
            post.setUser(postDto.getUser());
            post.setPhotos(postDto.getPhotos());
        } else {
            post = null;
        }
        return post;
    }

    private Date toDate(LocalDateTime localDateTime) {
        var instant = Timestamp.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).toInstant();
        var date = Date.from(instant);
        return date;
    }
}