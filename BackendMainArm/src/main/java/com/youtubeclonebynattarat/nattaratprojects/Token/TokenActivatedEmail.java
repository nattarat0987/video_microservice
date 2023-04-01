package com.youtubeclonebynattarat.nattaratprojects.Token;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import java.util.Arrays;
import java.util.List;

public class TokenActivatedEmail {

     private TokenActivatedEmail(){

    }
    public static String  tokengeneration(){
        List<CharacterRule> rules = Arrays.asList(
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 5),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 5),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 5));

        new CharacterRule(EnglishCharacterData.Alphabetical,5);
        PasswordGenerator generator = new PasswordGenerator();
        // Generated password is 12 characters long, which complies with policy
        return   generator.generatePassword(20, rules);


    }


}
