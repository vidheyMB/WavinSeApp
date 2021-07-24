package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.ProductActivity
import com.loyaltyworks.wavinseapp.utils.PreferenceHelper
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.model.RedeemGiftResponse
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductFragment
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import kotlinx.android.synthetic.main.row_product_layout.view.*

class ProductAdapter(
    val fragment: Fragment,
    val productViewModel: ProductViewModel,
    val redeemGiftResponse: RedeemGiftResponse, val onWishListClickListener: OnClickCallBack
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface OnClickCallBack{
        fun onwishListItemClickResponse( productList: ObjCatalogueList)
    }
     @RequiresApi(Build.VERSION_CODES.M)
     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val product_image = itemView.product_image
        val productName = itemView.productName
        val points = itemView.points

        val add_btn = itemView.add_btn
        val add_count_layout = itemView.add_count_layout
        val minus_btn = itemView.minus_btn
        val count_text = itemView.count_text
        val plus_btn = itemView.plus_btn

        init {


            add_btn.setOnClickListener {
                if(add_btn.text != "Add To Wishlist"){
                    val currentPoint = (fragment as ProductFragment).netPoints + redeemGiftResponse.ObjCatalogueList?.get(
                        adapterPosition
                    )?.PointsRequired!!

                    if(currentPoint <= (fragment).redeemablePoints) {
                        add_btn.visibility = View.GONE
                        add_count_layout.visibility = View.VISIBLE

                        // set value to 1
                        redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity = 1
                        redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfPointsDebit = redeemGiftResponse.ObjCatalogueList?.get(
                            adapterPosition
                        )?.PointsRequired
                        redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.RedemptionTypeId = 1 // Default for Product Catalogue

                        // save object in local DB
                        productViewModel.checkAndInsert(
                            redeemGiftResponse.ObjCatalogueList?.get(
                                adapterPosition
                            )?.ProductCode!!,
                            redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)!!
                        )

                        // update quantity
                        count_text.text =
                            redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity.toString()
                    }else{
                        (fragment.activity as ProductActivity).snackBar(
                            "Don\'t have sufficient point balance",
                            R.color.red
                        )
                    }
                }else{

                    onWishListClickListener.onwishListItemClickResponse(redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)!!)
                }

            }

            plus_btn.setOnClickListener {
                val currentPoint = (fragment as ProductFragment).netPoints + redeemGiftResponse.ObjCatalogueList?.get(
                    adapterPosition
                )?.PointsRequired!!

                if(currentPoint <= (fragment).redeemablePoints) {
                    // Increment value by 1
                    redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity =
                        redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity?.plus(
                            1
                        )

                    updateQuantity()
                }else{
                    (fragment.activity as ProductActivity).snackBar(
                        "Don't have sufficient point balance",
                        R.color.red
                    )
                }
            }

            minus_btn.setOnClickListener {
                if(redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity!! > 1) {
                    // Decrement value by 1
                    redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity =
                        redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity?.minus(
                            1
                        )

                    updateQuantity()
                }else{
                    (fragment.activity as ProductActivity).snackBar("Remove from cart", R.color.red)
                }
            }



        }

         private fun updateQuantity() {

             // update points
             redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfPointsDebit =
                 redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.PointsRequired!! * redeemGiftResponse.ObjCatalogueList?.get(
                     adapterPosition
                 )?.NoOfQuantity!!


             // Update quantity
             productViewModel.updateProductQuantity(
                 redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.ProductCode!!,
                 redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity!!,
                 redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfPointsDebit!!
             )

             // update quantity
             count_text.text =
                 redeemGiftResponse.ObjCatalogueList?.get(adapterPosition)?.NoOfQuantity.toString()
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_product_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = redeemGiftResponse.ObjCatalogueList?.get(position)

        // initial set
        if(products?.NoOfQuantity == 0) {
            holder.add_btn.visibility = View.VISIBLE
            holder.add_count_layout.visibility = View.GONE
        }
        else {
            holder.add_btn.visibility = View.GONE
            holder.add_count_layout.visibility = View.VISIBLE
        }

        // set quantity
        holder.count_text.text = products?.NoOfQuantity.toString()

        holder.productName.text = products?.ProductName.toString()
        holder.points.text = products?.PointsRequired.toString()

        Glide.with(holder.itemView).load(BuildConfig.PROMO_IMAGE_BASE + "UploadFiles/CatalogueImages/" + products?.ProductImage).into(
            holder.product_image
        )


        if(products != null && products.PointsRequired!! <= PreferenceHelper.getCustomerDashboard(holder.itemView.context)!!.ObjCustomerDashboardList!![0].RedeemablePointsBalance!!){
            holder.add_btn.text = "Add To Cart"
        }else{
            if(products!!.HasWishListAdded!=null){
                holder.add_btn.text = "Added To Wishlist"
                holder.add_btn.visibility = View.GONE
            }else {
                holder.add_btn.text = "Add To Wishlist"
                holder.add_btn.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("Product", products)
            holder.itemView.findNavController().navigate(
                R.id.action_productFragment_to_productDetailsFragment,
                bundle
            )
        }

    }

    override fun getItemCount(): Int {
        return redeemGiftResponse.ObjCatalogueList!!.size
    }
}