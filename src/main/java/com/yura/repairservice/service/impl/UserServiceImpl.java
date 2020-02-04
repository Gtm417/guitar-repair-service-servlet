package com.yura.repairservice.service.impl;

import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.entity.UserEntity;
import com.yura.repairservice.exception.AlreadyRegisteredUserException;
import com.yura.repairservice.exception.UserNotFoundException;
import com.yura.repairservice.repository.UserRepository;
import com.yura.repairservice.service.UserService;
import com.yura.repairservice.service.encoder.PasswordEncoder;
import com.yura.repairservice.service.mapper.EntityMapper;
import com.yura.repairservice.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository repository;
    private final EntityMapper<UserEntity, User> mapper;
    private final Validator<User> validator;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, EntityMapper<UserEntity, User> mapper, Validator<User> validator, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user) {
        validator.validate(user);

        repository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new AlreadyRegisteredUserException("User with such email already exist " + u.getEmail());
        });

        User userWithEncodedPassword = new User(user, passwordEncoder.encode(user.getPassword()));

        repository.save(mapper.mapDomainToEntity(userWithEncodedPassword));
        LOGGER.info("User registered " + user.getEmail());
    }

    @Override
    public User login(String email, String password) {
        return repository
                .findByEmail(email)
                .map(mapper::mapEntityToDomain)
                .filter(user -> Objects.equals(user.getPassword(), passwordEncoder.encode(password)))
                .orElseThrow(() -> new UserNotFoundException("User not found with " + email + " email and provided password"));
    }

    @Override
    public User findById(Integer id) {
        return repository
                .findById(id)
                .map(mapper::mapEntityToDomain)
                .orElseThrow(() -> new UserNotFoundException("Order not found with provided id " + id));
    }

    @Override
    public List<User> findAll(Integer offset, Integer limit) {
        return repository
                .findAll(offset, limit)
                .stream()
                .map(mapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Integer numberOfEntries() {
        return repository.countAll();
    }
}
