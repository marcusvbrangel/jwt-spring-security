package com.mvbr.jwtspringsecurity.controller;

import com.mvbr.jwtspringsecurity.dto.AuthenticateUserRequest;
import com.mvbr.jwtspringsecurity.dto.AuthenticateUserResponse;
import com.mvbr.jwtspringsecurity.dto.CreateUserRequest;
import com.mvbr.jwtspringsecurity.dto.CreateUserResponse;
import com.mvbr.jwtspringsecurity.dto.ResetPasswordRequest;
import com.mvbr.jwtspringsecurity.service.AuthService;
import com.mvbr.jwtspringsecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(
            @Valid @RequestBody AuthenticateUserRequest authenticateUserRequest) {
        AuthenticateUserResponse token = authService.authenticateUser(authenticateUserRequest);
        // todo: verificar se usuario esta enabled
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        return new ResponseEntity<>(createUserResponse, HttpStatus.CREATED);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        userService.confirmUser(token);
        return ResponseEntity.ok("Usuário confirmado com sucesso!");
    }

    @GetMapping("/unlock")
    public ResponseEntity<String> unlockUser(@RequestParam("email") String email) {
        authService.unlockUser(email);
        return ResponseEntity.ok("Usuário desbloqueado com sucesso!");
    }

    @PostMapping("/redefinir")
    public ResponseEntity<String> redefinirSenha(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getToken(), request.getNovaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestParam("email") String email) {
        authService.startPasswordReset(email);
        return ResponseEntity.ok("Se o e-mail existir, as instruções de redefinição foram enviadas.");
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

}
