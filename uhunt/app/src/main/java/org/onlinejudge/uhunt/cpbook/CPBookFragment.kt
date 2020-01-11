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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_cpbook.*

import org.onlinejudge.uhunt.R

class CPBookFragment : Fragment() {

    companion object {
        fun newInstance() = CPBookFragment()
    }

    private lateinit var viewModel: CPBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CPBookViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cpbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabsViewPager.adapter = ProblemsCategoryStateAdapter(this)

        mainCategoriesTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        TabLayoutMediator(mainCategoriesTabLayout, tabsViewPager) { tab, position ->
            tab.icon = when(position) {
                0 -> ContextCompat.getDrawable(requireContext(), R.drawable.basics_icon)
                1 -> ContextCompat.getDrawable(requireContext(), R.drawable.intermediate_icon)
                else -> ContextCompat.getDrawable(requireContext(), R.drawable.advanced_icon)
            }
        }.attach()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getCPBookProblems().observe(viewLifecycleOwner, Observer { problemCategoryList ->
            Log.d("Tesssssst", problemCategoryList[0].title)
        })
    }

}
