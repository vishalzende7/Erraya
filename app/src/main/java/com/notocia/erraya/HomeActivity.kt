package com.notocia.erraya

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.NavHostFragment
import com.notocia.erraya.uicomponents.BottomNav

class HomeActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNav: BottomNav
//    private val animationFadeOut = ValueAnimator.ofFloat(1f, 0f)
//    private val animationFadeIn = ValueAnimator.ofFloat(0f, 1f)
//    private var isShown = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO:Enable bellow code
        //window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_home)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnSelectListener(listener)
//        animationFadeOut.interpolator = LinearInterpolator()
//        animationFadeOut.duration = 500
//        animationFadeOut.addUpdateListener {
//            val alpha = it.animatedValue as Float
//            bottomNav.alpha = alpha
//        }
//        animationFadeOut.doOnStart {
//            isShown = false
//        }
//        animationFadeOut.doOnEnd {0
//            bottomNav.visibility = View.GONE
//        }

//        animationFadeIn.interpolator = LinearInterpolator()
//        animationFadeIn.duration = 500
//        animationFadeIn.addUpdateListener {
//            val alpha = it.animatedValue as Float
//            bottomNav.alpha = alpha
//        }
//        animationFadeIn.doOnStart {
//            bottomNav.visibility = View.VISIBLE
//            isShown = true
//        }
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host) as NavHostFragment
    }

    private val listener = object : BottomNav.NavigationTabSelectListener {
        override fun onSelectTab(index: Int, v: View) {
            val navController = navHostFragment.navController
            when (index) {
                0 -> {
                    //Home
                    navController.navigate(R.id.homeFragment)

                }
                1 -> {
                    //Message
                    navController.navigate(R.id.messageFragment)

                }
                2 -> {
                    //Insights
                    navController.navigate(R.id.insightsFragment)

                }
                3 -> {
                    //Profile
                    navController.navigate(R.id.profileFragment2)

                }
            }
        }
    }

    override fun onBackPressed() {
        try {
            val navController = navHostFragment.navController
            val id = navController.currentDestination?.id
            if (id == R.id.homeFragment || id == R.id.messageFragment || id == R.id.insightsFragment || id == R.id.profileFragment2) {
                finish()
            } else {
                navController.popBackStack()
            }
        } catch (e: Exception) {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNav.invalidateOnStartCycle(bottomNav.getActiveTab())
    }


//    override fun onScrollChange(
//        v: NestedScrollView?,
//        scrollX: Int,
//        scrollY: Int,
//        oldScrollX: Int,
//        oldScrollY: Int
//    ) {
//        if(scrollY > 50 && isShown){
//            if(animationFadeIn.isRunning)
//                animationFadeIn.end()
//            if(animationFadeOut.isRunning)
//                animationFadeOut.end()
//            animationFadeOut.start()
//        }
//
//        if(scrollY < 10 && !isShown){
//            if(animationFadeOut.isRunning)
//                animationFadeOut.end()
//            if(animationFadeIn.isRunning)
//                animationFadeIn.end()
//            animationFadeIn.start()
//        }
//    }
}