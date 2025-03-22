package com.hook4startup.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private String userId;
    private String postId;
    private String username;
    private String content;
    private String profileImageUrl;
    private String postImageUrl;
    private int numberOfComments;
    private int numberOfLikes;
    private int numberOfPosts;
    private boolean profileStatus;
}
