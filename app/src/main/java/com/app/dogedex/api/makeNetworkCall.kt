package com.app.dogedex.api

import com.app.dogedex.R
import com.app.dogedex.utils.UNAUTHORIZE_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (e: UnknownHostException) {
        ApiResponseStatus.Error(R.string.unknown_host_exception_error)
    } catch (e: retrofit2.HttpException){
       val errorMessage = if (e.code() == UNAUTHORIZE_CODE){
           R.string.wrong_user_or_password
       }else{
           R.string.unknown_error
       }
        ApiResponseStatus.Error(errorMessage)
    }
    catch (e: Exception) {

        val errorMessage = when(e.message){
            "sign_up_error" -> R.string.sign_up_error
            "sign_in_error" -> R.string.sign_in_error
            "user_already_exist" -> R.string.user_already_exist
            else -> R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    }
}
