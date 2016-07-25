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

/*
   "Windows" is a trademark of Microsoft Corporation.

   The trademark holders are not affiliated with the maker
   of this product and do not endorse this product.
 */

package org.jire.arrowhead.windows

import com.sun.jna.Native
import com.sun.jna.platform.win32.Psapi.MODULEINFO
import com.sun.jna.platform.win32.WinDef
import org.jire.arrowhead.Module

/**
 * Represents a module of a Windows process.
 */
class WindowsModule(override val address: Long, override val process: WindowsProcess,
                    val handle: WinDef.HMODULE, val info: MODULEINFO) : Module {

	override val name by lazy {
		val baseName = ByteArray(256)
		Psapi.INSTANCE.GetModuleBaseNameA(process.handle, handle, baseName, baseName.size)
		Native.toString(baseName)!!
	}

	override val size = info.SizeOfImage.toLong()

}