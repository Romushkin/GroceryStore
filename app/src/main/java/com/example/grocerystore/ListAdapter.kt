package com.example.grocerystore

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, productList: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.list_item, productList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val productImageIV = view?.findViewById<ImageView>(R.id.productImageIV)
        val productNameTV = view?.findViewById<TextView>(R.id.productNameTV)
        val productCostTV = view?.findViewById<TextView>(R.id.productCostTV)

        productImageIV?.setImageURI(Uri.parse(product?.image))
        productNameTV?.text = product?.name
        productCostTV?.text = product?.cost

        return view!!
    }
}