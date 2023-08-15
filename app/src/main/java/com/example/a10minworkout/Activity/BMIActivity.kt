package com.example.a10minworkout.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a10minworkout.R
import com.example.a10minworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode


class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }
    private var currentVisibleView : String = METRIC_UNITS_VIEW

    private var binding:ActivityBmiactivityBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=  ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarActivityBmi)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarActivityBmi?.setNavigationOnClickListener{
                onBackPressedDispatcher.onBackPressed()
        }

        makeVisibleMetricUnitView()

        binding?.rgUnits?.setOnCheckedChangeListener{_,checkedId : Int ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }
            else{
                makeVisibleUsUnitView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsUnitWeight?.visibility = View.GONE
        binding?.tilUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilUsUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.llUsUnitsView?.visibility = View.VISIBLE

        binding?.tilMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUnitHeight?.visibility = View.GONE
        binding?.tilUsUnitWeight?.visibility = View.VISIBLE
        binding?.tilUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsUnitHeightFeet?.text!!.clear()
        binding?.etUsUnitHeightInch?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBmiResult(bmi:Float){

        val bmiLabel : String
        val bmiDescription : String
        if(bmi.compareTo(15f)<= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "You need a better health and nutrition"
        }
        else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "You need a better health and nutrition"
        }
        else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "You need a better health and nutrition"
        }
        else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "You have good BMI"
        }
        else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "You need a better diet and nutrition"
        }
        else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Severely overweight"
            bmiDescription = "You need a better diet"
        }
        else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Very Severely overweight"
            bmiDescription = "You need a good diet "
        }
        else{
            bmiLabel = "Very Severely Obese overweight"
            bmiDescription = "Warning!! You need a good diet "
        }

        val bmiValue  = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits():Boolean{
        var isValid =true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits():Boolean{
        var isValid =true

        if(binding?.etUsUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etUsUnitHeightFeet?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etUsUnitHeightInch?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue :Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val weightValue :Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBmiResult(bmi)
            }
            else{
                Toast.makeText(this,"Enter valid values",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if(validateUsUnits()){
                val heightValueFeet :String = binding?.etUsUnitHeightFeet?.text.toString()
                val heightValueInch :String = binding?.etUsUnitHeightInch?.text.toString()

                val usWeightValue :Float = binding?.etUsUnitWeight?.text.toString().toFloat()

                val heightValue = heightValueInch.toFloat() + heightValueFeet.toFloat() * 12

                val bmi = 703 * (usWeightValue / (heightValue * heightValue))
                displayBmiResult(bmi)
            }
            else{
                Toast.makeText(this,"Enter valid values",Toast.LENGTH_SHORT).show()
            }
        }
    }
}