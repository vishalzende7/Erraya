package com.notocia.erraya.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.notocia.erraya.R
import com.notocia.erraya.models.BasicInfo
import com.notocia.erraya.uicomponents.InfoWidget
import com.notocia.erraya.utils.InfoWidgetUtils

class FragmentViewProfile:Fragment() {

    private lateinit var basicInfoWidget: InfoWidget
    private lateinit var interestInfoWidget:InfoWidget
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_view_profile,container,false)
        basicInfoWidget = v.findViewById(R.id.basicInfoWidget)
        interestInfoWidget = v.findViewById(R.id.interestInfoWidget)
        prepInfo()
        return v
    }

    private fun prepInfo() {
        val bInfoList = ArrayList<BasicInfo>()

        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(0), "5'11 feet", 0))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(1), "Active", 1))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(2), "Postgraduate Degree", 2))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(3), "Sagittarius", 3))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(4), "Hinduism", 4))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(5), "Socially", 5))
        bInfoList.add(BasicInfo(InfoWidgetUtils.getBasicInfoIcon(6), "Regularly", 6))

        basicInfoWidget.addAllWidget(bInfoList)

        val iInfoList = ArrayList<BasicInfo>()

        iInfoList.add(BasicInfo(InfoWidgetUtils.getIIIcon(0), "Swimming", 0))
        iInfoList.add(BasicInfo(InfoWidgetUtils.getIIIcon(1), "Reading", 1))
        iInfoList.add(BasicInfo(InfoWidgetUtils.getIIIcon(2), "Cricket", 2))

        interestInfoWidget.addAllWidgetWithoutIcon(iInfoList)
//
//        aboutInfoWidget.addPlainText(
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ac sit mauris risus auctor urna, ut sodales molestie maecenas. Quis erat elementum scelerisque mauris. \n" +
//                    "Quisque rutrum proin sed in mi dui lectus vulputate id. Nulla adipiscing vel convallis cras a. Quisque faucibus nec porta sit."
//        )
    }
}