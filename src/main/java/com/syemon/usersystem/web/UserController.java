package com.syemon.usersystem.web;

import com.syemon.usersystem.service.UserApplicationService;
import com.syemon.usersystem.service.UserQuery;
import com.syemon.usersystem.service.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.syemon.usersystem.web.UserController.BASE_PATH;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String BASE_PATH = "/users";
    private final UserApplicationService userApplicationService;

    @GetMapping("/{login}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String login) {
        log.info("REQUEST: {}/{}}", BASE_PATH, login);

        UserResponse response = userApplicationService.getUser(new UserQuery(login));
        log.info("RESPONSE: {}", response);
        return ResponseEntity.ok(response);
    }
}
