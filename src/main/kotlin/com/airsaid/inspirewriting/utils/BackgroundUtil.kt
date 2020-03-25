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

package com.airsaid.inspirewriting.utils

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.wm.impl.IdeBackgroundUtil

/**
 * @author airsaid
 */
object BackgroundUtil {
  fun updateBackground(path: String) {
    setBackground(path)
  }

  fun clearBackground() {
    setBackground("")
  }

  private fun setBackground(path: String) {
    val newValue = calcNewValue(path)
    val prop = PropertiesComponent.getInstance()
    prop.setValue(IdeBackgroundUtil.FRAME_PROP, null)
    prop.setValue(IdeBackgroundUtil.EDITOR_PROP, newValue)
    IdeBackgroundUtil.repaintAllWindows()
  }

  private fun calcNewValue(path: String): String? {
    if (path.isEmpty()) {
      return null
    }
    val oldValue = PropertiesComponent.getInstance().getValue(IdeBackgroundUtil.EDITOR_PROP, "")
    val values = oldValue.split(",")
    if (values.isEmpty()) {
      return oldValue
    }
    val oldPath = values[0]
    return oldValue.replace(oldPath, path)
  }
}