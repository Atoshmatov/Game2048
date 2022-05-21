package uz.gita.game20481220.data.repository.impl

import uz.gita.game20481220.data.local.MySharedPreferences
import uz.gita.game20481220.data.repository.AppRepository
import kotlin.random.Random

class AppRepositoryImpl private constructor() : AppRepository {

    private val preference by lazy { MySharedPreferences.getInstance() }
    private var canMove: Boolean = false
    private var soundBegin: Boolean = false

    companion object {
        private var obj: AppRepository? = null

        fun init() {
            if (obj != null) return
            obj = AppRepositoryImpl()
        }

        fun getAppRepository(): AppRepository = obj!!
    }

    private val matrix: Array<Array<Int>> =
        arrayOf(
            arrayOf(0, 0, 0, 0), // 0 0 4 4
            arrayOf(0, 0, 0, 0), // 0 0 4 8
            arrayOf(0, 0, 0, 0), // 0 0 0 4
            arrayOf(0, 0, 0, 0)  // 0 0 2 4
        )

    private val oldMatrix: Array<Array<Int>> =
        arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
    private var addElement = 2
    private var isWin = false
    private var currentScore = preference.currentScore

    init {
        when {
            preference.isGameSaved -> {
                initMatrix()
            }
            else -> {
                addNewElementToMatrix()
                addNewElementToMatrix()
                preference.isGameSaved = false
            }
        }
    }

    private fun addNewElementToMatrix() {
        val coordinates = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) {
                    coordinates.add(Pair(i, j))
                }
            }
        }
        if (coordinates.size != 0) {
            val randomIndex = Random.nextInt(0, coordinates.size)
            matrix[coordinates[randomIndex].first][coordinates[randomIndex].second] =
                addElement
        }
    }

    private fun initMatrix() {
        val stringMatrix = preference.matrix?.split("#")
        var index = 0
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                matrix[i][j] = Integer.parseInt(stringMatrix?.get(index++) ?: "0")
            }
        }
    }

    private fun checkOldMatrix(): Boolean {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (oldMatrix[i][j] != 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun fillOldMatrix() {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldMatrix[i][j] = matrix[i][j]
            }
        }
    }

    override fun getCurrentMatrixData(): Array<Array<Int>> = matrix

    override fun getOldMatrix(): Array<Array<Int>> = oldMatrix

    override fun fillMatrixWithOldMatrix() {
        if (checkOldMatrix())
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    matrix[i][j] = oldMatrix[i][j]
                }
            }
    }

    override fun currentScore(): Int = currentScore

    override fun bestResult(): Int {
        return when {
            currentScore > preference.bestResult -> {
                preference.bestResult = currentScore
                currentScore
            }
            else -> preference.bestResult
        }
    }

    override fun checkForFinish(): Boolean {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (j - 1 != -1 && matrix[i][j] == matrix[i][j - 1] && matrix[i][j] != 0) {
                    return false
                }
                if (j + 1 != 4 && matrix[i][j] == matrix[i][j + 1] && matrix[i][j] != 0) {
                    return false
                }
                if (i - 1 != -1 && matrix[i][j] == matrix[i - 1][j] && matrix[i][j] != 0) {
                    return false
                }
                if (i + 1 != 4 && matrix[i][j] == matrix[i + 1][j] && matrix[i][j] != 0) {
                    return false
                }
                if (matrix[i][j] == 0) return false
            }
        }
        if (preference.bestResult < currentScore) {
            preference.bestResult = currentScore
        }
        return true
    }

    override fun checkForWin(): Boolean {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 2048) {
                    if (preference.bestResult < currentScore) {
                        preference.bestResult = currentScore
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun saveCurrentMatrixState() {
        preference.isGameSaved = true
        val stringBuilder: StringBuilder = StringBuilder()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                stringBuilder.append(matrix[i][j]).append("#")
            }
        }
        stringBuilder.deleteAt(stringBuilder.lastIndex)
        preference.matrix = stringBuilder.toString()
    }

    override fun saveCurrentScore(score: Int) {
        preference.currentScore = score
        currentScore = score
    }

    override fun fillMatrixForRestart() {
        preference.isGameSaved = false
        preference.currentScore = 0
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                matrix[i][j] = 0
            }
        }
        currentScore = 0
        addNewElementToMatrix()
        addNewElementToMatrix()
    }

    override fun moveLeft() {
        canMove = false
        fillOldMatrix()
        soundBegin = true
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[i][j])
                    }
                }

                matrix[i][j] = 0
            }
            for (j in 0 until amount.size) {
                matrix[i][j] = amount[j]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }
    }

    override fun moveRight() {
        canMove = false
        fillOldMatrix()
        soundBegin = true

        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[i][j])
                else {
                    if (amount.last() == matrix[i][j] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[i][j])
                    }
                }

                matrix[i][j] = 0
            }
            for (k in 0 until amount.size) {
                matrix[i][3 - k] = amount[k]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }

    }

    override fun moveUp() {
        canMove = false
        fillOldMatrix()
        soundBegin = true

        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in 0 until matrix[i].size) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }

                matrix[j][i] = 0
            }
            for (j in 0 until amount.size) {
                matrix[j][i] = amount[j]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }
    }

    override fun moveDown() {
        canMove = false
        fillOldMatrix()
        soundBegin = true

        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        canMove = true
                        currentScore += amount.last() * 2
                        amount[amount.size - 1] = amount.last() * 2
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }

                matrix[j][i] = 0
            }
            for (k in 0 until amount.size) {
                matrix[3 - k][i] = amount[k]
            }
        }
        if (!checkForFinish() && !isWin) {
            addNewElementToMatrix()
        }
    }
}