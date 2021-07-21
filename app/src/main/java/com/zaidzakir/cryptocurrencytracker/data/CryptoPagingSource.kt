package com.zaidzakir.cryptocurrencytracker.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.remote.CryptoApi
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import retrofit2.HttpException
import java.io.IOException

/**
 *Created by Zaid Zakir
 */
private const val CRYPTO_PAGING_INDEX = 1
class CryptoPagingSource(
    private val lunarCrushApi: CryptoApi
): PagingSource<Int, CoinData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinData> {
        //to trigger api request and turn data into pages
        val position = params.key ?:CRYPTO_PAGING_INDEX
        return try {
            val response = lunarCrushApi.getCoinsMarket(sort = "p", order = true)
//            val response = lunarCrushApi.getCoinsMarket(BuildConfig.API_KEY,"fast",params.loadSize,position)
            val cryptoData = response.body()
            if (response.isSuccessful) {
                LoadResult.Page(
                        data = cryptoData!!.data,
                        prevKey = if (position == CRYPTO_PAGING_INDEX) null else position - 1,
                        nextKey = if (cryptoData.data.isEmpty()) null else position + 1
                )
            } else {
                Log.d("paging error : ", "Something went wrong")
                val throwable = Throwable("error")
                LoadResult.Error(throwable)
            }
        }catch (e : IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CoinData>): Int? {
        //NO-OP
        return null
    }
}