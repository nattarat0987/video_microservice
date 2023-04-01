package com.youtubeclonebynattarat.nattaratprojects.Service;

import com.youtubeclonebynattarat.nattaratprojects.Entity.User;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionEmail;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionUser;
import com.youtubeclonebynattarat.nattaratprojects.DTO.EmailactivateDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.LoginRequestDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.LoginResponseDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.RegistrationRequestDto;
import com.youtubeclonebynattarat.nattaratprojects.Repository.UserRepository;
import com.youtubeclonebynattarat.nattaratprojects.Token.GetDataToken;
import com.youtubeclonebynattarat.nattaratprojects.Token.TokenActivatedEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final EmailService emailService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final int time_token = 20; // TODO Time_Token_Email

    public LoginResponseDto login(LoginRequestDto loginRequest) throws BaseException {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUser.isEmpty()) {
            throw new ExceptionUser("Invalid username or password.");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ExceptionUser("Invalid username or password.");
        }
        if (!user.getActivated()) {
            throw new ExceptionUser("user no Activated account");
        }
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(tokenService.CreateToken(user));
        return loginResponse;
    }

    public String registration(RegistrationRequestDto userRequest) throws BaseException {
        if (ObjectUtils.isEmpty(userRequest.getUsername())) {
            throw new ExceptionUser("Username isEmpty");
        }
        if (ObjectUtils.isEmpty(userRequest.getPassword())) {
            throw new ExceptionUser("Password isEmpty");
        }
        if (ObjectUtils.isEmpty(userRequest.getEmailAddress())) {
            throw new ExceptionUser("EmailAddress isEmpty");
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmailAddress(userRequest.getEmailAddress());
        String token = TokenActivatedEmail.tokengeneration();
        user.setToken(token);
        user.setActivated(false);
        user.setTokenExpired(token_Activated_expired(time_token));
        emailService.sendActivateUserEmail(userRequest.getEmailAddress(), userRequest.getUsername(), token);
        userRepository.save(user);
        return "Registration successful";
    }

    public String ActivatedEmail(String token_request) throws BaseException {
        if (token_request == null) {
            throw new ExceptionEmail("token null");
        }
        Optional<User> token_opt = userRepository.findByToken(token_request);
        if (token_opt.isEmpty()) {
            throw new ExceptionEmail("token Empty");
        }
        User user = token_opt.get();
        if (user.getActivated()) {
            throw new ExceptionEmail("user already activated your account");
        }
        if (checkTimeToken(user.getTokenExpired())) {
            throw new ExceptionEmail("token Expired");
        }
        user.setActivated(true);
        userRepository.save(user);
        return "Activate Email successful";
    }

    public void resendEmail(EmailactivateDto request) throws BaseException {
        if (ObjectUtils.isEmpty(request)) {
            throw new ExceptionEmail("Email_empty");
        }
        Optional<User> optEmail = userRepository.findByEmailAddress(request.getEmail());
        if (optEmail.isEmpty()) {
            throw new ExceptionEmail("Email_empty");
        }
        User user = optEmail.get();
        if (user.getActivated()) {
            throw new ExceptionEmail("user has already activated email");
        }
        String token = TokenActivatedEmail.tokengeneration();
        user.setToken(token);
        user.setTokenExpired(token_Activated_expired(time_token));
        emailService.sendActivateUserEmail(user.getEmailAddress(), user.getUsername(), token);
        userRepository.save(user);
    }

    private Boolean checkTimeToken(Date time_token) {
        Date now = new Date();
        return now.after(time_token);//
    }

    private Date token_Activated_expired(int time_token) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, time_token);
        return calendar.getTime();
    }

    private User getUser() throws BaseException {
        Optional<String> OptuserId = GetDataToken.getDataToken();
        if (OptuserId.isEmpty()) {
            throw new ExceptionUser("userId Empty");
        }
        String userId = OptuserId.get();
        log.info("userId ="+userId);
        Optional<User> Optuser = userRepository.findById(userId);
        if (Optuser.isEmpty()) {
            throw new ExceptionUser("user Empty");
        }
        return Optuser.get();
    }

    public void addToLikeVideo(String videoId) throws BaseException {
        User user = getUser();
        user.addToLikeVideo(videoId);
        userRepository.save(user);
    }
    public void addTodisLikeVideo(String videoId) throws BaseException {
        User user = getUser();
        user.addTodisLikeVideo(videoId);
        userRepository.save(user);
    }

    public void addToVideoHistory(String videoId) throws BaseException {
        User user = getUser();
        user.addToVideoHistory(videoId);
        userRepository.save(user);
    }


    public boolean IF_likedVideos(String videoId) throws BaseException {
        return getUser().getLikedVideos().stream().anyMatch(Likevideo -> Likevideo.equals(videoId));
    }

    public boolean IF_dislikedVideos(String videoId) throws BaseException {
        return getUser().getDislikedVideos().stream().anyMatch(Likevideo -> Likevideo.equals(videoId));
    }

    public void removeToLikevideoId(String videoId) throws BaseException {
        User user = getUser();
        user.removeToLikevideoId(videoId);
        userRepository.save(user);
    }

    public void removeTodisLikevideoId(String videoId) throws BaseException {
        User user = getUser();
        user.removeTodisLikevideoId(videoId);
        userRepository.save(user);
    }

    public void subscribeUser(String userId) throws BaseException {
        User principal = getUser();
        principal.addToSubscribedToUser(userId);
        User user = userFindById(userId);
        user.addToSubscribers(userId);
        userRepository.save(principal);
        userRepository.save(user);
    }

    public User userFindById(String userId) throws BaseException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionUser("userId Empty"));

    }

    public void UnsubscribeUser(String userId) throws BaseException {
        User principal = getUser();
        principal.removeToSubscribedToUser(userId);
        User user = userFindById(userId);
        user.removeToSubscribers(userId);
        userRepository.save(principal);
        userRepository.save(user);
    }

    public Set<String> userHistory(String userId) throws BaseException {
        User user = userFindById(userId);
        return user.getVideoHistory();
    }
}
