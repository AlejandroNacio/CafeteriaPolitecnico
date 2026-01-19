package com.example.cafeteriapolitecnico

data class Producto(
    val nombre: String,
    val precio: Double,
    val imagenResId: Int
)

data class Usuario(
    val usuario: String
)

data class ItemPedido(
    val producto: Producto,
    val cantidad: Int
)


val productos = listOf(
    Producto("Bocadillo Jamón", 10.0, R.drawable.jamon),
    Producto("Bocadillo Tortilla", 10.0, R.drawable.tortilla),
    Producto("Bocadillo Mixto", 10.0, R.drawable.mixto),
    Producto("Bocadillo Queso", 10.0, R.drawable.queso)
)
