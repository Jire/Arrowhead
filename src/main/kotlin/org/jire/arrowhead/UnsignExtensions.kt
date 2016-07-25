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

/**
 * Converts to an unsigned byte represented in an int.
 */
fun Byte.unsign() = java.lang.Byte.toUnsignedInt(this)

/**
 * Converts to an unsigned short represented in an int.
 */
fun Short.unsign() = java.lang.Short.toUnsignedInt(this)

/**
 * Converts to an unsigned int represented in a long.
 */
fun Int.unsign() = java.lang.Integer.toUnsignedLong(this)