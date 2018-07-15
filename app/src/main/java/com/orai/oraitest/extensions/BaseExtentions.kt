package com.orai.oraitest.extensions

fun Int?.isNullOrZero(): Boolean = this == null || this == 0

fun String?.isNullOrEmpty(): Boolean{
    return this != null && this.isNotEmpty()
}

fun Any?.isNotNull(): Boolean {
    return this != null
}

fun String.toIntOrNegative1(): Int {
    return this.toIntOrNull() ?: -1
}

fun <T> List<T>.getLastOrNull(): T? {
    if (isEmpty())
        return null
    else
        return last()
}

fun <T> List<T>.getPositionOrScaledIntoSize(pos: Int): T{
    var position = Math.min(pos, size - 1)
    position = Math.max(position, 0)
    return this[position]
}

fun Int.isEven() = this%2 == 0


