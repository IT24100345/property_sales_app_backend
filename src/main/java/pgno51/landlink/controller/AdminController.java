package pgno51.landlink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pgno51.landlink.model.Post;
import pgno51.landlink.model.User;
import pgno51.landlink.model.VerificationStatus;
import pgno51.landlink.repo.PostRepo;
import pgno51.landlink.repo.UserRepo;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepo userRepo;
    private final PostRepo postRepo;

    public AdminController(UserRepo userRepo, PostRepo postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @GetMapping
    public ResponseEntity<List<User>> admin() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @GetMapping("/pending-posts")
    public ResponseEntity<List<Post>> getPendingPosts() {
        var x = postRepo.findAllPostsByVerificationStatus(VerificationStatus.PENDING);
        x.forEach(IO::println);

        return ResponseEntity.ok(x);
    }

}
