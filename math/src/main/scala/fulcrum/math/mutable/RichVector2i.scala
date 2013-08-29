/*
 * RichVector2i.scala
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

/**
 * Defines additional operations available on mutable two-element integer vectors.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class RichVector2i(val self: Vector2[Int]) extends AnyVal {

  /** Flips the bits of each element in this vector. */
  def flip(): self.type = {
    self.x = ~self.x
    self.y = ~self.y
    self
  }

  /** Negates each element in this vector. */
  def negate(): self.type = {
    self.x = -self.x
    self.y = -self.y
    self
  }

  /** Shifts the elements of this vector to the left using a scalar and assigns them back to this vector. */
  def <<=(that: Int): self.type = {
    self.x <<= that
    self.y <<= that
    self
  }

  /**
   * Shifts the elements of this vector to the left using the corresponding elements of a vector and assigns them back
   * to this vector.
   */
  def <<=(that: Vector2[Int]): self.type = {
    self.x <<= that.x
    self.y <<= that.y
    self
  }

  /**
   * Shifts the elements of this vector to the right using a scalar, filling the new left bits with zeroes and assigns
   * them back to this vector.
   */
  def >>>=(that: Int): self.type = {
    self.x >>>= that
    self.y >>>= that
    self
  }

  /**
   * Shifts the elements of this vector to the right using the corresponding elements of a vector, filling the new left
   * bits with zeroes and assigns them back to this vector.
   */
  def >>>=(that: Vector2[Int]): self.type = {
    self.x >>>= that.x
    self.y >>>= that.y
    self
  }

  /**
   * Shifts the elements of this vector to the right using a scalar, filling the new left bits with the value of the
   * previous left-most bit, and assigns them back to this vector.
   */
  def >>=(that: Int): self.type = {
    self.x >>= that
    self.y >>= that
    self
  }

  /**
   * Shifts the elements of this vector to the right using the corresponding elements of a vector, filling the new left
   * bits with the value of the previous left-most bit, and assigns them back to this vector.
   */
  def >>=(that: Vector2[Int]): self.type = {
    self.x >>= that.x
    self.y >>= that.y
    self
  }

  /** Calculates the bitwise-or of a scalar and each element of this vector and assigns it to this vector. */
  def |=(that: Int): self.type = {
    self.x |= that
    self.y |= that
    self
  }

  /**
   * Calculates the bitwise-or of each element of a vector and the corresponding element of this vector and assigns it
   * to this vector.
   */
  def |=(that: Vector2[Int]): self.type = {
    self.x |= that.x
    self.y |= that.y
    self
  }

  /** Calculates the bitwise-and of a scalar and each element of this vector and assigns it to this vector. */
  def &=(that: Int): self.type = {
    self.x &= that
    self.y &= that
    self
  }

  /**
   * Calculates the bitwise-and of each element of a vector and the corresponding element of this vector and assigns it
   * to this vector.
   */
  def &=(that: Vector2[Int]): self.type = {
    self.x &= that.x
    self.y &= that.y
    self
  }

  /** Calculates the bitwise-exclusive-or of a scalar and each element of this vector and assigns it to this vector. */
  def ^=(that: Int): self.type = {
    self.x ^= that
    self.y ^= that
    self
  }

  /**
   * Calculates the bitwise-exclusive-or of each element of a vector and the corresponding element of this vector and
   * assigns it to this vector.
   */
  def ^=(that: Vector2[Int]): self.type = {
    self.x ^= that.x
    self.y ^= that.y
    self
  }

  /** Adds a scalar to each element of this vector and assigns it to this vector. */
  def +=(that: Int): self.type = {
    self.x += that
    self.y += that
    self
  }

  /** Adds each element of a vector to the corresponding element of this vector and assigns it to this vector. */
  def +=(that: Vector2[Int]): self.type = {
    self.x += that.x
    self.y += that.y
    self
  }

  /**
   * Adds a scalar to each element of this vector and assigns it to this vector (alias to avoid conflicts with string
   * concatenation).
   */
  def :+=(that: Int): self.type = this += that

  /**
   * Adds each element of a vector to the corresponding element of this vector and assigns it to this vector (alias to
   * avoid conflicts with string concatenation).
   */
  def :+=(that: Vector2[Int]): self.type = this += that

  /** Subtracts a scalar from each element of this vector and assigns it to this vector. */
  def -=(that: Int): self.type = {
    self.x -= that
    self.y -= that
    self
  }

  /** Subtracts each element of a vector from the corresponding element of this vector and assigns it to this vector. */
  def -=(that: Vector2[Int]): self.type = {
    self.x -= that.x
    self.y -= that.y
    self
  }

  /** Multiplies each element of this vector by a scalar and assigns it to this vector. */
  def *=(that: Int): self.type = {
    self.x *= that
    self.y *= that
    self
  }

  /** Multiplies each element of this vector by the corresponding element of a vector and assigns it to this vector. */
  def *=(that: Vector2[Int]): self.type = {
    self.x *= that.x
    self.y *= that.y
    self
  }

  /** Divides each element of this vector by a scalar and assigns it to this vector. */
  def /=(that: Int): self.type = {
    self.x /= that
    self.y /= that
    self
  }

  /** Divides each element of this vector by the corresponding element of a vector and assigns it to this vector. */
  def /=(that: Vector2[Int]): self.type = {
    self.x /= that.x
    self.y /= that.y
    self
  }

  /**
   * Calculates the remainder after division of each element of this vector by a scalar and assigns it to this vector.
   */
  def %=(that: Int): self.type = {
    self.x %= that
    self.y %= that
    self
  }

  /**
   * Calculates the remainder after division of each element of this vector by the corresponding element of a vector
   * and assigns it to this vector.
   */
  def %=(that: Vector2[Int]): self.type = {
    self.x %= that.x
    self.y %= that.y
    self
  }

}