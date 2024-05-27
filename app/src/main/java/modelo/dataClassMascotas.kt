package modelo

data class dataClassMascotas(
    val uuid: String,
    var nombreMascota: String,
    var peso: Int,
    var edad: Int
)
// val es una constante que no cambia
//var es una variable que puede cambiar