package org.onlinejudge.uhunt.data.network.model

data class Submission(
        var submissionId: Int = 0,
        var problemId: Int = 0,
        var verdictId: Int = 0,
        var runtime: Int = 0,
        var submissionTime: Int = 0,
        var languageId: Int = 0,
        var submissionRank: Int = 0
)