/*
 * MIT License
 *
 * Copyright (c) 2018 BMORIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.morin.kotlin.flatfile.model

import KffLexer
import KffParser
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CommonTokenStream

data class Entry(val name: String, val value: Long)
data class Type(val name: String, val entries: List<Entry>)
data class Descriptor(val filename: String, val types: List<Type>) {
    companion object {

        fun from(filename: String, charStream: CharStream): Descriptor? {
            val parser = KffParser(CommonTokenStream(KffLexer(charStream)))
            return fromConfigsContext(filename, parser.configs())
        }

        private fun fromConfigsContext(filename: String, configs: KffParser.ConfigsContext?): Descriptor? {
            if (configs == null) return null

            return Descriptor(filename,
                configs.config().map { configContext ->
                    val entries = configContext.entry().map { entryContext ->
                        Entry(entryContext.FIELD().text, entryContext.NUMBER().text.toLong()) // toLong is OK because it parsed before as a number.
                    }
                    Type(configContext.TYPE().text, entries)
                })
        }
    }
}
