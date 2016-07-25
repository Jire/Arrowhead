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

package org.jire.arrowhead.windows

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary

/**
 * Provides a standard calling library interface for `Psapi`.
 */
interface Psapi : StdCallLibrary {

	/**
	 * Retrieves a handle for each module in the specified process that meets the specified filter criteria.
	 * @param hProcess A handle to the process.
	 * @param lphModule An array that receives the list of module handles.
	 * @param lpcbNeeded The number of bytes to store all module handles in the _lphModule_ array.
	 * @param dwFilterFlag The filter criteria.
	 */
	fun EnumProcessModulesEx(hProcess: WinNT.HANDLE, lphModule: Array<WinDef.HMODULE?>, cb: Int,
	                         lpcbNeeded: IntByReference, dwFilterFlag: Int = FilterFlags.LIST_MODULES_ALL): Boolean

	/**
	 * Retrieves the base name of the specified module.
	 */
	fun GetModuleBaseNameA(hProcess: WinNT.HANDLE, hModule: WinDef.HMODULE,
	                       lpBaseName: ByteArray, nSize: Int): Int

	companion object {

		/**
		 * The loaded instance of our `Psapi` standard call library.
		 *
		 * Use this to actually call the native functions.
		 */
		val INSTANCE = Native.loadLibrary("Psapi", Psapi::class.java)!!

	}

}