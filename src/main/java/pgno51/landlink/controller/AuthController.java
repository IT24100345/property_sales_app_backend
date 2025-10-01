package pgno51.landlink.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pgno51.landlink.model.Role;
import pgno51.landlink.model.User;
import pgno51.landlink.repo.UserRepo;
import pgno51.landlink.util.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final private UserRepo userRepo;
    final private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public User findByUsername(@RequestParam int id) {
        return userRepo.findById(id).orElse(null);
    }


    @PostMapping("/create")
    public User createUser(@RequestBody User user) {

        IO.println(user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);

        return userRepo.save(user);
    }

    @PostMapping("/update")
    public boolean updateUser(@RequestBody User user) {
        IO.println(user.toString());
        userRepo.updateUserProfileById(user.getId(), user.getUsername(), user.getEmail());
        return true;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        );

        if (!auth.isAuthenticated()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = jwtUtil.generate(loginRequest.username, auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        var user = ((User) auth.getPrincipal());

        return ResponseEntity.ok(Map.of(
                "jwt", jwt,
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "roles", user.getRoles()
        ));
    }

    private record LoginRequest(String username,String password) {}

}
