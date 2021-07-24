package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.wavinseapp.model.*
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.ProductActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.ProductAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter.ProductCategorySpinnersAdapter
import com.loyaltyworks.wavinseapp.ui.enrollment.wishlist.WishListViewModel
import kotlinx.android.synthetic.main.product_fragment.*

class ProductFragment : Fragment() , ProductAdapter.OnClickCallBack{

    var netPoints:Int = 0
    var redeemablePoints:Int = 0

    var mSelectedCategoryType: ObjCatalogueCategoryJson? = null

    private lateinit var wishListViewModel: WishListViewModel
     var wishListAddedProducts: List<ObjCatalogue>? = null


    private val viewModel : ProductViewModel by viewModels{
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Redeemable point balance
        redeemablePoints = PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].RedeemablePointsBalance!!

        wishListViewModel = ViewModelProvider(this).get(WishListViewModel::class.java)

        return inflater.inflate(R.layout.product_fragment, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        val products = mutableListOf<ObjCatalogueList>()

        viewModel.productCategoryResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.GetCatalogueCategoryDetailsResult.ObjCatalogueCategoryListJson.isNullOrEmpty()) {
                val categoryTypeList: MutableList<ObjCatalogueCategoryJson> =
                        it.GetCatalogueCategoryDetailsResult.ObjCatalogueCategoryListJson!!.toMutableList()
                categoryTypeList.add(
                        0,
                        ObjCatalogueCategoryJson(
                                CatogoryName = "Select Category Type",
                                CatogoryId = -1
                        )
                )

                product_category.adapter = ProductCategorySpinnersAdapter(requireContext(), categoryTypeList)

            }
        })

        viewModel.allProducts.observe(viewLifecycleOwner, Observer {
            products.addAll(it)
        })

        viewModel.netPoints.observe(viewLifecycleOwner, Observer {
            if (it != null)
                netPoints = it
            else netPoints = 0
        })

        viewModel.wishListAddedResponse.observe(viewLifecycleOwner, Observer {
            if(it!=null && !it.ObjCatalogueList.isNullOrEmpty()){
                wishListAddedProducts = it.ObjCatalogueList
            }

            viewModel.setRedeemGiftRequest(
                    RedeemGiftRequest(
                            ActionType = "6",
                            ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                            ObjCatalogueDetail(CatalogueType = "1", MerchantId = "1")
                    )
            )

        })


        viewModel.redeemGiftLiveData.observe(viewLifecycleOwner, Observer {

            LoadingDialogue.dismissDialog()

            if(it!=null && !it.ObjCatalogueList.isNullOrEmpty()) {

                it.ObjCatalogueList?.forEachIndexed { index, product ->
                    val produc = products.find { it.ProductCode ==  product.ProductCode}
                    if(produc!=null)
                        it.ObjCatalogueList!![index] = produc
                }

                if(!wishListAddedProducts.isNullOrEmpty()){
                    wishListAddedProducts!!.forEach {
                        for (i in products) {
                            if(i.ProductCode!! == it.ProductCode){
                                i.HasWishListAdded = true
                            }
                        }
                    }
                }

                productListRecyclerview.visibility = View.VISIBLE
                product_error.visibility = View.GONE
                productListRecyclerview.adapter = ProductAdapter(this,viewModel, it, this)

            }else{

                productListRecyclerview.visibility = View.GONE
                product_error.visibility = View.VISIBLE

            }

        })


        wishListViewModel.getAddOrNotWishListLiveData.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                LoadingDialogue.dismissDialog()
                if(it!=null && !it.ReturnMessage.isNullOrEmpty()){
                when {
                    it.ReturnMessage.equals("1~EXIST",true) -> {
                        (activity as ProductActivity).snackBar("Wishlist already added", R.color.primary_color)
                        return@Observer
                    }
                    it.ReturnMessage.toInt()>0 -> {
                        (activity as ProductActivity).snackBar("Product has been added to wishlist successfully", R.color.primary_color)
                        return@Observer
                    }
                    else -> {
                        (activity as ProductActivity).snackBar("Failed to add product into wishlist", R.color.red)
                        return@Observer
                    }

                }

            }else{
                (activity as ProductActivity).snackBar("Failed to add product into wishlist", R.color.red)
            }
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        redeemable_points.text = ""+redeemablePoints?.toString()

        LoadingDialogue.showDialog(requireContext())

        viewModel.setWishListAddeddRequest(WishListAddedRequest(ActionType = 18, ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString()))

        viewModel.setProductCategoryRequest(
            ProductCategoryRequest(
                ObjCatalogueRetriveRequest(ActionType = "6",ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                ObjCatalogueCategoryDetail(IsActive = "1")
                )
            )
        )

        product_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                mSelectedCategoryType = parent!!.getItemAtPosition(position) as ObjCatalogueCategoryJson
                if(mSelectedCategoryType!!.CatogoryId!=-1){
                    LoadingDialogue.showDialog(requireContext())
                    viewModel.setRedeemGiftRequest(
                            RedeemGiftRequest(
                                    ActionType = "6",
                                    ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                                    ObjCatalogueDetail(CatalogueType = "1",CatogoryId= mSelectedCategoryType!!.CatogoryId.toString(), MerchantId = "1")
                            )
                    )
                }else{
                    LoadingDialogue.showDialog(requireContext())
                    viewModel.setRedeemGiftRequest(
                            RedeemGiftRequest(
                                    ActionType = "6",
                                    ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                                    ObjCatalogueDetail(CatalogueType = "1",CatogoryId= mSelectedCategoryType!!.CatogoryId.toString(), MerchantId = "1")
                            )
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }


    }


    override fun onResume() {
        super.onResume()
    }

    override fun onwishListItemClickResponse(productList: ObjCatalogueList) {
        LoadingDialogue.showDialog(requireContext())
        wishListViewModel.getAddOrNotWishListLiveData(
            AddOrNotWishListRequest(ActionType = 0,
            ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                ObjCatalogueDetails(CashValue = productList.CashValue.toString(), CatalogueId = productList.CatalogueId.toString())
            )
        )
    }

}