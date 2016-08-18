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
import org.jire.arrowhead.windows.User32

/**
 * Returns the key state of the specified virtual key code.
 *
 * @param virtualKeyCode The key code of which to check the state.
 * @throws UnsupportedOperationException If the platform is not supported.
 */
fun keyState(virtualKeyCode: Int): Int = when {
	Platform.isWindows() || Platform.isWindowsCE() -> User32.GetKeyState(virtualKeyCode).toInt()
	else -> throw UnsupportedOperationException("Unsupported platform (osType=${Platform.getOSType()}")
}

/**
 * Checks whether or not the key state of the specified virtual key code is pressed.
 *
 * @param virtualKeyCode The key code of which to check the state.
 * @return `true` if the key is pressed, `false` otherwise.
 * @throws UnsupportedOperationException If the platform is not supported.
 */
fun keyPressed(virtualKeyCode: Int) = keyState(virtualKeyCode) < 0

/**
 * Checks whether or not the key state of the specified virtual key code is pressed,
 * then runs the specified action code block.
 *
 * @param virtualKeyCode The key code of which to check the state.
 * @param action The code to run if the key is pressed.
 * @return `true` if the key is pressed, `false` otherwise.
 * @throws UnsupportedOperationException If the platform is not supported.
 */
inline fun keyPressed(virtualKeyCode: Int, action: () -> Unit) {
	if (keyPressed(virtualKeyCode)) action()
}

/**
 * Checks whether or not the key state of the specified virtual key code is released (not pressed).
 *
 * @param virtualKeyCode The key code of which to check the state.
 * @return `false` if the key is pressed, `true` otherwise.
 * @throws UnsupportedOperationException If the platform is not supported.
 */
fun keyReleased(virtualKeyCode: Int) = !keyPressed(virtualKeyCode)

/**
 * Checks whether or not the key state of the specified virtual key code is released (not pressed),
 * then runs the specified action code block.
 *
 * @param virtualKeyCode The key code of which to check the state.
 * @param action The code to run if the key is released (not pressed).
 * @return `false` if the key is pressed, `true` otherwise.
 * @throws UnsupportedOperationException If the platform is not supported.
 */
inline fun keyReleased(virtualKeyCode: Int, action: () -> Unit) {
	if (keyReleased(virtualKeyCode)) action()
}