package pgno51.landlink.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pgno51.landlink.dto.CreatePostRequest;
import pgno51.landlink.model.Post;
import pgno51.landlink.model.PremiumPost;
import pgno51.landlink.model.User;
import pgno51.landlink.model.VerificationStatus;
import pgno51.landlink.repo.PostRepo;
import pgno51.landlink.repo.PremiumPostRepo;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepo postRepo;
    private final PremiumPostRepo premiumPostRepo;

    public PostController(PostRepo postRepo, PremiumPostRepo premiumPostRepo) {
        this.postRepo = postRepo;
        this.premiumPostRepo = premiumPostRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postRepo.findAll());
    }

    @GetMapping
    public ResponseEntity<?> getPosts(Authentication authentication) {
        var x = (User) authentication.getPrincipal();
        return ResponseEntity.ok(postRepo.findAllByAuthor_Id(x.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest req, Authentication auth) {
        // resolve author: prefer provided authorId, otherwise use authenticated user
        User author = (User) auth.getPrincipal();

        Post post = new Post();
        post.setLandArea(req.getLandArea() != null ? req.getLandArea() : 0);
        post.setViews(req.getViews() != null ? req.getViews() : 0);
        post.setInquiries(req.getInquiries() != null ? req.getInquiries() : 0);
        post.setPrice(req.getPrice() != null ? req.getPrice() : 0f);
        post.setTitle(req.getTitle());
        post.setDescription(req.getDescription());
        post.setLocation(req.getLocation());
        post.setContactPhone(req.getContactPhone());
        post.setImages(req.getImages());
        post.setFeatures(req.getFeatures());
        post.setType(req.getType());
        post.setVerificationStatus(VerificationStatus.PENDING);
        // optional: map verificationStatus if you need to
        post.setAuthor(author);

        Post saved = postRepo.save(post);
        return ResponseEntity.ok(saved);
    }


    @PostMapping("/verify")
    public ResponseEntity<Post> updateVerificationStatus(@RequestBody Post post, boolean verified) {
        post.setVerificationStatus(verified ? VerificationStatus.VERIFIED : VerificationStatus.REJECTED);
        return ResponseEntity.ok(postRepo.save(post));
    }

    @PostMapping("/{id}/make-premium")
    public ResponseEntity<PremiumPost> markAsPremium(@PathVariable int id) {
        var premPost = new PremiumPost();
        premPost.setPost(postRepo.findById(id).orElse(null));
        var x = premiumPostRepo.save(premPost);
        return ResponseEntity.ok(x);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postRepo.findById(id));
    }


}
