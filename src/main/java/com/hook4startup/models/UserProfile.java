package com.hook4startup.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userProfiles")
@Data
@JsonIgnoreProperties({"numberOfPosts"})
public class UserProfile {
    @Id
    private String id;
   @DBRef
   @JsonBackReference
    private User userId;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date lastModifiedDate;
    @NonNull
    private String username;
    @NonNull
    private String fullName;

    private String bio;

    private String profilePictureUrl;

 @DBRef
 @JsonManagedReference
 private List<Post> numberOfPosts = new ArrayList<>();

    private String numberOfFollowers;

    private String numberOfFollowing;

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  UserProfile that = (UserProfile) o;
  return Objects.equals(id, that.id); // Use unique identifier
 }

 @Override
 public int hashCode() {
  return Objects.hash(id); // Use unique identifier
 }


 @Override
    public String toString() {
        return "InstagramUserProfile{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", bio='" + bio + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", numberOfPosts=" + numberOfPosts +
                ", numberOfFollowers=" + numberOfFollowers +
                ", numberOfFollowing=" + numberOfFollowing +
                '}';
    }
}
