package org.onlinejudge.uhunt.data.network.model

import com.google.gson.annotations.SerializedName

data class ProblemSubCategory(

	@field:SerializedName("arr")
	val arr: List<List<String?>?>? = null,

	@field:SerializedName("title")
	val title: String? = null
)