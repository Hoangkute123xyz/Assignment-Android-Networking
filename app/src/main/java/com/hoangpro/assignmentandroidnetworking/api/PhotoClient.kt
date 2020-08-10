package com.hoangpro.assignmentandroidnetworking.api

import com.hoangpro.assignmentandroidnetworking.api.model.CategoryData
import com.hoangpro.assignmentandroidnetworking.api.model.CommentData
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoData
import com.hoangpro.assignmentandroidnetworking.api.model.PhotoDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoClient {
    companion object{
        private val API by lazy { create() }
        private fun create():PhotoAPI{
            val retrofit = Retrofit.Builder().baseUrl("https://www.flickr.com/")
                .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            return retrofit.create(PhotoAPI::class.java)
        }

        fun getAllFavoritePhotos(page:Int,onSuccess:(result:PhotoData?)->Unit,onFailure:()->Unit){
            API.getFavoritePhotos(page).enqueue(object : Callback<PhotoData>{
                override fun onFailure(call: Call<PhotoData>, t: Throwable) {
                    onFailure()
                }

                override fun onResponse(call: Call<PhotoData>, response: Response<PhotoData>) {
                    if (response.isSuccessful){
                        onSuccess(response.body())
                    } else {
                        onFailure()
                    }
                }
            })
        }

        suspend fun getCategories(page: Int,onSuccess: (CategoryData?) -> Unit,onFailure: () -> Unit){
            try{
                val response = API.getCategory(page)
                withContext(Dispatchers.Main){
                    onSuccess(response)
                }
            } catch (e:Throwable) {
                withContext(Dispatchers.Main){
                    onFailure()
                }
            }
        }

        suspend fun getPhotosOfGallery(galleryId:String,page: Int,onSuccess: (PhotoData) -> Unit,onFailure: () -> Unit){
            try{
                val result = API.getPhotoByGallery(galleryId,page)
                withContext(Dispatchers.Main){
                    onSuccess(result)
                }
            } catch (e:Throwable){
                withContext(Dispatchers.Main){
                    onFailure()
                }
            }
        }

        suspend fun getPhotoDetail(photoId:String,onSuccess: (PhotoDetailData) -> Unit,onFailure: () -> Unit){
            try{
                val result = API.getPhotoDetail(photoId)
                withContext(Dispatchers.Main){
                    onSuccess(result)
                }
            } catch (e:Throwable){
                withContext(Dispatchers.Main){
                    onFailure()
                }
            }
        }

        suspend fun getCommentOfPhoto(photoId:String,onSuccess: (CommentData) -> Unit,onFailure: () -> Unit){
            try{
                val result = API.getCommentOfPhoto(photoId)
                withContext(Dispatchers.Main){
                    onSuccess(result)
                }
            } catch (e:Throwable){
                withContext(Dispatchers.Main){
                    onFailure()
                }
            }
        }

        suspend fun searchPhoto(text:String,page:Int,onSuccess: (PhotoData) -> Unit,onFailure: () -> Unit){
            try{
                val result = API.searchPhotos(text,page)
                withContext(Dispatchers.Main){
                    onSuccess(result)
                }
            } catch (e:Throwable){
                withContext(Dispatchers.Main){
                    onFailure()
                }
            }
        }
    }
}