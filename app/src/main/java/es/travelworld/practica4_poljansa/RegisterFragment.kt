package es.travelworld.practica4_poljansa

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavOptions
import com.google.android.material.snackbar.Snackbar
import es.travelworld.practica4_poljansa.databinding.FragmentRegisterBinding
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Variable para el binding
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    // Registrar un ActivityResultLauncher para la solicitud de permisos
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    // Registrar un ActivityResultLauncher para tomar la foto
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>

    // URI donde se almacenará la foto capturada
    private lateinit var photoUri: Uri

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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root // Devuelve la vista raíz
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hacer que la toolbar y el layout  sean visibles
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbarRegistro)
        toolbar.visibility = View.VISIBLE
        val layout = requireActivity().findViewById<LinearLayout>(R.id.linearLayout)
        layout.visibility = View.VISIBLE


        // Establecer la Toolbar como ActionBar si la actividad es una AppCompatActivity
        if (requireActivity() is AppCompatActivity) {
            (requireActivity() as AppCompatActivity).setSupportActionBar(requireActivity().findViewById(R.id.toolbarRegistro))
        }

        /*// Configurar el Listener para el botón de navegación (por ejemplo, volver atrás)
        requireActivity().findViewById<Toolbar>(R.id.toolbarRegistro).setNavigationOnClickListener {
            // Simular la acción de retroceder o cerrar la actividad
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }*/

        requireActivity().findViewById<Toolbar>(R.id.toolbarRegistro).setNavigationOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


        // Ruta para almacenar la imagen capturada
        val file = File(requireContext().externalCacheDir, "photo.jpg")
        photoUri = FileProvider.getUriForFile(requireActivity(), "${requireContext().packageName}.provider", file)

        // Registrar el ActivityResultLauncher para la cámara
        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // La foto fue tomada, mostrarla en el ImageView
                    binding.foto.setImageURI(photoUri)
                }
            }

        // Registrar el ActivityResultLauncher para solicitar permisos en tiempo de ejecución
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Permiso concedido, abrir la cámara
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    takePictureLauncher.launch(intent)
                } else {
                    // Permiso denegado, mostrar un mensaje
                    Toast.makeText(requireActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }

        // Configurar el ImageView para que abra la cámara al hacer clic
        binding.foto.setOnClickListener {
            if (checkPermissions()) {
                // Crear un Intent para abrir la cámara
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri) // Usar el URI seguro
                takePictureLauncher.launch(intent)
            } else {
                // Solicitar permisos si no han sido concedidos
                requestPermissions()
            }
        }


        // PROCESO PARA LA VALIDACIÓN DE CARACTERES PARA NOMBRE Y APELLIDOS

        // Validar el campo 'nombre' cuando el usuario termine de escribir
        binding.nombre.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateNombre(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se necesita hacer nada aquí
            }
        })

        // Validar el campo 'apellido' cuando el usuario termine de escribir
        binding.apellido.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateApellido(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se necesita hacer nada aquí
            }
        })

        // PROCESO PARA EL FUNCIONAMIENTO DEL SPINNER


        // Lista de opciones para el Spinner
        val options = listOf("¿Cuantos años tienes?","0-5", "6-11", "12-17", "18-99")

        // Crear un ArrayAdapter para llenar el Spinner
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adapter al Spinner
        binding.spinner.adapter = adapter

        // Escuchar el evento de selección del Spinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {

                    // Verificar si se selecciona un valor válido
                    if (position < 4) {

                        // Mostrar el Snackbar con el mensaje de advertencia
                        Snackbar.make(
                            binding.root,
                            "Necesitas ser mayor de edad para usar esta app",
                            Snackbar.LENGTH_LONG
                        ).show()

                    } else {

                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error al procesar el spinner: ${e.message}")
                    e.printStackTrace()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }

        //PROCESO PARA LA HABILITACIÓN DEL BOTÓN

        //Añadimos listeners a los campos nombre y apellido y habilitamos el botón en función de lo que tenemos escrito.
        binding.nombre.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                habilitarBoton()  // Llama a la función para habilitar o deshabilitar el botón
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita hacer nada aquí
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Tampoco se necesita hacer nada aquí
            }
        })

        binding.apellido.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                habilitarBoton()  // Llama a la función para habilitar o deshabilitar el botón
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita hacer nada aquí
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Tampoco se necesita hacer nada aquí
            }
        })



        //PROCESO PARA VINCULAR TEXTO CON LA URL

        // Vinculamos el botón a la URL mediante un Intent
        binding.textViewLink.setOnClickListener {
            // Crear un Intent para abrir el navegador
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://developers.google.com/ml-kit/terms"))
            startActivity(intent)
        }



        //PROCESO PARA RECUPERAR INFO Y MANDARLO AL LOGIN
        binding.imageButtonMeApunto.setOnClickListener(){

            val navController = findNavController()
            val bundle = Bundle().apply {
                putString("extra_usuario", binding.nombre.text.toString())
                putString("extra_pass", binding.apellido.text.toString())
            }
            navController.navigate(R.id.action_registerFragment_to_loginFragment, bundle)

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // Verificar si los permisos necesarios han sido concedidos
    private fun checkPermissions(): Boolean {
        val permissionCamera = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        val permissionStorage =
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return permissionCamera == PackageManager.PERMISSION_GRANTED && permissionStorage == PackageManager.PERMISSION_GRANTED
    }

    // Solicitar permisos en tiempo de ejecución
    private fun requestPermissions() {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // Validar el campo 'nombre'
    private fun validateNombre(s: Editable?): Boolean {
        if (s != null && (s.contains("@") || s.contains("!"))) {
            binding.nombre.error = "Ups, no creo que sea correcto, revísalo"
            return false
        } else {
            binding.nombre.error = null  // Si no hay error, eliminar el mensaje de error
            return true
        }
    }

    // Validar el campo 'nombre'
    private fun validateApellido(s: Editable?): Boolean {
        if (s != null && (s.contains("@") || s.contains("!"))) {
            binding.apellido.error = "Ups, no creo que sea correcto, revísalo"
            return false
        } else {
            binding.apellido.error = null  // Si no hay error, eliminar el mensaje de error
            return true
        }
    }

    private fun habilitarBoton(){
        if(validateNombre(binding.nombre.text) && validateApellido(binding.apellido.text) && this.binding.nombre.length() >= 1 && this.binding.apellido.length() >= 1)
        {
            binding.imageButtonMeApunto.isEnabled = true
            binding.imageButtonMeApunto.animate().alpha(1f).start()
        }
        else{
            binding.imageButtonMeApunto.isEnabled = false
            binding.imageButtonMeApunto.animate().alpha(0.5f).start()
        }
    }
}