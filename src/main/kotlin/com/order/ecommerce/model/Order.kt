package com.order.ecommerce.model

import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.model.Address.Companion.toAddressDto
import com.order.ecommerce.model.OrderItem.Companion.toOrderItemDto
import com.order.ecommerce.util.Converters
import lombok.Data
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "ecommerce_order")
@Data
class Order(

    @Id
    @Column(name = "order_id", nullable = false, unique = true)
    open var orderId: String,

    @Column(name = "order_status")
    open var orderStatus: String,

    @Column(name = "customer_id")
    open var customerId: String,

    @Column(name = "sub_total")
    open var subTotal: Double,

    @Column(name = "total_amt")
    open var totalAmt: Double,

    @Column(name = "tax")
    open var tax: Double,

    @Column(name = "shipping_charges")
    open var shippingCharges: Double,

    @Column(name = "title")
    open var title: String,

    @Column(name = "shipping_mode")
    open var shippingMode: String,

    @Column(name = "created_at")
    open var createdAt: LocalDateTime,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "payment_id", name = "payment_id")
    open var payment: Payment,

    @OneToOne
    @JoinColumn(referencedColumnName = "address_id", name = "billing_address_id")
    open var billingAddress: Address,

    @OneToOne
    @JoinColumn(referencedColumnName = "address_id", name = "shipping_address_id")
    open var shippingAddress: Address,

    @OneToMany(targetEntity = OrderItem::class, fetch = FetchType.LAZY, mappedBy = "order")
    open var orderItems: MutableList<OrderItem>?

) : Serializable {
    companion object {
        fun Order.toOrderDto() : OrderDto {
            return OrderDto(
                customerId = customerId,
                orderItems = orderItems?.toOrderItemDto() ?: emptyList(),
                shippingCharges = shippingCharges,
                subTotal = subTotal,
                totalAmt = totalAmt,
                tax = tax,
                title = title,
                shippingMode = Converters.getEnumOf(shippingMode),
                amount = payment.getAmount(),
                paymentMode = Converters.getEnumOf(payment.getPaymentMode()),
                billingAddress = billingAddress.toAddressDto(),
                shippingAddress = shippingAddress.toAddressDto(),
            )
        }
    }
}
