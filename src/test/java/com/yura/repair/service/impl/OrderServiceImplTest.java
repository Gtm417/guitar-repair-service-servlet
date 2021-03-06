package com.yura.repair.service.impl;

import com.yura.repair.dto.InstrumentDto;
import com.yura.repair.dto.OrderDto;
import com.yura.repair.entity.Status;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.InstrumentEntity;
import com.yura.repair.entity.OrderEntity;
import com.yura.repair.entity.UserEntity;
import com.yura.repair.exception.InvalidParameterException;
import com.yura.repair.exception.OrderAlreadyUpdatedException;
import com.yura.repair.exception.OrderNotFoundException;
import com.yura.repair.repository.OrderRepository;
import com.yura.repair.service.mapper.EntityMapper;
import com.yura.repair.service.validator.Validator;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    private static final OrderDto ORDER_DTO = getOrderDto();
    private static final OrderEntity ORDER_ENTITY = getOrderEntity();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EntityMapper<OrderEntity, OrderDto> orderMapper;

    @Mock
    private Validator<OrderDto> orderValidator;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void addShouldAddOrder() {
        when(orderMapper.mapDtoToEntity(ORDER_DTO)).thenReturn(ORDER_ENTITY);
        when(orderRepository.save(ORDER_ENTITY)).thenReturn(true);

        orderService.add(ORDER_DTO);

        verify(orderRepository).save(ORDER_ENTITY);
    }

    @Test
    public void addShouldThrowInvalidParameterExceptionForNull() {
        exception.expect(InvalidParameterException.class);
        doThrow(InvalidParameterException.class).when(orderValidator).validate(null);

        orderService.add(null);
    }

    @Test
    public void findByIdShouldReturnOrder() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(ORDER_ENTITY));
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        OrderDto actual = orderService.findById(1);

        assertEquals(ORDER_DTO, actual);
    }

    @Test
    public void findByIdShouldThrowUOrderNotFoundException() {
        exception.expect(OrderNotFoundException.class);
        exception.expectMessage("Order not found with provided id");
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        orderService.findById(1);
    }

    @Test
    public void findAllShouldReturnListOfOrders() {
        List<OrderDto> expected = Collections.singletonList(ORDER_DTO);
        List<OrderEntity> orderEntities = Collections.singletonList(ORDER_ENTITY);

        when(orderRepository.findAll(anyInt(), anyInt())).thenReturn(orderEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        List<OrderDto> actual = orderService.findAll(1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllShouldReturnEmptyList() {
        List<OrderDto> expected = Collections.emptyList();
        List<OrderEntity> orderEntities = Collections.emptyList();

        when(orderRepository.findAll(anyInt(), anyInt())).thenReturn(orderEntities);

        List<OrderDto> actual = orderService.findAll(1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findByClientShouldReturnListOfOrders() {
        List<OrderDto> expected = Collections.singletonList(ORDER_DTO);
        List<OrderEntity> orderEntities = Collections.singletonList(ORDER_ENTITY);

        when(orderRepository.findAllByClientId(anyInt(), anyInt(), anyInt())).thenReturn(orderEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        List<OrderDto> actual = orderService.findByClient(1, 1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findByClientShouldReturnEmptyList() {
        List<OrderDto> expected = Collections.emptyList();
        List<OrderEntity> orderEntities = Collections.emptyList();

        when(orderRepository.findAllByClientId(anyInt(), anyInt(), anyInt())).thenReturn(orderEntities);

        List<OrderDto> actual = orderService.findByClient(1, 1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findByMasterShouldReturnListOfOrders() {
        List<OrderDto> expected = Collections.singletonList(ORDER_DTO);
        List<OrderEntity> orderEntities = Collections.singletonList(ORDER_ENTITY);

        when(orderRepository.findAllByMasterId(anyInt(), anyInt(), anyInt())).thenReturn(orderEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        List<OrderDto> actual = orderService.findByMaster(1, 1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findByMasterShouldReturnEmptyList() {
        List<OrderDto> expected = Collections.emptyList();
        List<OrderEntity> orderEntities = Collections.emptyList();

        when(orderRepository.findAllByMasterId(anyInt(), anyInt(), anyInt())).thenReturn(orderEntities);

        List<OrderDto> actual = orderService.findByMaster(1, 1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findByStatusShouldReturnListOfOrders() {
        List<OrderDto> expected = Collections.singletonList(ORDER_DTO);
        List<OrderEntity> orderEntities = Collections.singletonList(ORDER_ENTITY);

        when(orderRepository.findAllByStatus(any(Status.class), anyInt(), anyInt())).thenReturn(orderEntities);
        when(orderMapper.mapEntityToDto(ORDER_ENTITY)).thenReturn(ORDER_DTO);

        List<OrderDto> actual = orderService.findByStatus(Status.ACCEPTED, 1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void findByStatusShouldReturnEmptyList() {
        List<OrderDto> expected = Collections.emptyList();
        List<OrderEntity> orderEntities = Collections.emptyList();

        when(orderRepository.findAllByStatus(any(Status.class), anyInt(), anyInt())).thenReturn(orderEntities);

        List<OrderDto> actual = orderService.findByStatus(Status.ACCEPTED, 1, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void acceptOrderShouldUpdateOrder() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(ORDER_ENTITY));

        orderService.acceptOrder(ORDER_DTO, 1.1);

        verify(orderRepository).update(any(OrderEntity.class));
    }

    @Test
    public void acceptOrderShouldThrowOrderAlreadyUpdatedException() {
        exception.expect(OrderAlreadyUpdatedException.class);
        exception.expectMessage("Order already accepted");
        OrderEntity orderEntity = new OrderEntity(ORDER_ENTITY, Status.ACCEPTED);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.acceptOrder(ORDER_DTO, 1.1);
    }

    @Test
    public void rejectOrderShouldUpdateOrder() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(ORDER_ENTITY));

        orderService.rejectOrder(ORDER_DTO, "reason");

        verify(orderRepository).update(any(OrderEntity.class));
    }

    @Test
    public void rejectOrderShouldThrowOrderAlreadyUpdatedException() {
        exception.expect(OrderAlreadyUpdatedException.class);
        exception.expectMessage("Order already accepted");
        OrderEntity orderEntity = new OrderEntity(ORDER_ENTITY, Status.ACCEPTED);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.rejectOrder(ORDER_DTO, "reason");
    }

    @Test
    public void processOrderShouldUpdateOrder() {
        OrderEntity orderEntity = new OrderEntity(ORDER_ENTITY, Status.ACCEPTED);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.processOrder(ORDER_DTO, UserDto.builder().build());

        verify(orderRepository).update(any(OrderEntity.class));
    }

    @Test
    public void processOrderShouldThrowOrderAlreadyUpdatedException() {
        exception.expect(OrderAlreadyUpdatedException.class);
        exception.expectMessage("Order already processed");

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(ORDER_ENTITY));

        orderService.processOrder(ORDER_DTO, UserDto.builder().build());
    }

    @Test
    public void completeOrderShouldUpdateOrder() {
        OrderEntity orderEntity = new OrderEntity(ORDER_ENTITY, Status.PROCESSING);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.completeOrder(ORDER_DTO);

        verify(orderRepository).update(any(OrderEntity.class));
    }

    @Test
    public void completeOrderShouldThrowOrderAlreadyUpdatedException() {
        exception.expect(OrderAlreadyUpdatedException.class);
        exception.expectMessage("Order already completed");

        OrderEntity orderEntity = new OrderEntity(ORDER_ENTITY, Status.COMPLETED);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderEntity));

        orderService.completeOrder(ORDER_DTO);
    }

    @Test
    public void setPriceShouldUpdateOrder() {
        when(orderMapper.mapDtoToEntity(any(OrderDto.class))).thenReturn(ORDER_ENTITY);
        orderService.setPrice(ORDER_DTO, 1.1);
        verify(orderRepository).update(ORDER_ENTITY);
    }

    @Test
    public void numberOfEntriesShouldReturnNumberOfEntries() {
        when(orderRepository.countAll()).thenReturn(10);

        int expected = 10;
        int actual = orderService.numberOfEntries();

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfEntriesByClientIdShouldReturnNumberOfEntries() {
        when(orderRepository.countByClientId(anyInt())).thenReturn(10);

        int expected = 10;
        int actual = orderService.numberOfEntriesByClientId(1);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfEntriesByMasterIdShouldReturnNumberOfEntries() {
        when(orderRepository.countByMasterId(anyInt())).thenReturn(10);

        int expected = 10;
        int actual = orderService.numberOfEntriesByMasterId(1);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfEntriesByStatusShouldReturnNumberOfEntries() {
        when(orderRepository.countByStatus(any(Status.class))).thenReturn(10);

        int expected = 10;
        int actual = orderService.numberOfEntriesByStatus(Status.ACCEPTED);

        assertEquals(expected, actual);
    }

    @Test
    public void isNotUserOrderShouldReturnFalse() {
        boolean actual = orderService.isNotUserOrder(UserDto.builder().withId(1).build(), ORDER_DTO);
        assertFalse(actual);
    }

    @Test
    public void isNotUserOrderShouldReturnTrue() {
        boolean actual = orderService.isNotUserOrder(UserDto.builder().withId(2).build(), ORDER_DTO);
        assertTrue(actual);
    }

    @Test
    public void isNotUserOrderShouldReturnTrueForNull() {
        boolean actual = orderService.isNotUserOrder(null, ORDER_DTO);
        assertTrue(actual);
    }

    @Test
    public void isNotMasterOrderShouldReturnFalse() {
        boolean actual = orderService.isNotMasterOrder(UserDto.builder().withId(1).build(), ORDER_DTO);
        assertFalse(actual);
    }

    @Test
    public void isNotMasterOrderShouldReturnTrue() {
        boolean actual = orderService.isNotMasterOrder(UserDto.builder().withId(2).build(), ORDER_DTO);
        assertTrue(actual);
    }

    @Test
    public void isNotMasterOrderShouldReturnFalseForNull() {
        OrderDto orderDto = OrderDto.builder().build();
        boolean actual = orderService.isNotMasterOrder(UserDto.builder().withId(1).build(), orderDto);
        assertFalse(actual);
    }



    private static OrderDto getOrderDto() {
        return OrderDto.builder()
                .withId(1)
                .withInstrument(InstrumentDto.builder()
                        .withBrand("Cort")
                        .withModel("123")
                        .withYear(1995)
                        .build())
                .withStatus(Status.NEW)
                .withDate(LocalDateTime.of(1990, 12, 12, 12, 12))
                .withUser(UserDto.builder()
                        .withId(1)
                        .withName("Yura")
                        .build())
                .withService("Service")
                .withMaster(UserDto.builder().withId(1).build())
                .build();
    }

    private static OrderEntity getOrderEntity() {
        return OrderEntity.builder()
                .withId(1)
                .withInstrument(InstrumentEntity.builder()
                        .withId(1)
                        .withBrand("Cort")
                        .withModel("123")
                        .withYear(1995)
                        .build())
                .withStatus(Status.NEW)
                .withDate(LocalDateTime.of(1990, 12, 12, 12, 12))
                .withUser(UserEntity.builder()
                        .withId(1)
                        .withName("Yura")
                        .build())
                .withService("Service")
                .build();
    }
}