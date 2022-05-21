package uz.gita.game20481220.mvp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.gita.game20481220.R
import uz.gita.game20481220.databinding.ScreenMainBinding
import uz.gita.game20481220.mvp.contract.MainContract
import uz.gita.game20481220.mvp.presenter.MainPresenter

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
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        subscribeViewBinding()
    }

    private fun subscribeViewBinding() = with(binding) {
        cardPlay.setOnClickListener {
            presenter.onClickPlay()
        }
        cardAbout.setOnClickListener {
            presenter.onClickAbout()
        }
        exit.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun navigateToGameScreen() {
        findNavController().navigate(R.id.action_mainScreen_to_gameScreen)
    }

    override fun popUpAboutDialog() = with(binding) {
        aboutView.visibility = VISIBLE
        home.setOnClickListener {
            aboutView.visibility = GONE
        }
    }

    override fun showAboutDialog() {
        presenter.onClickAbout()
    }



}