package com.youtubeclonebynattarat.nattaratprojects.Exceptios;

public class ExceptionEmail extends BaseException{
    public ExceptionEmail(String code) {
        super("Email "+code);
    }
}
