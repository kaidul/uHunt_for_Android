package org.onlinejudge.uhunt.data.model

enum class Verdict(val verdictId: Int) {
    SUBMISSION_ERROR(10),
    CAN_NOT_BE_JUDGED(15),
    IN_QUEUE(20),
    COMPILE_ERROR(30),
    RESTRICTED_FUNCTION(35),
    RUNTIME_ERROR(40),
    OUTPUT_LIMIT(45),
    TIME_LIMIT(50),
    MEMORY_LIMIT(60),
    WRONG_ANSWER(70),
    PRESENTATION_ERROR(80),
    ACCEPTED(90)
}