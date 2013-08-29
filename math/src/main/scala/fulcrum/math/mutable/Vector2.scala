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
package fulcrum.math.mutable

import language.implicitConversions

/**
 * A mutable two-element mathematical vector.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class Vector2[@specialized(Boolean, Int, Float) T](var x: T, var y: T) extends fulcrum.math.Vector2[T] {

  /** @inheritdoc */
  override def copy(x: T = this.x, y: T = this.y): Vector2[T] = Vector2(x, y)

  /** Sets the element at the specified index in the list `(x, y)`. */
  def update(index: Int, value: T): Unit = index match {
    case 0 => x = value
    case 1 => y = value
    case _ => throw new IllegalArgumentException(index.toString)
  }

  /** Assigns the elements of this vector to the values of the corresponding elements of a vector. */
  def :=(that: fulcrum.math.Vector2[T]): this.type = {
    this.x = that.x
    this.y = that.y
    this
  }

}

/**
 * A factory for mutable two-element mathematical vectors.
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
  def apply[@specialized(Boolean, Int, Float) T](x: T, y: T): Vector2[T] = new Vector2(x, y)

  /** Extracts the elements of a vector. */
  def unapply[@specialized(Boolean, Int, Float) T](vector: Vector2[T]): Option[(T, T)] = Some((vector.x, vector.y))

}
