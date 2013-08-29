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
package fulcrum.math.mutable

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Test case for [[fulcrum.math.mutable.RichVector2f]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RichVector2fSpec extends FunSpec with ShouldMatchers {

  describe("RichVector2f") {

    it("should support assignment forms of unary operators from Float") {
      Vector2(1f, 2f).negate() should equal(Vector2(-1f, -2f))
    }

    it("should support assignment forms of arithemetic operators from Float") {
      import Vector2._
      (Vector2(1f, 2f) += 1f) should equal(Vector2(2f, 3f))
      (Vector2(1f, 2f) += Vector2(3f, 4f)) should equal(Vector2(4f, 6f))
      (Vector2(1f, 2f) :+= 1f) should equal(Vector2(2f, 3f))
      (Vector2(1f, 2f) :+= Vector2(3f, 4f)) should equal(Vector2(4f, 6f))
      (Vector2(3f, 4f) -= 1f) should equal(Vector2(2f, 3f))
      (Vector2(3f, 4f) -= Vector2(1f, 2f)) should equal(Vector2(2f, 2f))
      (Vector2(1f, 2f) *= 2f) should equal(Vector2(2f, 4f))
      (Vector2(1f, 2f) *= Vector2(3f, 4f)) should equal(Vector2(3f, 8f))
      (Vector2(3f, 4f) /= 2f) should equal(Vector2(1.5f, 2f))
      (Vector2(3f, 4f) /= Vector2(1f, 2f)) should equal(Vector2(3f, 2f))
      (Vector2(3f, 4f) %= 2f) should equal(Vector2(1f, 0f))
      (Vector2(3f, 4f) %= Vector2(1f, 2f)) should equal(Vector2(0f, 0f))
    }

  }

}