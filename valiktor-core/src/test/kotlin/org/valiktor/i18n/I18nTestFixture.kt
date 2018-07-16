package org.valiktor.i18n

import org.valiktor.Constraint
import java.util.*
import kotlin.reflect.full.declaredMemberProperties

object SupportedLocales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

val INVALID_LOCALE = Locale("INVALID")

private const val DEFAULT_BUNDLE = "org/valiktor/messages"

private val SUPPORTED_LOCALES: List<Locale> = SupportedLocales::class.declaredMemberProperties
        .map { it.get(SupportedLocales) }
        .map { it as Locale }

fun Constraint.interpolatedMessages(): Map<Locale, String> =
        SUPPORTED_LOCALES
                .map {
                    it to interpolate(
                            MessageBundle(
                                    baseName = this.messageBundle,
                                    locale = it,
                                    fallbackBaseName = this.messageBundle),
                            this.messageKey,
                            this.messageParams)
                }
                .toMap()

fun <T : Any> Formatter<T>.formatAllSupportedLocales(value: T): Map<Locale, String> = SUPPORTED_LOCALES
        .map {
            it to this.format(value, MessageBundle(
                    baseName = DEFAULT_BUNDLE,
                    locale = it,
                    fallbackBaseName = DEFAULT_BUNDLE))
        }
        .toMap()

fun Formatter<Array<Any>>.formatAllSupportedLocales(value: Array<Any>): Map<Locale, String> = SUPPORTED_LOCALES
        .map {
            it to this.format(value, MessageBundle(
                    baseName = DEFAULT_BUNDLE,
                    locale = it,
                    fallbackBaseName = DEFAULT_BUNDLE))
        }
        .toMap()