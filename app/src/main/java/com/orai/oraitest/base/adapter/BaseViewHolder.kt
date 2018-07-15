package com.orai.oraitest.base.adapter

import android.view.View

interface BaseViewHolder<ItemType> {
    fun renderView(v: View)
    fun fillView(item: ItemType, position: Int)
}
