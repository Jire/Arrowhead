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

import com.sun.jna.Pointer

/**
 * Writes to the source at the specified address using a supplied memory.
 *
 * @param address The native address to write to.
 * @param bytes The amount of bytes for the supplied memory.
 * @param writeBody Applies the write to the pointer.
 */
inline fun Source.write(address: Long, bytes: Int, writeBody: Pointer.() -> Unit) {
	val resource = MemoryCache[bytes]
	resource.writeBody()
	write(address, resource)
}

/**
 * Writes to the source at the specified address using a supplied memory.
 *
 * @param address The native address to write to.
 * @param bytes The amount of bytes for the supplied memory.
 * @param writeBody Applies the write to the pointer.
 */
inline fun Source.write(address: Int, bytes: Int, writeBody: Pointer.() -> Unit)
		= write(address.toLong(), bytes, writeBody)

/**
 * Reads from the source at the specified address with an implicit return type.
 *
 * @param address The native address to read from.
 * @param offset The offset in bytes off the native address.
 * @param T The implicit return type of one of the following:
 *                 * Byte
 *                 * Short
 *                 * Char
 *                 * Int
 *                 * Long
 *                 * Float
 *                 * Double
 *                 * Boolean
 */
inline operator fun <reified T : Any> Source.get(address: Long, offset: Long = 0) = when (T::class.java) {
	java.lang.Byte::class.java -> byte(address, offset)
	java.lang.Short::class.java -> short(address, offset)
	java.lang.Character::class.java -> char(address, offset)
	java.lang.Integer::class.java -> int(address, offset)
	java.lang.Long::class.java -> long(address, offset)
	java.lang.Float::class.java -> float(address, offset)
	java.lang.Double::class.java -> double(address, offset)
	java.lang.Boolean::class.java -> boolean(address, offset)
	else -> throw IllegalArgumentException()
} as T

/**
 * Reads from the source at the specified address with an implicit return type.
 *
 * @param address The native address to read from.
 * @param offset The offset in bytes off the native address.
 * @param T The implicit return type of one of the following:
 *                 * Byte
 *                 * Short
 *                 * Char
 *                 * Int
 *                 * Long
 *                 * Float
 *                 * Double
 *                 * Boolean
 */
inline operator fun <reified T : Any> Source.get(address: Int, offset: Long = 0): T
		= get(address.toLong(), offset)