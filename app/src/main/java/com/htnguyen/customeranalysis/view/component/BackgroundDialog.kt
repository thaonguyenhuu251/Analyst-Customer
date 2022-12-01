package com.htnguyen.customeranalysis.view.component

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.databinding.BackgroundDialogBinding
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.ultils.FileUtils
import com.htnguyen.customeranalysis.ultils.PreferencesSettings

class BackgroundDialog : DialogFragment() {
    private lateinit var binding: BackgroundDialogBinding
    private var checkView: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        binding = BackgroundDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setCheckColor()
    }

    private fun initView() {
        binding.radioGroup1.clearCheck()
        binding.radioGroup2.clearCheck()
        binding.radioGroup1.setOnCheckedChangeListener(listener1)
        binding.radioGroup2.setOnCheckedChangeListener(listener2)
    }

    override fun onResume() {
        super.onResume()
    }


    private val listener1: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { buttonView, isChecked ->
            binding.radioGroup2.setOnCheckedChangeListener(null)
            binding.radioGroup2.clearCheck()
            binding.radioGroup2.setOnCheckedChangeListener(listener2)

            PreferencesSettings.saveToBackground(
                requireContext(),
                FileUtils.getColorTheme(isChecked)
            )

            PreferencesSettings.saveToColor(
                requireContext(),
                FileUtils.getColorString(isChecked))
            //Event.eventChangeBackground(Methods.getColorTheme(listColor))
        }


    private val listener2: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { buttonView, isChecked ->
            binding.radioGroup1.setOnCheckedChangeListener(null)
            binding.radioGroup1.clearCheck()
            binding.radioGroup1.setOnCheckedChangeListener(listener1)

            PreferencesSettings.saveToBackground(
                requireContext(),
                FileUtils.getColorTheme(isChecked)
            )

            PreferencesSettings.saveToColor(
                requireContext(),
                FileUtils.getColorString(isChecked))
        }

    private fun setCheckColor() {
        when (PreferencesSettings.getColor(requireContext())) {
            Constants.COLOR_TEAL -> binding.rbColor1.isChecked = true
            Constants.COLOR_ORANGE -> binding.rbColor2.isChecked = true
            Constants.COLOR_SKYBLUE -> binding.rbColor3.isChecked = true
            Constants.COLOR_GREEN -> binding.rbColor4.isChecked = true
            Constants.COLOR_YELLOW -> binding.rbColor5.isChecked = true
            Constants.COLOR_RED -> binding.rbColor6.isChecked = true
            Constants.COLOR_BLUE -> binding.rbColor7.isChecked = true
            else -> binding.rbColor8.isChecked = true

        }
    }

    companion object {
        const val TAG = "ViewModeDialog"
    }
}