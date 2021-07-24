package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productDetails

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.ApplicationClass
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.baseClass.ViewModelFactory
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.ProductActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.utils.dialogBox.LoadingDialogue
import com.loyaltyworks.wavinseapp.model.AddOrNotWishListRequest
import com.loyaltyworks.wavinseapp.model.ObjCatalogueDetails
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import com.loyaltyworks.wavinseapp.ui.enrollment.wishlist.WishListViewModel
import kotlinx.android.synthetic.main.product_details_fragment.*

class ProductDetailsFragment : Fragment() {

    var netPoints: Int = 0
    var redeemablePoints: Int = 0

    private lateinit var products: ObjCatalogueList
    private lateinit var wishListViewModel: WishListViewModel
    private val viewModel: ProductViewModel by viewModels {
        ViewModelFactory((activity?.application as ApplicationClass).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wishListViewModel = ViewModelProvider(this).get(WishListViewModel::class.java)
        // Redeemable point balance
        redeemablePoints =
            PreferenceHelper.getCustomerDashboard(requireContext())!!.ObjCustomerDashboardList!![0].RedeemablePointsBalance!!

        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.allProducts.observe(viewLifecycleOwner, Observer { it ->
            val product = it?.find { it.ProductCode == products.ProductCode }
            if (product == null) {
                add_count_layout.visibility = View.GONE
                if (add_btn.text.toString() != "Add To Wishlist")
                    add_btn.visibility = View.VISIBLE
                totalProductPoints.visibility = View.GONE
            } else {
                add_count_layout.visibility = View.VISIBLE
                add_btn.visibility = View.GONE
                totalProductPoints.visibility = View.VISIBLE
                // update the product object
                products = product

                // initial set product point
                totalPointsDisplay()
                // update quantity
                count_text.text = products.NoOfQuantity.toString()

            }
        })

        viewModel.netPoints.observe(viewLifecycleOwner, Observer {
            if (it != null)
                netPoints = it
            else netPoints = 0
        })

        wishListViewModel.getAddOrNotWishListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.ReturnMessage.isNullOrEmpty()) {
                LoadingDialogue.dismissDialog()
                when {
                    it.ReturnMessage.equals("1~EXIST", true) -> {
                        (activity as ProductActivity).snackBar(
                            "Product has been already added into wishlist",
                            R.color.primary_color
                        )
                        return@Observer
                    }
                    it.ReturnMessage.toInt() > 0 -> {
                        (activity as ProductActivity).snackBar(
                            "Product has been added to wishlist successfully",
                            R.color.primary_color
                        )
                        return@Observer
                    }
                    else -> {
                        (activity as ProductActivity).snackBar(
                            "Failed to add product into wishlist",
                            R.color.red
                        )
                        return@Observer
                    }

                }

            } else {
                LoadingDialogue.dismissDialog()
                (activity as ProductActivity).snackBar(
                    "Failed to add product into wishlist",
                    R.color.red
                )
            }


        })

    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null)
            products = requireArguments().getSerializable("Product") as ObjCatalogueList

        productName.text = products.ProductName
        category.text = "Category : " + products.CatogoryName
        points.text = "Points/Product : " + products.PointsRequired
        product_code.text = "Product Code : " + products.ProductCode
        delivery_type.text = "Delivery type : " + products.DeliveryType

        description.text = products.ProductDesc
        tc.text = products.TermsCondition

        if (products.NoOfQuantity!! > 0) {
            add_count_layout.visibility = View.VISIBLE
            add_btn.visibility = View.GONE
        } else {
            add_count_layout.visibility = View.GONE
            add_btn.visibility = View.VISIBLE
        }


        if (products != null && products.PointsRequired!! <= PreferenceHelper.getCustomerDashboard(
                requireContext()
            )!!.ObjCustomerDashboardList!![0].RedeemablePointsBalance!!
        ) {
            add_btn.text = "Add To Cart"
        } else {
            if (products!!.HasWishListAdded != null) {
                add_btn.visibility = View.GONE
                add_btn.text = "Added To Wishlist"
            } else {
                add_btn.visibility = View.GONE
                add_btn.text = "Add To Wishlist"
            }
        }

        Glide.with(view)
            .load(BuildConfig.PROMO_IMAGE_BASE + "UploadFiles/CatalogueImages/" + products.ProductImage)
            .into(product_image)

        // initial set product point
        totalPointsDisplay()
        // update quantity
        count_text.text = products.NoOfQuantity.toString()


        add_btn.setOnClickListener {
            if (add_btn.text != "Add To Wishlist") {
                val currentPoint = netPoints + products.PointsRequired!!

                if (currentPoint <= redeemablePoints) {
                    add_btn.visibility = View.GONE
                    add_count_layout.visibility = View.VISIBLE

                    // set value to 1
                    products.NoOfQuantity = 1
                    products.NoOfPointsDebit = products.PointsRequired
                    products.RedemptionTypeId = 1 // Default for Product Catalogue

                    // save object in local DB
                    viewModel.checkAndInsert(products.ProductCode!!, products!!)

                    totalPointsDisplay()
                } else {
                    (activity as ProductActivity).snackBar(
                        "Don\'t have sufficient point balance",
                        R.color.red
                    )
                }

            } else {
                LoadingDialogue.showDialog(requireContext())
                wishListViewModel.getAddOrNotWishListLiveData(
                    AddOrNotWishListRequest(
                        ActionType = 0,
                        ActorId = PreferenceHelper.getSECustomerDetails(requireContext())!!.lstCustomerJson!![0].UserId.toString(),
                        ObjCatalogueDetails(
                            CashValue = products.CashValue.toString(),
                            CatalogueId = products.CatalogueId.toString()
                        )
                    )
                )
            }

        }

        plus_btn.setOnClickListener {
            val currentPoint = netPoints + products.PointsRequired!!

            if (currentPoint <= redeemablePoints) {
                // Increment value by 1
                products.NoOfQuantity =
                    products.NoOfQuantity?.plus(1)

                updateQuantity()

                totalPointsDisplay()
            } else {
                (activity as ProductActivity).snackBar(
                    "Don't have sufficient point balance",
                    R.color.red
                )
            }

        }

        minus_btn.setOnClickListener {
            if (products.NoOfQuantity!! > 1) {
                // Decrement value by 1
                products.NoOfQuantity =
                    products.NoOfQuantity?.minus(1)

                updateQuantity()

                totalPointsDisplay()
            } else {
                (activity as ProductActivity).snackBar("Remove from cart", R.color.red)
            }
        }

    }

    private fun totalPointsDisplay() {
        // update quantity
        count_text.text = products.NoOfQuantity.toString()

        totalProductPoints.text =
            "Total points : " + (products.PointsRequired?.times(products.NoOfQuantity!!))


    }

    private fun updateQuantity() {

        // update points
        products.NoOfPointsDebit =
            products.PointsRequired!! * products.NoOfQuantity!!


        // Update quantity
        viewModel.updateProductQuantity(
            products.ProductCode!!,
            products.NoOfQuantity!!,
            products.NoOfPointsDebit!!
        )

        // update quantity
        count_text.text = products.NoOfQuantity.toString()

    }

}