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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item_problem_category.view.*
import org.onlinejudge.uhunt.R
import org.onlinejudge.uhunt.data.network.model.ProblemSubCategory

class ProblemSubCategoryRecyclerAdapter(private val problemSubCategoryList: ArrayList<ProblemSubCategory>):
        RecyclerView.Adapter<ProblemSubCategoryRecyclerAdapter.ItemHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_problem_category, parent, false))
    }

    override fun getItemCount(): Int {
        return problemSubCategoryList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvProblemCategoryTitle.text = problemSubCategoryList[position].title
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProblemCategoryTitle: TextView = itemView.problem_category_title
    }
}