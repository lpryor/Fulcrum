/*
 * PipeOperatorSupport.scala
 * 
 * Copyright (c) 2013 Lonnie Pryor III
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
package fulcrum.util

import language.experimental.macros
import reflect.macros.Context

/**
 * A view that enables the pipe operator `|>` on any value with no extra overhead.
 */
final class PipeOperatorSupport[I](val input: I) extends AnyVal {

import PipeOperatorSupportMacros._

  /** Applies the specified function to the input value. */
  def |>[O](function: I => O): O = macro PipeOperatorSupportMacros.pipe[I, O]

}

/**
 * Definitions of the package macro logic.
 */
object PipeOperatorSupportMacros {

  /** Transforms the non-empty option's value. */
  def pipe[I, O: c.WeakTypeTag](c: Context { type PrefixType = PipeOperatorSupport[I] }) //
  (function: c.Expr[I => O]): c.Expr[O] =
    CommonMacros.applied(c)(function, c.universe.reify(c.prefix.splice.input))

}