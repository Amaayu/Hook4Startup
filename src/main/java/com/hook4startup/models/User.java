package com.hook4startup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"posts", "sessionTokenId"}) // ✅ Avoid Circular Reference
@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String username;

    @NonNull
    private String password; // ✅ Plain Text Password (As Requested)

    @NonNull
    private String email;

    @DBRef
    @JsonIgnoreProperties({"userId"})
    private UserProfile userProfileId;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @DBRef
    @JsonIgnoreProperties({"userId"})
    private List<Post> posts = new ArrayList<>();

    @DBRef
    @JsonIgnoreProperties({"userId"})
    private SessionToken sessionTokenId;

    private String roles = "User_Role"; // ✅ Default Role

    private boolean makeProfileStatus;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfileId = userProfile;
    }
}
