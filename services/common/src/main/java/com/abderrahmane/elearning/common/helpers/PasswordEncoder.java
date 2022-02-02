package com.abderrahmane.elearning.common.helpers;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import at.favre.lib.crypto.bcrypt.BCrypt.Verifyer;

@Component
public class PasswordEncoder {
    private Hasher hasher = BCrypt.withDefaults();
    private Verifyer verifyer = BCrypt.verifyer();
    private final int cost = 10;

    public String encode (String password) {
        return hasher.hashToString(cost, password.toCharArray());
    }

    public boolean check (String hash, String password) {
        return verifyer.verify(password.toCharArray(), hash).verified;
    }
}
