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
 * Rather than be useful and upfront, let's go the scenic route.
 *
 * Ensure of the following:
 *     * You understand what an operating system is
 *     * You're familiar with said operating system's process manager
 *
 * K, we're good.
 *
 * _**At heart**_, this is a magical time-travel vortex that will take you to the next dimension.
 *
 * _**At soul**_, this is like a butler that doesn't care what you ask because
 * he hires his own butler who actually fulfills your requests.
 */
interface Process : Source {

	/**
	 * The processes' special snowflake, thumbprint, or whatever you wanna' call it.
	 */
	val id: Int

	/**
	 * A map of module names to the modules themselves.
	 */
	val modules: Map<String, Module>

}