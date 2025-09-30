package pgno51.landlink.controller;

import org.springframework.web.bind.annotation.*;
import pgno51.landlink.model.User;
import pgno51.landlink.repo.UserRepo;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final private UserRepo userRepo;

    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public User findByUsername(@RequestParam int id) {
        return userRepo.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

}
