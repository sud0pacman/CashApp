package com.sudo_pacman.cashapp.core.utils

fun expirationDateValidator(expirationDate: String): Boolean {
    if (expirationDate.length < 5) return false

    return (expirationDate.substring(0, 2).toInt() > 0 && expirationDate.substring(0, 2).toInt() < 13)
        && (expirationDate.substring(3).toInt() > 24 && expirationDate.substring(3).toInt() < 29)
}