package uz.gita.game2048.mvp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import uz.gita.game2048.R
import uz.gita.game2048.data.enums.SideEnum
import uz.gita.game2048.databinding.ScreenGameBinding
import uz.gita.game2048.mvp.contract.GameContract
import uz.gita.game2048.mvp.presenter.GamePresenter
import uz.gita.game2048.utils.MyTouchListener
import uz.gita.game2048.utils.getBackgroundByValue

class GameScreen : Fragment(R.layout.screen_game), GameContract.View {
    private var _binding: ScreenGameBinding? = null
    private val binding get() = _binding!!

    private val presenter: GameContract.Presenter by lazy { GamePresenter(this) }

    private val buttons: MutableList<TextView> by lazy { ArrayList() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding(binding)
        presenter.initGame()
    }

    private fun subscribeViewBinding(binding: ScreenGameBinding) = with(binding) {
        btnNew.setOnClickListener {
            presenter.onClickRestart()
        }
        btnUndo.setOnClickListener {
            presenter.onClickUndo()
        }
        restartNo.setOnClickListener {
            presenter.onClickDialogRestartNo()
        }
        restartYes.setOnClickListener {
            presenter.onClickDialogRestartYes()
        }
        winRestart.setOnClickListener {
            presenter.onClickDialogWinRestart()
        }
        winHome.setOnClickListener {
            presenter.popBackStack()
        }
        for (i in 0 until mainView.childCount) {
            val line: LinearLayoutCompat = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                buttons.add(line.getChildAt(j) as TextView)
            }
        }
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setResultListener {
            when (it) {
                SideEnum.DOWN -> presenter.swipeSideDown()
                SideEnum.UP -> presenter.swipeSideUp()
                SideEnum.LEFT -> presenter.swipeSideLeft()
                SideEnum.RIGHT -> presenter.swipeSideRight()
            }
        }
        mainView.setOnTouchListener(myTouchListener)
    }

    override fun illustrateMatrix(matrix: Array<Array<Int>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    buttons[i * 4 + j].text = ""
                    buttons[i * 4 + j].setBackgroundResource(matrix[i][j].getBackgroundByValue())
                } else {
                    buttons[i * 4 + j].text = matrix[i][j].toString()
                    buttons[i * 4 + j].setBackgroundResource(matrix[i][j].getBackgroundByValue())
                }
            }
        }
    }

    override fun popBackStack() {
        findNavController().popBackStack()
    }

    override fun swipeUpAnimation(position: Int) {
        YoYo.with(Techniques.FadeInUp).duration(200).playOn(buttons[position])
    }

    override fun swipeDownAnimation(position: Int) {
        YoYo.with(Techniques.FadeInDown).duration(200).playOn(buttons[position])
    }

    override fun swipeLeftAnimation(position: Int) {
        YoYo.with(Techniques.FadeInRight).duration(200).playOn(buttons[position])
    }

    override fun swipeRightAnimation(position: Int) {
        YoYo.with(Techniques.FadeInLeft).duration(200).playOn(buttons[position])
    }

    override fun showRestartDialog(state: Boolean) = with(binding) {
        when (state) {
            true -> dialogRestart.visibility = VISIBLE
            else -> dialogRestart.visibility = GONE
        }
    }

    override fun showGameOverDialog(state: Boolean) = with(binding) {
        when (state) {
            true -> dialogGameOver.visibility = VISIBLE
            else -> dialogGameOver.visibility = GONE
        }
    }

    override fun showWinDialog(state: Boolean) = with(binding) {
        when (state) {
            true -> dialogWin.visibility = VISIBLE
            else -> dialogWin.visibility = GONE
        }
    }

    override fun setEnabledContainerCells(state: Boolean) = with(binding) {
        mainView.isEnabled = state
    }

    override fun setEnabledUndo(state: Boolean) = with(binding) {
        btnUndo.isEnabled = state
    }

    override fun setEnabledRestart(state: Boolean) = with(binding) {
        btnNew.isEnabled = state
    }

    override fun setCurrentScore(currentScore: Int) = with(binding) {
        tvScore.text = getString(R.string.text_current_score, currentScore)
    }

    override fun setBestResult(bestResult: Int) = with(binding) {
        tvBest.text = getString(R.string.text_best_result, bestResult)
    }

    override fun onPause() {
        super.onPause()
        presenter.saveCurrentMatrixState()
        presenter.saveCurrentScore()
    }

}