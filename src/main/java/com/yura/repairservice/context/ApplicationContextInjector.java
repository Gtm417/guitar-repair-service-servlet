package com.yura.repairservice.context;

import com.yura.repairservice.command.Command;
import com.yura.repairservice.command.LoginCommand;
import com.yura.repairservice.command.RegisterCommand;
import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.entity.InstrumentEntity;
import com.yura.repairservice.entity.OrderEntity;
import com.yura.repairservice.entity.UserEntity;
import com.yura.repairservice.repository.InstrumentRepository;
import com.yura.repairservice.repository.OrderRepository;
import com.yura.repairservice.repository.UserRepository;
import com.yura.repairservice.repository.connector.DBConnector;
import com.yura.repairservice.repository.impl.InstrumentRepositoryImpl;
import com.yura.repairservice.repository.impl.OrderRepositoryImpl;
import com.yura.repairservice.repository.impl.UserRepositoryImpl;
import com.yura.repairservice.service.InstrumentService;
import com.yura.repairservice.service.OrderService;
import com.yura.repairservice.service.UserService;
import com.yura.repairservice.service.encoder.PasswordEncoder;
import com.yura.repairservice.service.impl.InstrumentServiceImpl;
import com.yura.repairservice.service.impl.OrderServiceImpl;
import com.yura.repairservice.service.impl.UserServiceImpl;
import com.yura.repairservice.service.mapper.EntityMapper;
import com.yura.repairservice.service.mapper.InstrumentMapper;
import com.yura.repairservice.service.mapper.OrderMapper;
import com.yura.repairservice.service.mapper.UserMapper;
import com.yura.repairservice.service.validator.InstrumentValidator;
import com.yura.repairservice.service.validator.OrderValidator;
import com.yura.repairservice.service.validator.UserValidator;
import com.yura.repairservice.service.validator.Validator;
import org.apache.commons.dbcp.BasicDataSource;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextInjector {
    private static final String DATABASE_PROPERTY_FILE = "database";

    private static final BasicDataSource BASIC_DATA_SOURCE = new BasicDataSource();
    private static final DBConnector CONNECTOR = new DBConnector(DATABASE_PROPERTY_FILE, BASIC_DATA_SOURCE);
    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();
    private static final UserRepository USER_REPOSITORY = new UserRepositoryImpl(CONNECTOR);
    private static final InstrumentRepository INSTRUMENT_REPOSITORY = new InstrumentRepositoryImpl(CONNECTOR);
    private static final OrderRepository ORDER_REPOSITORY = new OrderRepositoryImpl(CONNECTOR);
    private static final EntityMapper<UserEntity, User> USER_MAPPER = new UserMapper();
    private static final EntityMapper<InstrumentEntity, Instrument> INSTRUMENT_MAPPER = new InstrumentMapper();
    private static final EntityMapper<OrderEntity, Order> ORDER_MAPPER = new OrderMapper(USER_MAPPER, INSTRUMENT_MAPPER);
    private static final Validator<User> USER_VALIDATOR = new UserValidator();
    private static final Validator<Instrument> INSTRUMENT_VALIDATOR = new InstrumentValidator();
    private static final Validator<Order> ORDER_VALIDATOR = new OrderValidator();
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_REPOSITORY, USER_MAPPER, USER_VALIDATOR, PASSWORD_ENCODER);
    private static final InstrumentService INSTRUMENT_SERVICE = new InstrumentServiceImpl(INSTRUMENT_REPOSITORY, INSTRUMENT_MAPPER, INSTRUMENT_VALIDATOR);
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY, ORDER_MAPPER, ORDER_VALIDATOR);

    private static final Command LOGIN_COMMAND = new LoginCommand(USER_SERVICE);
    private static final Command REGISTER_COMMAND = new RegisterCommand(USER_SERVICE);
    private static final Map<String, Command> COMMAND_NAME_TO_COMMAND = new HashMap<>();

    static {
        COMMAND_NAME_TO_COMMAND.put("login", LOGIN_COMMAND);
        COMMAND_NAME_TO_COMMAND.put("register", REGISTER_COMMAND);
    }

    private static volatile ApplicationContextInjector applicationContextInjector;

    private ApplicationContextInjector() {
    }

    public static ApplicationContextInjector getInstance() {
        if (applicationContextInjector == null) {
            synchronized (ApplicationContextInjector.class) {
                if (applicationContextInjector == null) {
                    applicationContextInjector = new ApplicationContextInjector();
                }
            }
        }
        return applicationContextInjector;
    }

    public  Map<String, Command> getCommand() {
        return COMMAND_NAME_TO_COMMAND;
    }

    //TODO delete
    public InstrumentService getInstrumentService() {
        return INSTRUMENT_SERVICE;
    }

    //TODO delte
    public UserService getUserService() {
        return USER_SERVICE;
    }

    //TODO delete
    public OrderRepository getOrderRepository() {
        return ORDER_REPOSITORY;
    }
}
