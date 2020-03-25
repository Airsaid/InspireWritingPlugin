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

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

/**
 * @author airsaid
 */
object NotificationUtil {
  fun notifyWarning(content: String) {
    val n = Notification(
      "Inspire Writing",
      "Warning",
      content,
      NotificationType.WARNING
    )
    Notifications.Bus.notify(n)
  }
}