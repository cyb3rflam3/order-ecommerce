package com.order.ecommerce.util

class Converters {
    companion object {
        inline fun<reified E : Enum<E>> getEnumOf(enumInString: String): E {
            return enumValueOf(enumInString)
        }
    }
}