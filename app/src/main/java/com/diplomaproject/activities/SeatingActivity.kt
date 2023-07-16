package com.diplomaproject.activities

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.diplomaproject.R
import com.diplomaproject.databinding.ActivitySeatingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.HashMap
import java.util.UUID

class SeatingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeatingBinding
    lateinit var row_view_1: View
    lateinit var row_view_3: View
    lateinit var row_view_2: View
    lateinit var row_view_4: View
    lateinit var row_view_5: View
    var textIdList = arrayOf(
        R.id.column_text_1,
        R.id.column_text_2,
        R.id.column_text_3,
        R.id.column_text_4,
        R.id.column_text_5,
        R.id.column_text_6,
        R.id.column_text_7,
        R.id.column_text_8,
    )
    var textViewCount = 8
    private var col_text_view_array_1: Array<TextView?>? = null
    private var col_text_view_array_2: Array<TextView?>? = null
    private var col_text_view_array_3: Array<TextView?>? = null
    private var col_text_view_array_4: Array<TextView?>? = null
    private var col_text_view_array_5: Array<TextView?>? = null
    var quantityCount = 0
    var price = 10
    var rbString = "9:00 am"
    private lateinit var dateString: String
    private lateinit var loadingDialog: Dialog
    lateinit var movieName: String
    lateinit var moviePoster: String
    lateinit var room: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seating)
        movieName = intent.getStringExtra("movie_name").toString()
        moviePoster = intent.getStringExtra("movie_poster").toString()
        room = intent.getStringExtra("room").toString()
        binding.title.text = movieName
        binding.buyBtn.setOnClickListener() {
            bookTicket();
        }
        row_view_1 = findViewById(R.id.row_layout_A)
        row_view_2 = findViewById(R.id.row_layout_B)
        row_view_3 = findViewById(R.id.row_layout_C)
        row_view_4 = findViewById(R.id.row_layout_D)
        row_view_4 = findViewById(R.id.row_layout_D)
        row_view_5 = findViewById(R.id.row_layout_E)

        col_text_view_array_1 = arrayOfNulls(textViewCount)
        col_text_view_array_2 = arrayOfNulls(textViewCount)
        col_text_view_array_3 = arrayOfNulls(textViewCount)
        col_text_view_array_4 = arrayOfNulls(textViewCount)
        col_text_view_array_5 = arrayOfNulls(textViewCount)

        for (i in 0 until textViewCount) {
            col_text_view_array_1!![i] = row_view_1.findViewById<View>(textIdList[i]) as TextView
            col_text_view_array_1!![i]!!.setOnClickListener { v ->
                col_text_view_array_1!![i]!!.isSelected = !col_text_view_array_1!![i]!!.isSelected
                quantityCount = if (col_text_view_array_1!![i]!!.isSelected) {
                    quantityCount + 1
                } else {
                    if (quantityCount == 0) {
                        0
                    } else {
                        quantityCount - 1
                    }
                }
                setBackgroundTextView(col_text_view_array_1, i)
                quantityCheck()
            }
            col_text_view_array_2!![i] = row_view_2.findViewById<View>(textIdList[i]) as TextView
            col_text_view_array_2!![i]!!.setOnClickListener { v ->
                col_text_view_array_2!![i]!!.isSelected =
                    !col_text_view_array_2!![i]!!.isSelected
                quantityCount = if (col_text_view_array_2!![i]!!.isSelected) {
                    quantityCount + 1
                } else {
                    if (quantityCount == 0) {
                        0
                    } else {
                        quantityCount - 1
                    }
                }
                setBackgroundTextView(col_text_view_array_2, i)
                quantityCheck()
            }

            col_text_view_array_3!![i] = row_view_3.findViewById<View>(textIdList[i]) as TextView
            col_text_view_array_3!![i]!!.setOnClickListener { v ->
                col_text_view_array_3!![i]!!.isSelected =
                    !col_text_view_array_3!![i]!!.isSelected
                quantityCount = if (col_text_view_array_3!![i]!!.isSelected) {
                    quantityCount + 1
                } else {
                    if (quantityCount == 0) {
                        0
                    } else {
                        quantityCount - 1
                    }
                }
                setBackgroundTextView(col_text_view_array_3, i)
                quantityCheck()
            }

            col_text_view_array_4!![i] = row_view_4.findViewById<View>(textIdList[i]) as TextView
            col_text_view_array_4!![i]!!.setOnClickListener { v ->
                col_text_view_array_4!![i]!!.isSelected =
                    !col_text_view_array_4!![i]!!.isSelected
                quantityCount = if (col_text_view_array_4!![i]!!.isSelected) {
                    quantityCount + 1
                } else {
                    if (quantityCount == 0) {
                        0
                    } else {
                        quantityCount - 1
                    }
                }
                setBackgroundTextView(col_text_view_array_4, i)
                quantityCheck()
            }

            col_text_view_array_5!![i] = row_view_5.findViewById<View>(textIdList[i]) as TextView
            col_text_view_array_5!![i]!!.setOnClickListener { v ->
                col_text_view_array_5!![i]!!.isSelected =
                    !col_text_view_array_5!![i]!!.isSelected
                quantityCount = if (col_text_view_array_5!![i]!!.isSelected) {
                    quantityCount + 1
                } else {
                    if (quantityCount == 0) {
                        0
                    } else {
                        quantityCount - 1
                    }
                }
                setBackgroundTextView(col_text_view_array_5, i)
                quantityCheck()
            }

        }

        val pickDateBtn = findViewById<Button>(R.id.pickDateBtn)


        val calendar = Calendar.getInstance()
        loadingDialog = Dialog(this)
        loadingDialog.setContentView(R.layout.loading_layout)
        loadingDialog.window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        loadingDialog.setCancelable(false)
        val day = calendar.get(Calendar.DAY_OF_MONTH);
        val year = calendar.get(Calendar.YEAR);
        val month = calendar.get(Calendar.MONTH);
        dateString = day.toString() + "/" + (month + 1) + "/" + year
        pickDateBtn.text = dateString
        var datePicker: DatePickerDialog
        pickDateBtn.setOnClickListener {
            datePicker = DatePickerDialog(
                this,
                { view, year, month, dayOfMonth -> // adding the selected date in the edittext
                    pickDateBtn.setText(dayOfMonth.toString() + "/" + (month + 1) + "/" + year)
                    dateString = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                    for (i in 0 until textViewCount) {
                        col_text_view_array_1!![i]!!.isEnabled = true
                        col_text_view_array_2!![i]!!.isEnabled = true
                        col_text_view_array_3!![i]!!.isEnabled = true
                        col_text_view_array_4!![i]!!.isEnabled = true
                        col_text_view_array_5!![i]!!.isEnabled = true

                        col_text_view_array_1!![i]!!.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                        col_text_view_array_2!![i]!!.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                        col_text_view_array_3!![i]!!.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                        col_text_view_array_4!![i]!!.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                        col_text_view_array_5!![i]!!.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                    }
                    quantityCount = 0
                    binding.txtQuality.text = "0"
                    binding.txtTotalPrice.text = "$0"
                    check(movieName!!, loadingDialog, dateString, rbString)
                }, year, month, day
            )
            datePicker.datePicker.minDate = calendar.timeInMillis
            val string_date = "15-April-2024"

            val f = SimpleDateFormat("dd-MMM-yyyy")
            try {
                val d: Date = f.parse(string_date)
                val milliseconds = d.time
                datePicker.datePicker.maxDate = milliseconds
                datePicker.show()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = findViewById<View>(checkedId) as RadioButton
            for (i in 0 until textViewCount) {
                col_text_view_array_1!![i]!!.isEnabled = true
                col_text_view_array_2!![i]!!.isEnabled = true
                col_text_view_array_3!![i]!!.isEnabled = true
                col_text_view_array_4!![i]!!.isEnabled = true
                col_text_view_array_5!![i]!!.isEnabled = true

                col_text_view_array_1!![i]!!.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                col_text_view_array_2!![i]!!.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                col_text_view_array_3!![i]!!.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                col_text_view_array_4!![i]!!.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)
                col_text_view_array_5!![i]!!.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)

            }
            rbString = rb.text.toString()
            check(movieName!!, loadingDialog, dateString, rb.text.toString())

        }
        check(movieName!!, loadingDialog, dateString, "9:00 am")
    }

    private fun check(
        movieName: String,
        loadingDialog: Dialog,
        dateString: String,
        radioStr: String
    ) {
        loadingDialog.show()
        FirebaseFirestore.getInstance().collection("Tickets")
            .document(movieName)
            .collection("Users")
            .get().addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (documentList in list) {
                        if (dateString == documentList.get("date").toString() &&
                            radioStr == documentList.get("time").toString()
                        ) {
                            val resultSeating = documentList.get("seating_no").toString()
                            val lstValues: List<String> = resultSeating.split(",").map { it.trim() }
                            lstValues.forEach {
                                when {
                                    "A" == it[0].toString() -> {
                                        val result: Int = it[1].toString().toInt()
                                        col_text_view_array_1!![result]!!.isEnabled = false
                                        col_text_view_array_1!![result]!!.background =
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.button_default_set_dialog_outline,
                                                null
                                            )
                                    }

                                    "B" == it[0].toString() -> {
                                        val result: Int = it[1].toString().toInt()
                                        col_text_view_array_2!![result]!!.isEnabled = false
                                        col_text_view_array_2!![result]!!.background =
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.button_default_set_dialog_outline,
                                                null
                                            )
                                    }

                                    "C" == it[0].toString() -> {
                                        val result: Int = it[1].toString().toInt()
                                        col_text_view_array_3!![result]!!.isEnabled = false
                                        col_text_view_array_3!![result]!!.background =
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.button_default_set_dialog_outline,
                                                null
                                            )
                                    }

                                    "D" == it[0].toString() -> {
                                        val result: Int = it[1].toString().toInt()
                                        col_text_view_array_4!![result]!!.isEnabled = false
                                        col_text_view_array_4!![result]!!.background =
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.button_default_set_dialog_outline,
                                                null
                                            )
                                    }

                                    "E" == it[0].toString() -> {
                                        val result: Int = it[1].toString().toInt()
                                        col_text_view_array_5!![result]!!.isEnabled = false
                                        col_text_view_array_5!![result]!!.background =
                                            ResourcesCompat.getDrawable(
                                                resources,
                                                R.drawable.button_default_set_dialog_outline,
                                                null
                                            )
                                    }
                                }
                            }
                            quantityCount = 0
                            binding.txtQuality.text = "0"
                            binding.txtTotalPrice.text = "$0"
                        }
                    }
                    loadingDialog.dismiss()
                } else {
                    loadingDialog.dismiss()
                }
            }
    }

    private fun textResult(
        rowName: String,
        col_text_view_array: Array<TextView?>
    ): java.lang.StringBuilder {
        val row_result = StringBuilder()
        for (i in 0 until textViewCount) {
            if (col_text_view_array[i]!!.isSelected) {
                row_result.append("$rowName$i,")
            }
        }
        return row_result
    }

    public fun bookTicket() {
        val selectedId = binding.radioGroup.checkedRadioButtonId

        val radioButton = findViewById<View>(selectedId) as RadioButton

        val SeatingStr = StringBuilder()
        SeatingStr.append(textResult("A", col_text_view_array_1!!))
        SeatingStr.append(textResult("B", col_text_view_array_2!!))
        SeatingStr.append(textResult("C", col_text_view_array_3!!))
        SeatingStr.append(textResult("D", col_text_view_array_4!!))
        SeatingStr.append(textResult("E", col_text_view_array_5!!))

        SeatingStr.deleteCharAt(SeatingStr.length - 1)
        Toast.makeText(
            this,
            SeatingStr.toString(), Toast.LENGTH_LONG
        ).show()
        loadingDialog.show()
        val registerHM: HashMap<String, Any> = HashMap()
        val id = UUID.randomUUID().toString()
        val user_id = FirebaseAuth.getInstance().currentUser!!.uid
        registerHM["movie_name"] = movieName!!
        registerHM["banner_image_url"] = moviePoster!!
        registerHM["user_id"] = user_id
        registerHM["id"] = id
        registerHM["seating_no"] = SeatingStr.toString()
        registerHM["date"] = binding.pickDateBtn.text
        registerHM["time"] = radioButton.text
        registerHM["room"] = room!!
        val totalPrice = price * quantityCount
        registerHM["total_price"] = totalPrice
        registerHM["price"] = price
        registerHM["quantity"] = quantityCount
        FirebaseFirestore.getInstance().collection("Tickets").document(movieName)
            .collection("Users").document(id).set(registerHM)
            .addOnCompleteListener { task1 ->
                if (task1.isSuccessful) {
                    FirebaseFirestore.getInstance().collection("User_Tickets")
                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .collection("Users").document(id).set(registerHM)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Ticket Booked Successfully",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                loadingDialog.dismiss()
                                finish()
                            }
                        }
                }
            }
    }

    private fun quantityCheck() {
        binding.txtQuality.text = quantityCount.toString()
        if (quantityCount > 0) {
            binding.txtTotalPrice.text = "$" + (price * quantityCount).toString()
        } else {
            Toast.makeText(this, "Please Select atleast 1 seat", Toast.LENGTH_LONG).show()
        }
    }

    private fun setBackgroundTextView(textViewArray: Array<TextView?>?, i: Int) {
        if (textViewArray!![i]!!.isSelected) {
            textViewArray[i]!!.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.button_light_set_dialog_outline,
                null
            )

        } else {
            textViewArray[i]!!.background =
                ResourcesCompat.getDrawable(resources, R.drawable.button_outline, null)

        }
    }
}