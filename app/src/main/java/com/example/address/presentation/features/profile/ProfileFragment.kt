package com.example.address.presentation.features.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.address.R
import com.example.address.databinding.FragmentProfileBinding
import com.example.address.domain.RegisterAddress
import com.google.android.material.snackbar.Snackbar


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var gender = "male"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.nextButton.setOnClickListener {
            if (!isNameValid()) {
                Snackbar.make(binding.root, "نام صحیح نیست.", 5000).show()
                return@setOnClickListener
            }
            if (!isFamilyNameValid()) {
                Snackbar.make(binding.root, "نام خانوادگی صحیح نیست.", 5000).show()
                return@setOnClickListener
            }
            if (!isPhoneNumberValid()) {
                Snackbar.make(binding.root, "فرمت شماره موبایل وارد شده صحیح نیست", 5000).show()
                return@setOnClickListener
            }
            if (!isPhoneNumLengthCorrect()) {
                Snackbar.make(binding.root, "شماره موبایل باید بیش از 11 رقم باشد", 5000).show()
                return@setOnClickListener
            }
            if (!isHomeNumberValid()) {
                Snackbar.make(binding.root, "فرمت شماره تلفن ثابت صحیح نیست", 5000).show()
                return@setOnClickListener
            }
            if (!isHomeNumLengthCorrect()) {
                Snackbar.make(binding.root, "شماره تلفن ثابت باید بیش از 11 رقم باشد", 5000).show()
                return@setOnClickListener
            }
            if (!isFullAddressValid()) {
                Snackbar.make(binding.root, "آدرس صحیح نیست یا کمتر از حد مجاز است.", 5000).show()
                return@setOnClickListener
            }
            if (isAllFieldsCorrect())
                navigateToMapsPage()
        }

        binding.edtTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isNameValid()) {
                    binding.nameIcon.setBackgroundResource(R.drawable.flat_tick_icon)
                } else {
                    binding.nameIcon.setBackgroundResource(R.drawable.blank_tick_icon)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtTextFamilyName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isFamilyNameValid()) {
                    binding.familyNameIcon.setBackgroundResource(R.drawable.flat_tick_icon)
                } else {
                    binding.familyNameIcon.setBackgroundResource(R.drawable.blank_tick_icon)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtTextFullAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isFullAddressValid()) {
                    binding.addressIcon.setBackgroundResource(R.drawable.flat_tick_icon)
                } else {
                    binding.addressIcon.setBackgroundResource(R.drawable.blank_tick_icon)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtTextPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isPhoneNumberValid() && isPhoneNumLengthCorrect()) {
                    binding.phoneNumberIcon.setBackgroundResource(R.drawable.flat_tick_icon)
                } else {
                    binding.phoneNumberIcon.setBackgroundResource(R.drawable.blank_tick_icon)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.edtTextHomeNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isHomeNumberValid() && isHomeNumLengthCorrect()) {
                    binding.homeNumberIcon.setBackgroundResource(R.drawable.flat_tick_icon)
                } else {
                    binding.homeNumberIcon.setBackgroundResource(R.drawable.blank_tick_icon)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
            gender = when (radioGroup.checkedRadioButtonId) {
                binding.radioButtonFemale.id -> "female"
                else -> "male"
            }
        }

    }

    private fun isFullAddressValid() = binding.edtTextFullAddress.text.length > 13

    private fun isHomeNumberValid() =
        if (binding.edtTextHomeNumber.text.isNotEmpty()) binding.edtTextHomeNumber.text[0] == '0' else false

    private fun isHomeNumLengthCorrect() = binding.edtTextHomeNumber.text.length > 10

    private fun isPhoneNumberValid() =
        if (binding.edtTextPhoneNumber.text.length > 2) binding.edtTextPhoneNumber.text[0] == '0' && binding.edtTextPhoneNumber.text[1] == '9' else false

    private fun isPhoneNumLengthCorrect() = binding.edtTextPhoneNumber.text.length == 11

    private fun isFamilyNameValid() = binding.edtTextFamilyName.text.length > 1

    private fun isNameValid() = binding.edtTextName.text.length > 1

    private fun isAllFieldsCorrect() = isFullAddressValid() && isFamilyNameValid() &&
            isNameValid() && isHomeNumLengthCorrect() && isPhoneNumberValid() && isPhoneNumLengthCorrect() && isHomeNumberValid()

    private fun navigateToMapsPage() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToMapsFragment2(
                RegisterAddress(
                    binding.edtTextFullAddress.text.toString(),
                    binding.edtTextPhoneNumber.text.toString(),
                    binding.edtTextHomeNumber.text.toString(),
                    binding.edtTextName.text.toString(),
                    gender,
                    binding.edtTextFamilyName.text.toString()
                )
            )
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}