/*
 * Copyright 2019 Airsaid. https://github.com/airsaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.airsaid.inspirewriting.config

import com.intellij.ide.plugins.PluginManager
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.extensions.PluginId
import java.io.File

/**
 * @author airsaid
 */
object PluginConfig {

    private const val PLUGIN_ID = "com.airsaid.InspireWriting"

    private const val DEF_INPUT_COUNT = 100
    private const val DEF_IMAGE_DIR_NAME = "Images"

    private const val KEY_IMAGES_PATH = PLUGIN_ID.plus(".imagesPath")
    private const val KEY_INPUT_COUNT = PLUGIN_ID.plus(".inputCount")

    fun setImagesPath(path: String) = PropertiesComponent.getInstance().setValue(KEY_IMAGES_PATH, path)

    fun getImagesPath(): String = PropertiesComponent.getInstance()
        .getValue(KEY_IMAGES_PATH, getDefaultImagesPath())

    fun setInputCount(count: Int) = PropertiesComponent.getInstance()
        .setValue(KEY_INPUT_COUNT, count, getDefaultInputCount())

    fun getInputCount(): Int = PropertiesComponent.getInstance()
        .getInt(KEY_INPUT_COUNT, DEF_INPUT_COUNT)

    private fun getDefaultImagesPath(): String {
        val path = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID))?.path?.path ?: ""
        val dir = File(path, DEF_IMAGE_DIR_NAME)
        if (!dir.exists()) dir.mkdirs()
        return dir.path
    }

    private fun getDefaultInputCount(): Int = DEF_INPUT_COUNT
}