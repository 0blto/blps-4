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


@Component
@Named("checkingProduct")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CheckingProductDelegate implements JavaDelegate {

    OrderService orderService;
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("order_id");
        boolean isReceived = (Boolean) execution.getVariable("is_received");
        orderService.updateOrderStatus(
                orderId,
                isReceived
                        ? Order.Status.RECEIVED
                        : Order.Status.REJECTED
        );
        EmailSender.sendEmail(Utils.getUserEmail(identityService, orderService.findOrderById(orderId).getCustomerName()),
                isReceived ? "Order received" : "Order is returned",
                isReceived
                        ? "Order is received! Remember that you have 10 seconds to return"
                        : "Order is returned. We will give you money back in 5 mins"
        );
    }
}
