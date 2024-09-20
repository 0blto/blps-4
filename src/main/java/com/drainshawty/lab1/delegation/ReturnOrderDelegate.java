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
@Named("returnOrder")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReturnOrderDelegate implements JavaDelegate {

    OrderService orderService;
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("order_id");
        Order.Status currentStatus = orderService.findOrderById(orderId).getStatus();
        orderService.updateOrderStatus(
                orderId,
                Objects.equals(currentStatus, Order.Status.FINALIZED)
                        ? Order.Status.FINALIZED
                        : Order.Status.REJECTED
        );
        EmailSender.sendEmail(Utils.getUserEmail(identityService, orderService.findOrderById(orderId).getCustomerName()),
                Objects.equals(currentStatus, Order.Status.FINALIZED) ? "Order can't be returned" : "Order returned",
                Objects.equals(currentStatus, Order.Status.FINALIZED)
                        ? "Sorry"
                        : "Order is returned. We will give you money back in 5 mins"
        );
    }
}
