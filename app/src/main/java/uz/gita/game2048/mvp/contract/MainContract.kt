package uz.gita.game2048.mvp.contract

interface MainContract {
    interface Model {
        fun getMatrixData(): Array<Array<Int>>
    }

    interface View {
        fun navigateToGameScreen()
        fun popUpAboutDialog()
        fun showAboutDialog()

    }

    interface Presenter {
        fun onClickPlay()
        fun onClickAbout()
    }
}