package com.orai.oraitest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.orai.oraitest.R
import com.orai.oraitest.base.adapter.BaseListAdapter
import com.orai.oraitest.base.adapter.BaseViewHolder
import com.orai.oraitest.helper.DateConverter
import com.orai.oraitest.models.SessionModel

class MainPageAdapter(layoutInflater: LayoutInflater): BaseListAdapter<SessionModel>(layoutInflater){

    override fun layoutIdForType(type: Int) = R.layout.session_list_page

    override fun getNewHolderForType(type: Int): BaseViewHolder<SessionModel> = ViewHolder()

    class ViewHolder: BaseViewHolder<SessionModel>{
        lateinit var uSessionId: TextView
        lateinit var uDate: TextView

        override fun renderView(v: View) {
            uSessionId = v.findViewById(R.id.uSessionId)
            uDate = v.findViewById(R.id.uDate)
        }

        override fun fillView(item: SessionModel, position: Int) {
            uSessionId.text = item.sessionId
            uDate.text = DateConverter.convertTimestampIntoData(item.timestamp)
        }
    }
}