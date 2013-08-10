/*
 * package.scala
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
package fulcrum

import language.experimental.macros
import language.{
  higherKinds,
  implicitConversions
}
import reflect.macros.Context

/**
 * Contains the global definitions in the `util` package.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
package object util {

  /**
   * An implicit view that enables the pipe operator `|>` on any value.
   */
  implicit def anyToPipeOperatorSupport[I](input: I): PipeOperatorSupport[I] = macro PackageMacros.anyToPipe[I]

  /**
   * Definitions of the package macro logic.
   */
  object PackageMacros {

    /** Generates the pipe operator expansion. */
    def anyToPipe[I: c.WeakTypeTag](c: Context)(input: c.Expr[I]): c.Expr[PipeOperatorSupport[I]] =
      c.universe.reify(new PipeOperatorSupport(input.splice))

  }

}