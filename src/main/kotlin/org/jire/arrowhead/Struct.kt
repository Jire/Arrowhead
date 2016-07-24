/*
   Copyright 2016 Thomas Nappo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package org.jire.arrowhead

import com.sun.jna.Structure

/**
 * Represents a native struct, with the ability to be reused via [Structs].
 *
 * All fields which are to be used as native members must be annotated with [@JvmField][kotlin.jvm.JvmField].
 */
abstract class Struct : Structure() {

	/**
	 * Whether or not the struct has been released.
	 */
	@Volatile var released = false
		internal set

	override fun getFieldOrder(): List<String> = javaClass.declaredFields.map { it.name }

}