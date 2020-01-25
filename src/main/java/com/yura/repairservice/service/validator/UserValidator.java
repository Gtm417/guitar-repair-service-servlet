package com.yura.repairservice.service.validator;

import com.yura.repairservice.domain.User;
import com.yura.repairservice.exception.InvalidUserParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);

    private static final String NAME_REGEX = "[a-zA-Zа-яА-Яієї']{2,25}";
    private static final String SURNAME_REGEX = "[a-zA-Zа-яА-Яієї']{2,25}";
    private static final String EMAIL_REGEX = "(\\w{2,})@(\\w+\\.)([a-z]{2,4})";
    private static final String PASSWORD_REGEX = "[A-Za-z0-9]{8,}";
    private static final String PHONE_REGEX = "[0-9]{10}";

    @Override
    public void validate(User user) {
        if (Objects.isNull(user)) {
            LOGGER.warn("User is null");
            throw new InvalidUserParameterException("User is null");
        }

        validate(user.getName(), NAME_REGEX, "Name is not correct");
        validate(user.getSurname(), SURNAME_REGEX, "Surname is not correct");
        validate(user.getEmail(), EMAIL_REGEX, "Email is not correct");
        validate(user.getPassword(), PASSWORD_REGEX, "Password is not correct");
        validate(user.getPhone(), PHONE_REGEX, "Phone is not correct");

    }

    private void validate(String parameter, String regex, String message) {
        Matcher matcher = Pattern.compile(regex).matcher(parameter);

        if (!matcher.find()) {
            LOGGER.warn(message);
            throw new InvalidUserParameterException(message);
        }
    }
}