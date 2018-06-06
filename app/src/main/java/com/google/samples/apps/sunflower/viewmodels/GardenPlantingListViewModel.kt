/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.viewmodels

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.samples.apps.sunflower.data.GardenPlanting
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings

class GardenPlantingListViewModel internal constructor(
        private val gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {

    private val gardenPlantings: MediatorLiveData<List<GardenPlanting>> = MediatorLiveData()
    private val plantAndGardenPlantings: MediatorLiveData<List<PlantAndGardenPlantings>> = MediatorLiveData()

    init {
        val liveGardenPlantings = gardenPlantingRepository.getGardenPlantings()
        gardenPlantings.addSource(liveGardenPlantings, gardenPlantings::setValue)

        val livePlantAndGardenPlantings = Transformations.map(gardenPlantingRepository.getPlantAndGardenPlantings()) {
            it.filter { it.gardenPlantings.isNotEmpty() }
        }
        plantAndGardenPlantings.addSource(livePlantAndGardenPlantings, plantAndGardenPlantings::setValue)
    }

    fun getGardenPlantings() = gardenPlantings

    fun getPlantAndGardenPlantings() = plantAndGardenPlantings
}