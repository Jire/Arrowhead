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

import com.sun.jna.Memory
import com.sun.jna.Pointer

/**
 * A native source which can be read from and written to with native addresses.
 */
interface Source {

	/**
	 * Reads at the specified native address into the specified pointer.
	 *
	 * @param address The native address to read from.
	 * @param data The memory to read into.
	 * @param bytesToRead The amount of bytes to read. (By default this is the size of the memory.)
	 */
	fun read(address: Long, data: Pointer, bytesToRead: Int)

	/**
	 * Reads at the specified native address into the specified memory.
	 *
	 * @param address The native address to read from.
	 * @param data The memory to read into.
	 * @param bytesToRead The amount of bytes to read. (By default this is the size of the memory.)
	 */
	fun read(address: Long, data: Memory, bytesToRead: Int = data.size().toInt()): Unit
			= read(address, data, bytesToRead)

	/**
	 * Reads at the specified native address into the specified struct.
	 *
	 * @param address The native address to read from.
	 * @param struct The struct to read into.
	 * @param bytesToRead The amount of bytes to read. (By default this is the size of the struct.)
	 */
	fun read(address: Long, struct: Struct, bytesToRead: Int = struct.size()): Unit
			= read(address, struct.pointer, bytesToRead)

	/**
	 * Reads at the specified native address into a memory.
	 *
	 * @param address The native address to read from.
	 * @param bytesToRead The amount of bytes to read.
	 */
	fun read(address: Long, bytesToRead: Int): Memory {
		val resource = MemoryCache[bytesToRead]
		read(address, resource, bytesToRead) // read to the memory using the implementation
		return resource
	}

	/**
	 * Reads a byte at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun byte(address: Long, offset: Long = 0) = read(address, 1).getByte(offset)

	/**
	 * Reads a short at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun short(address: Long, offset: Long = 0) = read(address, 2).getShort(offset)

	/**
	 * Reads a char at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun char(address: Long, offset: Long = 0) = read(address, 2).getChar(offset)

	/**
	 * Reads an int at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun int(address: Long, offset: Long = 0) = read(address, 4).getInt(offset)

	/**
	 * Reads a long at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun long(address: Long, offset: Long = 0) = read(address, 8).getLong(offset)

	/**
	 * Reads a float at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun float(address: Long, offset: Long = 0) = read(address, 4).getFloat(offset)

	/**
	 * Reads a double at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun double(address: Long, offset: Long = 0) = read(address, 8).getDouble(offset)

	/**
	 * Reads a boolean at the specified native address, offset by the specified offset.
	 *
	 * @param address The native address to read from.
	 * @param offset The offset in bytes off the native address.
	 */
	fun boolean(address: Long, offset: Long = 0) = byte(address, offset).unsign() > 0

	/**
	 * Writes the specified memory to the specified native address.
	 *
	 * @param address The native address to write to.
	 * @param data A pointer to the data to write.
	 */
	fun write(address: Long, data: Pointer, bytesToWrite: Int)

	/**
	 * Writes the specified memory to the specified native address.
	 *
	 * @param address The native address to write to.
	 * @param data A memory pointer of the data to write.
	 * @param bytesToWrite The amount of bytes to write of the memory. (By default this is the size of the memory.)
	 */
	fun write(address: Long, data: Memory, bytesToWrite: Int = data.size().toInt()): Unit
			= write(address, data, bytesToWrite)

	/**
	 * Writes the specified struct to the specified native address.
	 *
	 * @param address The native address to write to.
	 * @param struct The struct to write.
	 * @param bytesToWrite The amount of bytes to write of the struct. (By default this is the size of the struct.)
	 */
	fun write(address: Long, struct: Struct, bytesToWrite: Int = struct.size()): Unit
			= write(address, struct.pointer, bytesToWrite)

	/**
	 * Writes at the specified native address to the specified byte value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the byte to write.
	 */
	operator fun set(address: Long, value: Byte) = write(address, 1) {
		setByte(0, value)
	}

	/**
	 * Writes at the specified native address to the specified short value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the short to write.
	 */
	operator fun set(address: Long, value: Short) = write(address, 2) {
		setShort(0, value)
	}

	/**
	 * Writes at the specified native address to the specified char value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the char to write.
	 */
	operator fun set(address: Long, value: Char) = write(address, 2) {
		setChar(0, value)
	}

	/**
	 * Writes at the specified native address to the specified int value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the int to write.
	 */
	operator fun set(address: Long, value: Int) = write(address, 4) {
		setInt(0, value)
	}

	/**
	 * Writes at the specified native address to the specified long value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the long to write.
	 */
	operator fun set(address: Long, value: Long) = write(address, 8) {
		setLong(0, value)
	}

	/**
	 * Writes at the specified native address to the specified float value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the float to write.
	 */
	operator fun set(address: Long, value: Float) = write(address, 4) {
		setFloat(0, value)
	}

	/**
	 * Writes at the specified native address to the specified double value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the double to write.
	 */
	operator fun set(address: Long, value: Double) = write(address, 8) {
		setDouble(0, value)
	}

	/**
	 * Writes at the specified native address to the specified boolean value.
	 *
	 * @param address The native address to write to.
	 * @param value The value of the boolean to write.
	 */
	operator fun set(address: Long, value: Boolean) = set(address, (if (value) 1 else 0).toByte())

}