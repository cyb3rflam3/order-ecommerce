package com.order.ecommerce.model

import com.order.ecommerce.dto.OrderItemDto
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ecommerce_order_item")
class OrderItem(

    @EmbeddedId
    private var orderItemPk: OrderItemPk,

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private var product: Product? = null,

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private var order: Order? = null,

    @Column(name = "quantity", nullable = false)
    private var quantity: String

) : Serializable {
    companion object {
        fun List<OrderItem>.toOrderItemDto(): List<OrderItemDto> = this.map { OrderItemDto(
            productId = it.orderItemPk.getProductId(),
            quantity = it.quantity
        ) }
    }
}
