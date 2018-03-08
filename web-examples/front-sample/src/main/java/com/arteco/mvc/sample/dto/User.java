package com.arteco.mvc.sample.dto;

import com.arteco.mvc.sample.domain.MyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;

    public User(MyUser u) {
        this.username = u.getUsername();
    }
}
