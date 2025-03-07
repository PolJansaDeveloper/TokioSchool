package es.travelworld.practica4_poljansa


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePagerAdapter.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun getItemCount(): Int = 4 // Tenemos 4 pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment1()
            1 -> HomeFragment2()
            2 -> HomeFragment3()
            3 -> HomeFragment4()
            else -> throw IllegalStateException("Posición desconocida: $position")
        }


    }
}