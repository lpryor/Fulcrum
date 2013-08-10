/*
 * CommonMacros.scala
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

import reflect.macros.Context

/**
 * Common operations required by multiple macro expansions.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
trait CommonMacros[C <: Context] {

  /** The macro context. */
  val c: C

  import c.universe._

  /**
   * Creates an expression that is the optimized application of `function` to `input`.
   */
  def applied[I, O: WeakTypeTag](function: Expr[I => O], input: Expr[I]): Expr[O] =
    function.tree match {
      case AnonFunction(name, body) =>
        c.Expr[O](Block(List(ValDef(Modifiers(), name, TypeTree(), input.tree)), c.resetAllAttrs(body)))
      case AnonPartialFunction(cases) =>
        c.Expr[O](Match(input.tree, cases.init))
      case tree =>
        reify(function.splice(input.splice))
    }

  /**
   * Extractor for anonymous function trees.
   */
  private[this] object AnonFunction {

    /** Extracts the parameter information and body from an anonymous function declaration. */
    def unapply(tree: Tree): Option[(TermName, Tree)] = tree match {
      case Function(List(ValDef(_, name, _, _)), body) =>
        Some((name, body))
      case Block(List(), Function(List(ValDef(_, name, _, _)), body)) =>
        Some((name, body))
      case _ => None
    }

  }

  /**
   * Extractor for anonymous partial function trees.
   */
  private[this] object AnonPartialFunction {

    /** Extracts the parameter information and body from an anonymous function declaration. */
    def unapply(tree: Tree): Option[List[CaseDef]] = tree match {
      // FIXME Hack required until a SYNTHETIC flag is available.
      case Typed(Block(List(clsTree @ ClassDef(mods, _, _, _)), _), _) if show(mods) contains "<synthetic>" =>
        clsTree collect {
          case defTree @ DefDef(_, name, _, _, _, _) if name.decoded == "applyOrElse" => defTree
        } match {
          case defTree :: Nil => defTree collect {
            case matchTree @ Match(_, _) => matchTree
          } match {
            case matchTree :: Nil => Some(matchTree.cases)
            case _ => None
          }
          case _ => None
        }
      case _ => None
    }

  }

}

/**
 * Factory for context-specific common macros.
 */
object CommonMacros {

  /** Creates a new context-specific common macros instance. */
  def apply(context: Context): CommonMacros[context.type] =
    new CommonMacros[context.type] { override val c: context.type = context }

}