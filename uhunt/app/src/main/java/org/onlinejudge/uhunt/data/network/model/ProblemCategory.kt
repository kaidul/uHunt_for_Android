package org.onlinejudge.uhunt.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProblemCategory(

	@field:SerializedName("arr")
	val arr: ArrayList<ProblemSubCategory?>? = null,

	@field:SerializedName("title")
	val title: String? = null
): Serializable