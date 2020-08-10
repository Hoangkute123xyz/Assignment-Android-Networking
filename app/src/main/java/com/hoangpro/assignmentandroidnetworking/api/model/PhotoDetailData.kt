package com.hoangpro.assignmentandroidnetworking.api.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class PhotoDetailData (
    @SerializedName("photo") val photo: Photo,
    @SerializedName("stat") val stat: String
) {
    companion object {
        fun create(json: String): PhotoDetailData {
            val gson = GsonBuilder().create()
            return gson.fromJson(json,  PhotoDetailData::class.java)
        }
    }

    override fun toString(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }


    data class Photo (
        @SerializedName("id") val id: String,
        @SerializedName("secret") val secret: String,
        @SerializedName("server") val server: String,
        @SerializedName("farm") val farm: Int,
        @SerializedName("dateuploaded") val dateuploaded: String,
        @SerializedName("isfavorite") val isfavorite: Int,
        @SerializedName("license") val license: Int,
        @SerializedName("safety_level") val safetyLevel: Int,
        @SerializedName("rotation") val rotation: Int,
        @SerializedName("owner") val owner: Owner,
        @SerializedName("title") val title: Title,
        @SerializedName("description") val description: Description,
        @SerializedName("visibility") val visibility: Visibility,
        @SerializedName("dates") val dates: Dates,
        @SerializedName("views") val views: String,
        @SerializedName("editability") val editability: Editability,
        @SerializedName("publiceditability") val publiceditability: Publiceditability,
        @SerializedName("usage") val usage: Usage,
        @SerializedName("comments") val comments: Comments,
        @SerializedName("notes") val notes: Notes,
        @SerializedName("people") val people: People,
        @SerializedName("tags") val tags: Tags,
        @SerializedName("urls") val urls: Urls,
        @SerializedName("media") val media: String
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

    data class Owner (
        @SerializedName("nsid") val nsid: String,
        @SerializedName("username") val username: String,
        @SerializedName("realname") val realname: String,
        @SerializedName("location") val location: String,
        @SerializedName("iconserver") val iconserver: String,
        @SerializedName("iconfarm") val iconfarm: Int,
        @SerializedName("path_alias") val pathAlias: String
    ) {
        companion object {
            fun create(json: String): Owner {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  Owner::class.java)
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
                return gson.fromJson(json,  Title::class.java)
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
                return gson.fromJson(json,Description::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Visibility (
        @SerializedName("ispublic") val ispublic: Int,
        @SerializedName("isfriend") val isfriend: Int,
        @SerializedName("isfamily") val isfamily: Int
    ) {
        companion object {
            fun create(json: String): Visibility {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Visibility::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Dates (
        @SerializedName("posted") val posted: String,
        @SerializedName("taken") val taken: String,
        @SerializedName("takengranularity") val takengranularity: Int,
        @SerializedName("takenunknown") val takenunknown: Int,
        @SerializedName("lastupdate") val lastupdate: String
    ) {
        companion object {
            fun create(json: String): Dates {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,Dates::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Editability (
        @SerializedName("cancomment") val cancomment: Int,
        @SerializedName("canaddmeta") val canaddmeta: Int
    ) {
        companion object {
            fun create(json: String): Editability {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,Editability::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Publiceditability (
        @SerializedName("cancomment") val cancomment: Int,
        @SerializedName("canaddmeta") val canaddmeta: Int
    ) {
        companion object {
            fun create(json: String): Publiceditability {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Publiceditability::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Usage (
        @SerializedName("candownload") val candownload: Int,
        @SerializedName("canblog") val canblog: Int,
        @SerializedName("canprint") val canprint: Int,
        @SerializedName("canshare") val canshare: Int
    ) {
        companion object {
            fun create(json: String): Usage {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  Usage::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Comments (
        @SerializedName("_content") val Content: Int
    ) {
        companion object {
            fun create(json: String): Comments {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Comments::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Notes (
        @SerializedName("note") val note: List<Any>
    ) {
        companion object {
            fun create(json: String): Notes {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  Notes::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class People (
        @SerializedName("haspeople") val haspeople: Int
    ) {
        companion object {
            fun create(json: String): People {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, People::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Tags (
        @SerializedName("tag") val tag: List<Tag>
    ) {
        companion object {
            fun create(json: String): Tags {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Tags::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Tag (
        @SerializedName("id") val id: String,
        @SerializedName("author") val author: String,
        @SerializedName("authorname") val authorname: String,
        @SerializedName("raw") val raw: String,
        @SerializedName("_content") val Content: String,
        @SerializedName("machine_tag") val machineTag: Int
    ) {
        companion object {
            fun create(json: String): Tag {
                val gson = GsonBuilder().create()
                return gson.fromJson(json,  Tag::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }


    data class Urls (
        @SerializedName("url") val url: List<Url>
    ) {
        companion object {
            fun create(json: String): Urls {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Urls::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }

    data class Url (
        @SerializedName("type") val type: String,
        @SerializedName("_content") val Content: String
    ) {
        companion object {
            fun create(json: String): Url {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, Url::class.java)
            }
        }

        override fun toString(): String {
            val gson = GsonBuilder().create()
            return gson.toJson(this)
        }
    }
}