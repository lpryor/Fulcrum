/*
 * RichVector2b.scala
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
 * Defines additional operations available on two-element boolean vectors.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class RichVector2b(val self: Vector2[Boolean]) extends AnyVal {

  /** Returns the negation of each element in this vector. */
  def unary_! : Vector2[Boolean] = Vector2(!self.x, !self.y)

  /** Returns the equality comparison of a scalar and each element of this vector. */
  def ===(that: Boolean): Vector2[Boolean] = Vector2(self.x == that, self.y == that)

  /** Returns the equality comparison of each element of a vector and the corresponding element of this vector. */
  def ===(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x == that.x, self.y == that.y)

  /** Returns the inequality comparison of a scalar and each element of this vector. */
  def =!=(that: Boolean): Vector2[Boolean] = Vector2(self.x != that, self.y != that)

  /** Returns the inequality comparison of each element of a vector and the corresponding element of this vector. */
  def =!=(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x != that.x, self.y != that.y)

  /** Returns the logical-or of a scalar and each element of this vector. */
  def ||(that: Boolean): Vector2[Boolean] = Vector2(self.x || that, self.y || that)

  /** Returns the logical-or of each element of a vector and the corresponding element of this vector. */
  def ||(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x || that.x, self.y || that.y)

  /** Returns the logical-and of a scalar and each element of this vector. */
  def &&(that: Boolean): Vector2[Boolean] = Vector2(self.x && that, self.y && that)

  /** Returns the logical-and of each element of a vector and the corresponding element of this vector. */
  def &&(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x && that.x, self.y && that.y)

  /** Returns the bitwise-or of a scalar and each element of this vector. */
  def |(that: Boolean): Vector2[Boolean] = Vector2(self.x | that, self.y | that)

  /** Returns the bitwise-or of each element of a vector and the corresponding element of this vector. */
  def |(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x | that.x, self.y | that.y)

  /** Returns the bitwise-and of a scalar and each element of this vector. */
  def &(that: Boolean): Vector2[Boolean] = Vector2(self.x & that, self.y & that)

  /** Returns the bitwise-and of each element of a vector and the corresponding element of this vector. */
  def &(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x & that.x, self.y & that.y)

  /** Returns the bitwise-exclusive-or of a scalar and each element of this vector. */
  def ^(that: Boolean): Vector2[Boolean] = Vector2(self.x ^ that, self.y ^ that)

  /** Returns the bitwise-exclusive-or of each element of a vector and the corresponding element of this vector. */
  def ^(that: Vector2[Boolean]): Vector2[Boolean] = Vector2(self.x ^ that.x, self.y ^ that.y)

  /** Returns an immutable copy of this vector by converting the elements to floats. */
  def toVector2f: immutable.Vector2[Float] = immutable.Vector2(if (self.x) 1f else 0f, if (self.y) 1f else 0f)

  /** Returns an immutable copy of this vector by converting the elements to integers. */
  def toVector2i: immutable.Vector2[Int] = immutable.Vector2(if (self.x) 1 else 0, if (self.y) 1 else 0)

}