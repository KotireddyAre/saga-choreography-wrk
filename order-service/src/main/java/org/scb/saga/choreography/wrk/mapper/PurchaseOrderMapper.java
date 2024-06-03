package org.scb.saga.choreography.wrk.mapper;

import org.mapstruct.*;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;

import java.util.Random;
import java.util.random.RandomGenerator;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PurchaseOrderMapper {

    @Mapping(target = "price", source = "amount")
    @Mapping(target = "id", expression = "java(generateRandomNumber())")
    PurchaseOrder toEntity(OrderRequestDto orderRequestDto);

    OrderRequestDto toDto(PurchaseOrder purchaseOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PurchaseOrder partialUpdate(OrderRequestDto orderRequestDto, @MappingTarget PurchaseOrder purchaseOrder);

    default Integer generateRandomNumber() {
        return Random.from(RandomGenerator.getDefault())
                .nextInt(100000);
    }
}