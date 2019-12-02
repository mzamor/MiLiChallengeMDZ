package com.example.milichallenge.presentation.itemSelected.view


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.milichallenge.R
import com.example.milichallenge.presentation.main.view.MainActivity

class ProductQuantityDialogFragment(var quantityAvailable: Int?) : DialogFragment() {

    interface ProductQuantity {
        fun productQuantitySelected(quantity: Int)
    }

    var myContext : Context?  =null
    init {
        myContext=context
    }
    lateinit var productQuantity: ProductQuantity
    var listView: ListView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val arrayProductQuantity = resources.getStringArray(R.array.string_array_product_quantity)

        val alertDialogBuilder = AlertDialog.Builder(context!!)
            .setItems(arrayProductQuantity, DialogInterface.OnClickListener { dialogInterface, i ->
                if (i + 1 <= quantityAvailable!!) {
                    if (i != 6) {
                        productQuantity.productQuantitySelected(i + 1)
                    } else {
                        val mBuilder = AlertDialog.Builder(context!!)
                        val mLayout = LinearLayout(context)
                        val mTvQuantityProducts = TextView(context)
                        val mEtQuantityProducts = EditText(context)
                        mTvQuantityProducts.text = "Elegí cantidad"
                        mEtQuantityProducts.setSingleLine()
                        mEtQuantityProducts.hint = "Elegir Cantidad"
                        mEtQuantityProducts.inputType = InputType.TYPE_CLASS_NUMBER
                        mLayout.orientation = LinearLayout.VERTICAL
                        mLayout.addView(mTvQuantityProducts)
                        mLayout.addView(mEtQuantityProducts)
                        mLayout.setPadding(50, 40, 50, 10)
                        mBuilder.setView(mLayout)
                        mBuilder.setPositiveButton("Confirmar") { dialogInterfaceManual, j ->
                            val productQuantityValue = mEtQuantityProducts.text.toString().toInt()
                            if(productQuantityValue <= quantityAvailable!!) {
                                productQuantity.productQuantitySelected(productQuantityValue)
                            }else{

                            }
                        }
                        mBuilder.create().show()
                    }
                }
            })
        val dialog = alertDialogBuilder.create()
        listView = dialog.listView
        listView?.divider = ColorDrawable(Color.DKGRAY)
        listView?.dividerHeight = 2
        return dialog
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            productQuantity = context as ProductQuantity
        } catch (e: ClassCastException) {
        }
    }
}
