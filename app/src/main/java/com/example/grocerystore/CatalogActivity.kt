package com.example.grocerystore

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

class CatalogActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    var photoUri: Uri? = null
    var products: MutableList<Product> = mutableListOf()
    var listAdapter: ListAdapter? = null
    var check = true
    var item: Int? = null

    private lateinit var toolbarMain: Toolbar

    private lateinit var editImageIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var productCostET: EditText
    private lateinit var productDescriptionET: EditText
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

            listAdapter = ListAdapter(this@CatalogActivity, products)
            listViewLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
            clearEditFields()
            listAdapter?.notifyDataSetChanged()
        }

        listViewLV.setOnItemClickListener { parent, view, position, id ->
            val product = listAdapter!!.getItem(position)
            item = position
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("product", product)
            intent.putExtra("products", this.products as ArrayList<Product>)
            intent.putExtra("check", check)
            intent.putExtra("item", item)
            startActivity(intent)
        }

    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val productCost = productCostET.text.toString()
        val productDescription = productDescriptionET.text.toString()
        val productImage = photoUri.toString()
        val product = Product(productName, productCost, productImage, productDescription)
        products.add(product)
        clearEditFields()
        photoUri = null
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
        productDescriptionET = findViewById(R.id.productDescriptionET)
        saveBTN = findViewById(R.id.saveBTN)
        listViewLV = findViewById(R.id.listViewLV)
    }


    override fun onResume() {
        super.onResume()
        check = intent.extras?.getBoolean("newcheck") ?: true
        if (!check) {
            products = intent.getSerializableExtra("list") as MutableList<Product>
            listAdapter = ListAdapter(this, products)
            check = true
        }
        listViewLV.adapter = listAdapter


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data
                editImageIV.setImageURI(photoUri)
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