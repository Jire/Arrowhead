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
import com.sun.jna.platform.win32.Win32Exception
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.jire.arrowhead.Process
import com.sun.jna.platform.win32.Kernel32.INSTANCE as JNAKernel32
import com.sun.jna.platform.win32.Psapi.INSTANCE as JNAPsapi
import org.jire.arrowhead.windows.Psapi.Companion.INSTANCE as Psapi

/**
 * Represents a process on Windows.
 */
class WindowsProcess(override val id: Int, val handle: WinNT.HANDLE) : Process {

	override val modules by lazy {
		val map = Object2ObjectArrayMap<String, WindowsModule>()

		val modules = arrayOfNulls<WinDef.HMODULE>(1024)
		val needed = IntByReference()
		if (Psapi.EnumProcessModulesEx(handle, modules, modules.size, needed)) {
			for (i in 0..needed.value / Native.getNativeSize(WinDef.HMODULE::class.java)) {
				val hModule = modules[i] ?: continue
				val info = MODULEINFO()
				if (JNAPsapi.GetModuleInformation(handle, hModule, info, info.size())) {
					val address = Pointer.nativeValue(hModule.pointer)
					val module = WindowsModule(address, this, hModule, info)
					map.put(module.name, module)
				}
			}
		}

		return@lazy map
	}

	override fun read(address: Long, pointer: Pointer, bytesToRead: Int) {
		if (Kernel32.ReadProcessMemory(handle.pointer, address, pointer, bytesToRead, 0) <= 0)
			throw Win32Exception(Native.getLastError())
	}

	override fun write(address: Long, pointer: Pointer, bytesToWrite: Int) {
		if (Kernel32.WriteProcessMemory(handle.pointer, address, pointer, bytesToWrite, 0) <= 0)
			throw Win32Exception(Native.getLastError())
	}

}