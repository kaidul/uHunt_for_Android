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

package org.onlinejudge.uhunt.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_profile.*
import org.onlinejudge.uhunt.R
import org.onlinejudge.uhunt.data.model.Verdict

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profileViewModel.getCurrentLoggedInUserSubmissions().observe(this, Observer { userSubmission ->
            name_and_username.text = String.format("%s (%s)", userSubmission.username, userSubmission.name)
            val solvedProblemsList = userSubmission.subs
                    ?.filter { it?.get(2) == Verdict.ACCEPTED.verdictId }
                    ?.distinctBy { it?.get(1) }
                    ?.sortedBy { it?.get(1) }
            val solvedProblemsCount = solvedProblemsList?.size
            solved_and_submissions.text = getString(R.string.solved_and_submission_text).format(solvedProblemsCount, userSubmission.subs?.size)
            // TODO ("Show Problem Number Instead Of Problem ID")
            solved_problem_list.text = solvedProblemsList?.joinToString(separator = " ") { it?.get(1).toString() }

            val triedButNotSolvedProblemsList = userSubmission.subs
                    ?.distinctBy { it?.get(1) }
                    ?.sortedBy { it?.get(1) }?.toMutableList()
            if (solvedProblemsList != null && triedButNotSolvedProblemsList != null) {
                for (solvedProblem in solvedProblemsList) {
                    for (triedButNotSolvedProblem in triedButNotSolvedProblemsList) {
                        if (triedButNotSolvedProblem?.get(1) == solvedProblem?.get(1)) {
                            triedButNotSolvedProblemsList.remove(triedButNotSolvedProblem)
                            break
                        }
                    }
                }
            }
            val triedButNotSolvedProblemsCount = triedButNotSolvedProblemsList?.size
            tried_but_failure.text = getString(R.string.tried_but_failed_text).format(triedButNotSolvedProblemsCount)
            // TODO ("Show Problem Number Instead Of Problem ID")
            tried_but_failure_list.text = triedButNotSolvedProblemsList?.joinToString(separator = " ") { it?.get(1).toString() }
        })
        return fragmentView
    }
}
