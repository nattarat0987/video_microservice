package com.youtubeclonebynattarat.nattaratprojects.Token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
@Slf4j
public class GetDataToken {
    private GetDataToken(){
    }

    public static Optional<String> getDataToken(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(ObjectUtils.isEmpty(context)){
            log.info("context == Null");
            return Optional.empty();
        }
        Authentication authentication = context.getAuthentication();
        if(ObjectUtils.isEmpty(authentication)){
            log.info("authentication == Null");
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if(ObjectUtils.isEmpty(principal)){
            log.info("principal == Null");
            return Optional.empty();
        }
        String user_Id = (String) principal;
        return Optional.of(user_Id);
    }
}
