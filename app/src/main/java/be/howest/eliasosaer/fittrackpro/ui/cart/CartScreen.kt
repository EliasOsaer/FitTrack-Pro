package be.howest.eliasosaer.fittrackpro.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import be.howest.eliasosaer.fittrackpro.domain.model.CartItem
import coil.compose.AsyncImage

private val CartCardColor = Color(0xFF252525)
private val CartStepperColor = Color(0xFF1F1F1F)
private val CheckoutBlue = Color(0xFF5B86D9)
private val ScreenBg = Color(0xFFF3F3F3)
private val DividerColor = Color(0xFF343434)
private val DeliveryFee = 4.90

@Composable
fun CartRoute(
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CartScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onCheckoutClick = onCheckoutClick,
        onIncreaseQuantity = { item ->
            viewModel.onUpdateQuantity(item.id, item.qty + 1)
        },
        onDecreaseQuantity = { item ->
            if (item.qty > 1) {
                viewModel.onUpdateQuantity(item.id, item.qty - 1)
            } else {
                viewModel.onRemove(item.id)
            }
        },
        modifier = modifier
    )
}

@Composable
fun CartScreen(
    uiState: CartUiState,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val subtotal = uiState.totalPrice
    val deliveryFee = if (uiState.cartItems.isEmpty()) 0.0 else DeliveryFee
    val total = subtotal + deliveryFee

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBg)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        if (uiState.cartItems.isEmpty()) {
            EmptyCartContent(
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CartCardColor,
                shape = RoundedCornerShape(24.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    userScrollEnabled = true
                ) {
                    itemsIndexed(
                        items = uiState.cartItems,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        CartItemRow(
                            item = item,
                            onIncreaseQuantity = { onIncreaseQuantity(item) },
                            onDecreaseQuantity = { onDecreaseQuantity(item) }
                        )

                        if (index < uiState.cartItems.lastIndex) {
                            Divider(
                                modifier = Modifier.padding(horizontal = 14.dp),
                                color = DividerColor,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CartCardColor,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PriceRow(
                        label = "Subtotal:",
                        value = formatPrice(subtotal)
                    )
                    PriceRow(
                        label = "Delivery Fee:",
                        value = formatPrice(deliveryFee)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF454545)
                    )
                    Text(
                        text = formatPrice(total),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = onCheckoutClick,
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1.25f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CheckoutBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CartItemImage(item = item)

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatPrice(item.price),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }

        QuantityStepper(
            quantity = item.qty,
            onIncrease = onIncreaseQuantity,
            onDecrease = onDecreaseQuantity
        )
    }
}

@Composable
private fun CartItemImage(
    item: CartItem,
    modifier: Modifier = Modifier
) {
    val imageUrl = try {
        item.imageUrl
    } catch (_: Exception) {
        null
    }

    if (!imageUrl.isNullOrBlank()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = item.name,
            modifier = modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF0F0F0))
        )
    }
}

@Composable
private fun QuantityStepper(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleStepButton(
            icon = Icons.Outlined.Remove,
            contentDescription = "Decrease quantity",
            onClick = onDecrease
        )

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 10.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

        CircleStepButton(
            icon = Icons.Outlined.Add,
            contentDescription = "Increase quantity",
            onClick = onIncrease
        )
    }
}

@Composable
private fun CircleStepButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = CartStepperColor,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.size(24.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
private fun PriceRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun EmptyCartContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add some gear from the shop first.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onBackClick,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Back to shop")
        }
    }
}

private fun formatPrice(value: Double): String {
    return "€" + String.format("%.2f", value)
}