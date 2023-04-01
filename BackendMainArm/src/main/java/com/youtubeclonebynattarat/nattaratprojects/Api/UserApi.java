package com.youtubeclonebynattarat.nattaratprojects.Api;

import com.youtubeclonebynattarat.nattaratprojects.DTO.EmailactivateDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.LoginRequestDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.LoginResponseDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.RegistrationRequestDto;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Service.UserService;
import com.youtubeclonebynattarat.nattaratprojects.Token.GetDataToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>  Login(@RequestBody LoginRequestDto request) throws BaseException {
        LoginResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String>  Registration(@RequestBody RegistrationRequestDto request) throws BaseException {
        String response = userService.registration(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate/{token}")
    public ResponseEntity<String>  ActivatedEmail(@PathVariable(value = "token") String token_request) throws BaseException {
        String response = userService.ActivatedEmail(token_request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/resend-activate-email")
    public ResponseEntity<Void> resendEmail(@RequestBody EmailactivateDto request)throws BaseException{
        userService.resendEmail(request);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/subscribe/{userId}")
    public ResponseEntity<Boolean>  subscribeUser(@PathVariable String userId) throws BaseException {
        userService.subscribeUser(userId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/Unsubscribe/{userId}")
    public ResponseEntity<Boolean>  UnsubscribeUser(@PathVariable String userId) throws BaseException {
        userService.UnsubscribeUser(userId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<Set<String>> userHistory(@PathVariable String userId) throws BaseException {
        Set<String> response = userService.userHistory(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getuser")
    public ResponseEntity<String> userHistory() {
        Optional<String> dataToken = GetDataToken.getDataToken();
        String s = dataToken.get();
        return ResponseEntity.ok(s);
    }























}
