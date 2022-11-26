package com.htnguyen.customeranalysis.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.adapter.LoginPasswordExpandableAdapter
import com.htnguyen.customeranalysis.database.CustomerRoomDatabaseClass
import com.htnguyen.customeranalysis.databinding.ActivityMainBinding
import com.htnguyen.customeranalysis.model.DataCustomer
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.ultils.Constants.FACEBOOK_PAGE_ID
import com.htnguyen.customeranalysis.ultils.Constants.FACEBOOK_URL
import com.htnguyen.customeranalysis.ultils.FileUtils
import com.htnguyen.customeranalysis.view.activity.AddDataActivity
import com.htnguyen.customeranalysis.view.activity.LanguageActivity
import com.htnguyen.customeranalysis.view.activity.TrashActivity
import com.htnguyen.customeranalysis.view.component.BackgroundDialog
import com.htnguyen.customeranalysis.view.fragment.ListCurrentFragment
import com.htnguyen.customeranalysis.view.fragment.ListMarkFragment
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val titles = arrayOf("Recent", "Pinned")
    private lateinit var binding: ActivityMainBinding
    var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    var layoutBottomSheet: LinearLayout? = null

    private lateinit var loginPassAdapter: LoginPasswordExpandableAdapter
    private lateinit var groupList: Array<String>
    private lateinit var childList: Array<String>

    private val customerDatabase by lazy {
        CustomerRoomDatabaseClass.getDataBase(this).customerDao()
    }

    private val newCustomerResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id =
                    result.data?.getLongExtra(Constants.CUSTOMER_ID, System.currentTimeMillis())
                val nameCustomer = result.data?.getStringExtra(Constants.CUSTOMER_NAME)
                val gender = result.data?.getBooleanExtra(Constants.CUSTOMER_GENDER, false)
                val married = result.data?.getBooleanExtra(Constants.CUSTOMER_MARRIED, false)
                val dependent = result.data?.getBooleanExtra(Constants.CUSTOMER_DEPENDENT, false)
                val education = result.data?.getBooleanExtra(Constants.CUSTOMER_EDUCATION, false)
                val employed = result.data?.getBooleanExtra(Constants.CUSTOMER_EMPLOYED, false)


                val creditHistory = result.data?.getFloatExtra(Constants.CUSTOMER_CREDIT, 0F)
                val propertyArea = result.data?.getIntExtra(Constants.CUSTOMER_PROPERTY, 0)
                val totalIncome = result.data?.getFloatExtra(Constants.CUSTOMER_TOTAL_INCOME, 0F)
                val incomeLog = result.data?.getFloatExtra(Constants.CUSTOMER_INCOME_LOG, 0F)
                val emi = result.data?.getFloatExtra(Constants.CUSTOMER_EMI, 0F)
                val balanceIncome =
                    result.data?.getFloatExtra(Constants.CUSTOMER_BALANCE_INCOME, 0F)

                val result = result.data?.getIntExtra(Constants.CUSTOMER_RESULT, 0)

                val newCustomer = DataCustomer(
                    id,
                    nameCustomer,
                    gender,
                    married,
                    dependent,
                    education,
                    employed,
                    creditHistory,
                    propertyArea,
                    totalIncome,
                    incomeLog,
                    emi,
                    balanceIncome,
                    result
                )
                lifecycleScope.launch {
                    customerDatabase.addCustomer(newCustomer)
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        groupList = resources.getStringArray(R.array.string_sub)
        childList = resources.getStringArray(R.array.string_list)
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

        loginPassAdapter = LoginPasswordExpandableAdapter(this, groupList, childList)

        binding.drawerMain.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes

                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                    /*drawerView.findViewById<TextView>(R.id.txtClockApp).setOnClickListener {
                        val i = Intent(this@MainActivity, LoginPassword::class.java)
                        startActivity(i)
                    }



                    drawerView.findViewById<TextView>(R.id.txtMyProfile).setOnClickListener {
                        startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                    }*/

                    drawerView.findViewById<ExpandableListView>(R.id.expandableListView)
                        .setAdapter(loginPassAdapter)

                    drawerView.findViewById<ExpandableListView>(R.id.expandableListView)
                        .setOnGroupClickListener { expandableListView, view, i, l ->
                            setListViewHeight(expandableListView, i)
                            false
                        }

                    drawerView.findViewById<TextView>(R.id.txtLanguage).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                        startActivity(Intent(this@MainActivity, LanguageActivity::class.java))
                    }

                    drawerView.findViewById<TextView>(R.id.txtTrash).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                        startActivity(Intent(this@MainActivity, TrashActivity::class.java))
                    }

                    drawerView.findViewById<TextView>(R.id.txtBackground).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                        val backgroundColorDialog = BackgroundDialog()
                        backgroundColorDialog.show(
                            supportFragmentManager,
                            backgroundColorDialog.tag
                        )

                    }

                    drawerView.findViewById<TextView>(R.id.text_ContactUs).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                        askPermissionAndCall()
                    }

                    drawerView.findViewById<TextView>(R.id.txtAboutMe).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                        openFacebookProfile(this@MainActivity)
                    }

                    drawerView.findViewById<TextView>(R.id.txtChatWithMe).setOnClickListener {
                        binding.drawerMain.closeDrawers()
                        startActivity(
                            newFacebookIntent(
                                this@MainActivity.packageManager,
                                "https://www.facebook.com/PhanAnhHaUI"
                            )
                        )
                    }

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
        binding.btnAdd.setOnClickListener {
            val i = Intent(this@MainActivity, AddDataActivity::class.java)
            newCustomerResultLauncher.launch(i)
        }
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
        private val arrayTitle: Array<String>,
        private val fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity) {
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
        (bottomSheetBehavior as BottomSheetBehavior<*>).addBottomSheetCallback(object :
            BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        binding.layoutBottomSearch.bottomSheet.setOnClickListener { v ->
            if ((bottomSheetBehavior as BottomSheetBehavior<*>).state != BottomSheetBehavior.STATE_EXPANDED) {
                (bottomSheetBehavior as BottomSheetBehavior<*>).state =
                    BottomSheetBehavior.STATE_EXPANDED
                binding.btnAdd.visibility = View.GONE
            } else {
                (bottomSheetBehavior as BottomSheetBehavior<*>).state =
                    BottomSheetBehavior.STATE_COLLAPSED
                binding.btnAdd.visibility = View.VISIBLE
                binding.layoutBottomSearch.edtSearch.setText("")
                FileUtils.hideKeyboard(this)
            }
        }

        binding.layoutBottomSearch.imgClose.setOnClickListener {
            binding.layoutBottomSearch.edtSearch.setText("")
        }
    }

    private fun askPermissionAndCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val sendSmsPermisson = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.CALL_PHONE),
                    Constants.MY_PERMISSION_REQUEST_CODE_CALL_PHONE
                )
                return
            }
        }
        callNow()
    }

    @SuppressLint("MissingPermission")
    private fun callNow() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:0393397641")
        try {
            this.startActivity(callIntent)
        } catch (ex: Exception) {
            Toast.makeText(
                applicationContext, "Your call failed... " + ex.message,
                Toast.LENGTH_LONG
            ).show()
            ex.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.MY_PERMISSION_REQUEST_CODE_CALL_PHONE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show()
                    callNow()
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.MY_PERMISSION_REQUEST_CODE_CALL_PHONE) {
            when (resultCode) {
                RESULT_OK -> {
                    // Do something with data (Result returned).
                    Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show()
                }
                RESULT_CANCELED -> {
                    Toast.makeText(this, "Action Cancelled", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getFacebookPageURL(context: Context): String? {
        val packageManager: PackageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$FACEBOOK_URL"
            } else { //older versions of fb app
                "fb://page/$FACEBOOK_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            FACEBOOK_URL //normal web url
        }
    }

    private fun newFacebookIntent(pm: PackageManager, url: String): Intent {
        var uri = Uri.parse(url)
        try {
            val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return Intent(Intent.ACTION_VIEW, uri)
    }

    private fun openFacebookProfile(activity: Activity) {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl: String = getFacebookPageURL(activity).toString()
        facebookIntent.data = Uri.parse(facebookUrl)
        activity.startActivity(facebookIntent)
    }

    private fun setListViewHeight(
        listView: ExpandableListView,
        group: Int
    ) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight
            if (listView.isGroupExpanded(i) && i != group
                || !listView.isGroupExpanded(i) && i == group
            ) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem: View = listAdapter.getChildView(
                        i, j, false, null,
                        listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
        }
        val params = listView.layoutParams
        var height = (totalHeight
                + listView.dividerHeight * (listAdapter.groupCount - 1))
        if (height < 10) height = 200
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }

}