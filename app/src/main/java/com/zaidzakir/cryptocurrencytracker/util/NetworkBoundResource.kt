package com.zaidzakir.cryptocurrencytracker.util

import kotlinx.coroutines.flow.*

/**
 *Created by Zaid Zakir
 */
inline fun <RequestType,ResultType> NetworkBoundResource(
    crossinline query :()->Flow<ResultType>,
    crossinline fetch : suspend() -> RequestType,
    crossinline saveFetchResult : suspend (RequestType) ->Unit,
    crossinline shouldFetch : (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)){
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(data) }
        }catch (e : Exception){
            query().map { Resource.Error<String>("Something went wrong! $e")}
        }
    }else{
        query().map { Resource.Success(data) }
    }

    emitAll(flow)
}