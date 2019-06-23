/*
 * uHunt for Android - The most comprehensive Android app for uHunt and Competitive programming
 *   Copyright (C) 2018 Kaidul Islam, Esraa Ibrahim
 *
 *   This file is part of uHunt for Android.
 *
 *   uHunt for Android is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   uHunt for Android is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with uHunt for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

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