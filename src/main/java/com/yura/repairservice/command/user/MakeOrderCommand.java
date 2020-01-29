package com.yura.repairservice.command.user;

import com.yura.repairservice.command.Command;
import com.yura.repairservice.domain.instrument.Instrument;
import com.yura.repairservice.domain.order.Order;
import com.yura.repairservice.domain.order.Status;
import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.service.InstrumentService;
import com.yura.repairservice.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class MakeOrderCommand implements Command {
    public final InstrumentService instrumentService;
    public final OrderService orderService;

    public MakeOrderCommand(InstrumentService instrumentService, OrderService orderService) {
        this.instrumentService = instrumentService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Integer instrumentId = instrumentService.add(Instrument.builder()
                .withBrand(request.getParameter("brand"))
                .withModel(request.getParameter("model"))
                .withYear(Integer.parseInt(request.getParameter("year")))
                .build());

        Order order = Order.builder()
                .withInstrument(Instrument.builder()
                        .withId(instrumentId)
                        .build())
                .withStatus(Status.NEW)
                .withDate(LocalDateTime.now())
                .withUser((User) request.getSession().getAttribute("user"))
                .withService((String) request.getParameter("service"))
                .build();
        System.out.println(order);
        orderService.add(order);
        request.setAttribute("orderSuccess", true);

        return "user-add-order.jsp";
    }
}
