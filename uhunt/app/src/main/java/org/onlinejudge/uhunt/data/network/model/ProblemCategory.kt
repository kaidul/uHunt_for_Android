package org.onlinejudge.uhunt.data.network.model

import com.google.gson.annotations.SerializedName

data class ProblemCategory(

	@field:SerializedName("arr")
	val arr: List<ProblemSubCategory?>? = null,

	@field:SerializedName("title")
	val title: String? = null
)