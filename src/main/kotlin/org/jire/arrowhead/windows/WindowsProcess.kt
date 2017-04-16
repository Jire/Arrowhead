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

/*
 * "Windows" is a trademark of Microsoft Corporation.
 *
 * The trademark holders are not affiliated with the maker
 * of this product and do not endorse this product.
 */

package org.jire.arrowhead.windows

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Psapi.MODULEINFO
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.jire.arrowhead.Process
import java.util.*
import com.sun.jna.platform.win32.Kernel32.INSTANCE as JNAKernel32
import com.sun.jna.platform.win32.Psapi.INSTANCE as JNAPsapi
import org.jire.arrowhead.windows.Psapi.Companion.INSTANCE as Psapi

/**
 * Represents a process on Windows.
 */
class WindowsProcess(override val id: Int, val handle: WinNT.HANDLE) : Process {

	private val modulesMap = Collections.synchronizedMap(Object2ObjectArrayMap<String, WindowsModule>())

	override fun loadModules() {
		modulesMap.clear()

		val modules = arrayOfNulls<WinDef.HMODULE>(4096)
		val needed = IntByReference()
		if (Psapi.EnumProcessModulesEx(handle, modules, modules.size, needed)) {
			for (i in 0..needed.value / Native.getNativeSize(WinDef.HMODULE::class.java)) {
				val hModule = modules[i] ?: continue
				val info = MODULEINFO()
				if (JNAPsapi.GetModuleInformation(handle, hModule, info, info.size())) {
					val address = Pointer.nativeValue(hModule.pointer)
					val module = WindowsModule(address, this, hModule, info)
					modulesMap.put(module.name, module)
				}
			}
		}
	}

	override val modules: Map<String, WindowsModule> = modulesMap

	override fun read(address: Pointer, data: Pointer, bytesToRead: Int)
			= Kernel32.ReadProcessMemory(handle.pointer, address, data, bytesToRead, 0) > 0

	override fun write(address: Pointer, data: Pointer, bytesToWrite: Int)
			= Kernel32.WriteProcessMemory(handle.pointer, address, data, bytesToWrite, 0) > 0

}
