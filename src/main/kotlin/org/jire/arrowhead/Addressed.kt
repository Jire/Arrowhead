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

/**
 * Something that has an address and whose address can be used as a base to offset another.
 */
interface Addressed {

	/**
	 * The address.
	 */
	val address: Long

	/**
	 * Offsets the base address by the specified offset.
	 *
	 * @param offset The offset in bytes off the base address.
	 */
	fun offset(offset: Long) = address + offset

	/**
	 * Offsets the base address by the specified offset.
	 *
	 * @param offset The offset in bytes off the base address.
	 */
	fun offset(offset: Int) = offset(offset.toLong())

}