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
import com.sun.jna.Structure

class iovec(@JvmField var iov_base: Pointer? = null, @JvmField var iov_len: Int = 0) : Structure() {

	override fun getFieldOrder() = arrayListOf("iov_base", "iov_len")

}