package cn.ccwb.lib_net.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.NullPointerException
import java.lang.reflect.Type

class GsonConverterFactory private constructor(gson: Gson) : Converter.Factory() {


    private var gson: Gson = gson

    companion object {
        @JvmOverloads
        fun create(gson: Gson? = Gson()): GsonConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return GsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter: TypeAdapter<*> = this.gson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(gson,adapter)
    }


    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {

        val adapter: TypeAdapter<*> = this.gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter);
    }

}