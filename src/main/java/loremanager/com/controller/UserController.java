package loremanager.com.controller;

import loremanager.com.domain.UserLore;
import loremanager.com.service.UserLoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserLoreService userLoreService;

    @PostMapping
    public ResponseEntity<UserLore> addUser(@RequestBody UserLore userLore) {

        return ResponseEntity.ok().body(userLoreService.saveUser(userLore));

    }

    @GetMapping
    public ResponseEntity<List<UserLore>> findAll(){

        return ResponseEntity.ok().body(userLoreService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLore> findById(@PathVariable Integer id) {

        return ResponseEntity.ok().body(userLoreService.findById(id));

    }

    @PostMapping("/save")
    public ResponseEntity<UserLore> saveUser(@RequestBody UserLore userLore) {

        return ResponseEntity.ok().body(userLoreService.saveUser(userLore));

    }

}
