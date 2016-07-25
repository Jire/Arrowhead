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

import com.sun.jna.Pointer

/**
 * Remember reading `Process`' documentation? No..? Well go read it first.
 *
 * Anyway, a module is a native library linked (usually dynamically like with a DLL) to a process.
 *
 * This type doubles as an `Addressed` which handles the offsetting for its `Source` operations.
 * All of the `Source` operations are done using its parent process.
 */
interface Module : Source, Addressed {

	/**
	 * The process of which this module belongs to.
	 */
	val process: Process

	/**
	 * The name of the module.
	 */
	val name: String

	/**
	 * The size of the module in bytes.
	 */
	val size: Long

	override fun read(address: Long, pointer: Pointer, bytesToRead: Int)
			= process.read(offset(address), pointer, bytesToRead)

	override fun write(address: Long, pointer: Pointer, bytesToWrite: Int)
			= process.write(offset(address), pointer, bytesToWrite)

}