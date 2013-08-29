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
package fulcrum.math

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Test case for [[fulcrum.math.RichVector2b]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RichVector2bSpec extends FunSpec with ShouldMatchers {

  describe("RichVector2b") {

    it("should support unary operators from Boolean") {
      !Vector2(true, false) should equal(Vector2(false, true))
    }

    it("should support comparison operators from Boolean") {
      import Vector2._
      val v = Vector2(true, false)
      v === true should equal(Vector2(true, false))
      v === Vector2(true, false) should equal(Vector2(true, true))
      v === false should equal(Vector2(false, true))
      v === Vector2(false, true) should equal(Vector2(false, false))
      v =!= true should equal(Vector2(false, true))
      v =!= Vector2(true, false) should equal(Vector2(false, false))
      v =!= false should equal(Vector2(true, false))
      v =!= Vector2(false, true) should equal(Vector2(true, true))
    }

    it("should support logical operators from Boolean") {
      val v = Vector2(true, false)
      v || true should equal(Vector2(true, true))
      v || Vector2(true, true) should equal(Vector2(true, true))
      v || false should equal(Vector2(true, false))
      v || Vector2(false, false) should equal(Vector2(true, false))
      v && true should equal(Vector2(true, false))
      v && Vector2(true, true) should equal(Vector2(true, false))
      v && false should equal(Vector2(false, false))
      v && Vector2(false, false) should equal(Vector2(false, false))
    }

    it("should support bitwise operators from Boolean") {
      val v = Vector2(true, false)
      v | true should equal(Vector2(true, true))
      v | Vector2(true, true) should equal(Vector2(true, true))
      v | false should equal(Vector2(true, false))
      v | Vector2(false, false) should equal(Vector2(true, false))
      v & true should equal(Vector2(true, false))
      v & Vector2(true, true) should equal(Vector2(true, false))
      v & false should equal(Vector2(false, false))
      v & Vector2(false, false) should equal(Vector2(false, false))
      v ^ true should equal(Vector2(false, true))
      v ^ Vector2(true, true) should equal(Vector2(false, true))
      v ^ false should equal(Vector2(true, false))
      v ^ Vector2(false, false) should equal(Vector2(true, false))
    }

    it("should create immutable copies by converting elements") {
      val v = Vector2(true, false)
      v.toVector2f should equal(immutable.Vector2(1f, 0f))
      v.toVector2i should equal(immutable.Vector2(1, 0))
    }

  }

}