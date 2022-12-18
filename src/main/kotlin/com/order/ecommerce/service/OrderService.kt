package com.order.ecommerce.service

import com.order.ecommerce.dto.OrderCreateResponse
import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.enum.OrderStatus
import com.order.ecommerce.mapper.OrderDetailsMapper
import com.order.ecommerce.model.Order
import com.order.ecommerce.model.Order.Companion.toOrderDto
import com.order.ecommerce.model.OrderItem
import com.order.ecommerce.repository.OrderItemRepository
import com.order.ecommerce.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val orderDetailsMapper: OrderDetailsMapper,
    val orderItemRepository: OrderItemRepository
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(OrderService::class.java)
    }

    fun updateOrderStatus(orderId: String, orderStatus: String) {
        val order: Order = orderRepository.findById(orderId).orElseThrow()
        order.orderStatus = orderStatus
        orderRepository.save(order)
    }

    fun findOrderById(orderId: String): OrderDto {
        val order = orderRepository.findById(orderId).orElseThrow()
        return order.toOrderDto()
    }

    @Transactional
    fun createOrder(orderDto: OrderDto): OrderCreateResponse {
        log.info("Creating Order for customer {}", orderDto.customerId)
        val savedOrder: Order =
            orderRepository.save(orderDto.toOrderEntity(UUID.randomUUID().toString()))

        val orderItemList: List<OrderItem> =
            orderDetailsMapper.buildOrderItems(orderDto.orderItems, savedOrder.orderId)
        orderItemRepository.saveAll(orderItemList)
        return OrderCreateResponse(savedOrder.orderId, savedOrder.toOrderDto())

    }

    fun OrderDto.toOrderEntity(orderId: String) = Order(
        orderId = orderId,
        orderStatus = OrderStatus.PROCESSING.name,
        customerId = customerId,
        subTotal = subTotal,
        totalAmt = totalAmt,
        tax = tax,
        shippingCharges = shippingCharges,
        title = title,
        shippingMode = shippingMode.name,
        createdAt = LocalDateTime.now(),
        payment = orderDetailsMapper.buildAndLoadPayment(amount, paymentMode),
        billingAddress = orderDetailsMapper.buildAndLoadAddress(billingAddress),
        shippingAddress = orderDetailsMapper.buildAndLoadAddress(shippingAddress),
        orderItems = null

    )

}
