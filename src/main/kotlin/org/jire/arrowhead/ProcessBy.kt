/*
 * Copyright 2016 Thomas Nappo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jire.arrowhead

import com.sun.jna.Platform
import org.jire.arrowhead.windows.Windows

/**
 * Attempts to open a process of the specified process ID.
 *
 * @param processID The ID of the process to open.
 */
fun processByID(processID: Int): Process? = when {
	Platform.isWindows() || Platform.isWindowsCE() -> Windows.openProcess(processID)
	else -> null
}

/**
 * Attempts to open a process of the specified process name.
 *
 * @param processName The name of the process to open.
 */
fun processByName(processName: String): Process? = when {
	Platform.isWindows() || Platform.isWindowsCE() -> Windows.openProcess(processName)
	else -> null
}