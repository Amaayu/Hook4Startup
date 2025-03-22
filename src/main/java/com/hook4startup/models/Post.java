package com.hook4startup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Document(collection = "posts")
@Data
public class Post {

    @Id
    private String postId;

    @DBRef
    @JsonIgnoreProperties({"posts"}) // ✅ Prevent Circular Reference
    private User userId;

    private String username;
    private String profileImageUrl;
    private String postImageUrl;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private String content;

    @DBRef
    @JsonIgnoreProperties({"posts"}) // ✅ Avoid Circular Reference in Likes
    private List<User> likes = new ArrayList<>();

    @DBRef
    @JsonIgnoreProperties({"postId"}) // ✅ Prevent Circular Reference in Comments
    private List<Comment> comments = new ArrayList<>();

    private String imageUrl;

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", likes=" + likes.size() +
                ", comments=" + comments.size() +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
