/*
 * Vector2.scala
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
package fulcrum.math

import language.implicitConversions

/**
 * A two-element mathematical vector.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
trait Vector2[@specialized(Boolean, Int, Float) T] extends Product2[T, T] {

  /** The `x` element of this vector. */
  def x: T

  /** The `y` element of this vector. */
  def y: T

  /** Returns the element at the specified index in the list `(x, y)`. */
  def apply(index: Int): T = index match {
    case 0 => x
    case 1 => y
    case _ => throw new IllegalArgumentException(index.toString)
  }

  /** Equals comparison that operates on specialized values. */
  def ==(that: Vector2[T]) = x == that.x && y == that.y

  /** Not-equals comparison that operates on specialized values. */
  def !=(that: Vector2[T]) = x != that.x || y != that.y

  /** Creates a copy of this vector, replacing any specified element values. */
  def copy(x: T = this.x, y: T = this.y): Vector2[T] = Vector2(x, y)

  /** Returns an immutable copy of this vector. */
  def toVector2: immutable.Vector2[T] = immutable.Vector2(x, y)

  /** @inheritdoc */
  override def _1 = x

  /** @inheritdoc */
  override def _2 = y

  /** @inheritdoc */
  override def canEqual(that: Any) = that.isInstanceOf[Vector2[_]]

  /** @inheritdoc */
  override def hashCode() = x.hashCode ^ y.hashCode

  /** @inheritdoc */
  override def equals(that: Any) = that match {
    case that: Vector2[_] => x == that.x && y == that.y
    case _ => false
  }

  /** @inheritdoc */
  override def toString() = s"Vector2($x, $y)"

}

/**
 * A factory for two-element mathematical vectors.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
object Vector2 {

  /** Provides additional operations available on two-element boolean vectors. */
  implicit def vector2bToRichVector2b(v: Vector2[Boolean]): RichVector2b = new RichVector2b(v)

  /** Provides additional operations available on two-element float vectors. */
  implicit def vector2fToRichVector2f(v: Vector2[Float]): RichVector2f = new RichVector2f(v)

  /** Provides additional operations available on two-element integer vectors. */
  implicit def vector2iToRichVector2i(v: Vector2[Int]): RichVector2i = new RichVector2i(v)

  /** Creates a new vector with the specified elements. */
  def apply[@specialized(Boolean, Int, Float) T](x: T, y: T): Vector2[T] = immutable.Vector2(x, y)

  /** Extracts the elements of a vector. */
  def unapply[@specialized(Boolean, Int, Float) T](vector: Vector2[T]): Option[(T, T)] = Some((vector.x, vector.y))

}
