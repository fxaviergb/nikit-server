package com.teamdroid.wise.repository;

import com.teamdroid.wise.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final Map<String, String> userDatabase = new HashMap<>();

    public UserRepository() {
        // Usuarios precargados en la "base de datos"
        userDatabase.put("admin", "password");
        userDatabase.put("user1", "pass123");
        userDatabase.put("user2", "mypassword");
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userDatabase.containsKey(username)
                ? new User(username, userDatabase.get(username))
                : null);
    }
}
