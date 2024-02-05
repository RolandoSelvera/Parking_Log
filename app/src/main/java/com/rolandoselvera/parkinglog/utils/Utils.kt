package com.rolandoselvera.parkinglog.utils

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rolandoselvera.parkinglog.R
import com.rolandoselvera.parkinglog.data.models.Car
import com.rolandoselvera.parkinglog.data.models.CarEntity
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

/**
 * Obtain JsonString from object
 *
 * @param data Object to serialize
 * @return json string from Object
 */
fun <T> serialize(data: T): String? {
    val mGson = Gson()

    val json: String? = try {
        mGson.toJson(data)
    } catch (e: JsonSyntaxException) {
        e.printStackTrace()
        null
    }

    return json
}

/**
 * Obtain Object from jsonString
 *
 * @param json String to convert in Object
 * @param typeClass Object class to convert
 *
 * @return Object from json string
 */
fun <T> deserialize(json: String, typeClass: Class<T>): T? {
    val mGson = Gson()

    val data: T? = try {
        mGson.fromJson(json, typeClass)
    } catch (e: JsonSyntaxException) {
        e.printStackTrace()
        null
    }

    return data
}

fun Car?.toCarEntity(): CarEntity? {
    return this?.let {
        CarEntity(
            id = it.id,
            brand = it.brand,
            model = it.model,
            carPlate = it.carPlate,
            color = it.color,
            owner = it.owner,
            frontSide = it.frontSide,
            backSide = it.backSide,
            leftSide = it.leftSide,
            rightSide = it.rightSide
        )
    }
}

fun EditText.validateAndSetError(
    textInputLayout: TextInputLayout
): Boolean {
    var error = false

    this.validator().nonEmpty().addErrorCallback {
        textInputLayout.isErrorEnabled = true
        if (it.contains("empty", true)) {
            textInputLayout.error = context.getString(R.string.required_field)
        } else {
            textInputLayout.error = context.getString(R.string.required_field)
        }
        error = true
    }.addSuccessCallback {
        textInputLayout.isErrorEnabled = false
        textInputLayout.error = null
    }.check()

    return error
}

private fun EditText.setFieldValue(value: String?) {
    this.setText(value)
}
