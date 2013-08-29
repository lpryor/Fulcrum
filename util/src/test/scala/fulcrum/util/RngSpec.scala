/*
 * RngSpec.scala
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
package fulcrum.util

import io.Source
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Test case for [[fulcrum.util.Rng]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class RngSpec extends FunSpec with ShouldMatchers {

  describe("Rng") {

    it("should match the output from the original Mersenne Twister implementation") {
      val rng = Rng(Array(0x123, 0x234, 0x345, 0x456))
      val lines = Source.fromURL(getClass.getResource("RngSpec.txt")).getLines
      for (line <- lines) {
        var l = rng.nextInt().toLong
        if (l < 0) l += 4294967296L
        l.toString should be(line)
      }
    }

    it("should produce boolean values with customizable proabilities") {
      val rng = Rng()
      assert(rng.nextBoolean().isInstanceOf[Boolean])
      assert(rng.nextBoolean(0.5f).isInstanceOf[Boolean])
      rng.nextBoolean(0f) should be(false)
      rng.nextBoolean(1f) should be(true)
      evaluating(rng.nextBoolean(-1f)) should produce[IllegalArgumentException]
      evaluating(rng.nextBoolean(2f)) should produce[IllegalArgumentException]
      assert(rng.nextBoolean(0.5).isInstanceOf[Boolean])
      rng.nextBoolean(0.0) should be(false)
      rng.nextBoolean(1.0) should be(true)
      evaluating(rng.nextBoolean(-1.0)) should produce[IllegalArgumentException]
      evaluating(rng.nextBoolean(2.0)) should produce[IllegalArgumentException]
    }

    it("should produce byte values") {
      val rng = Rng(1234567)
      assert(rng.nextByte().isInstanceOf[Byte])
    }

    it("should produce short values") {
      val rng = Rng(1234567)
      assert(rng.nextShort().isInstanceOf[Short])
    }

    it("should produce character values") {
      val rng = Rng(1234567)
      assert(rng.nextChar().isInstanceOf[Char])
    }

    it("should produce integer values with customizable size limits") {
      val rng = Rng(1234567)
      assert(rng.nextInt().isInstanceOf[Int])
      rng.nextInt(7) should be < (7)
      evaluating(rng.nextInt(0)) should produce[IllegalArgumentException]
    }

    it("should produce long values with customizable size limits") {
      val rng = Rng(1234567)
      assert(rng.nextLong().isInstanceOf[Long])
      rng.nextLong(7) should be < (7L)
      evaluating(rng.nextLong(0)) should produce[IllegalArgumentException]
    }

    it("should produce float values with customizable policies for returning 0 and 1") {
      val rng = Rng(1234567)
      assert(rng.nextFloat().isInstanceOf[Float])
      assert(rng.nextFloat(false, false).isInstanceOf[Float])
      assert(rng.nextFloat(true, false).isInstanceOf[Float])
      assert(rng.nextFloat(false, true).isInstanceOf[Float])
      assert(rng.nextFloat(true, true).isInstanceOf[Float])
    }

    it("should produce double values with customizable policies for returning 0 and 1") {
      val rng = Rng(1234567)
      assert(rng.nextDouble().isInstanceOf[Double])
      assert(rng.nextDouble(false, false).isInstanceOf[Double])
      assert(rng.nextDouble(true, false).isInstanceOf[Double])
      assert(rng.nextDouble(false, true).isInstanceOf[Double])
      assert(rng.nextDouble(true, true).isInstanceOf[Double])
    }

    it("should produce Gaussian double values") {
      val rng = Rng(1234567)
      assert(rng.nextGaussian().isInstanceOf[Double])
      assert(rng.nextGaussian().isInstanceOf[Double])
    }

    it("should produce fill byte arrays and ranges of byte arrays") {
      val rng = Rng(1234567)
      var bytes = Array.fill(10)(0.toByte)
      rng.nextBytes(bytes)
      assert(bytes exists (_ != 0))
      bytes = Array.fill(10)(0.toByte)
      rng.nextBytes(bytes, 1, 8)
      assert(bytes(0) == 0)
      assert(bytes(9) == 0)
      assert(bytes exists (_ != 0))
    }

  }

}