package uz.gita.game20481220.mvp.presenter

import uz.gita.game20481220.mvp.contract.MainContract
import uz.gita.game20481220.mvp.models.MainModel

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    private val model: MainContract.Model by lazy { MainModel() }

    override fun onClickPlay() {
        view.navigateToGameScreen()
    }

    override fun onClickAbout() {
        view.popUpAboutDialog()
    }
}