package es.travelworld.practica4_poljansa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.travelworld.practica4_poljansa.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


        // Inflar el layout con ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Configurar el ViewPager con el adaptador
        viewPager.adapter = HomePagerAdapter(this)

        // Conectar TabLayout con ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position)) // Asigna iconos a las pestañas
            when (position) {
                0 -> tab.view.setBackgroundResource(R.drawable.tab_background_1) // Azul
                1 -> tab.view.setBackgroundResource(R.drawable.tab_background_2) // Rosa
                2 -> tab.view.setBackgroundResource(R.drawable.tab_background_3) // Lila
                3 -> tab.view.setBackgroundResource(R.drawable.tab_background_4) // Amarillo
            }
        }.attach()


        //Activar el menú en la toolbar
        setSupportActionBar(binding.toolbarHome)

// 2. Manejar el Deep Link si se ha abierto desde una URL
        intent?.data?.let { deepLinkUri ->
            // Aquí obtienes el Deep Link. Si es "myapp://home", lo rediriges al fragmento correcto
            if (deepLinkUri.toString() == "myapp://home") {
                // Aquí, puedes hacer que tu ViewPager se mueva a la página del HomeFragment
                viewPager.setCurrentItem(0) // o el fragmento correspondiente
            }
        }

        // Recuperar los datos enviados desde LoginActivity
        val usuario = intent.getParcelableExtra<Usuario>("usuario", Usuario::class.java) ?: "Usuario desconocido"


    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu) // Inflar el menú con tus íconos
        return true
    }


    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_eurodisney -> {
                // Navegar a la página de Eurodisney
                val eurodisneyIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.disneylandparis.com"))
                startActivity(eurodisneyIntent)
                true
            }
            R.id.menu_car_rental -> {
                // Abrir el fragment de Alquilar un coche
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FrLy, CarRentalFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            0 -> R.drawable.baseline_camera_alt_24
            1 -> R.drawable.baseline_directions_car_24
            2 -> R.drawable.baseline_area_chart_24
            3 -> R.drawable.baseline_face_24
            else -> throw IllegalStateException("Posición desconocida: $position")
        }
    }

}