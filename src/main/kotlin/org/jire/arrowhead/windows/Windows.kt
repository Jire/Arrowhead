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
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.platform.win32.Kernel32.INSTANCE as JNAKernel32

/**
 * Utility functions for dealing with native processes on Windows.
 */
object Windows {

	/**
	 * Reusable DWORD of value zero; not intended to be mutated.
	 */
	val DWORD_ZERO = WinDef.DWORD(0)

	/**
	 * Opens a native process on Windows by the specified process ID, given the specified access flags.
	 *
	 * @param processID The process ID of the process to open.
	 * @param accessFlags The access permission flags given to the process.
	 */
	fun openProcess(processID: Int, accessFlags: Int = WinNT.PROCESS_ALL_ACCESS): WindowsProcess {
		val handle = JNAKernel32.OpenProcess(accessFlags, true, processID)
		return WindowsProcess(processID, handle)
	}

	/**
	 * Opens a native process on Windows of the specified process name.
	 *
	 * @param processName The process name of the process to open.
	 */
	fun openProcess(processName: String): WindowsProcess? {
		val snapshot = JNAKernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPALL, DWORD_ZERO)
		val entry = Tlhelp32.PROCESSENTRY32.ByReference() // we reuse the same entry during iteration
		try {
			while (JNAKernel32.Process32Next(snapshot, entry)) {
				val fileName = Native.toString(entry.szExeFile)
				if (processName.equals(fileName)) return openProcess(entry.th32ProcessID.toInt())
			}
		} finally {
			JNAKernel32.CloseHandle(snapshot)
		}
		return null
	}

}