package com.example.grocerystore

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

    private lateinit var ImageViewEditIV: ImageView
    private lateinit var productNameTV: TextView
    private lateinit var productCostTV: TextView
    private lateinit var productDescriptionTV: TextView

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
    }
    private fun init() {
        toolbarMain = findViewById(R.id.toolbarMain)
        ImageViewEditIV = findViewById(R.id.ImageViewEditIV)
        productNameTV = findViewById(R.id.productNameTV)
        productCostTV = findViewById(R.id.productCostTV)
        productDescriptionTV = findViewById(R.id.productDescriptionTV)

        val product = intent.extras?.getSerializable("product") as Product
/*        var check = intent.extras?.getBoolean("check")
        val item = intent.extras?.getInt("item")*/

        val name = product.name
        val cost = product.cost
        val image: Uri? = Uri.parse(product.image)
        val description = product.decription

        productNameTV.text = name
        productCostTV.text = cost
        productDescriptionTV.text = description
        ImageViewEditIV.setImageURI(image)

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