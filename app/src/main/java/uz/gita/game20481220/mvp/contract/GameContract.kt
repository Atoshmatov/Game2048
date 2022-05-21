package uz.gita.game20481220.mvp.contract

interface GameContract {
    interface Model {
        fun getMatrix(): Array<Array<Int>>
        fun getOldMatrix(): Array<Array<Int>>
        fun moveLeft()
        fun moveRight()
        fun moveUp()
        fun moveDown()
        fun getScore(): Int
        fun getBestResult(): Int
        fun fillMatrix()
        fun fillMatrixForRestart()
        fun saveCurrentMatrixState()
        fun checkForFinish(): Boolean
        fun checkForWin(): Boolean
        fun saveCurrentScore(score: Int)
    }

    interface View {
        fun illustrateMatrix(matrix: Array<Array<Int>>)

        fun setCurrentScore(currentScore: Int)
        fun setBestResult(bestResult: Int)
        fun showRestartDialog(state: Boolean)
        fun showGameOverDialog(state: Boolean)
        fun showWinDialog(state: Boolean)
        fun setEnabledContainerCells(state: Boolean)
        fun setEnabledUndo(state: Boolean)
        fun setEnabledRestart(state: Boolean)
        fun popBackStack()

        fun swipeUpAnimation(position: Int)
        fun swipeDownAnimation(position: Int)
        fun swipeLeftAnimation(position: Int)
        fun swipeRightAnimation(position: Int)
    }

    interface Presenter {
        fun initGame()
//        fun startPlay()
        fun swipeSideLeft()
        fun swipeSideRight()
        fun swipeSideDown()
        fun swipeSideUp()
        fun onClickRestart()
        fun onClickUndo()
        fun onClickDialogRestartNo()
        fun onClickDialogRestartYes()
        fun onClickDialogWinRestart()
        fun saveCurrentMatrixState()
        fun saveCurrentScore()
        fun popBackStack()
    }
}