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

package org.onlinejudge.uhunt.cpbook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_problems_category_tab.*

import org.onlinejudge.uhunt.R
import org.onlinejudge.uhunt.data.network.model.ProblemCategory

class ProblemsCategoryTabFragment : Fragment() {

    companion object {

        private const val ARG_PROBLEMS_CATEGORY = "PROBLEMS_CATEGORY"

        fun newInstance(problemsCategory: ArrayList<ProblemCategory>?): ProblemsCategoryTabFragment {
            val problemsCategoryTabFragment = ProblemsCategoryTabFragment()
            if (problemsCategory != null) {
                val bundle = Bundle()
                bundle.putSerializable(ARG_PROBLEMS_CATEGORY, problemsCategory)
                problemsCategoryTabFragment.arguments = bundle
            }
            return problemsCategoryTabFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments?.containsKey(ARG_PROBLEMS_CATEGORY) == true) {
            val categories: ArrayList<ProblemCategory> = arguments?.getSerializable(ARG_PROBLEMS_CATEGORY) as ArrayList<ProblemCategory>

            category1_title.text = categories[0].title
            category1_sub_items.adapter = ProblemSubCategoryRecyclerAdapter(categories[0].arr)
            category1_sub_items.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            category2_title.text = categories[1].title
            category2_sub_items.adapter = ProblemSubCategoryRecyclerAdapter(categories[1].arr)
            category2_sub_items.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            category3_title.text = categories[2].title
            category3_sub_items.adapter = ProblemSubCategoryRecyclerAdapter(categories[2].arr)
            category3_sub_items.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_problems_category_tab, container, false)
    }
}
