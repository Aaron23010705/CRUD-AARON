package aaron.garcia.crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //1 Mandar a llamar todos los elementos

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtEdad = findViewById<EditText>(R.id.txtEdad)
        val txtPeso= findViewById<EditText>(R.id.txtPeso)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        //2 Programar el botón para agregar
        btnAgregar.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                //1- Creo un objeto de la clase conexión dentro de la cortina
                val objConexion = ClaseConexion().cadenaConexion()

                //2-Creo una variable que contenga un preparestatement
                val addMascota = objConexion?.prepareStatement("Insert into tbMascotas values (?,?,?)")!!
                addMascota.setString(1 , txtNombre.text.toString())
                addMascota.setInt(2, txtPeso.text.toString().toInt())
                addMascota.setInt(3, txtEdad.text.toString().toInt())
                addMascota.executeUpdate()

            }
        }

    }
}