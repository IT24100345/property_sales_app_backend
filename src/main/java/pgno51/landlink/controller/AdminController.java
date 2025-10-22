package pgno51.landlink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pgno51.landlink.model.Post;
import pgno51.landlink.model.User;
import pgno51.landlink.model.VerificationStatus;
import pgno51.landlink.repo.PostRepo;
import pgno51.landlink.repo.UserRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getPendingPosts() {
        var x = postRepo.findAllPostsByVerificationStatus(VerificationStatus.PENDING);

        var ls = x.stream().map(post -> Map.of(
                "id", post.getId(),
                "title", post.getTitle(),
                "submittedBy", post.getAuthor().getUsername(),
                "submittedAt", post.getCreatedAt(),
                "priority", "High"
        )).toList();

        return ResponseEntity.ok(ls);
    }

    @PostMapping("/pending-posts/{id}/approve")
    public ResponseEntity<?> approvePost(@PathVariable int id) {

        var x = postRepo.findById(id).orElseThrow();

        x.setVerificationStatus(VerificationStatus.VERIFIED);

        postRepo.save(x);

        return ResponseEntity.ok(true);
    }

}
