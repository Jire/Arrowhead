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

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import kotlin.reflect.KClass

/**
 * Caching and more for [Struct]s.
 */
object StructCache {

	/**
	 * The caching map which maps types to their struct pool.
	 */
	val map: MutableMap<Class<*>, Struct> = Object2ObjectArrayMap<Class<*>, Struct>()

	/**
	 * Gets (and constructs, if necessary) a struct of the specified type using the provided arguments.
	 *
	 * @param type The desired type of the struct.
	 * @param args The arguments to pass to the constructor of the struct.
	 */
	inline operator fun <reified T : Struct> get(type: Class<*>, vararg args: Any): T {
		var struct = map[type]
		if (struct == null) {
			struct = (if (args.isNotEmpty()) {
				val types = type.declaredFields.map { it.type }.toTypedArray()
				val constructor = type.getDeclaredConstructor(*types)
				constructor.newInstance(*args)
			} else {
				type.getDeclaredConstructor().newInstance()
			}) as T
			map[type] = struct
		}
		return struct as T
	}

	/**
	 * Gets (and constructs, if necessary) a struct of the specified type using the provided arguments.
	 *
	 * @param type The desired type of the struct.
	 * @param args The arguments to pass to the constructor of the struct.
	 */
	inline operator fun <reified T : Struct> get(type: KClass<T>, vararg args: Any): T = get(type.java, *args)

}

/**
 * Gets (and constructs, if necessary) a struct of the type using the provided arguments.
 *
 * @param args The arguments to pass to the constructor of the struct.
 */
inline operator fun <reified T : Struct> KClass<T>.get(vararg args: Any)
		= StructCache.get(this, *args) // Explosions can't be passed to get operator... a bug perhaps?
