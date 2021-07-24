package com.loyaltyworks.wavinseapp.ui.enrollment.reatiler_promotion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.reatiler_promotion.adapter.PromotionsAdapter
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.GetWhatsNewRequest
import com.loyaltyworks.wavinseapp.model.LstPromotionList
import kotlinx.android.synthetic.main.retailer_promotion_fragment.*

class RetailerPromotionFragment : Fragment(), PromotionsAdapter.OnItemClickListener {

    private lateinit var viewModel: RetailerPromotionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RetailerPromotionViewModel::class.java)

        return inflater.inflate(R.layout.retailer_promotion_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getPromotionListLiveData.observe(viewLifecycleOwner, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null && !it.LstPromotionJsonList.isNullOrEmpty()) {
                promotionRecycler.adapter = PromotionsAdapter(it, this)
            } else {
                error_txt.visibility = View.VISIBLE
                promotionRecycler.visibility = View.GONE
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadingDialogue.showDialog(requireContext())
        viewModel.getWhatsNew(
            GetWhatsNewRequest(
                "99", PreferenceHelper.getLoginDetails(
                    requireContext()
                )?.UserList!![0].UserId.toString()
            )
        )
    }

    override fun onItemClicked(offersPromotions: LstPromotionList) {
        val bundle = Bundle()
        bundle.putSerializable("offerPromotions", offersPromotions)
        findNavController().navigate(R.id.action_retailerPromotionFragment_to_promotionDetailFragment,bundle)
    }

}