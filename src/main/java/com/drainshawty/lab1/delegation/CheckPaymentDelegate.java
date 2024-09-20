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
@Named("checkPayment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CheckPaymentDelegate implements JavaDelegate {

    OrderService orderService;
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("order_id");
        Boolean isPaid = (Boolean) execution.getVariable("is_paid");
        if (isPaid) orderService.updateOrderStatus(orderId, Order.Status.PAID);
        else orderService.updateOrderStatus(orderId, Order.Status.CANCELLED);
        EmailSender.sendEmail(Utils.getUserEmail(identityService, orderService.findOrderById(orderId).getCustomerName()),
                "Payment",
                isPaid ? "Order is paid. Wait for it" : "Order is not paid. Try again."
        );
    }
}
