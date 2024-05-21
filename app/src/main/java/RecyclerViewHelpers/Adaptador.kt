package RecyclerViewHelper

import aaron.garcia.crud.R
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassMascotas

//El adaptador es la clase que me ayuda a mostrar los datos,
class Adaptador(private var Datos: List<dataClassMascotas>) : RecyclerView.Adapter<ViewHolder>() {

    fun ActualizarLista(nuevoLista: List <dataClassMascotas>){
        Datos = nuevoLista
        notifyDataSetChanged()//Esto mnotifica al RecyclerView que hay datos nuevos
    }

    ////////////////// TODO: Eliminar datos

    //Lo que pasa aq
    fun eliminarDatos (nombreMascotas:String, posicion: Int) {
        //Actualizo la lista de datos y notifico al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO) {
            // creamos un objeto de la clase conexion

            val objConexion = ClaseConexion().cadenaConexion()

            // 2- Crear una variable que contenga un preparestatement (donde se mete el código de sqlserver
            val deleteMascota = objConexion?.prepareStatement( "delete from tbMascotas where nombreMascota = ?")!!
            deleteMascota.setString(1, nombreMascotas)
            deleteMascota.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
         Datos = listaDatos.toList()
        //Notificar el adaptador sobre los cambios
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    //on createviewholder es el metodo que nos permite enlazar la carta creada con nuestro recycle view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)

        return ViewHolder(vista)
    }

    //Devolver los datos existentes, si tenemos 5 mascotas, devolveria 5 mascotas     b
    override fun getItemCount() = Datos.size


//Nos ayuda a controlar los clicks que el usuario hace en las cartas, es decir permite al usuario interactuar con la carta
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val mascota = Datos[position]
    holder.textView.text = mascota.nombreMascota


holder.imgEditar.setOnClickListener {
    val context = holder.itemView.context

    val builder = AlertDialog.Builder(context)
    builder.setTitle("Editar")
    builder.setMessage("Estas seguro que quieres editar?")


    builder.setPositiveButton("Si") { dialog, which ->
        dialog.dismiss()
    }
    builder.setNegativeButton("no") { dialog, which ->
        dialog.dismiss()
    }

    val dialog = builder.create()
    dialog.show()
}
    //todo: click al icono de eliminar
    holder.imgBorrar.setOnClickListener {

        val context = holder.itemView.context

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Elimnar")
        builder.setMessage("¿Está seguro que quiere eliminar la mascota?")

        //Botones
        builder.setPositiveButton("Si") { dialog, which ->
            eliminarDatos(mascota.nombreMascota, position)
        }
            builder.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()


        }

    }
    }


