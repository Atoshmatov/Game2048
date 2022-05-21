package uz.gita.game20481220.mvp.models

import uz.gita.game20481220.data.repository.AppRepository
import uz.gita.game20481220.data.repository.impl.AppRepositoryImpl
import uz.gita.game20481220.mvp.contract.MainContract

class MainModel : MainContract.Model {
    private val repository: AppRepository by lazy { AppRepositoryImpl.getAppRepository() }

    override fun getMatrixData(): Array<Array<Int>> = repository.getCurrentMatrixData()
}