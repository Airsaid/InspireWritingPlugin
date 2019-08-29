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

import com.airsaid.inspirewriting.InspireWriting
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

/**
 * @author airsaid
 */
class InspireWritingConfigurable : Configurable {

    private lateinit var rootPanel: JPanel
    private lateinit var imagePath: TextFieldWithBrowseButton
    private lateinit var inputCount: JSpinner

    override fun getDisplayName(): String {
        return "Inspire Writing"
    }

    override fun isModified(): Boolean {
        val path = imagePath.text
        return path.isNotEmpty()
    }

    override fun apply() {
        val path = imagePath.text
        val count = inputCount.value.toString().toInt()
        PluginConfig.setImagesPath(path)
        PluginConfig.setInputCount(count)

        ApplicationManager
            .getApplication()
            .getComponent(InspireWriting::class.java)
            .updateImagesPath()
    }

    override fun reset() {
        imagePath.text = PluginConfig.getImagesPath()
        inputCount.value = PluginConfig.getInputCount()
        updateUI()
    }

    override fun createComponent(): JComponent? {
        imagePath.isEditable = false
        val singleFolderDesc = FileChooserDescriptorFactory.createSingleFolderDescriptor()
        imagePath.addBrowseFolderListener(object : TextBrowseFolderListener(singleFolderDesc) {})
        inputCount.model = SpinnerNumberModel(PluginConfig.getInputCount(), 10, Int.MAX_VALUE, 10)
        return rootPanel
    }

    private fun updateUI() {
        imagePath.updateUI()
        inputCount.updateUI()
    }

}