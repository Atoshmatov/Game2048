package uz.gita.game2048.mvp.models

import uz.gita.game2048.data.repository.AppRepository
import uz.gita.game2048.data.repository.impl.AppRepositoryImpl
import uz.gita.game2048.mvp.contract.GameContract

class GameModel : GameContract.Model {

    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override fun getMatrix(): Array<Array<Int>> {
        return repository.getCurrentMatrixData()
    }

    override fun getOldMatrix(): Array<Array<Int>>  = repository.getOldMatrix()

    override fun moveLeft() {
        repository.moveLeft()
    }

    override fun moveRight() {
        repository.moveRight()
    }

    override fun moveUp() {
        repository.moveUp()
    }

    override fun moveDown() {
        repository.moveDown()
    }

    override fun getScore(): Int  = repository.currentScore()

    override fun getBestResult(): Int  =repository.bestResult()

    override fun fillMatrix() = kotlin.run { repository.fillMatrixWithOldMatrix() }

    override fun fillMatrixForRestart() = kotlin.run { repository.fillMatrixForRestart() }

    override fun saveCurrentMatrixState() = kotlin.run { repository.saveCurrentMatrixState() }

    override fun checkForFinish(): Boolean = repository.checkForFinish()

    override fun checkForWin(): Boolean = repository.checkForWin()

    override fun saveCurrentScore(score: Int) = kotlin.run { repository.saveCurrentScore(score) }
}

