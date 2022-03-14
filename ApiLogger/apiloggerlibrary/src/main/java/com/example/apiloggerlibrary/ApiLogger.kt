package com.example.apiloggerlibrary

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject

class ApiLogger {

    private val gson = Gson()
    fun printData(dataModel: DomainEntity<out Any>?, isData: Boolean) {
        dataModel?.data?.let {
            val jsonObject = JSONObject(gson.toJson(dataModel))
            val dtoName = it.javaClass.name.split(".").toTypedArray()
            var logString = "Code : ${dataModel.code}\nMsg  : ${dataModel.message}\n" +
                    "Type : ${dtoName[dtoName.size - 1]}\n\n"

            if(isData) {
                logString +=
                    printDataSample(jsonObject.getString("data").toString(),0)
            }

            Log.d("apiLogger",logString)
        } ?: Log.d("apiLogger", "dataModel is null")
    }

    private val splitList = listOf( "\",\"", "},", "}", "{", "[", "]")
    private var insertPrefix = ""
    private var insertSuffix = ""

    private fun printDataSample(text: String, splitIndex: Int): String {
        var resultString = ""

        val splitString = text.split(splitList[splitIndex]).toTypedArray()

        for (index in splitString.indices) {
            setInsertTab(splitList[splitIndex])
            resultString += insertPrefix + splitString[index] + insertSuffix
        }

        return if(splitIndex != splitList.size - 1) {
            printDataSample(resultString, splitIndex+1)
        } else {
            resultString
        }
    }

    fun setInsertTab(split: String) {

        when (split) {
            "}" -> {
                insertPrefix = "}"
                insertSuffix = "\n"
            }
            "{" -> {
                insertPrefix = "{\n"
                insertSuffix = "\n"
            }
            "]" -> {
                insertPrefix = "]"
                insertSuffix = "\n"
            }
            "[" -> {
                insertPrefix = "[\n"
                insertSuffix = "\n"
            }
            "}," -> {
                insertPrefix = "\n"
                insertSuffix = ""
            }
            "\",\"" -> {
                insertPrefix = ""
                insertSuffix = "\"\n\""
            }
        }
    }
}