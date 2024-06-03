package org.scb.saga.choreography.wrk.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.scb.saga.choreography.wrk.dto.PaymentDto;
import org.scb.saga.choreography.wrk.entity.UserTransaction;
import org.scb.saga.choreography.wrk.events.order.OrderEvent;
import org.scb.saga.choreography.wrk.events.payment.PaymentEvent;
import org.scb.saga.choreography.wrk.events.payment.PaymentStatus;
import org.scb.saga.choreography.wrk.repository.UserBalanceRepository;
import org.scb.saga.choreography.wrk.repository.UserTransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserBalanceRepository balanceRepository;
    private final UserTransactionRepository transactionRepository;

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        val purchaseOrder = orderEvent.getPurchaseOrder();
        val paymentDto = PaymentDto.builder()
                .userId(purchaseOrder.getUserId())
                .orderId(purchaseOrder.getOrderId())
                .amount(purchaseOrder.getPrice())
                .build();
        return this.balanceRepository.findById(paymentDto.getUserId())
                .filter(userBalance -> userBalance.getBalance() >= purchaseOrder.getPrice())
                .map(userBalance -> {
                    userBalance.setBalance(userBalance.getBalance() - purchaseOrder.getPrice());
                    this.transactionRepository.save(UserTransaction.of(purchaseOrder.getOrderId(), purchaseOrder.getUserId(), purchaseOrder.getPrice()));
                    return new PaymentEvent(paymentDto, PaymentStatus.RESERVED);
                })
                .orElse(new PaymentEvent(paymentDto, PaymentStatus.REJECTED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        this.transactionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
                .ifPresent(userTransaction -> {
                    this.transactionRepository.delete(userTransaction);
                    this.balanceRepository.findById(userTransaction.getUserId())
                            .ifPresent(ub -> ub.setBalance(ub.getBalance() + userTransaction.getAmount()));
                });
    }
}
