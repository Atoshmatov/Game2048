package uz.gita.game2048.mvp.presenter

import uz.gita.game2048.mvp.contract.GameContract
import uz.gita.game2048.mvp.models.GameModel

class GamePresenter(private val view: GameContract.View) : GameContract.Presenter {
    private val model: GameContract.Model = GameModel()
    private var currentScore = 0
    private lateinit var matrix: Array<Array<Int>>
    private lateinit var oldMatrix: Array<Array<Int>>

    private fun restartGame() {
        model.fillMatrixForRestart()
        initGame()
    }

    override fun initGame() {
        currentScore = model.getScore()
        view.setCurrentScore(currentScore)
        view.setBestResult(model.getBestResult())
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrix()
        view.illustrateMatrix(matrix)
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

//    override fun startPlay() {
//        view.illustrateMatrix(model.getMatrix())
//    }

    override fun swipeSideLeft() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.moveLeft()

        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrix()
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeLeftAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeSideRight() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.moveRight()
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrix()
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeRightAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeSideDown() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.moveDown()
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrix()
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeDownAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun swipeSideUp() {
        view.setEnabledUndo(true)
        currentScore = model.getScore()
        model.moveUp()
        oldMatrix = model.getOldMatrix()
        matrix = model.getMatrix()
        view.illustrateMatrix(matrix)
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                if (matrix[i][j] != 0 && oldMatrix[i][j] != matrix[i][j]) view.swipeUpAnimation(i * 4 + j)
        view.setCurrentScore(model.getScore())
        view.setBestResult(model.getBestResult())
        if (model.checkForWin()) {
            view.showWinDialog(true)
            view.setEnabledContainerCells(false)
            view.showRestartDialog(false)
            view.setEnabledUndo(false)
            view.setEnabledRestart(false)
            return
        }
        if (model.checkForFinish()) {
            view.setEnabledContainerCells(false)
            view.showGameOverDialog(true)
        }
    }

    override fun onClickRestart() {
        view.setEnabledContainerCells(false)
        view.showGameOverDialog(false)
        view.showWinDialog(false)
        view.setEnabledUndo(false)
        view.showRestartDialog(true)
    }

    override fun onClickUndo() {
        view.setEnabledUndo(false)
        model.saveCurrentScore(currentScore)
        view.setCurrentScore(model.getScore())
        if (model.checkForFinish()) {
            model.fillMatrix()
            view.illustrateMatrix(model.getMatrix())
            view.setEnabledContainerCells(true)
            view.showGameOverDialog(false)
            view.showWinDialog(false)
            return
        }
        model.fillMatrix()
        view.illustrateMatrix(model.getMatrix())
    }

    override fun onClickDialogRestartNo() {
        view.setEnabledContainerCells(true)
        view.showRestartDialog(false)
        view.setEnabledUndo(true)
    }

    override fun onClickDialogRestartYes() {
        model.saveCurrentScore(0)
        model.fillMatrixForRestart()
        view.showRestartDialog(false)
        view.setEnabledContainerCells(true)
        view.setEnabledUndo(true)
        restartGame()
    }

    override fun onClickDialogWinRestart() {
        view.showWinDialog(false)
        view.setEnabledContainerCells(true)
        view.setEnabledUndo(true)
        view.setEnabledRestart(true)
        restartGame()
    }

    override fun saveCurrentMatrixState() = kotlin.run { model.saveCurrentMatrixState() }
    override fun saveCurrentScore() = kotlin.run { model.saveCurrentScore(model.getScore()) }

    override fun popBackStack() {
        view.popBackStack()
    }
}