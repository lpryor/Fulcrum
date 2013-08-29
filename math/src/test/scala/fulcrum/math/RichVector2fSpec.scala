/*
 * RichVector2fSpec.scala
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
 * Test case for [[fulcrum.math.RichVector2f]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RichVector2fSpec extends FunSpec with ShouldMatchers {

  describe("RichVector2f") {

    it("should support unary operators from Float") {
      val v = Vector2(1f, 2f)
      +v should equal(Vector2(+1f, +2f))
      -v should equal(Vector2(-1f, -2f))
    }

    it("should support comparison operators from Float") {
      import Vector2._
      val v = Vector2(1f, 2f)
      v === 1f should equal(Vector2(true, false))
      v === Vector2(1f, 2f) should equal(Vector2(true, true))
      v === 2f should equal(Vector2(false, true))
      v === Vector2(2f, 1f) should equal(Vector2(false, false))
      v =!= 1f should equal(Vector2(false, true))
      v =!= Vector2(1f, 2f) should equal(Vector2(false, false))
      v =!= 2f should equal(Vector2(true, false))
      v =!= Vector2(2f, 1f) should equal(Vector2(true, true))
      v < 1f should equal(Vector2(false, false))
      v < Vector2(1f, 2f) should equal(Vector2(false, false))
      v < 3f should equal(Vector2(true, true))
      v < Vector2(3f, 4f) should equal(Vector2(true, true))
      v <= 1f should equal(Vector2(true, false))
      v <= Vector2(1f, 2f) should equal(Vector2(true, true))
      v <= 3f should equal(Vector2(true, true))
      v <= Vector2(3f, 4f) should equal(Vector2(true, true))
      v > 1f should equal(Vector2(false, true))
      v > Vector2(1f, 2f) should equal(Vector2(false, false))
      v > 0f should equal(Vector2(true, true))
      v > Vector2(0f, 1f) should equal(Vector2(true, true))
      v >= 1f should equal(Vector2(true, true))
      v >= Vector2(1f, 2f) should equal(Vector2(true, true))
      v >= 3f should equal(Vector2(false, false))
      v >= Vector2(3f, 4f) should equal(Vector2(false, false))
    }

    it("should support arithemetic operators from Float") {
      import Vector2._
      val v1 = Vector2(1f, 2f)
      val v2 = Vector2(3f, 4f)
      v1 + 1f should equal(Vector2(2f, 3f))
      v1 + v2 should equal(Vector2(4f, 6f))
      v1 :+ 1f should equal(Vector2(2f, 3f))
      v1 :+ v2 should equal(Vector2(4f, 6f))
      v2 - 1f should equal(Vector2(2f, 3f))
      v2 - v1 should equal(Vector2(2f, 2f))
      v1 * 2f should equal(Vector2(2f, 4f))
      v1 * v2 should equal(Vector2(3f, 8f))
      v2 / 2f should equal(Vector2(1.5f, 2f))
      v2 / v1 should equal(Vector2(3f, 2f))
      v2 % 2f should equal(Vector2(1f, 0f))
      v2 % v1 should equal(Vector2(0f, 0f))
    }

    it("should create immutable copies by converting elements") {
      val v = Vector2(1f, 0f)
      v.toVector2b should equal(immutable.Vector2(true, false))
      v.toVector2i should equal(immutable.Vector2(1, 0))
    }

  }

}