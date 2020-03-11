package com.rpl.hotel.controllers;

import com.rpl.hotel.models.User;
import com.rpl.hotel.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public User postPerson(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public List<User> postUsers(@RequestBody List<User> users) {
        return userRepository.saveAll(users);
    }

    @GetMapping("users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userRepository.findOne(id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("users/{ids}")
    public List<User> getUsers(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return userRepository.findAll(listIds);
    }

    @GetMapping("users/count")
    public Long getCount() {
        return userRepository.count();
    }

    @DeleteMapping("user/{id}")
    public Long deletePerson(@PathVariable String id) {
        return userRepository.delete(id);
    }

    @DeleteMapping("users/{ids}")
    public Long deletePersons(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return userRepository.delete(listIds);
    }

    @DeleteMapping("users")
    public Long deleteUsers() {
        return userRepository.deleteAll();
    }

    @PutMapping("user")
    public User putUser(@RequestBody User user) {
        return userRepository.update(user);
    }

    @PutMapping("users")
    public Long putUser(@RequestBody List<User> users) {
        return userRepository.update(users);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
