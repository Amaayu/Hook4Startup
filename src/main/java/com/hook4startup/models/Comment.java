package com.hook4startup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Document(collection = "comments")
@Data
public class Comment {

    @Id
    private String commentId;

    @DBRef
    @JsonIgnoreProperties({"posts", "comments", "likes"})
    private User userId;

    @DBRef
    @JsonIgnoreProperties({"comments", "likes"})
    private Post postId;

    private String content;

    @CreatedDate
    private Date creationDate;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId='" + commentId + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
