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
        }catch (e : Throwable){
            query().map { Resource.Error(e.localizedMessage,data)}
        }
    }else{
        query().map { Resource.Success(data) }
    }

    emitAll(flow)
}