package com.order.ecommerce.controller

import com.order.ecommerce.dto.OrderCreateResponse
import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.model.Order.Companion.toOrderDto
import com.order.ecommerce.service.OrderService
import com.order.ecommerce.util.OrderUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderControllerUnitTest {


    @Mock
    lateinit var orderService: OrderService

    @InjectMocks
    lateinit var orderController: OrderController

    private val orderDtoRequest: OrderDto = OrderUtil.createTestOrder()
    private val mockOrderResponse = OrderUtil.createMockOrderResponse().toOrderDto()
    private val mockOrderCreateResponse: OrderCreateResponse =
        OrderCreateResponse("2e99fe21-2243-4004-9640-e992bbcc5040", mockOrderResponse)


    @Test
    fun testCreateOrder() {
        Mockito.`when`(orderService.createOrder(orderDtoRequest))
            .thenReturn(mockOrderCreateResponse)
        val actualResponse = orderController.createOrder(orderDtoRequest)
        assertThat(actualResponse).isEqualTo(mockOrderCreateResponse)
    }

    @Test
    fun testGetOrder() {
        Mockito.`when`(orderService.findOrderById("2e99fe21-2243-4004-9640-e992bbcc5040"))
            .thenReturn(mockOrderResponse)
        val actualResponse = orderController.findOrderById("2e99fe21-2243-4004-9640-e992bbcc5040")
        assertThat(actualResponse).isEqualTo(mockOrderResponse)
    }

}
