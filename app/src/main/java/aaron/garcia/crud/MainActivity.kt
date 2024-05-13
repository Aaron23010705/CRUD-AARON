package aaron.garcia.crud

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassMascotas

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

        //Agrege el rcv mascotas
        val rcvMascotas = findViewById<RecyclerView>(R.id.rcvMascotas)

        //Asignarle un layout al RecyclerView
        rcvMascotas.layoutManager = LinearLayoutManager(this)


  //////////////////////// TODO: Mostrar datos /////////////////////////

  //Crear función para mostrar los datos

        fun obtenerDatos() : List<dataClassMascotas>{
            //1- Creo un objeto de la clase conexión
            val objConexion = ClaseConexion().cadenaConexion()

            //2 - Creo un statement
            //El símbolo de pregunta es pq los datos pueden ser nulos
            val statement = objConexion?.createStatement()
            val result = statement?.executeQuery("select * from tbMascotas")!!

            //en esta variable se añaden TODOS los valores de mascotas
            val mascotas = mutableListOf<dataClassMascotas>()

            //Recorro todos los registros de la base de datos
            //.next() significa que mientras haya un valor después de ese se va a repetir el proceso
            while (result.next()){
                val nombre = result.getString("nombreMascota")
                val mascota = dataClassMascotas(nombre)
                mascotas.add(mascota)
            }
            return mascotas
        }
//Ultimo paso
        //Asignar el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val mascotasBD = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(mascotasBD)
                rcvMascotas.adapter = adapter
            }
        }



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


                //Refresco la lista
                val nuevasMascotas = obtenerDatos()
                withContext(Dispatchers.Main) {
                    (rcvMascotas.adapter as? Adaptador)?.ActualizarLista(nuevasMascotas)

                    //el adaptador es el que escucha y vigila que los datos se esten creando
                }
            }
        }

    }
}