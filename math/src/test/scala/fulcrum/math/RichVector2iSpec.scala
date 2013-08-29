/*
 * RichVector2iSpec.scala
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

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Test case for [[fulcrum.math.RichVector2i]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RichVector2iSpec extends FunSpec with ShouldMatchers {

  describe("RichVector2i") {

    it("should support unary operators from Int") {
      val v = Vector2(1, 2)
      ~v should equal(Vector2(~1, ~2))
      +v should equal(Vector2(+1, +2))
      -v should equal(Vector2(-1, -2))
    }

    it("should support shift operators from Int") {
      val v = Vector2(2, 4)
      v << 1 should equal(Vector2(4, 8))
      v << Vector2(1, 1) should equal(Vector2(4, 8))
      v >> 1 should equal(Vector2(1, 2))
      v >> Vector2(1, 1) should equal(Vector2(1, 2))
      v >>> 1 should equal(Vector2(1, 2))
      v >>> Vector2(1, 1) should equal(Vector2(1, 2))
    }

    it("should support comparison operators from Int") {
      import Vector2._
      val v = Vector2(1, 2)
      v === 1 should equal(Vector2(true, false))
      v === Vector2(1, 2) should equal(Vector2(true, true))
      v === 2 should equal(Vector2(false, true))
      v === Vector2(2, 1) should equal(Vector2(false, false))
      v =!= 1 should equal(Vector2(false, true))
      v =!= Vector2(1, 2) should equal(Vector2(false, false))
      v =!= 2 should equal(Vector2(true, false))
      v =!= Vector2(2, 1) should equal(Vector2(true, true))
      v < 1 should equal(Vector2(false, false))
      v < Vector2(1, 2) should equal(Vector2(false, false))
      v < 3 should equal(Vector2(true, true))
      v < Vector2(3, 4) should equal(Vector2(true, true))
      v <= 1 should equal(Vector2(true, false))
      v <= Vector2(1, 2) should equal(Vector2(true, true))
      v <= 3 should equal(Vector2(true, true))
      v <= Vector2(3, 4) should equal(Vector2(true, true))
      v > 1 should equal(Vector2(false, true))
      v > Vector2(1, 2) should equal(Vector2(false, false))
      v > 0 should equal(Vector2(true, true))
      v > Vector2(0, 1) should equal(Vector2(true, true))
      v >= 1 should equal(Vector2(true, true))
      v >= Vector2(1, 2) should equal(Vector2(true, true))
      v >= 3 should equal(Vector2(false, false))
      v >= Vector2(3, 4) should equal(Vector2(false, false))
    }

    it("should support bitwise operators from Int") {
      val v = Vector2(0x0F, 0xF0)
      v | 0xF0 should equal(Vector2(0xFF, 0xF0))
      v | Vector2(0xF0, 0x0F) should equal(Vector2(0xFF, 0xFF))
      v & 0xF0 should equal(Vector2(0x00, 0xF0))
      v & Vector2(0xF0, 0x0F) should equal(Vector2(0x00, 0x00))
      v ^ 0xF0 should equal(Vector2(0xFF, 0x00))
      v ^ Vector2(0xF0, 0x0F) should equal(Vector2(0xFF, 0xFF))
    }

    it("should support arithemetic operators from Int") {
      import Vector2._
      val v1 = Vector2(1, 2)
      val v2 = Vector2(3, 4)
      v1 + 1 should equal(Vector2(2, 3))
      v1 + v2 should equal(Vector2(4, 6))
      v1 :+ 1 should equal(Vector2(2, 3))
      v1 :+ v2 should equal(Vector2(4, 6))
      v2 - 1 should equal(Vector2(2, 3))
      v2 - v1 should equal(Vector2(2, 2))
      v1 * 2 should equal(Vector2(2, 4))
      v1 * v2 should equal(Vector2(3, 8))
      v2 / 2 should equal(Vector2(1, 2))
      v2 / v1 should equal(Vector2(3, 2))
      v2 % 2 should equal(Vector2(1, 0))
      v2 % v1 should equal(Vector2(0, 0))
    }

    it("should create immutable copies by converting elements") {
      val v = Vector2(1, 0)
      v.toVector2b should equal(immutable.Vector2(true, false))
      v.toVector2f should equal(immutable.Vector2(1f, 0f))
    }

  }

}