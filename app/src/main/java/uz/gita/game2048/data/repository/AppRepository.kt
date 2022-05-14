package uz.gita.game2048.data.repository

interface AppRepository {
    fun getCurrentMatrixData(): Array<Array<Int>>
    fun getOldMatrix(): Array<Array<Int>>

    fun fillMatrixWithOldMatrix()
    fun currentScore(): Int
    fun bestResult(): Int
    fun checkForFinish(): Boolean
    fun checkForWin(): Boolean
    fun saveCurrentMatrixState()
    fun saveCurrentScore(score: Int)
    fun fillMatrixForRestart()

    fun moveLeft()
    fun moveRight()
    fun moveUp()
    fun moveDown()
}