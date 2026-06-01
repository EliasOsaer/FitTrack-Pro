package be.howest.eliasosaer.fittrackpro.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String?,
    val qty: Int
)
