package com.tilikki.movipedia.helper

class AdditionalResponseField(private vararg val additionalFields: String) {
    override fun toString(): String {
        return additionalFields.joinToString(separator = ",") { it }
    }

    operator fun invoke(): String {
        return toString()
    }
}