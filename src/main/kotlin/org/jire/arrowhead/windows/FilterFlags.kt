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

package org.jire.arrowhead.windows

/**
 * Filter flags used for module listing.
 */
object FilterFlags {

	/**
	 * List the 32-bit modules.
	 */
	const val LIST_MODULES_32BIT = 0x01

	/**
	 * List the 64-bit modules.
	 */
	const val LIST_MODULES_64BIT = 0x02

	/**
	 * List all modules.
	 */
	const val LIST_MODULES_ALL = 0x03

	/**
	 * Use the default behavior.
	 */
	const val LIST_MODULES_DEFAULT = 0x0

}