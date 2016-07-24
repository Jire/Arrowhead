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

import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import com.sun.jna.Pointer

/**
 * Provides zero-garbage [WriteProcessMemory] and [ReadProcessMemory] access.
 *
 * The "secret sauce" is avoiding JNA object allocations from translation-necessary types.
 */
object Kernel32 {

	/**
	 * Writes data to an area of memory in a specified process. The entire area
	 * to be written to must be accessible or the operation fails.
	 * @param hProcess A handle to the process memory to be modified.
	 * @param lpBaseAddress The base address in the specified process to which
	 * data is written.
	 * @param lpBuffer The buffer that contains data to be written in the
	 * address space of the specified process.
	 * @param nSize The number of bytes to be written to the specified process.
	 * @param lpNumberOfBytesWritten A variable that receives the number of bytes
	 * transferred into the specified process.  If {@code null} the parameter is ignored.
	 * @return {@code 1} if successful, {@code 0} otherwise.
	 * To get extended error information, call {@link #GetLastError()}.
	 * @see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms681674(v=vs.85).aspx">
	 *     WriteProcessMemory documentation</a>
	 */
	@JvmStatic
	external fun WriteProcessMemory(hProcess: Pointer, lpBaseAddress: Long, lpBuffer: Pointer,
	                                nSize: Int, lpNumberOfBytesWritten: Int): Long

	/**
	 * Reads data from an area of memory in a specified process. The entire area
	 * to be read must be accessible or the operation fails.
	 *
	 * @see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms680553(v=vs.85).aspx">MSDN</a>
	 * @param hProcess
	 *            A handle to the process with memory that is being read. The
	 *            handle must have PROCESS_VM_READ access to the process.
	 * @param lpBaseAddress
	 *            A pointer to the base address in the specified process from
	 *            which to read. <br>
	 *            Before any data transfer occurs, the system verifies that all
	 *            data in the base address and memory of the specified size is
	 *            accessible for read access, and if it is not accessible the
	 *            function fails.
	 * @param lpBuffer
	 *            A pointer to a buffer that receives the contents from the
	 *            address space of the specified process.
	 * @param nSize
	 *            The number of bytes to be read from the specified process.
	 * @param lpNumberOfBytesRead
	 *            A pointer to a variable that receives the number of bytes
	 *            transferred into the specified buffer. If lpNumberOfBytesRead
	 *            is NULL, the parameter is ignored.
	 * @return If the function succeeds, the return value is nonzero.<br>
	 *         If the function fails, the return value is 0 (zero). To get
	 *         extended error information, call GetLastError.<br>
	 *         The function fails if the requested read operation crosses into
	 *         an area of the process that is inaccessible.
	 */
	@JvmStatic
	external fun ReadProcessMemory(hProcess: Pointer, lpBaseAddress: Long, lpBuffer: Pointer,
	                               nSize: Int, lpNumberOfBytesRead: Int): Long

	init {
		Native.register(NativeLibrary.getInstance("Kernel32"))
	}

}