package org.onlinejudge.uhunt.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_profile.*

import org.onlinejudge.uhunt.R
import org.onlinejudge.uhunt.data.model.Verdict

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
