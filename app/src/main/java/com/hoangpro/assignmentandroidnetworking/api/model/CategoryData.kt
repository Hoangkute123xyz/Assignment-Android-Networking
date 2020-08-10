package com.hoangpro.assignmentandroidnetworking.api.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class CategoryData (
    @SerializedName("galleries") val galleries: Galleries,
    @SerializedName("stat") val stat: String
) {
    companion object {
        fun create(json: String): CategoryData {
            val gson = GsonBuilder().create()
            return gson.fromJson(json, CategoryData::class.java)
        }
    }

    override fun toString(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }

    data class Galleries (
        @SerializedName("total") val total: Int,
        @SerializedName("per_page") val perPage: Int,
        @SerializedName("user_id") val userId: String,
        @SerializedName("continuation") val continuation: Int,
        @SerializedName("gallery") val gallery: ArrayList<Gallery>
    ) {
        companion object {
            fun create(json: String): Galleries {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Galleries::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Gallery (
        @SerializedName("id") val id: String,
        @SerializedName("gallery_id") val galleryId: String,
        @SerializedName("url") val url: String,
        @SerializedName("owner") val owner: String,
        @SerializedName("username") val username: String,
        @SerializedName("iconserver") val iconserver: Int,
        @SerializedName("iconfarm") val iconfarm: Int,
        @SerializedName("primary_photo_id") val primaryPhotoId: String,
        @SerializedName("date_create") val dateCreate: String,
        @SerializedName("date_update") val dateUpdate: String,
        @SerializedName("count_photos") val countPhotos: Int,
        @SerializedName("count_videos") val countVideos: Int,
        @SerializedName("count_total") val countTotal: Int,
        @SerializedName("count_views") val countViews: Int,
        @SerializedName("count_comments") val countComments: Int,
        @SerializedName("title") val title: Title,
        @SerializedName("description") val description: Description,
        @SerializedName("sort_group") val sortGroup: String,
        @SerializedName("cover_photos") val coverPhotos: CoverPhotos,
        @SerializedName("primary_photo_server") val primaryPhotoServer: String,
        @SerializedName("primary_photo_farm") val primaryPhotoFarm: Int,
        @SerializedName("primary_photo_secret") val primaryPhotoSecret: String,
        @SerializedName("primary_photo_extras") val primaryPhotoExtras: PrimaryPhotoExtras
    ) {
        companion object {
            fun create(json: String): Gallery {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Gallery::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }
    data class Title (
        @SerializedName("_content") val Content: String
    ) {
        companion object {
            fun create(json: String): Title {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,Title::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Description (
        @SerializedName("_content") val Content: String
    ) {
        companion object {
            fun create(json: String): Description {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Description::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class CoverPhotos (
        @SerializedName("photo") val photo: List<Photo>
    ) {
        companion object {
            fun create(json: String): CoverPhotos {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  CoverPhotos::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Photo (
        @SerializedName("url") val url: String,
        @SerializedName("width") val width: String,
        @SerializedName("height") val height: String,
        @SerializedName("is_primary") val isPrimary: Int,
        @SerializedName("is_video") val isVideo: Int
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

    data class PrimaryPhotoExtras (
        @SerializedName("o_width") val oWidth: String,
        @SerializedName("o_height") val oHeight: String,
        @SerializedName("dateupload") val dateupload: String,
        @SerializedName("lastupdate") val lastupdate: String,
        @SerializedName("datetaken") val datetaken: String,
        @SerializedName("datetakengranularity") val datetakengranularity: Int,
        @SerializedName("datetakenunknown") val datetakenunknown: Int,
        @SerializedName("ownername") val ownername: String,
        @SerializedName("iconserver") val iconserver: String,
        @SerializedName("iconfarm") val iconfarm: Int,
        @SerializedName("views") val views: String,
        @SerializedName("tags") val tags: String,
        @SerializedName("machine_tags") val machineTags: String,
        @SerializedName("originalsecret") val originalsecret: String,
        @SerializedName("originalformat") val originalformat: String,
        @SerializedName("latitude") val latitude: Int,
        @SerializedName("longitude") val longitude: Int,
        @SerializedName("accuracy") val accuracy: Int,
        @SerializedName("context") val context: Int,
        @SerializedName("media") val media: String,
        @SerializedName("media_status") val mediaStatus: String,
        @SerializedName("url_sq") val urlSq: String,
        @SerializedName("height_sq") val heightSq: Int,
        @SerializedName("width_sq") val widthSq: Int,
        @SerializedName("url_t") val urlT: String,
        @SerializedName("height_t") val heightT: Int,
        @SerializedName("width_t") val widthT: Int,
        @SerializedName("url_s") val urlS: String,
        @SerializedName("height_s") val heightS: String,
        @SerializedName("width_s") val widthS: String,
        @SerializedName("url_m") val urlM: String,
        @SerializedName("height_m") val heightM: String,
        @SerializedName("width_m") val widthM: String,
        @SerializedName("url_o") val urlO: String,
        @SerializedName("height_o") val heightO: String,
        @SerializedName("width_o") val widthO: String,
        @SerializedName("pathalias") val pathalias: String
    ) {
        companion object {
            fun create(json: String): PrimaryPhotoExtras {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, PrimaryPhotoExtras::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }
}
