package com.example.grocerystore

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class CatalogActivity : AppCompatActivity() {
    private val GALLERY_REQUEST = 302
    var bitmap: Bitmap? = null
    val products: MutableList<Product> = mutableListOf()

    private lateinit var toolbarMain: Toolbar

    private lateinit var editImageIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var productCostET: EditText
    private lateinit var saveBTN: Button
    private lateinit var listViewLV: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalog)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        setSupportActionBar(toolbarMain)

        editImageIV.setOnClickListener{
            val imagePickerIntent = Intent(Intent.ACTION_PICK)
            imagePickerIntent.type = "image/*"
            startActivityForResult(imagePickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            createProduct()

            val listAdapter = ListAdapter(this@CatalogActivity, products)
            listViewLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            clearEditFields()
        }

    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val productCost = productCostET.text.toString()
        val productImage = bitmap
        val product = Product(productName, productCost, productImage)
        products.add(product)
    }

    private fun clearEditFields() {
        productNameET.text.clear()
        productCostET.text.clear()
        editImageIV.setImageResource(R.drawable.ic_product)
    }

    private fun init() {
        toolbarMain = findViewById(R.id.toolbarMain)
        editImageIV = findViewById(R.id.editImageIV)
        productNameET = findViewById(R.id.productNameET)
        productCostET = findViewById(R.id.productCostET)
        saveBTN = findViewById(R.id.saveBTN)
        listViewLV = findViewById(R.id.listViewLV)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                editImageIV.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) finishAffinity()
        return super.onOptionsItemSelected(item)
    }
}