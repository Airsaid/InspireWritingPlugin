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

package com.airsaid.inspirewriting.handler

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author airsaid
 */
class ListenerActionHandler(
  private val oldHandler: TypedActionHandler,
  private var maxCount: Int,
  private val callback: () -> Unit
) : TypedActionHandler {

  private var count = 0
  private val delayTask = SingleDelayTask()

  override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
    oldHandler.execute(editor, charTyped, dataContext)
    val text = charTyped.toString().trim()
    if (text.isEmpty()) return

    if (count < maxCount) count += 1

    if (count >= maxCount) {
      delayTask.schedule(2L, TimeUnit.SECONDS) {
        callback.invoke()
        count = 0
      }
    }
  }

  fun setMaxCount(maxCount: Int) {
    this.maxCount = maxCount
    this.count = 0
  }

  fun dispose() {
    delayTask.shutdown()
  }
}

class SingleDelayTask {

  private val executor = ScheduledThreadPoolExecutor(1)

  fun schedule(delay: Long, unit: TimeUnit, command: () -> Unit) {
    if (executor.queue.isNotEmpty()) {
      executor.queue.clear()
    }
    executor.schedule(command, delay, unit)
  }

  fun shutdown() {
    executor.shutdown()
  }

}