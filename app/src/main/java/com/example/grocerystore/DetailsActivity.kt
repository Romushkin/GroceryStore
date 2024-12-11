package com.example.grocerystore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar

    private val GALLERY_REQUEST = 302
    var photoUri: Uri? = null

    private lateinit var ImageViewEditIV: ImageView
    private lateinit var productNameTV: TextView
    private lateinit var productCostTV: TextView
    private lateinit var productDescriptionTV: TextView
    private lateinit var saveEditBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        setSupportActionBar(toolbarMain)

        ImageViewEditIV.setOnClickListener{
            val imagePickerIntent = Intent(Intent.ACTION_PICK)
            imagePickerIntent.type = "image/*"
            startActivityForResult(imagePickerIntent, GALLERY_REQUEST)
        }

        val product = intent.extras?.getSerializable("product") as Product
        val products = intent.getSerializableExtra("products")
        var check = intent.extras?.getBoolean("check")
        val item = intent.extras?.getInt("item")

        val name = product.name
        val cost = product.cost
        val image: Uri? = Uri.parse(product.image)
        val description = product.decription

        productNameTV.text = name
        productCostTV.text = cost
        productDescriptionTV.text = description
        ImageViewEditIV.setImageURI(image)

        saveEditBTN.setOnClickListener {
            val productImage = photoUri.toString()
            val product = Product(name, cost, productImage, description)
            val list: MutableList<Product> = products as MutableList<Product>
            if (item != null) {
                swap(item, product, products)
            }
            check = false
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("list", list as ArrayList<Product>)
            intent.putExtra("newcheck", check)
            startActivity(intent)
            finish()
        }
    }

    private fun init() {
        toolbarMain = findViewById(R.id.toolbarMain)
        ImageViewEditIV = findViewById(R.id.ImageViewEditIV)
        productNameTV = findViewById(R.id.productNameTV)
        productCostTV = findViewById(R.id.productCostTV)
        productDescriptionTV = findViewById(R.id.productDescriptionTV)
        saveEditBTN = findViewById(R.id.saveEditBTN)
    }


    private fun swap(item: Int, product: Product, products: MutableList<Product>) {
        products.add(item+1, product)
        products.removeAt(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ImageViewEditIV = findViewById(R.id.ImageViewEditIV)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data
                ImageViewEditIV.setImageURI(photoUri)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.backMenu -> startActivity(Intent(this, CatalogActivity::class.java))
            R.id.exitMenu -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}