package com.example.cafeteriapolitecnico

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MyComplexLayout(modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()

    var nombreCliente by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf(0) }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var listaPedidos by remember { mutableStateOf(listOf<ItemPedido>()) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ImagenPoli()
        Spacer(modifier = Modifier.height(20.dp))
        Titulo()
        Spacer(modifier = Modifier.height(20.dp))

        Cliente(usuario = nombreCliente, onValueChange = { nombreCliente = it })

        Spacer(modifier = Modifier.height(20.dp))

        MyDropDownItem(selectedProducto = productoSeleccionado, onProductoSelected = { productoSeleccionado = it })

        Spacer(modifier = Modifier.height(20.dp))

        ComplexContador(cantidad = cantidad, onCantidadChange = { cantidad = it })

        Spacer(modifier = Modifier.height(20.dp))

        AñadirPedido(
            producto = productoSeleccionado,
            cantidad = cantidad,
            onButtonClick = {
                if (productoSeleccionado != null && cantidad > 0) {
                    listaPedidos = listaPedidos + ItemPedido(productoSeleccionado!!, cantidad)
                    productoSeleccionado = null
                    cantidad = 0
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextoPedido(cliente = nombreCliente, cantidad = listaPedidos.size)

        Spacer(modifier = Modifier.height(20.dp))

        listaPedidos.forEach { pedido ->
            PedidoCard(producto = pedido.producto, cantidad = pedido.cantidad)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (listaPedidos.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            BotonConfirmar(
                onClick = {
                    mostrarDialogo = true
                }
            )
        }
        MyDialog(
            mostrar = mostrarDialogo,
            onDismiss = {
                mostrarDialogo = false
            }
        )
    }
}
