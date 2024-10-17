package com.example.taiga_okuma_stressmeter.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.taiga_okuma_stressmeter.R
import com.example.taiga_okuma_stressmeter.util.CSVUtil
import com.example.taiga_okuma_stressmeter.data.StressData

class StressViewModel : ViewModel() {

    // Track the current page of images
    var currentPage by mutableStateOf(0)
        private set

    // Track the selected stress level
    var selectedStressLevel by mutableStateOf(-1)
        private set

    // Images per page
    val imagesPerPage = 16


    // All images
    val allImages = listOf(
        R.drawable.fish_normal017,
        R.drawable.psm_alarm_clock,
        R.drawable.psm_alarm_clock2,
        R.drawable.psm_angry_face,
        R.drawable.psm_anxious,
        R.drawable.psm_baby_sleeping,
        R.drawable.psm_bar,
        R.drawable.psm_barbed_wire2,
        R.drawable.psm_beach3,
        R.drawable.psm_bird3,
        R.drawable.psm_blue_drop,
        R.drawable.psm_cat,
        R.drawable.psm_clutter,
        R.drawable.psm_clutter3,
        R.drawable.psm_dog_sleeping,
        R.drawable.psm_exam4,
        R.drawable.psm_gambling4,
        R.drawable.psm_headache,
        R.drawable.psm_headache2,
        R.drawable.psm_hiking3,
        R.drawable.psm_kettle,
        R.drawable.psm_lake3,
        R.drawable.psm_lawn_chairs3,
        R.drawable.psm_lonely,
        R.drawable.psm_lonely2,
        R.drawable.psm_mountains11,
        R.drawable.psm_neutral_child,
        R.drawable.psm_neutral_person2,
        R.drawable.psm_peaceful_person,
        R.drawable.psm_puppy,
        R.drawable.psm_puppy3,
        R.drawable.psm_reading_in_bed2,
        R.drawable.psm_running3,
        R.drawable.psm_running4,
        R.drawable.psm_sticky_notes2,
        R.drawable.psm_stressed_cat,
        R.drawable.psm_stressed_person,
        R.drawable.psm_stressed_person12,
        R.drawable.psm_stressed_person3,
        R.drawable.psm_stressed_person4,
        R.drawable.psm_stressed_person6,
        R.drawable.psm_stressed_person7,
        R.drawable.psm_stressed_person8,
        R.drawable.psm_talking_on_phone2,
        R.drawable.psm_to_do_list,
        R.drawable.psm_to_do_list3,
        R.drawable.psm_wine3,
        R.drawable.psm_work4,
        R.drawable.psm_yoga4
    )

    // Stress data storage
    var stressDataList by mutableStateOf<List<StressData>>(emptyList())
        private set

    // Function to return stress data
    fun getStressData(): List<StressData> = stressDataList

    fun addStressData(context: Context, timestamp: String, stressLevel: Int) {
        // Add data to the in-memory list
        stressDataList = stressDataList + StressData(timestamp, stressLevel)

        // Call utility function to save the data to CSV
        CSVUtil.saveStressData(context, StressData(timestamp, stressLevel))
    }



    // Function to load stress data from CSV
    fun loadStressData(context: Context) {
        stressDataList = CSVUtil.loadStressData(context)
    }

    // Get the current set of images (for the current page)
    val currentImages: List<Int>
        get() = allImages.drop(currentPage * imagesPerPage).take(imagesPerPage)

    // Check if there are more images to load
    val hasMoreImages: Boolean
        get() = (currentPage + 1) * imagesPerPage < allImages.size

    // Select stress level based on image click
    fun selectStressLevel(level: Int) {
        selectedStressLevel = level
    }

    // Load the next set of images
    fun loadNextPage() {
        if (hasMoreImages) {
            currentPage++
        }
    }

}
