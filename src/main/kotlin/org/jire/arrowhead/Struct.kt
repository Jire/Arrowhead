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

import com.sun.jna.Structure

/**
 * Represents a native struct, with the ability to be reused via [StructCache].
 *
 * All fields which are to be used as native members must be annotated with [@JvmField][kotlin.jvm.JvmField].
 */
abstract class Struct : Structure() {

	/**
	 * Whether or not the struct has been released.
	 */
	@Volatile var released = false
		private set

	/**
	 * Releases this struct back into the caching pool.
	 *
	 * After the struct is released you should no longer use it.
	 *
	 * You should not hold a reference to structs you have released.
	 */
	fun release() = apply {
		if (released) throw IllegalStateException("You must renew the struct before releasing it!")

		StructCache.map.put(javaClass, this)
		released = true
	}

	/**
	 * Reads at the specified native address into this struct.
	 *
	 * @param source The source to read from.
	 * @param address The native address to read at.
	 */
	fun read(source: Source, address: Long) = apply {
		if (!released) throw IllegalStateException("You must release the struct before renewing it!")

		source.read(address, this)
		released = false
	}

	/**
	 * Reads at the specified native address into this struct.
	 *
	 * @param source The source to read from.
	 * @param address The native address to read at.
	 */
	fun read(source: Source, address: Int) = read(source, address.toLong())

	/**
	 * Writes this struct to the specified native address.
	 *
	 * @param source The source to read from.
	 * @param address The native address to write at.
	 */
	fun write(source: Source, address: Long) = apply {
		source.write(address, this)
	}

	/**
	 * Writes this struct to the specified native address.
	 *
	 * @param source The source to read from.
	 * @param address The native address to write at.
	 */
	fun write(source: Source, address: Int) = write(source, address.toLong())

	override fun getFieldOrder(): List<String> = javaClass.declaredFields.map { it.name }

}