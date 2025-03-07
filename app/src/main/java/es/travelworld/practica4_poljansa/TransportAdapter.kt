package es.travelworld.practica4_poljansa

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.travelworld.practica4_poljansa.databinding.ItemTransportBinding

class TransportAdapter(private val transportList: List<Transporte>) :
    RecyclerView.Adapter<TransportAdapter.TransportViewHolder>() {

    // Método para crear un nuevo ViewHolder y vincularlo con el layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportViewHolder {
        // Inflar el layout de cada item (CardView)
        val binding = ItemTransportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransportViewHolder(binding) // Retornar el ViewHolder
    }

    // Método para vincular los datos de cada item a su vista correspondiente
    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {
        // Obtener el objeto de Transporte correspondiente a esta posición
        val transporte = transportList[position]
        holder.bind(transporte) // Vincular los datos al ViewHolder
    }

    override fun getItemCount(): Int = transportList.size

    inner class TransportViewHolder(private val binding: ItemTransportBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transport: Transporte) {

            // Asignamos los valores correspondientes del objeto 'Transporte'
            binding.trName.text = transport.nombre // Asignamos el 'nombre' (que es un String) a 'tvTransportName'
            binding.trPrice.text = transport.precio // Asignamos el 'precio' (que es un String) a 'tvPrice'
            binding.trImage.setImageResource(transport.imagen) // Asignamos la imagen desde el recurso 'imagen'

            // Establece el color de fondo de la CardView en función del transporte
            binding.cvTransport.setCardBackgroundColor(
                ContextCompat.getColor(binding.root.context, transport.getColorResId())
            )

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "Seleccionaste: ${transport.nombre}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
