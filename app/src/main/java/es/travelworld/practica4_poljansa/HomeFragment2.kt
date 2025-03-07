package es.travelworld.practica4_poljansa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import es.travelworld.practica4_poljansa.databinding.FragmentHome2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHome2Binding? = null
    private val binding get() = _binding!!
    private lateinit var transportAdapter: TransportAdapter
    private lateinit var transportViewModel: TransportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout con ViewBinding
        _binding = FragmentHome2Binding.inflate(inflater, container, false)


        // Obtener el ViewModel
        transportViewModel = ViewModelProvider(this).get(TransportViewModel::class.java)

        // Configurar RecyclerView
        setupRecyclerView()

        // Observar los cambios en la lista de transportes
        transportViewModel.transportList.observe(viewLifecycleOwner, Observer { transportList ->
            transportAdapter = TransportAdapter(transportList)
            binding.rvTransporte.adapter = transportAdapter
        })

        return binding.root // Devuelve la vista raíz
    }

    //Le diremos al RecyclerView en que posicion queremos que se muestre utilizando el LayoutManager
    private fun setupRecyclerView() {
        // Configurar el RecyclerView
        binding.rvTransporte.layoutManager = LinearLayoutManager(context)
    }

}