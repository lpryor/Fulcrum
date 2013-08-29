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
package fulcrum.math.mutable

/**
 * Defines additional operations available on mutable two-element boolean vectors.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class RichVector2b(val self: Vector2[Boolean]) extends AnyVal {

  /** Negates each element in this vector. */
  def negate(): self.type = {
    self.x = !self.x
    self.y = !self.y
    self
  }

  /** Calculates the logical-or of a scalar and each element of this vector and assigns it to this vector. */
  def ||=(that: Boolean): self.type = {
    self.x ||= that
    self.y ||= that
    self
  }

  /**
   * Calculates the logical-or of each element of a vector and the corresponding element of this vector and assigns it
   * to this vector.
   */
  def ||=(that: Vector2[Boolean]): self.type = {
    self.x ||= that.x
    self.y ||= that.y
    self
  }

  /** Calculates the logical-and of a scalar and each element of this vector and assigns it to this vector. */
  def &&=(that: Boolean): self.type = {
    self.x &&= that
    self.y &&= that
    self
  }

  /**
   * Calculates the logical-and of each element of a vector and the corresponding element of this vector and assigns it
   * to this vector.
   */
  def &&=(that: Vector2[Boolean]): self.type = {
    self.x &&= that.x
    self.y &&= that.y
    self
  }

  /** Calculates the bitwise-or of a scalar and each element of this vector and assigns it to this vector. */
  def |=(that: Boolean): self.type = {
    self.x |= that
    self.y |= that
    self
  }

  /**
   * Calculates the bitwise-or of each element of a vector and the corresponding element of this vector and assigns it
   * to this vector.
   */
  def |=(that: Vector2[Boolean]): self.type = {
    self.x |= that.x
    self.y |= that.y
    self
  }

  /** Calculates the bitwise-and of a scalar and each element of this vector and assigns it to this vector. */
  def &=(that: Boolean): self.type = {
    self.x &= that
    self.y &= that
    self
  }

  /**
   * Calculates the bitwise-and of each element of a vector and the corresponding element of this vector and assigns it
   * to this vector.
   */
  def &=(that: Vector2[Boolean]): self.type = {
    self.x &= that.x
    self.y &= that.y
    self
  }

  /** Calculates the bitwise-exclusive-or of a scalar and each element of this vector and assigns it to this vector. */
  def ^=(that: Boolean): self.type = {
    self.x ^= that
    self.y ^= that
    self
  }

  /**
   * Calculates the bitwise-exclusive-or of each element of a vector and the corresponding element of this vector and
   * assigns it to this vector.
   */
  def ^=(that: Vector2[Boolean]): self.type = {
    self.x ^= that.x
    self.y ^= that.y
    self
  }

}