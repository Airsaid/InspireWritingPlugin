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

package com.airsaid.inspirewriting

import com.airsaid.inspirewriting.config.PluginConfig
import com.airsaid.inspirewriting.handler.ListenerActionHandler
import com.airsaid.inspirewriting.utils.BackgroundUtil
import com.airsaid.inspirewriting.utils.NotificationUtil
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.ProjectManager
import java.io.File

/**
 * @author airsaid
 */
class InspireWriting : BaseComponent {

  private val imagesPaths = mutableListOf<String>()
  private lateinit var handler: ListenerActionHandler

  override fun initComponent() {
    super.initComponent()
    val typedAction = EditorActionManager.getInstance().typedAction
    handler = ListenerActionHandler(typedAction.handler, PluginConfig.getInputCount()) {
      changeBackgroundImage()
    }
    typedAction.setupHandler(handler)
    updateImagesPath()
  }

  fun setInputCount(count: Int) {
    handler.setMaxCount(count)
  }

  fun updateImagesPath() {
    val project = ProjectManager.getInstance().defaultProject
    ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Find images...", false) {
      override fun run(indicator: ProgressIndicator) {
        val imagesDir = File(PluginConfig.getImagesPath())
        imagesPaths.clear()
        imagesPaths.addAll(imagesDir.walk()
          .maxDepth(1)
          .filter { it.isFile }
          .filter { it.extension in listOf("png", "jpg", "jpeg") }
          .map {
            indicator.text = "Found ${it.name} image..."
            it.path
          })

        if (imagesPaths.isEmpty()) {
          NotificationUtil.notifyWarning(
            "The image file is not found in the path. Please check ${imagesDir.path} path."
          )
        }
      }
    })
  }

  private fun changeBackgroundImage() {
    if (imagesPaths.isEmpty()) return

    val path = imagesPaths.random()
    BackgroundUtil.updateBackground(path)
  }

  override fun disposeComponent() {
    super.disposeComponent()
    handler.dispose()
  }

}