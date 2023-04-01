package com.youtubeclonebynattarat.nattaratprojects.Exceptios;


public class ExceptionUser extends BaseException{
    public ExceptionUser(String code) {
        super("User "+code);
    }

}
