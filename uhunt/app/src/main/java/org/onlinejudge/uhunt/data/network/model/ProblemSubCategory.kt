package org.onlinejudge.uhunt.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProblemSubCategory(

	@field:SerializedName("arr")
	val arr: ArrayList<ArrayList<String?>?>? = null,

	@field:SerializedName("title")
	val title: String? = null
): Serializable