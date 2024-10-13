package com.example.nfcproducto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nfcproducto.ui.theme.Category.ContenidoCategoryEditar
import com.example.nfcproducto.ui.theme.Category.ContenidoCategoryEliminar
import com.example.nfcproducto.ui.theme.Category.ContenidoCategoryListado
import com.example.nfcproducto.ui.theme.Product.ContenidoProductoDetalle
import com.example.nfcproducto.ui.theme.Product.ContenidoProductoEliminar
import com.example.nfcproducto.ui.theme.Product.ContenidoProductoFormulario
import com.example.nfcproducto.ui.theme.Product.ContenidoProductosListado
import com.example.nfcproducto.ui.theme.Product.ProductoApiServiceC
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AlmacenApp() {
    val urlBase = "http://10.0.2.2:8000/" // o tu IP si usarás un dispositivo externo
    val retrofit = Retrofit.Builder().baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create()).build()

    // Crear instancias de los servicios API
    val nfcApiService = retrofit.create(NfcApiService::class.java)
    val categoryApiService = retrofit.create(CategoryApiService::class.java)
    val productoApiService = retrofit.create(ProductoApiService::class.java)
    val productoApiServiceC = retrofit.create(ProductoApiServiceC::class.java)

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.padding(top = 40.dp),
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController) },
        content = { paddingValues ->
            Contenido(
                paddingValues,
                navController = navController,
                nfcApiService = nfcApiService,
                categoryApiService = categoryApiService,
                productoApiService = productoApiService,
                productoApiServiceC = productoApiServiceC
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Almacen APP",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFE89348)
        )
    )
}

@Composable
fun BarraInferior(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.LightGray
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "inicio",
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Productos") },
            label = { Text("Productos") },
            selected = navController.currentDestination?.route == "productos",
            onClick = { navController.navigate("productos") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Nfc, contentDescription = "NFC") },
            label = { Text("NFC") },
            selected = navController.currentDestination?.route == "nfc",
            onClick = { navController.navigate("nfc") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Category, contentDescription = "Categorías") },
            label = { Text("Categorías") },
            selected = navController.currentDestination?.route == "categorias",
            onClick = { navController.navigate("categorias") }
        )
    }
}

@Composable
fun Contenido(
    pv: PaddingValues,
    navController: NavHostController,
    productoApiService: ProductoApiService, // Incluye ProductoApiService
    productoApiServiceC: ProductoApiServiceC, // Incluye ProductoApiService
    nfcApiService: NfcApiService, // Incluye NfcApiService
    categoryApiService: CategoryApiService // Incluye CategoryApiService
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(pv)
    ) {
        NavHost(
            navController = navController,
            startDestination = "inicio" // Ruta de inicio
        ) {
            composable("inicio") { ScreenInicio() }

            // Vistas para productos
            composable("productos") {
                ContenidoProductosListado(navController, productoApiServiceC)
            }
            composable("productoNuevo") {
                ContenidoProductoFormulario(navController, productoApiServiceC, 0)
            }
            composable("productoVer/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoProductoDetalle(navController, productoApiService, it.arguments!!.getInt("id"))
            }
            composable("productoEditar/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoProductoFormulario(navController, productoApiServiceC, it.arguments!!.getInt("id"))
            }
            composable("productoDel/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoProductoEliminar(navController, productoApiServiceC, it.arguments!!.getInt("id"))
            }
            // Vistas para categorias
            composable("categorias") {
                ContenidoCategoryListado(navController, categoryApiService)
            }
            composable("categoriaNueva") {
                ContenidoCategoryEditar(navController, categoryApiService, 0)
            }
            composable("categoriaEditar/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoCategoryEditar(navController, categoryApiService, it.arguments!!.getInt("id"))
            }
            composable("categoriaDel/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })
            ) {
                ContenidoCategoryEliminar(navController, categoryApiService, it.arguments!!.getInt("id"))
            }
        }
    }
}

@Composable
fun ScreenInicio() {
    Text(text = "Inicio")
}