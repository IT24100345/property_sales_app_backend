package pgno51.landlink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @GetMapping
    public ResponseEntity<String> getPosts(Authentication authentication) {
        IO.println(authentication.getPrincipal());
        return ResponseEntity.ok("Posts " + authentication.getName());
    }
}
