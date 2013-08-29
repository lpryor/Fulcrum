/*
 * RichVector2f.scala
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

/**
 * Defines additional operations available on two-element float vectors.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class RichVector2f(val self: Vector2[Float]) extends AnyVal {

  /** Returns this vector, unmodified. */
  def unary_+ : Vector2[Float] = self

  /** Returns the negation of each element in this vector. */
  def unary_- : Vector2[Float] = Vector2(-self.x, -self.y)

  /** Returns the equality comparison of a scalar and each element of this vector. */
  def ===(that: Float): Vector2[Boolean] = Vector2(self.x == that, self.y == that)

  /** Returns the equality comparison of each element of a vector and the corresponding element of this vector. */
  def ===(that: Vector2[Float]): Vector2[Boolean] = Vector2(self.x == that.x, self.y == that.y)

  /** Returns the inequality comparison of a scalar and each element of this vector. */
  def =!=(that: Float): Vector2[Boolean] = Vector2(self.x != that, self.y != that)

  /** Returns the inequality comparison of each element of a vector and the corresponding element of this vector. */
  def =!=(that: Vector2[Float]): Vector2[Boolean] = Vector2(self.x != that.x, self.y != that.y)

  /** Returns the less-than comparison of a scalar and each element of this vector. */
  def <(that: Float): Vector2[Boolean] = Vector2(self.x < that, self.y < that)

  /** Returns the less-than comparison of each element of a vector and the corresponding element of this vector. */
  def <(that: Vector2[Float]): Vector2[Boolean] = Vector2(self.x < that.x, self.y < that.y)

  /** Returns the less-than-or-equal-to comparison of a scalar and each element of this vector. */
  def <=(that: Float): Vector2[Boolean] = Vector2(self.x <= that, self.y <= that)

  /**
   * Returns the less-than-or-equal-to comparison of each element of a vector and the corresponding element of this
   * vector.
   */
  def <=(that: Vector2[Float]): Vector2[Boolean] = Vector2(self.x <= that.x, self.y <= that.y)

  /** Returns the greater-than comparison of a scalar and each element of this vector. */
  def >(that: Float): Vector2[Boolean] = Vector2(self.x > that, self.y > that)

  /** Returns the greater-than comparison of each element of a vector and the corresponding element of this vector. */
  def >(that: Vector2[Float]): Vector2[Boolean] = Vector2(self.x > that.x, self.y > that.y)

  /** Returns the greater-than-or-equal-to comparison of a scalar and each element of this vector. */
  def >=(that: Float): Vector2[Boolean] = Vector2(self.x >= that, self.y >= that)

  /**
   * Returns the greater-than-or-equal-to comparison of each element of a vector and the corresponding element of this
   * vector.
   */
  def >=(that: Vector2[Float]): Vector2[Boolean] = Vector2(self.x >= that.x, self.y >= that.y)

  /** Adds a scalar to each element of this vector. */
  def +(that: Float): Vector2[Float] = Vector2(self.x + that, self.y + that)

  /** Adds each element of a vector to the corresponding element of this vector. */
  def +(that: Vector2[Float]): Vector2[Float] = Vector2(self.x + that.x, self.y + that.y)

  /** Adds a scalar to each element of this vector (alias to avoid conflicts with string concatenation). */
  def :+(that: Float): Vector2[Float] = this + that

  /**
   * Adds each element of a vector to the corresponding element of this vector (alias to avoid conflicts with string
   * concatenation).
   */
  def :+(that: Vector2[Float]): Vector2[Float] = this + that

  /** Subtracts a scalar from each element of this vector. */
  def -(that: Float): Vector2[Float] = Vector2(self.x - that, self.y - that)

  /** Subtracts each element of a vector from the corresponding element of this vector. */
  def -(that: Vector2[Float]): Vector2[Float] = Vector2(self.x - that.x, self.y - that.y)

  /** Multiplies each element of this vector by a scalar. */
  def *(that: Float): Vector2[Float] = Vector2(self.x * that, self.y * that)

  /** Multiplies each element of this vector by the corresponding element of a vector. */
  def *(that: Vector2[Float]): Vector2[Float] = Vector2(self.x * that.x, self.y * that.y)

  /** Divides each element of this vector by a scalar. */
  def /(that: Float): Vector2[Float] = Vector2(self.x / that, self.y / that)

  /** Divides each element of this vector by the corresponding element of a vector. */
  def /(that: Vector2[Float]): Vector2[Float] = Vector2(self.x / that.x, self.y / that.y)

  /** Returns the remainder after division of each element of this vector by a scalar. */
  def %(that: Float): Vector2[Float] = Vector2(self.x % that, self.y % that)

  /** Returns the remainder after division of each element of this vector by the corresponding element of a vector. */
  def %(that: Vector2[Float]): Vector2[Float] = Vector2(self.x % that.x, self.y % that.y)

  /** Returns an immutable copy of this vector by converting the elements to booleans. */
  def toVector2b: immutable.Vector2[Boolean] = immutable.Vector2(self.x != 0f, self.y != 0f)

  /** Returns an immutable copy of this vector by converting the elements to integers. */
  def toVector2i: immutable.Vector2[Int] = immutable.Vector2(self.x.toInt, self.y.toInt)

}