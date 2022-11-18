package com.htnguyen.customeranalysis.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.databinding.ActivityMainBinding
import com.htnguyen.customeranalysis.ultils.FileUtils
import com.htnguyen.customeranalysis.view.fragment.ListCurrentFragment
import com.htnguyen.customeranalysis.view.fragment.ListMarkFragment

class MainActivity : AppCompatActivity() {
    private val titles = arrayOf("Recent", "Pinned")
    private lateinit var binding: ActivityMainBinding
    var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    var layoutBottomSheet: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        openDrawer()
        initView()
        setBottomSheetBehavior()
    }

    private fun openDrawer() {
        val navigationView = binding.navMain

        navigationView.setNavigationItemSelectedListener { true }
        binding.imgMenu.setOnClickListener {
            binding.drawerMain.openDrawer(GravityCompat.START)
        }

        binding.drawerMain.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes

                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                    /*drawerView.findViewById<TextView>(R.id.text_ContactUs).setOnClickListener {
                        askPermissionAndCall()
                    }

                    drawerView.findViewById<TextView>(R.id.txtClockApp).setOnClickListener {
                        val i = Intent(this@MainActivity, LoginPassword::class.java)
                        startActivity(i)
                    }
                    drawerView.findViewById<TextView>(R.id.txtAboutMe).setOnClickListener {
                        //startActivity(getOpenFacebookIntent())
                        openFacebookProfile(this@MainActivity)
                    }

                    drawerView.findViewById<TextView>(R.id.txtChatWithMe).setOnClickListener {
                        startActivity(
                            newFacebookIntent(
                                this@MainActivity.packageManager,
                                "https://www.facebook.com/PhanAnhHaUI"
                            )
                        )
                    }

                    drawerView.findViewById<TextView>(R.id.txtMyProfile).setOnClickListener {
                        startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                    }*/

                    drawerView.findViewById<TextView>(R.id.txtHome).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                    }

                }

                override fun onDrawerClosed(drawerView: View) {
                    // Respond when the drawer is closed
                }

                override fun onDrawerStateChanged(newState: Int) {
                    // Respond when the drawer motion state changes
                }
            }
        )
    }

    private fun initView() {
        binding.viewPager.adapter = MainPagerFragmentAdapter(titles, this)
        binding.viewPager.isUserInputEnabled = false

        setTabLayout()
    }

    private fun setTabLayout() {
        TabLayoutMediator(binding.tablayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    class MainPagerFragmentAdapter(
        private val arrayTitle : Array<String>,
        private val fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return arrayTitle.size
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return ListCurrentFragment()
                1 -> return ListMarkFragment()
            }
            return ListCurrentFragment()
        }
    }

    private fun setBottomSheetBehavior() {
        layoutBottomSheet = binding.layoutBottomSearch.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(layoutBottomSheet!!)
        (bottomSheetBehavior as BottomSheetBehavior<*>).addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        binding.layoutBottomSearch.bottomSheet.setOnClickListener { v ->
            if ((bottomSheetBehavior as BottomSheetBehavior<*>).state != BottomSheetBehavior.STATE_EXPANDED) {
                (bottomSheetBehavior as BottomSheetBehavior<*>).state = BottomSheetBehavior.STATE_EXPANDED
                binding.btnAdd.visibility = View.GONE
            } else {
                (bottomSheetBehavior as BottomSheetBehavior<*>).state = BottomSheetBehavior.STATE_COLLAPSED
                binding.btnAdd.visibility = View.VISIBLE
                binding.layoutBottomSearch.edtSearch.setText("")
                FileUtils.hideKeyboard(this)
            }
        }

        binding.layoutBottomSearch.imgClose.setOnClickListener {
            binding.layoutBottomSearch.edtSearch.setText("")
        }
    }
}