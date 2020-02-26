package io.github.luteoos.currencycalc.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

class GsonProvider {
    fun getDefaultGson() =
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .addSerializationExclusionStrategy(object : ExclusionStrategy{
                override fun shouldSkipClass(clazz: Class<*>?) = false

                override fun shouldSkipField(fieldAttr: FieldAttributes?): Boolean {
                   fieldAttr?.getAnnotation(Expose::class.java).let { expose ->
                       return expose != null && !expose.serialize
                   }
                }
            })
            .addDeserializationExclusionStrategy(object : ExclusionStrategy{
                override fun shouldSkipClass(clazz: Class<*>?) = false

                override fun shouldSkipField(fieldAttr: FieldAttributes?): Boolean {
                    fieldAttr?.getAnnotation(Expose::class.java).let { expose ->
                        return expose != null && !expose.deserialize
                    }
                }
            })
            .create()
}