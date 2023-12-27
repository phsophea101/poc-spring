package com.sample.spring;

import com.sample.spring.dto.UserDto;
import com.sample.spring.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @GetMapping("id/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return this.service.findById(id);
    }


    @GetMapping
    public List<UserDto> getUsers() {
        return this.service.findByUsers();
    }

    @PostMapping
    public String save(@RequestBody UserDto dto) {
        this.service.save(dto);
        return "record saved";
    }

    @PutMapping("{id}/{username}")
    public String update(@PathVariable String id, @PathVariable String username) {
        this.service.updateUsername(id, username);
        return "record updated";
    }

}
