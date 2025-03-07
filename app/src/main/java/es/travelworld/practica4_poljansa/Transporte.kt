package es.travelworld.practica4_poljansa

data class Transporte (
    val nombre: String,
    var precio: String,
    val imagen: Int
)

{
    // Devuelve el color correspondiente según el tipo de transporte
    fun getColorResId(): Int {
        return when (nombre) {
            "Classic Car" -> R.color.ClassicCar
            "Sport Car" -> R.color.SportCar
            "Flying Car" -> R.color.FlyingCar
            "Electric Car" -> R.color.ElectricCar
            "MotorHome" -> R.color.MotorHome
            "Pickup" -> R.color.Pickup
            "Airplane" -> R.color.Airplane
            "Bus" -> R.color.Bus
            else -> {R.color.ClassicCar}
        }
    }
}