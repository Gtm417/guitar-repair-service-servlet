package com.yura.repairservice.service.encoder;

import com.yura.repairservice.exception.PasswordEncodeException;
import com.yura.repairservice.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger(PasswordEncoder.class);

    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Password encode error");
            throw new PasswordEncodeException("Password encode error");
        }
    }
}
