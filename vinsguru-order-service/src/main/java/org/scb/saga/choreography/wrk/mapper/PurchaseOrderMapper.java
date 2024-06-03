package org.scb.saga.choreography.wrk.mapper;

import org.mapstruct.*;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PurchaseOrderMapper {

    @Mapping(target = "id", source = "orderId")
    PurchaseOrder toEntity(OrderRequestDto orderRequestDto);

    @Mapping(target = "orderId", source = "id")
    OrderRequestDto toDto(PurchaseOrder purchaseOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PurchaseOrder partialUpdate(OrderRequestDto orderRequestDto, @MappingTarget PurchaseOrder purchaseOrder);
}