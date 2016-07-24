# Arrowhead [ ![Download](https://api.bintray.com/packages/jire/maven/Arrowhead/images/download.svg) ](https://bintray.com/jire/maven/Arrowhead/_latestVersion)
_Straightforward native interfacing from Kotlin_

[![Build Status](https://travis-ci.org/Jire/Arrowhead.svg?branch=master)](https://travis-ci.org/Jire/Arrowhead)
[![Dependency Status](https://www.versioneye.com/user/projects/578f3deb88bf880040a26ee5/badge.svg?style=flat)](https://www.versioneye.com/user/projects/578f3deb88bf880040a26ee5)
[![License](https://img.shields.io/github/license/Jire/Arrowhead.svg)](https://github.com/Jire/Arrowhead/blob/master/LICENSE.txt)

---

### Sources

Sources are native references that can be read from and written to through Arrowhead.

`Process` and `Module` are the two sources available.

### Acquiring a process

You can acquire a process using a name (executable file name):

```kotlin
val process = processByName("process.exe")!!
```

You can also acquire by process ID (PID):

```kotlin
val process123 = processByID(123)!!
```

### Acquiring a module

You can use `.modules` off a process for a map of the module name to the `Module`.

```kotlin
val module = process.modules["module.dll"]!!
```

### Reading from a source

You can use the implicit data type to read from an address:

```kotlin
val someByte: Byte = process[0x123]
val someInt: Int = process[0x123]
val someFloat: Float = process[0x123]
val someBoolean: Boolean = process[0x123]
```

The implicit type also works when passing arguments or using if expressions.

```kotlin
if (process[0x321]) // Boolean type inferred
    Math.sin(process[0x555]) // Double type inferred
```

Sometimes it's easier or necessary to use explicit types, and we have you covered:

```kotlin
val someByte = process.byte(0x123)
val someInt = process.int(0x123)
val someFloat = process.float(0x123)
val someBoolean = process.boolean(0x123)
```

### Writing to a source

Writing to a source is just as easy as reading.

You can use "implicit" writing with the set operator.

```kotlin
something[0x123] = 1.toByte()
something[0x123] = 1
something[0x123] = 1F
something[0x123] = true
```

There are no "explicit" writes, as the "implicit" writes are simply method overloads.

### Dealing with structs

Defining structures is [the same as in JNA](https://jna.java.net/nonav/javadoc/overview-summary.html#structures),
except you should extend `Struct` (of `org.jire.arrowhead`) instead of JNA's `Structure` (of `com.sun.jna.Structure`)
to take advantage of the reuse ("caching") system and other enhancements like automatic field order.

For JNA and Arrowhead to detect fields correctly, make sure to annotate them with
[`@JvmField`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-field/).

To read a struct you can use its read function:

`struct.read(address = 0x123, source = something)`

To write the struct to an address you can use its write function:

`struct.write(address = 0x123, source = something)`