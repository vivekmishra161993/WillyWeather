package com.app.willyweathertest.network.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "jobs")
data class Jobs(
		@ColumnInfo(name = "created_at")
		@field:SerializedName("created_at")
		val createdAt: String? = null,
		@ColumnInfo(name = "description")
		@field:SerializedName("description")
		val description: String? = null,
		@ColumnInfo(name = "company")
		@field:SerializedName("company")
		val company: String? = null,
		@ColumnInfo(name = "company_url")
		@field:SerializedName("company_url")
		val companyUrl: String? = null,
		@ColumnInfo(name = "location")
		@field:SerializedName("location")
		val location: String? = null,
		@ColumnInfo(name = "id")
		@field:SerializedName("id")
		@PrimaryKey
		@NonNull
		val id: String = "",
		@ColumnInfo(name = "type")
		@field:SerializedName("type")
		val type: String? = null,
		@ColumnInfo(name = "title")
		@field:SerializedName("title")
		val title: String? = null,
		@ColumnInfo(name = "url")
		@field:SerializedName("url")
		val url: String? = null
) : Parcelable
