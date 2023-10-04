package com.example.blogexplorer.Retrofit

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BlogInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("x-hasura-admin-secret","32qR4KmXOIpsGPQKMqEJHGJS27G5s7HdSKO3gdtQd2kv5e852SiYwWNfxkZOBuQ6")
        return chain.proceed(request.build())
    }

}