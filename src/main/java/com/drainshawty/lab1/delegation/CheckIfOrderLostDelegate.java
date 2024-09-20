package com.drainshawty.lab1.delegation;

import com.drainshawty.lab1.Utils;
import com.drainshawty.lab1.model.Order;
import com.drainshawty.lab1.services.EmailSender;
import com.drainshawty.lab1.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.Objects;

@Component
@Named("checkIfOrderLost")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CheckIfOrderLostDelegate implements JavaDelegate {

    OrderService orderService;
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("order_id");
        boolean status = Objects.equals(execution.getVariable("is_arrived"), "arrived");
        execution.setVariable("is_arrived", status);
        if (status) orderService.updateOrderStatus(orderId, Order.Status.ARRIVED);
        else orderService.updateOrderStatus(orderId, Order.Status.LOST);
        EmailSender.sendEmail(Utils.getUserEmail(identityService, orderService.findOrderById(orderId).getCustomerName()),
                status ? "Order is arrived" : "Order lost",
                status ? "Order is ready to be taken." : "Order is lost. You will receive your money back in 5 mins."
        );
    }
}
