package com.hoangpro.assignmentandroidnetworking.api

import com.hoangpro.assignmentandroidnetworking.api.model.CategoryData
import com.hoangpro.assignmentandroidnetworking.api.model.CommentData
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoData
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoDetailData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAPI{
    @GET("services/rest/?method=flickr.favorites.getList&api_key=25012a3fa236579c9605df858f8df52b&user_id=189610044%40N06&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=20&format=json&nojsoncallback=1")
    fun getFavoritePhotos(@Query("page") page:Int):Call<PhotoData>

    @GET("services/rest/?method=flickr.galleries.getList&api_key=25012a3fa236579c9605df858f8df52b&user_id=189610044%40N06&per_page=20&primary_photo_extras=date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+original_format%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+o_dims%2C+views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_m%2C+url_o&continuation=0&cover_photos=1&format=json&nojsoncallback=1")
    suspend fun getCategory(@Query("page") page: Int):CategoryData

    @GET("services/rest/?method=flickr.galleries.getPhotos&api_key=25012a3fa236579c9605df858f8df52b&continuation=0&per_page=20&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&format=json&nojsoncallback=1")
    suspend fun getPhotoByGallery(@Query("gallery_id") galleryId:String,@Query("page") page: Int):PhotoData

    @GET("services/rest/?method=flickr.photos.getInfo&api_key=25012a3fa236579c9605df858f8df52b&&secret=9f61139e5ccb96f1&format=json&nojsoncallback=1")
    suspend fun getPhotoDetail(@Query("photo_id") photoId:String):PhotoDetailData

    @GET("services/rest/?method=flickr.photos.comments.getList&api_key=25012a3fa236579c9605df858f8df52b&format=json&nojsoncallback=1")
    suspend fun getCommentOfPhoto(@Query("photo_id") commentId:String):CommentData

    @GET("services/rest/?method=flickr.photos.search&api_key=25012a3fa236579c9605df858f8df52b&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&format=json&nojsoncallback=1")
    suspend fun searchPhotos(@Query("text") text:String,@Query("page") page:Int):PhotoData
}