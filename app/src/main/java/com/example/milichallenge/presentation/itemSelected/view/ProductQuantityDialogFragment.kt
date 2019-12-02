package com.example.milichallenge.presentation.itemSelected.view


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.milichallenge.R

class ProductQuantityDialogFragment(var quantityAvailable: Int?) : DialogFragment() {

    interface ProductQuantity {
        fun productQuantitySelected(quantity: Int)
    }

    var myContext: Context? = null
    var inputValidated: Boolean = false
    var valueInserted: Boolean = false

    init {
        myContext = context
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
                        dialogSetManualProductQuantity()
                    }
                }
            })

        val dialog = alertDialogBuilder.create()
        listView = dialog.listView
        listView?.divider = ColorDrawable(Color.DKGRAY)
        listView?.dividerHeight = 2
        return dialog
    }

    fun dialogSetManualProductQuantity() {
        val mBuilder = AlertDialog.Builder(context!!)
        val mEtQuantityProducts = EditText(context)
        val mLayout = LinearLayout(context)
        val mTvQuantityProducts = TextView(context)
        mTvQuantityProducts.text = resources.getText(R.string.choose_product_quantity_title)
        mEtQuantityProducts.setSingleLine()
        mEtQuantityProducts.hint = resources.getText(R.string.choose_product_quantity_hint)
        mEtQuantityProducts.inputType = InputType.TYPE_CLASS_NUMBER
        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(mTvQuantityProducts)
        mLayout.addView(mEtQuantityProducts)
        mLayout.setPadding(50, 40, 50, 10)
        mBuilder.setView(mLayout)
        mBuilder.setPositiveButton(resources.getText(R.string.confirm_product_quantity)) { dialogInterfaceManual, j ->
            val productQuantityValue = mEtQuantityProducts.text.toString().toInt()
            valueInserted = true
            inputValidated = productQuantityValue <= quantityAvailable!!
            if (inputValidated) {
                productQuantity.productQuantitySelected(productQuantityValue)
            }
        }
        mBuilder.create().show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            productQuantity = context as ProductQuantity
        } catch (e: ClassCastException) {
        }
    }
}
