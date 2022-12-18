package com.order.ecommerce.util

import com.order.ecommerce.dto.AddressDto
import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.dto.OrderItemDto
import com.order.ecommerce.enum.PaymentMode
import com.order.ecommerce.enum.ShippingMode
import com.order.ecommerce.mapper.OrderDetailsMapper.Companion.getPaymentModel
import com.order.ecommerce.mapper.OrderDetailsMapper.Companion.toAddressEntity
import com.order.ecommerce.model.Order
import com.order.ecommerce.model.OrderItem
import java.time.LocalDateTime

class OrderUtil {

    companion object {

        @JvmStatic
        fun createTestOrder(): OrderDto {
            return OrderDto(
                customerId = "1",
                subTotal = 6.0,
                totalAmt = 10.0,
                tax = 2.0,
                shippingCharges = 2.0,
                title = "test",
                shippingMode = ShippingMode.DELIVERY,
                amount = 10.0,
                paymentMode = PaymentMode.CREDIT,
                billingAddress = createAddress(),
                shippingAddress = createAddress(),
                orderItems = listOf(
                    OrderItemDto("101", "10"),
                    OrderItemDto("102", "10")
                )
            )
        }

        @JvmStatic
        fun createAddress() = AddressDto(
            address1 = "3755 M lane",
            address2 = "Apt 311",
            city = "Aurora",
            state = "IL",
            zip = "60504",
            email = "test.gmail.com",
            phone = "1234567890"
        )

        @JvmStatic
        fun createMockOrderResponse(): Order {

            val payment = getPaymentModel(0.0, PaymentMode.CASH)
            val billingAddress = createAddress().toAddressEntity()
            val shippingAddress = createAddress().toAddressEntity()
            val mutableList = mutableListOf<OrderItem>()

            val dateTime = LocalDateTime.parse("2022-10-17T11:31:27.771692");
            return Order(
                "2e99fe21-2243-4004-9640-e992bbcc5040",
                "PROCESSING",
                "2",
                6.0,
                10.0,
                2.0,
                2.0,
                "testProduct",
                ShippingMode.DELIVERY.name,
                dateTime,
                payment,
                billingAddress,
                shippingAddress,
                mutableList
            )
        }

    }

}
