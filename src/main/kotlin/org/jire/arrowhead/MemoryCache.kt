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

import com.sun.jna.Memory
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap

/**
 * Fast memory caching using a fast array map.
 *
 * Not thread safe. Do not use results after they have been reused.
 */
object MemoryCache {

	/**
	 * The maximum size of a memory in bytes to cache.
	 */
	const val CACHE_BYTE_MAX = 8

	/**
	 * The resource map cache, mapping size in bytes to memory.
	 */
	private val map = Int2ObjectArrayMap<Memory>()

	/**
	 * Returns a zeroed-out memory of the specified size in bytes.
	 *
	 * If the size meets the cached size limit, it will be reused.
	 *
	 * @param size The desired amount of bytes of the memory.
	 * @param clear Whether or not to clear (zero-out) the returned memory. (By default this is `false`.)
	 */
	operator fun get(size: Int, clear: Boolean = false): Memory {
		var memory = map.get(size)
		if (memory == null) {
			memory = Memory(size.toLong())
			if (size <= CACHE_BYTE_MAX)
				map.put(size, memory)
		} else if (clear) memory.clear()
		return memory
	}

}