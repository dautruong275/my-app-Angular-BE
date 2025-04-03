package com.dau.angular.controller.auth;

import com.dau.angular.Util.JwtUtil;
import com.dau.angular.dto.LoginRequest;
import com.dau.angular.dto.RegisterRequest;
import com.dau.angular.entity.Category;
import com.dau.angular.entity.User;
import com.dau.angular.repository.UserRepository;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.response.user.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; //
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Xác thực email và password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Lấy email từ authentication
        String username = authentication.getName();
        String token = jwtUtil.generateToken(username);

        // Lấy thông tin user từ JPA
        User user = userRepository.findByUsername(username);
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail()));
    }
    // Endpoint Register (mới)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // Tạo user mới
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Mã hóa mật khẩu
        user.setUsername(registerRequest.getUsername());

        // Lưu vào database
        userRepository.save(user);

        // Trả về thông báo thành công
        return ResponseEntity.ok(
                BaseResponse.<Category>builder()
                        .status(HttpStatus.CREATED)
                        .message("User registered successfully")
                        .data(null)
                        .build() );
    }
}
