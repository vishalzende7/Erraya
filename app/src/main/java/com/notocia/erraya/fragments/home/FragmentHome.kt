package com.notocia.erraya.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.widget.ViewAnimator
import androidx.fragment.app.Fragment
import androidx.navigation.AnimBuilder
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.R
import com.notocia.erraya.adapters.ProfileSwipeAdapter
import com.yuyakaido.android.cardstackview.*

class FragmentHome : Fragment(), CardStackListener {

    private lateinit var buttonPreference: SynthImageButton
    private lateinit var buttonMap: SynthImageButton
    private lateinit var buttonReset: SynthImageButton
    private lateinit var buttonNotification: SynthImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        buttonMap = v.findViewById(R.id.buttonMap)
        buttonPreference = v.findViewById(R.id.buttonPreference)
        buttonReset = v.findViewById(R.id.buttonReset)
        buttonNotification = v.findViewById(R.id.buttonNotification)

        buttonMap.setOnClickListener(listener)
        buttonPreference.setOnClickListener(listener)
        buttonReset.setOnClickListener(listener)
        buttonNotification.setOnClickListener(listener)
        val cardStack:CardStackView = v.findViewById(R.id.profileSwipeView)
        val mList:ArrayList<String> = ArrayList()
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        mList.add("asd")
        val manager = CardStackLayoutManager(context,this)
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        cardStack.layoutManager = manager
        cardStack.adapter = ProfileSwipeAdapter(mList,context,tap)


        return v
    }

    val tap = View.OnClickListener {
        findNavController().navigate(R.id.action_homeFragment_to_fragmentViewProfile)
    }

    val listener = View.OnClickListener {
        val i: String = it.tag as String
        when (i) {
            "0" -> {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentPreferences)
            }
            "1" -> {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentMap)
            }
            "2" -> {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentResetMode)
            }
            "3" -> {
                findNavController().navigate(R.id.action_homeFragment_to_fragmentNotification)
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("MTAG","Card dragged")
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.i("MTAG","Swiped to ${direction?.name}")
    }

    override fun onCardRewound() {
        Log.i("MTAG","Card Rewound")
    }

    override fun onCardCanceled() {
        Log.i("MTAG","Card Canceled")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.i("MATG","Card appeared at pos $position")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.i("MATG","Card Disappeared at pos $position")
    }
}