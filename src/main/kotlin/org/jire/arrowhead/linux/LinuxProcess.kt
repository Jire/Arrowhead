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

package org.jire.arrowhead.linux

import com.sun.jna.Pointer
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import java.lang.Long.parseLong
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class LinuxProcess(override val id: Int) : Process {

	private val local = ThreadLocal.withInitial { iovec() }
	private val remote = ThreadLocal.withInitial { iovec() }

	private val modulesMap = Collections.synchronizedMap(Object2ObjectArrayMap<String, LinuxModule>())

	override val modules: Map<String, Module> = modulesMap

	override fun loadModules() {
		modulesMap.clear()

		for (line in Files.readAllLines(Paths.get("/proc/$id/maps"))) {
			val split = line.split(" ")
			val regionSplit = split[0].split("-")

			val start = parseLong(regionSplit[0], 16)
			val end = parseLong(regionSplit[1], 16)

			val offset = parseLong(split[2], 16)
			if (offset <= 0) continue

			var path = "";
			var i = 5
			while (i < split.size) {
				val s = split[i].trim { it <= ' ' }
				if (s.isEmpty() && ++i > split.size) break
				else if (s.isEmpty() && !split[i].trim { it <= ' ' }.isEmpty()) path += split[i]
				else if (!s.isEmpty()) path += split[i]
				i++
			}

			val moduleName = path.substring(path.lastIndexOf("/") + 1, path.length)
			modulesMap.put(moduleName, LinuxModule(start, this, moduleName, end - start))
		}
	}

	override fun read(address: Pointer, data: Pointer, bytesToRead: Int): Boolean {
		val local = local.get()
		local.iov_base = data
		local.iov_len = bytesToRead

		val remote = remote.get()
		remote.iov_base = address
		remote.iov_len = bytesToRead

		return bytesToRead.toLong() == uio.process_vm_readv(id, local, 1, remote, 1, 0)
	}

	override fun write(address: Pointer, data: Pointer, bytesToWrite: Int): Boolean {
		val local = local.get()
		local.iov_base = data
		local.iov_len = bytesToWrite

		val remote = remote.get()
		remote.iov_base = address
		remote.iov_len = bytesToWrite

		return bytesToWrite.toLong() == uio.process_vm_writev(id, local, 1, remote, 1, 0)
	}

}