package com.htnguyen.customeranalysis.interfaces

import com.htnguyen.customeranalysis.model.DataCustomer
import io.reactivex.disposables.Disposable
import okhttp3.MultipartBody
import retrofit2.http.*
import java.util.*

const val BASE_URL = "https://YOUR.BASE.URL/API"
const val LOGIN: String = "login"
const val GET_POST_LIST: String = "posts"
const val GET_POST_DETAIL: String = "posts/{post_id}"
const val ATTACH_POST_DATA: String = "posts/{post_id}/upload"

interface ApiService {
    /*@POST(LOGIN)
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("username") userName: String,
        @Field("password") password: String
    ): Observable<DataCustomer>

    @GET(GET_POST_LIST)
    fun getPosts(
        @Header("Authorization") token: String
    ) : Observable<PostList>

    @GET(GET_POST_DETAIL)
    fun getPostDetail(
        @Header("Authorization") token: String,
        @Path("Post_id") postId: Int
    ) : Observable<PostDetail>

    @Multipart
    @POST(ATTACH_POST_DATA)
    fun uploadPostData(
        @Header("Authorization") token: String,
        @Part("post_id") postId: Int,
        @Part file: MultipartBody.Part
    ): Observable<PostDetail>

    companion object {
        private val apiService by lazy { ApiService.create() }
        private var disposable: Disposable? = null
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }*/
}