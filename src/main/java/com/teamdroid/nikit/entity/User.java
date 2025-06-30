package com.teamdroid.nikit.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String accountId;
    private String username;
    private String email;
    private String fullName;
    private boolean active;
    private String password; // Hash
    private List<String> roles;
    private Audit audit;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
