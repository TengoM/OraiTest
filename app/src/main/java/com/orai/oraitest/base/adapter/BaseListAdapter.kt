package com.orai.oraitest.base.adapter

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

abstract class BaseListAdapter<T>(protected var layoutInflater: LayoutInflater) : BaseAdapter() {

    @LayoutRes protected abstract fun layoutIdForType(type: Int): Int
    protected abstract fun getNewHolderForType(type: Int): BaseViewHolder<T>

    protected var mList: MutableList<T> = ArrayList()

    open fun addItems(newItems: List<T>, position: Int = -1) {
        val positionInList : Int
        if(position == -1)
            positionInList = mList.size
        else
            positionInList = position

        mList.addAll(positionInList, newItems)
        notifyDataSetChanged()
    }

    fun replaceItems(newItems: List<T>) {
        mList.clear()
        mList.addAll(newItems)
        notifyDataSetChanged()
    }

    val list: List<T>
        get() = mList

    override fun getCount() = mList.size

    override fun getItem(position: Int) = mList[position]

    override fun getItemId(position: Int) = 0L

    open fun getLast(): T? = mList.last()

    override fun getView(position: Int, convertView1: View?, parent: ViewGroup): View {
        var convertView = convertView1
        val holder: BaseViewHolder<T>?
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutIdForType(getItemViewType(position)), parent, false)
            holder = getNewHolderForType(getItemViewType(position))
            holder.renderView(convertView)
            convertView!!.tag = holder
        } else
            holder = convertView.tag as BaseViewHolder<T>

        holder.fillView(getItem(position), position)
        onHolderFilled(holder, position)
        return convertView
    }

    open fun onHolderFilled(holder: BaseViewHolder<T>, position: Int){}


}
