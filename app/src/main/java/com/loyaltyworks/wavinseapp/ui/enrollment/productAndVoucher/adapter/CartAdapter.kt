package com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.adapter



import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.wavinseapp.BuildConfig
import com.loyaltyworks.wavinseapp.R
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.ProductActivity
import com.loyaltyworks.wavinseapp.utils.BlockMultipleClick
import com.loyaltyworks.wavinseapp.model.ObjCatalogueList
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.myCart.MyCartFragment
import com.loyaltyworks.wavinseapp.ui.enrollment.productAndVoucher.productList.ProductViewModel
import kotlinx.android.synthetic.main.row_cart_layout.view.*

class CartAdapter(val fragment: Fragment, val productViewModel: ProductViewModel, val onRemoveCallBack: OnRemoveCallBack) : ListAdapter<ObjCatalogueList, CartAdapter.ViewHolder>(
    WORDS_COMPARATOR
) {

    interface OnRemoveCallBack{ fun onRemoveCode(position: Int, objCatalogueList: ObjCatalogueList)}

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val product_image = itemView.product_image
        val productName = itemView.productName
        val points = itemView.points
        val plus_btn = itemView.plus_btn
        val count_text = itemView.count_text
        val minus_btn = itemView.minus_btn
        val remove_btn = itemView.remove_btn
        val totalpoints = itemView.totalpoints

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_cart_layout, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = getItem(position)

        // set quantity
        holder.count_text.text = products?.NoOfQuantity.toString()

        holder.productName.text = products?.ProductName.toString()
        holder.points.text = products?.PointsRequired.toString()
        holder.totalpoints.text = (products.PointsRequired?.times(products.NoOfQuantity!!).toString())

        Glide.with(holder.itemView).load(BuildConfig.PROMO_IMAGE_BASE +"UploadFiles/CatalogueImages/"+ products?.ProductImage).into(holder.product_image)


        holder.plus_btn.setOnClickListener {

            val currentPoint = (fragment as MyCartFragment).netPoints + products.PointsRequired!!

            if(currentPoint <= (fragment).redeemablePoints) {
                // Increment value by 1
                products.NoOfQuantity = products.NoOfQuantity?.plus(1)
                updateQuantity(holder, products)
            }else{
                (fragment.activity as ProductActivity).snackBar("Don't have sufficient point balance", R.color.red)
            }

        }

        holder.minus_btn.setOnClickListener {

            if(products.NoOfQuantity!! > 1) {
                // Decrement value by 1
                products.NoOfQuantity = products.NoOfQuantity?.minus(1)

                updateQuantity(holder, products)
            }else{
                (fragment.activity as ProductActivity).snackBar("Remove from cart", R.color.red)
            }

        }

        // code remove
        holder.remove_btn.setOnClickListener {

            if (BlockMultipleClick.click()){
                return@setOnClickListener
            }
            onRemoveCallBack.onRemoveCode(position, products)

        }

    }

    private fun updateQuantity(holder: ViewHolder, objCatalogueList: ObjCatalogueList) {
        // update points
        objCatalogueList.NoOfPointsDebit =
            objCatalogueList.PointsRequired!! * objCatalogueList.NoOfQuantity!!

        // Update quantity
        productViewModel.updateProductQuantity(
            objCatalogueList.ProductCode!!,
            objCatalogueList.NoOfQuantity!!,
            objCatalogueList.NoOfPointsDebit!!
        )

        // update quantity
        holder.count_text.text = objCatalogueList.NoOfQuantity.toString()
        holder.totalpoints.text = (objCatalogueList.PointsRequired?.times(objCatalogueList.NoOfQuantity!!).toString())
    }


    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<ObjCatalogueList>() {
            override fun areItemsTheSame(oldItem: ObjCatalogueList, newItem: ObjCatalogueList): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ObjCatalogueList, newItem: ObjCatalogueList): Boolean {
                return oldItem.ProductCode == newItem.ProductCode
            }
        }
    }

}