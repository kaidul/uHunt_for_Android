package org.onlinejudge.uhunt.data.network.model

import com.google.gson.annotations.SerializedName

data class UserSubmission(

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("uname")
        val username: String? = null,

        @field:SerializedName("subs")
        val subs: List<List<Int?>?>? = null
//    Submission values are as the following
//        submissionId
//        problemId
//        verdictId
//        runtime
//        submissionTime
//        languageId
//        submissionRank
)