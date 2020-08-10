package com.hoangpro.assignmentandroidnetworking.api.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class CommentData (
    @SerializedName("comments") val comments: Comments,
    @SerializedName("stat") val stat: String
) {
    companion object {
        fun create(json: String): CommentData {
            val gson = GsonBuilder().create()
            return gson.fromJson(json, CommentData::class.java)
        }
    }

    override fun toString(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }

    data class Comments (
        @SerializedName("photo_id") val photoId: String,
        @SerializedName("comment") val comment: ArrayList<Comment>
    ) {
        companion object {
            fun create(json: String): Comments {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  Comments::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }
    data class Comment (
        @SerializedName("id") val id: String,
        @SerializedName("author") val author: String,
        @SerializedName("author_is_deleted") val authorIsDeleted: Int,
        @SerializedName("authorname") val authorname: String,
        @SerializedName("iconserver") val iconserver: String,
        @SerializedName("iconfarm") val iconfarm: Int,
        @SerializedName("datecreate") val datecreate: String,
        @SerializedName("permalink") val permalink: String,
        @SerializedName("path_alias") val pathAlias: String,
        @SerializedName("realname") val realname: String,
        @SerializedName("_content") val Content: String
    ) {
        companion object {
            fun create(json: String): Comment {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  Comment::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }
}