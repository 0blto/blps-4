package com.drainshawty.lab1.delegation;

import com.drainshawty.lab1.model.Order;
import com.drainshawty.lab1.services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.Objects;


@Component
@Named("finalizeOrder")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FinalizeOrderDelegate implements JavaDelegate {

    OrderService orderService;

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("order_id");
        Order.Status currentStatus = orderService.findOrderById(orderId).getStatus();
        orderService.updateOrderStatus(
                orderId,
                Objects.equals(currentStatus, Order.Status.RECEIVED)
                        ? Order.Status.FINALIZED
                        : currentStatus
        );

    }
}
