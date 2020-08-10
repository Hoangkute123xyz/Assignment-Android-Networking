package com.hoangpro.assignmentandroidnetworking.api.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class PhotoData (
    @SerializedName("photos") val photos: Photos,
    @SerializedName("stat") val stat: String
) {
    companion object {
        fun create(json: String): PhotoData {
            val gson = GsonBuilder().create()
            return gson.fromJson(json,  PhotoData::class.java)
        }
    }

    override fun toString(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }

    data class Photos (
        @SerializedName("page") val page: Int,
        @SerializedName("pages") val pages: Int,
        @SerializedName("perpage") val perpage: Int,
        @SerializedName("total") val total: String,
        @SerializedName("photo") val photo: ArrayList<Photo>
    ) {
        companion object {
            fun create(json: String): Photos {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Photos::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Photo (
        @SerializedName("id") val id: String,
        @SerializedName("owner") val owner: String,
        @SerializedName("secret") val secret: String,
        @SerializedName("server") val server: String,
        @SerializedName("farm") val farm: Int,
        @SerializedName("title") val title: String,
        @SerializedName("ispublic") val ispublic: Int,
        @SerializedName("isfriend") val isfriend: Int,
        @SerializedName("isfamily") val isfamily: Int,
        @SerializedName("views") val views: String,
        @SerializedName("date_faved") val dateFaved: String,
        @SerializedName("media") val media: String,
        @SerializedName("media_status") val mediaStatus: String,
        @SerializedName("url_sq") val urlSq: String,
        @SerializedName("height_sq") val heightSq: Int,
        @SerializedName("width_sq") val widthSq: Int,
        @SerializedName("url_t") val urlT: String,
        @SerializedName("height_t") val heightT: Int,
        @SerializedName("width_t") val widthT: Int,
        @SerializedName("url_s") val urlS: String,
        @SerializedName("height_s") val heightS: Int,
        @SerializedName("width_s") val widthS: Int,
        @SerializedName("url_q") val urlQ: String,
        @SerializedName("height_q") val heightQ: Int,
        @SerializedName("width_q") val widthQ: Int,
        @SerializedName("url_m") val urlM: String,
        @SerializedName("height_m") val heightM: Int,
        @SerializedName("width_m") val widthM: Int,
        @SerializedName("url_n") val urlN: String,
        @SerializedName("height_n") val heightN: Int,
        @SerializedName("width_n") val widthN: Int,
        @SerializedName("url_z") val urlZ: String,
        @SerializedName("height_z") val heightZ: Int,
        @SerializedName("width_z") val widthZ: Int,
        @SerializedName("url_c") val urlC: String,
        @SerializedName("height_c") val heightC: Int,
        @SerializedName("width_c") val widthC: Int,
        @SerializedName("url_l") val urlL: String,
        @SerializedName("height_l") val heightL: Int,
        @SerializedName("width_l") val widthL: Int,
        @SerializedName("pathalias") val pathalias: String
    ) {
        companion object {
            fun create(json: String): Photo {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Photo::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }
}
