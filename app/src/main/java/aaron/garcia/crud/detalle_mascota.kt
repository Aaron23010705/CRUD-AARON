package aaron.garcia.crud

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_mascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_mascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Poner mismo nombre que se puso en el adaptador

        //Recibir los valores
       val UUIDRECIBIDO = intent.getStringExtra("MascotaUUID")
        val nombreRecibido= intent.getStringExtra("Nombre")
        val edadRecibido = intent.getIntExtra("Peso", 0)
        val pesoRecibido = intent.getIntExtra("Edad", 0)

        //Mando a llamar a todos los elelemntos de la pantalla
        val txtUUIDDetalle = findViewById<TextView>(R.id.UUIDDetalle)
        val txtNombreDetalle = findViewById<TextView>(R.id.NombreDetalle)
        val txtPesoDetalle = findViewById<TextView>(R.id.PesoDetalle)
        val txtEdadDetalle = findViewById<TextView>(R.id.EdadDetalle)

        //Asignarle los datos recibidos a mis textos
//Segundo = primero
        txtUUIDDetalle.text = UUIDRECIBIDO
        txtNombreDetalle.text = nombreRecibido
        txtPesoDetalle.text = pesoRecibido.toString()
        txtEdadDetalle.text = edadRecibido.toString()


    }
}