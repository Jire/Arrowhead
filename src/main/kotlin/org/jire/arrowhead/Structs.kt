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

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import kotlin.reflect.KClass

/**
 * Caching and more for [Struct]s.
 */
object Structs {

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
	operator inline fun <reified T : Struct> get(type: Class<*>, vararg args: Any): T {
		var cached = map[type]
		if (cached == null) {
			cached = (if (args.size > 0) {
				val types = arrayOfNulls<Class<*>>(args.size)
				type.declaredFields.forEachIndexed { i, field -> types[i] = field.type }
				val constructor = type.getDeclaredConstructor(*types)
				constructor.newInstance(*args)
			} else type.newInstance()) as T
			map[type] = cached
		}
		return cached as T
	}

	/**
	 * Gets (and constructs, if necessary) a struct of the specified type using the provided arguments.
	 *
	 * @param type The desired type of the struct.
	 * @param args The arguments to pass to the constructor of the struct.
	 */
	operator inline fun <reified T : Struct> get(type: KClass<T>, vararg args: Any): T = get(type.java, *args)

}

/**
 * Gets (and constructs, if necessary) a struct of the type using the provided arguments.
 *
 * @param args The arguments to pass to the constructor of the struct.
 */
inline operator fun <reified T : Struct> KClass<T>.get(vararg args: Any): T = Structs.get(this, *args)

/**
 * Releases this struct back into the caching pool.
 *
 * After the struct is released you should no longer use it.
 *
 * You should not hold a reference to structs you have released.
 */
fun Struct.release() = apply {
	if (released) throw IllegalStateException("You must renew the struct before releasing it!")

	Structs.map.put(javaClass, this)
	released = true
}

/**
 * Reads at the specified native address into this struct.
 *
 * @param source The source to read from.
 * @param address The native address to read at.
 */
fun Struct.read(source: Source, address: Long) = apply {
	if (!released) throw IllegalStateException("You must release the struct before renewing it!")

	source.read(address, this)
	released = false
}

/**
 * Writes this struct to the specified native address.
 *
 * @param source The source to read from.
 * @param address The native address to write at.
 */
fun Struct.write(source: Source, address: Long) = apply {
	source.write(address, this)
}