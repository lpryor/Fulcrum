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
package fulcrum.math.mutable

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Test case for [[fulcrum.math.mutable.RichVector2i]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RichVector2iSpec extends FunSpec with ShouldMatchers {

  describe("RichVector2i") {

    it("should support assignment forms of unary operators from Int") {
      Vector2(1, 2).flip() should equal(Vector2(~1, ~2))
      Vector2(1, 2).negate() should equal(Vector2(-1, -2))
    }

    it("should support assignment forms of shift operators from Int") {
      (Vector2(2, 4) <<= 1) should equal(Vector2(4, 8))
      (Vector2(2, 4) <<= Vector2(1, 1)) should equal(Vector2(4, 8))
      (Vector2(2, 4) >>= 1) should equal(Vector2(1, 2))
      (Vector2(2, 4) >>= Vector2(1, 1)) should equal(Vector2(1, 2))
      (Vector2(2, 4) >>>= 1) should equal(Vector2(1, 2))
      (Vector2(2, 4) >>>= Vector2(1, 1)) should equal(Vector2(1, 2))
    }

    it("should support assignment forms of bitwise operators from Int") {
      (Vector2(0x0F, 0xF0) |= 0xF0) should equal(Vector2(0xFF, 0xF0))
      (Vector2(0x0F, 0xF0) |= Vector2(0xF0, 0x0F)) should equal(Vector2(0xFF, 0xFF))
      (Vector2(0x0F, 0xF0) &= 0xF0) should equal(Vector2(0x00, 0xF0))
      (Vector2(0x0F, 0xF0) &= Vector2(0xF0, 0x0F)) should equal(Vector2(0x00, 0x00))
      (Vector2(0x0F, 0xF0) ^= 0xF0) should equal(Vector2(0xFF, 0x00))
      (Vector2(0x0F, 0xF0) ^= Vector2(0xF0, 0x0F)) should equal(Vector2(0xFF, 0xFF))
    }

    it("should support arithemetic operators from Int") {
      import Vector2._
      (Vector2(1, 2) += 1) should equal(Vector2(2, 3))
      (Vector2(1, 2) += Vector2(3, 4)) should equal(Vector2(4, 6))
      (Vector2(1, 2) :+= 1) should equal(Vector2(2, 3))
      (Vector2(1, 2) :+= Vector2(3, 4)) should equal(Vector2(4, 6))
      (Vector2(3, 4) -= 1) should equal(Vector2(2, 3))
      (Vector2(3, 4) -= Vector2(1, 2)) should equal(Vector2(2, 2))
      (Vector2(1, 2) *= 2) should equal(Vector2(2, 4))
      (Vector2(1, 2) *= Vector2(3, 4)) should equal(Vector2(3, 8))
      (Vector2(3, 4) /= 2) should equal(Vector2(1, 2))
      (Vector2(3, 4) /= Vector2(1, 2)) should equal(Vector2(3, 2))
      (Vector2(3, 4) %= 2) should equal(Vector2(1, 0))
      (Vector2(3, 4) %= Vector2(1, 2)) should equal(Vector2(0, 0))
    }

  }

}