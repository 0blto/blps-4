package com.drainshawty.lab1.delegation;

import com.drainshawty.lab1.Utils;
import com.drainshawty.lab1.model.Order;
import com.drainshawty.lab1.services.EmailSender;
import com.drainshawty.lab1.services.OrderService;
import com.drainshawty.lab1.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Component
@Named("createOrder")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CreateOrderDelegate implements JavaDelegate {

    OrderService orderService;
    ProductService productService;
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) {
        String customerName = identityService.getCurrentAuthentication().getUserId();
        Long productId = (Long) execution.getVariable("product_id");
        Long amount = (Long) execution.getVariable("amount");

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAmount(amount);
        order.setProduct(productService.findProductById(productId));

        Order createdOrder = orderService.createOrder(order);
        EmailSender.sendEmail(Utils.getUserEmail(identityService, customerName),
                "Order",
                "Order created!\n" +
                        "Product: " + productService.findProductById(productId).getName() + "\n" +
                        "Amount: " + amount
        );
        execution.setVariable("order_id", createdOrder.getId());
    }
}
