package es.travelworld.practica4_poljansa

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import es.travelworld.practica4_poljansa.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("NAME_SHADOWING")
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Variable para el binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var nombre: TextInputEditText
    private lateinit var pass: TextInputEditText


    //Creamos las variables donde se almacenara el usuario registrado
    private lateinit var usuarioRegistro: String
    private lateinit var contrasenaRegistro: String

    // Crear un objeto de la clase Usuario
    private var nombreActual: String? = ""
    private var contrasenaActual: String? = ""

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

    ): View {

        // Inflar el layout con ViewBinding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root // Devuelve la vista raíz

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Esconder la toolbar y el linearLayout
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbarRegistro)
        toolbar.visibility = View.GONE

        val layout = requireActivity().findViewById<LinearLayout>(R.id.linearLayout)
        layout.visibility = View.GONE

        //Recuperamos datos del Bundle
        usuarioRegistro = arguments?.getString("extra_usuario") ?: ""
        contrasenaRegistro = arguments?.getString("extra_pass") ?: ""

        nombre = binding.TextInputEditTextUsuario
        pass = binding.TextInputEditTextPassword

        //Añadimos los listeners a los textos
        binding.TextInputEditTextUsuario.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                habilitarBoton()  // Llama a la función para habilitar o deshabilitar el botón
            }

            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // No se necesita hacer nada aquí
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Tampoco se necesita hacer nada aquí
            }
        })

        binding.TextInputEditTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                habilitarBoton()  // Llama a la función para habilitar o deshabilitar el botón
            }

            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // No se necesita hacer nada aquí
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Tampoco se necesita hacer nada aquí
            }
        })

        // Crear el objeto Usuario
        val usuario = Usuario("", "") // Crear una instancia vacía del usuario

        // Asignar el objeto Usuario al binding
        binding.user = usuario


        //Hago que al clicar el boton se recuperen los datos de usuario se actualizen y los muestre en un snackbar
        binding.imageButtonLogin.setOnClickListener() {

            try {
                // Usar 'let' para asegurarse de que 'user' no sea null y acceder a sus propiedades
                binding.user?.let {
                    nombreActual = binding.user?.nombre
                    contrasenaActual = binding.user?.contrasena
                }

                // Verificar si el usuario está registrado
                if (existeRegistro()) {
                    // Crear el Deep Link URI
                    val deepLinkUri = Uri.parse("myapp://home")

                    // Crear el Intent usando el Deep Link
                    val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                        // Asegurarnos de que sea tratado como una nueva tarea
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }

                    // Iniciar la actividad con el Intent
                    startActivity(intent)

                } else {
                    showInvalidCredentialsDialog()
                }

            } catch (e: Exception) {
                println(e.message)
            }

        }

        // Hacer los TextViews clickeables
        binding.getnew.setOnClickListener {
            // Mostrar un Snackbar cuando el TextView 'getnew' sea clickeado
            Snackbar.make(
                it,
                "¡Próximamente se implementarán estas funcionalidades!",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.createnew.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun existeRegistro(): Boolean {
        if (nombreActual == usuarioRegistro && contrasenaActual == contrasenaRegistro && nombreActual != "" && contrasenaActual != "") {
            return true
        } else {
            return false
        }
    }

    private fun habilitarBoton() {
        if (nombre.text?.toString() != "" && pass.text?.toString() != "") {
            binding.imageButtonLogin.isEnabled = true
            binding.imageButtonLogin.animate().alpha(1f).start()
        } else {
            binding.imageButtonLogin.isEnabled = false
            binding.imageButtonLogin.animate().alpha(0.5f).start()
        }
    }

    // Para mostrar el cuadro de diálogo de alerta
    private fun showInvalidCredentialsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Datos incorrectos")
        builder.setMessage("El usuario o la contraseña son incorrectos. Por favor, verifica los datos.")

        // Botón ENTENDIDO
        builder.setPositiveButton("ENTENDIDO") { dialog, _ ->
            // Cerrar la alerta (automático)
            dialog.dismiss()
        }

        // Crear y mostrar la alerta
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}