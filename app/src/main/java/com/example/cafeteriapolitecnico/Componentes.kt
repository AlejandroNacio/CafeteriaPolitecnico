package com.example.cafeteriapolitecnico

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImagenPoli(modifier: Modifier = Modifier){
    Image(painter = painterResource(R.drawable.poli),
        contentDescription = "Imagen Poli",
        modifier = modifier.size(150.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Titulo(modifier: Modifier = Modifier){
    Text("Cafetería Politécnico",
        textAlign = TextAlign.Center,
        color = Color.Cyan,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold)
}


@Composable
fun Cliente(
    usuario: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = usuario,
        onValueChange = onValueChange,
        label = { Text("Nombre cliente") },
        modifier = Modifier
            .width(300.dp)
            .padding(horizontal = 16.dp)
    )
}


@Composable
fun MyDropDownItem(
    selectedProducto: Producto?,
    onProductoSelected: (Producto) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button(onClick = { expanded = true }) {
            Text(selectedProducto?.nombre ?: "Bocadillo: ")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            productos.forEach { producto ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(producto.imagenResId),
                                contentDescription = producto.nombre,
                                modifier = Modifier.size(70.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(producto.nombre)
                                Text("${producto.precio} €", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    },
                    onClick = {
                        onProductoSelected(producto)
                        expanded = false
                    }
                )
            }
        }
    }
}

////////////////////////////////////////////////////////////////
@Composable
fun TituloCantidad(modifier: Modifier = Modifier){
    Text("Cantidad:",
        textAlign = TextAlign.Center,
        fontSize = 16.sp)
}
@Composable
fun ComplexContador(cantidad: Int, onCantidadChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TituloCantidad()
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(onClick = { if (cantidad > 0) onCantidadChange(cantidad - 1) }) {
                Text("-")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                cantidad.toString(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(onClick = { onCantidadChange(cantidad + 1) }) {
                Text("+")
            }
        }
    }
}
////////////////////////////////////////////////////////////////////////

@Composable
fun AñadirPedido(
    producto: Producto?,
    cantidad: Int,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val total = if (producto != null) producto.precio * cantidad else 0.0

    OutlinedButton(
        onClick = onButtonClick,
        modifier = modifier
    ) {
        Text(text = "Añadir Pedido (${total.toInt()})")
    }
}

@Composable
fun TextoPedido(cliente: String, cantidad: Int, modifier: Modifier = Modifier) {
    val nombreAMostrar = if (cliente.isNotBlank()) cliente else ""

    Text(
        text = "Lista de pedidos ($cantidad) para $nombreAMostrar",
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun PedidoCard(producto: Producto, cantidad: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        // Mantenemos el estilo de tu ejemplo
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Blue),
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color(0xFF7BE0AE)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // 1. Foto del producto
            Image(
                painter = painterResource(id = producto.imagenResId),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 2. Cantidad x Nombre del producto
            Text(
                text = "$cantidad x ${producto.nombre}",
                modifier = Modifier.weight(1f), // Ocupa el espacio central
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // 3. Precio total (Precio unitario * cantidad)
            Text(
                text = "${(producto.precio * cantidad).toInt()}€",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }
}

@Composable
fun BotonConfirmar(onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .width(250.dp)
            .height(50.dp),
        border = BorderStroke(2.dp, Color.Cyan)
    ) {
        Text(
            text = "Finalizar Compra",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MyDialog(
    mostrar: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (mostrar) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(onClick = onDismiss) { Text("Aceptar") }
            },
            dismissButton = {
                Button(onClick = onDismiss) { Text("Cancelar") }
            },
            title = { Text("Confirmación del pedido") },
            text = { Text("Gracias por realizar un pedido en la Cafetería del Politécnico") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.poli),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            },
            shape = RectangleShape
        )
    }
}