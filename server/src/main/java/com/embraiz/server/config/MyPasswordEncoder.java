package com.embraiz.server.config;

import com.embraiz.component.Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return Util.getMD5((String) charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(Util.getMD5((String) charSequence));
    }
}