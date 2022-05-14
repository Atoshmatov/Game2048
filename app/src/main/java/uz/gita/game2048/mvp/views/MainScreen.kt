package uz.gita.game2048.mvp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.gita.game2048.R
import uz.gita.game2048.databinding.ScreenMainBinding
import uz.gita.game2048.mvp.contract.MainContract
import uz.gita.game2048.mvp.presenter.MainPresenter
import uz.gita.game2048.utils.getBackgroundByValue

class MainScreen : Fragment(R.layout.screen_main), MainContract.View {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private val presenter: MainContract.Presenter by lazy { MainPresenter(this) }
    private val buttons: MutableList<TextView> by lazy { ArrayList() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding()
    }

    private fun subscribeViewBinding() = with(binding) {
        cardPlay.setOnClickListener {
            presenter.onClickPlay()
        }
    }

    override fun navigateToGameScreen() {
        findNavController().navigate(R.id.action_mainScreen_to_gameScreen)
    }

    override fun popUpAboutDialog() = with(binding) {
        cardAbout.setOnClickListener {
            aboutView.visibility = VISIBLE
        }
    }
}