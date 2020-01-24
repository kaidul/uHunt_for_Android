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

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.onlinejudge.uhunt.data.network.model.ProblemCategory

class ProblemsCategoryStateAdapter(fragment: Fragment, private val problemsCategory: ArrayList<ProblemCategory>? = null) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ProblemsCategoryTabFragment.newInstance(ArrayList(problemsCategory?.subList(0, 3)))
            1 -> ProblemsCategoryTabFragment.newInstance(ArrayList(problemsCategory?.subList(3, 6)))
            else -> ProblemsCategoryTabFragment.newInstance(ArrayList(problemsCategory?.subList(6, 9)))
        }
    }
}