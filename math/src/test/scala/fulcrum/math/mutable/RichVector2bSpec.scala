/*
 * RichVector2bSpec.scala
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
 * Test case for [[fulcrum.math.mutable.RichVector2b]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RichVector2bSpec extends FunSpec with ShouldMatchers {

  describe("mutable.RichVector2b") {

    it("should support assignment forms of unary operators from Boolean") {
      Vector2(true, false).negate() should equal(Vector2(false, true))
    }

    it("should support assignment forms of logical operators from Boolean") {
      (Vector2(true, false) ||= true) should equal(Vector2(true, true))
      (Vector2(true, false) ||= Vector2(true, true)) should equal(Vector2(true, true))
      (Vector2(true, false) ||= false) should equal(Vector2(true, false))
      (Vector2(true, false) ||= Vector2(false, false)) should equal(Vector2(true, false))
      (Vector2(true, false) &&= true) should equal(Vector2(true, false))
      (Vector2(true, false) &&= Vector2(true, true)) should equal(Vector2(true, false))
      (Vector2(true, false) &&= false) should equal(Vector2(false, false))
      (Vector2(true, false) &&= Vector2(false, false)) should equal(Vector2(false, false))
    }

    it("should support assignment forms of bitwise operators from Boolean") {
      val v = Vector2(true, false)
      (Vector2(true, false) |= true) should equal(Vector2(true, true))
      (Vector2(true, false) |= Vector2(true, true)) should equal(Vector2(true, true))
      (Vector2(true, false) |= false) should equal(Vector2(true, false))
      (Vector2(true, false) |= Vector2(false, false)) should equal(Vector2(true, false))
      (Vector2(true, false) &= true) should equal(Vector2(true, false))
      (Vector2(true, false) &= Vector2(true, true)) should equal(Vector2(true, false))
      (Vector2(true, false) &= false) should equal(Vector2(false, false))
      (Vector2(true, false) &= Vector2(false, false)) should equal(Vector2(false, false))
      (Vector2(true, false) ^= true) should equal(Vector2(false, true))
      (Vector2(true, false) ^= Vector2(true, true)) should equal(Vector2(false, true))
      (Vector2(true, false) ^= false) should equal(Vector2(true, false))
      (Vector2(true, false) ^= Vector2(false, false)) should equal(Vector2(true, false))
    }

  }

}