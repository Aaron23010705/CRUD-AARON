package RecyclerViewHelper

import aaron.garcia.crud.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    //on createviewholder es el metodo que nos permite enlazar la carta creada con nuestro recycle view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)

        return ViewHolder(vista)
    }

    //Devolver los datos existentes, si tenemos 5 mascotas, devolveria 5 mascotas
    override fun getItemCount() = Datos.size
//Nos ayuda a controlar los clicks que el usuario hace en las cartas, es decir permite al usuario interactuar con la carta
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.nombreMascota
    }

}
