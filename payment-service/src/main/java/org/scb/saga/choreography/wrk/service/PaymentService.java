package org.scb.saga.choreography.wrk.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.val;
import org.scb.saga.choreography.wrk.dto.PaymentRequestDto;
import org.scb.saga.choreography.wrk.entity.UserBalance;
import org.scb.saga.choreography.wrk.entity.UserTransaction;
import org.scb.saga.choreography.wrk.event.OrderEvent;
import org.scb.saga.choreography.wrk.event.PaymentEvent;
import org.scb.saga.choreography.wrk.event.PaymentStatus;
import org.scb.saga.choreography.wrk.repository.UserBalanceRepository;
import org.scb.saga.choreography.wrk.repository.UserTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class PaymentService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserTransactionRepository userTransactionRepository;

    public PaymentService(UserBalanceRepository userBalanceRepository,
                          UserTransactionRepository userTransactionRepository) {
        this.userBalanceRepository = userBalanceRepository;
        this.userTransactionRepository = userTransactionRepository;
    }

    @PostConstruct
    public void initUserBalance() {
        this.userBalanceRepository.saveAll(Stream.of(
                new UserBalance(101, 5000),
                new UserBalance(102, 3000),
                new UserBalance(103, 4200),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)).toList());
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        val orderRequestDto = orderEvent.getOrderRequestDto();

        val paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                        orderRequestDto.getUserId(), orderRequestDto.getAmount());

        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(userBalance -> userBalance.getPrice() > orderRequestDto.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
                            orderRequestDto.getUserId(), orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                })
                .orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(ut->{
                    userTransactionRepository.delete(ut);
                    userTransactionRepository.findById(ut.getUserId())
                            .ifPresent(ub->ub.setAmount(ub.getAmount()+ut.getAmount()));
                });
    }
}
