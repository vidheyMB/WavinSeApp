package com.loyaltyworks.wavinseapp.utils.fetchData.db

import androidx.annotation.WorkerThread
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import kotlinx.coroutines.flow.Flow


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao) {

    /**
     *     ////////////////  PRODUCT CODE CRUD  ///////////////////////
     */

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allProducts: Flow<List<ObjCatalogueList>> = wordDao.allProduct()

    val netPoints: Flow<Int> = wordDao.getNetPoints()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllProducts(): List<ObjCatalogueList> {
        return wordDao.getAllProducts()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getProductById(ProductCode:String): ObjCatalogueList {
        return wordDao.getProductById(ProductCode)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertProduct(objCatalogueList: ObjCatalogueList) {
        wordDao.insertProduct(objCatalogueList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun checkAndInsert(ProductCode: String, objCatalogueList: ObjCatalogueList) {
        wordDao.checkAndInsert(ProductCode, objCatalogueList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateProductQuantity(ProductCode:String, quantity:Int, netPoints:Int) {
        wordDao.updateProductQuantity(ProductCode, quantity, netPoints)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteProduct(code: String) {
        return wordDao.deleteProduct(code)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllProduct() {
        return wordDao.deleteAllProduct()
    }


    /**
     *     ////////////////  SCAN CODE CRUD  ///////////////////////
     */
   /* // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<QRCodeSaveRequest>> = wordDao.getScanCodes()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(qrCodeSaveRequest: QRCodeSaveRequest) {
        wordDao.insert(qrCodeSaveRequest)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllScanCodes(): List<QRCodeSaveRequest> {
       return wordDao.getAllScanCodes()
    }*/

/*
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun codeExistCheck(code: String): Long {
        return wordDao.codeExistCheck(code)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun codeCount(): Int {
        return wordDao.codeCount()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteCode(code: String) {
        return wordDao.deleteCode(code)
    }

  @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        return wordDao.deleteAll()
    }
*/


}