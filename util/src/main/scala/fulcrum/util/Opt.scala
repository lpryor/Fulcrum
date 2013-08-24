/*
 * Opt.scala
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
 * A [[scala.Option]]-like container that is represented at runtime by a nullable reference and whose methods are
 * inlined as null-checks at calling locations.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class Opt[+V >: Null](val value: V) extends AnyVal {

  /** Returns true if the underlying value is null, false otherwise. */
  def isEmpty: Boolean = macro OptMacros.isEmpty[V]

  /** Returns true if the underlying value is not null, false otherwise. */
  def nonEmpty: Boolean = macro OptMacros.nonEmpty[V]

  /** Returns true if the underlying value is not null, false otherwise. */
  def isDefined: Boolean = macro OptMacros.isDefined[V]

  /** Returns the underlying value if it is not null, other wise throws [[NoSuchElementException]]. */
  def get: V = macro OptMacros.get[V]

  /** Returns the underlying value if it is not null, otherwise returns the result of evaluating `default`. */
  def getOrElse[W >: V](default: W): W = macro OptMacros.getOrElse[V, W]

  /** Returns the underlying value even if it is null. */
  def orNull: V = macro OptMacros.orNull[V]

  /**
   * Applies `f` to the underlying value if it is not null and returns a new inlined option with the result, otherwise
   * returns an empty inline option.
   */
  def map[W >: Null](f: V => W): Opt[W] = macro OptMacros.map[V, W]

  /**
   * Applies `f` to the underlying value if it is not null and returns a new inlined option with the result, otherwise
   * returns the result of evaluating `ifEmpty`.
   */
  def fold[W](ifEmpty: W)(f: V => W): W = macro OptMacros.fold[V, W]

  /**
   * Applies `f` to the underlying value if it is not null and returns the result, otherwise returns an empty inline
   * option.
   */
  def flatMap[W >: Null](f: V => Opt[W]): Opt[W] = macro OptMacros.flatMap[V, W]

  /**
   * Returns this inline option if the underlying value is not null and satisfies the predicate `p`, otherwise returns
   * an empty inline option.
   */
  def filter(p: V => Boolean): Opt[V] = macro OptMacros.filter[V]

  /**
   * Returns this inline option if the underlying value is not null and does not satisfy the predicate `p`, otherwise
   * returns an empty inline option.
   */
  def filterNot(p: V => Boolean): Opt[V] = macro OptMacros.filterNot[V]

  /** Returns true if the underlying value is not null and satisfies the predicate `p`, false otherwise. */
  def exists(p: V => Boolean): Boolean = macro OptMacros.exists[V]

  /**
   * Returns true if the underlying value is null or it is not null and satisfies the predicate `p`, false otherwise.
   */
  def forall(p: V => Boolean): Boolean = macro OptMacros.forAll[V]

  /**
   * Applies the given function `f` to the underlying value if it is not null, otherwise does nothing.
   */
  def foreach[U](f: V => U): Unit = macro OptMacros.foreach[V, U]

  /**
   * Returns an inline option containing the result of applying `pf` to the underlying value, if it is not null and `pf`
   * is defined for that value, otherwise returns an empty inline option.
   */
  def collect[W >: Null](pf: PartialFunction[V, W]): Opt[W] = macro OptMacros.collect[V, W]

  /** Returns this inline option if it is nonempty, otherwise return the result of evaluating `alternative`. */
  def orElse[W >: V](alternative: Opt[W]): Opt[W] = macro OptMacros.orElse[V, W]

  /**
   * Returns a singleton iterator returning the underlying value if it is not null, otherwise returns an empty iterator.
   */
  def iterator: Iterator[V] = macro OptMacros.iterator[V]

  /**
   * Returns a [[Some]] containing the underlying value if it is not null, otherwise returns [[None]].
   */
  def toOption: Option[V] = macro OptMacros.toOption[V]

  /**
   * Returns a singleton list containing the underlying value if it is not null, otherwise returns an empty list.
   */
  def toList: List[V] = macro OptMacros.toList[V]

  /**
   * Returns a [[scala.util.Left]] containing the given argument `left` if this inline option is empty, otherwise
   * returns a [[scala.util.Right]] containing the underlying value.
   */
  def toRight[X](left: X): Either[X, V] = macro OptMacros.toRight[V, X]

  /**
   * Returns a [[scala.util.Right]] containing the given argument `right` if this inline option is empty, otherwise
   * returns a [[scala.util.Left]] containing the underlying value.
   */
  def toLeft[X](right: X): Either[V, X] = macro OptMacros.toLeft[V, X]

  /**
   * Returns this inline option if the underlying value is not null and satisfies the predicate `p`, otherwise returns
   * an empty inline option. This variation supports usage in for comprehensions.
   */
  def withFilter(p: V => Boolean): Opt[V] = macro OptMacros.filter[V]

  /** @inheritdoc */
  override def toString() = if (value == null) "Opt.empty" else "Opt(" + value + ")"

}

/**
 * Factory for inlined options.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
object Opt {

  /** The `toDefault` function used when applying partial functions in [[fulcrum.util.Opt]].`collect`. */
  val collectToDefault = ((_: Any) => null)

  /** Creates an empty inlined option. */
  def empty[V >: Null]: Opt[V] = macro OptMacros.empty[V]

  /** Creates a new inlined option that is empty only if $value is null. */
  def apply[V >: Null](value: V): Opt[V] = macro OptMacros.create[V]

}

/**
 * Definitions of the inlined option macro logic.
 */
object OptMacros {

  /** Creates an empty inlined option. */
  def empty[V >: Null: c.WeakTypeTag](c: Context): c.Expr[Opt[V]] =
    c.universe.reify(new Opt[V](null))

  /** Creates a new inlined option. */
  def create[V >: Null: c.WeakTypeTag](c: Context)(value: c.Expr[V]): c.Expr[Opt[V]] =
    c.universe.reify(new Opt(value.splice))

  /** Returns true if the option is empty. */
  def isEmpty[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[Boolean] =
    c.universe.reify(c.prefix.splice.value == null)

  /** Returns true if the option is non-empty. */
  def nonEmpty[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[Boolean] =
    c.universe.reify(c.prefix.splice.value != null)

  /** Returns true if the option is defined. */
  def isDefined[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[Boolean] =
    c.universe.reify(c.prefix.splice.value != null)

  /** Returns the non-null value or throws an exception. */
  def get[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[V] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null) throw new NoSuchElementException
      else value
    }

  /** Returns the non-null value or an alternative. */
  def getOrElse[V >: Null, W >: V: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (default: c.Expr[W]): c.Expr[W] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null) default.splice
      else value
    }

  /** Returns the value even if it is null. */
  def orNull[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[V] =
    c.universe.reify(c.prefix.splice.value)

  /** Transforms a non-empty option's value. */
  def map[V >: Null: c.WeakTypeTag, W >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (f: c.Expr[V => W]): c.Expr[Opt[W]] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Opt[W]](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify {
        if (id.splice != null) new Opt(CommonMacros.applied(c)(f, id).splice)
        else new Opt[W](null)
      }.tree))
  }

  /** Transforms the non-empty option into another option. */
  def fold[V >: Null: c.WeakTypeTag, W: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] })(ifEmpty: c.Expr[W]) //
  (f: c.Expr[V => W]): c.Expr[W] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[W](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify {
        if (id.splice != null) CommonMacros.applied(c)(f, id).splice
        else ifEmpty.splice
      }.tree))
  }

  /** Transforms the non-empty option into another option. */
  def flatMap[V >: Null: c.WeakTypeTag, W >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (f: c.Expr[V => Opt[W]]): c.Expr[Opt[W]] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Opt[W]](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify {
        if (id.splice != null) CommonMacros.applied(c)(f, id).splice
        else new Opt[W](null)
      }.tree))
  }

  /** Generates the filter expansion. */
  def filter[V >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (p: c.Expr[V => Boolean]): c.Expr[Opt[V]] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Opt[V]](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify {
        if (id.splice != null && CommonMacros.applied(c)(p, id).splice) new Opt(id.splice)
        else new Opt[V](null)
      }.tree))
  }

  /** Generates the filterNot expansion. */
  def filterNot[V >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (p: c.Expr[V => Boolean]): c.Expr[Opt[V]] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Opt[V]](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify {
        if (id.splice != null && !CommonMacros.applied(c)(p, id).splice) new Opt(id.splice)
        else new Opt[V](null)
      }.tree))
  }

  /** Generates the exists expansion. */
  def exists[V >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (p: c.Expr[V => Boolean]): c.Expr[Boolean] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Boolean](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify(id.splice != null && CommonMacros.applied(c)(p, id).splice).tree))
  }

  /** Generates the forAll expansion. */
  def forAll[V >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (p: c.Expr[V => Boolean]): c.Expr[Boolean] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Boolean](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify(id.splice == null || CommonMacros.applied(c)(p, id).splice).tree))
  }

  /** Generates the foreach expansion. */
  def foreach[V >: Null: c.WeakTypeTag, U](c: Context { type PrefixType = Opt[V] }) //
  (f: c.Expr[V => U]): c.Expr[Unit] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Unit](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify(if (id.splice != null) CommonMacros.applied(c)(f, id).splice).tree))
  }

  /** Generates the collect expansion. */
  def collect[V >: Null: c.WeakTypeTag, W >: Null: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (pf: c.Expr[PartialFunction[V, W]]): c.Expr[Opt[W]] = {
    import c.universe._
    val name = newTermName(c.fresh())
    val id = c.Expr[V](Ident(name))
    c.Expr[Opt[W]](Block(
      List(ValDef(Modifiers(), name, TypeTree(), reify(c.prefix.splice.value).tree)),
      reify {
        if (id.splice != null) new Opt[W](CommonMacros.appliedOrElse(c)(pf, id, reify(Opt.collectToDefault)).splice)
        else new Opt[W](null)
      }.tree))
  }

  /** Generates the orElse expansion. */
  def orElse[V >: Null, W >: V: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (alternative: c.Expr[Opt[W]]): c.Expr[Opt[W]] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null)
        alternative.splice
      else
        new Opt(value)
    }

  /** Generates the iterator expansion. */
  def iterator[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[Iterator[V]] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null)
        Iterator.empty
      else
        Iterator.single(value)
    }

  /** Generates the toOption expansion. */
  def toOption[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[Option[V]] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null)
        None
      else
        Some(value)
    }

  /** Generates the toList expansion. */
  def toList[V >: Null](c: Context { type PrefixType = Opt[V] }): c.Expr[List[V]] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null)
        Nil
      else
        value :: Nil
    }

  /** Generates the toRight expansion. */
  def toRight[V >: Null, X: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (left: c.Expr[X]): c.Expr[Either[X, V]] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null)
        Left(left.splice)
      else
        Right(value)
    }

  /** Generates the toLeft expansion. */
  def toLeft[V >: Null, X: c.WeakTypeTag](c: Context { type PrefixType = Opt[V] }) //
  (right: c.Expr[X]): c.Expr[Either[V, X]] =
    c.universe.reify {
      val value = c.prefix.splice.value
      if (value == null)
        Right(right.splice)
      else
        Left(value)
    }

}