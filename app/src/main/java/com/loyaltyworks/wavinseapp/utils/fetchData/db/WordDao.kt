package com.loyaltyworks.wavinseapp.utils.fetchData.db

import androidx.room.*
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    /**
     *     ////////////////  PRODUCT CODE CRUD  ///////////////////////
     */

    @Query("SELECT * FROM Product_table")
    fun allProduct(): Flow<List<ObjCatalogueList>>

    @Query("SELECT * FROM Product_table")
    suspend fun getAllProducts(): List<ObjCatalogueList>

    @Query("SELECT * FROM Product_table WHERE ProductCode=:ProductCode ")
    suspend fun getProductById(ProductCode: String): ObjCatalogueList

    @Query("SELECT SUM(Net_Points) FROM Product_table")
    fun getNetPoints(): Flow<Int>

    @Query("UPDATE Product_table SET NoOfQuantity=:quantity, Net_Points=:netPoints WHERE ProductCode=:ProductCode ")
    suspend fun updateProductQuantity(ProductCode: String, quantity: Int, netPoints: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(objCatalogueList: ObjCatalogueList)

    @Transaction
    suspend fun checkAndInsert(ProductCode: String, objCatalogueList: ObjCatalogueList) {
        // Anything inside this method runs in a single transaction.
        if(getProductById(ProductCode)==null)
            insertProduct(objCatalogueList)
    }

    @Query("DELETE FROM Product_table Where ProductCode =:ProductCode ")
    suspend fun deleteProduct(ProductCode: String)

    @Query("DELETE FROM Product_table")
    suspend fun deleteAllProduct()

    /**
     *     ////////////////  SCAN CODE CRUD  ///////////////////////
     */

   /* @Query("SELECT Code FROM scan_table ORDER BY id Desc")
    fun getScanCodes(): Flow<List<QRCodeSaveRequest>>

    @Query("SELECT Code FROM scan_table ORDER BY id Desc")
    suspend fun getAllScanCodes(): List<QRCodeSaveRequest>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(qrCodeSaveRequest: QRCodeSaveRequest)

    @Query("select Count(Code) from scan_table where Code =:code ")
    suspend fun codeExistCheck(code: String): Long

    @Query("SELECT Count(Code) FROM scan_table")
    suspend fun codeCount(): Int

    @Query("DELETE FROM scan_table")
    suspend fun deleteAll()

    @Query("DELETE FROM scan_table Where Code =:code ")
    suspend fun deleteCode(code: String)*/

}