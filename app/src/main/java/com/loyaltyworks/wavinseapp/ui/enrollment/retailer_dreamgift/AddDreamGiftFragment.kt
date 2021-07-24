package com.loyaltyworks.wavinseapp.ui.enrollment.retailer_dreamgift

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.model.AddDreamGiftRequest
import com.loyaltyworks.wavinseapp.model.CustomerDetailsResponseJson
import com.loyaltyworks.wavinseapp.utils.AppController
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.utils.DatePickerFragment
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import kotlinx.android.synthetic.main.fragment_add_dream_gift.*
import kotlinx.android.synthetic.main.member_list_row.*


class AddDreamGiftFragment : Fragment(), DatePickerFragment.CalenderCallBack {

    lateinit var mSelectedCustomer: CustomerDetailsResponseJson

    private lateinit var viewModel: ReatilerDreamGiftViewModel


    lateinit var giftName: String
    lateinit var pointRequired: String
    lateinit var expiryDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ReatilerDreamGiftViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dream_gift, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            mSelectedCustomer =
                arguments?.getSerializable("SelectedCustomer") as CustomerDetailsResponseJson


            mem_list_membershipId.text = mSelectedCustomer.LoyaltyId
            mem_list_username.text = mSelectedCustomer.FullName
            pointtext.visibility = View.VISIBLE
            if (mSelectedCustomer.PointsBalance != null) mem_list_point.text =
                mSelectedCustomer.PointsBalance.toString() else mem_list_point.text = "-"

            if (mSelectedCustomer.CustomerGrade != null && !TextUtils.isEmpty(mSelectedCustomer.CustomerGrade)) mem_list_tier.text =
                mSelectedCustomer.CustomerGrade else mem_list_tier.text = "-"

            if (mSelectedCustomer.UserOneHeader != null && !TextUtils.isEmpty(mSelectedCustomer.UserOneHeader)) ASMHeader.text =
                mSelectedCustomer.UserOneHeader else ASMHeader.text = "-"

            if (mSelectedCustomer.AsmName != null && !TextUtils.isEmpty(mSelectedCustomer.AsmName)) mem_list_asm.text =
                mSelectedCustomer.AsmName else mem_list_asm.text = "-"

            if (mSelectedCustomer.SeName != null && !TextUtils.isEmpty(mSelectedCustomer.SeName)) mem_list_se.text =
                mSelectedCustomer.SeName else mem_list_se.text = "-"

            if (mSelectedCustomer.IsActive!!) {
                mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
                mem_list_status.text = "Active"
            } else {
                mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
                mem_list_status.text = "Inactive"
            }

            if (mSelectedCustomer.Verified == 1) {
                mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_active))
                mem_list_approve.text = "Verified"
            } else {
                mem_list_activeImg.setImageDrawable(requireContext().resources.getDrawable(R.drawable.ic_inactive))
                mem_list_approve.text = "Not Verified"
            }

            if (mSelectedCustomer.CreatedDate != null) mem_list_date.text =
                mSelectedCustomer.CreatedDate
            if (mSelectedCustomer.CreatedByName != null) mem_list_createdby.text =
                mSelectedCustomer.CreatedByName
            mem_list_branch.text = mSelectedCustomer.LocationName

            /*if (mSelectedCustomer.ProfilePicture != null && mSelectedCustomer.ProfilePicture!!.isNotEmpty()
            ) {
                Glide.with(requireContext()).asBitmap().placeholder(R.drawable.placeholder)
                    .into(general_profile_photo)

            }*/
        }


        addDreamGift()

    }

    private fun addDreamGift() {


        // select and set expiry date of dream gift
        expiry_date_et.setOnClickListener {
            /*  DatePickerBox.date(activity) {
                  expiry_date_et.text = it
              }
  */
            if (BlockMultipleClick.click()) return@setOnClickListener
            val dateD = DatePickerFragment(
                requireContext(),
                this@AddDreamGiftFragment,
                2
            )
            dateD.show(requireActivity().fragmentManager.beginTransaction(), "DATE PICKER")

        }


        /*    override fun OnCalenderClickResult(
                selectedDate: String?,
                serverFormat: String?,
                actionType: Int?
            ) {
                DOB = serverFormat.toString()
                dob.text = selectedDate
            }
    */



        add_gift_btn.setOnClickListener {

            giftName = dream_gift_name_et.text.toString()
            pointRequired = points_required_et.text.toString()
            expiryDate = expiry_date_et.text.toString()


            if (TextUtils.isEmpty(giftName)) {
                dream_gift_name_et.error = "Enter Dream Gift Name"
            } else if (TextUtils.isEmpty(pointRequired)) {
                points_required_et.error = "Enter points required"
            } else if (points_required_et.text.toString().toInt() < 500) {
                Toast.makeText(
                    context,
                    "Points required cannot be less than 500",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (TextUtils.isEmpty(expiryDate)) {
//            mExpiryDate.setError("Enter expiry date");
                Toast.makeText(context, "Enter desired date", Toast.LENGTH_SHORT).show()
            } else {

                viewModel.getDreamGiftAddList(
                    AddDreamGiftRequest(
                        ActionType = "0",
                        ActorId = PreferenceHelper.getLoginDetails(requireContext())!!.UserList!![0].UserId.toString(),
                        DreamGiftName = giftName,
                        JDesiredDate = AppController.dateFormat(expiry_date_et.text.toString()),
                        LoyaltyID = mSelectedCustomer.LoyaltyId,
                        PointsRequired = pointRequired
                    )
                )


            }


        }

    }


    override fun OnCalenderClickResult(
        selectedDate: String?,
        serverFormat: String?,
        actionType: Int?
    ) {
        expiry_date_et.text = selectedDate
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getDreamGiftAddLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()

            if (it != null && it.ReturnValue == 1) {
                Toast.makeText(context, "Dream Gift Saved Successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Dream Gift Save failed", Toast.LENGTH_SHORT).show()
            }


        })
    }


}
