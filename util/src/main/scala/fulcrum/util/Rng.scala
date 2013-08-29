/*
 * Rng.scala
 * 
 * Copyright (c) 1999 by Michael Lecuyer
 * Copyright (c) 2003 by Sean Luke
 * Copyright (c) 2013 Lonnie Pryor III
 *
 * Portions of this file are licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * The code in this file is based on version 20 of the MersenneTwister
 * implementation by Sean Luke and Michael Lecuyer available at:
 * http://www.cs.gmu.edu/~sean/research/mersenne/MersenneTwister.java.
 * The following information is reproduced from the original file:
 * 
 * ----------------------------------------------------------------------------
 * 
 * MersenneTwister and MersenneTwisterFast
 * 
 * Version 20, based on version MT199937(99/10/29) of the Mersenne Twister
 * algorithm found at The Mersenne Twister Home Page
 * (http://www.math.keio.ac.jp/matumoto/emt.html), with the initialization
 * improved using the new 2002/1/26 initialization algorithm By Sean Luke,
 * October 2004.
 *
 * About the Mersenne Twister
 * 
 * This is a Java version of the C-program for MT19937: Integer version.
 * The MT19937 algorithm was created by Makoto Matsumoto and Takuji Nishimura,
 * who ask: "When you use this, send an email to: matumoto@math.keio.ac.jp
 * with an appropriate reference to your work".  Indicate that this is a
 * translation of their algorithm into Java.
 *
 * Reference
 * 
 * Makato Matsumoto and Takuji Nishimura,
 * "Mersenne Twister: A 623-Dimensionally Equidistributed Uniform Pseudo-Random
 * Number Generator",
 * ACM Transactions on Modeling and. Computer Simulation,
 * Vol. 8, No. 1, January 1998, pp 3--30.
 *
 * The MersenneTwister code is based on standard MT19937 C/C++ code by Takuji
 * Nishimura, with suggestions from Topher Cooper and Marc Rieffel, July 1997.
 * The code was originally translated into Java by Michael Lecuyer,
 * January 1999, and the original code is Copyright (c) 1999 by Michael Lecuyer.
 *
 * License
 *
 * Copyright (c) 2003 by Sean Luke.
 * Portions copyright (c) 1993 by Michael Lecuyer.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimer.
 * 
 *  - Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 *  - Neither the name of the copyright owners, their employers, nor the 
 * names of its contributors may be used to endorse or promote products 
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package fulcrum.util

/**
 * A pseudorandom number generator, based on the Mersenne Twister algorithm, that is stable across varied runtimes.
 *
 * NOTE: instances of this class are NOT thread safe and should NEVER be shared between threads.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@annotation.strictfp
class Rng protected (seed: Rng.Seed) {

  import Rng._

  /** The array for the state vector. */
  private val state = new Array[Int](N)
  /** The current index into the state vector. */
  private var cursor = seed match {
    case LongSeed(value) => initialize(value)
    case SeqSeed(value) => initialize(value)
  }
  /** The next gaussian result. */
  private var nextGaussianResult: Double = _
  /** True if the next gaussian result is valid. */
  private var hasNextGaussianResult: Boolean = false

  /**
   * Initializes this pseudorandom number generator with a seed derived from the least-significant 32 bits of a long
   * value.
   */
  private def initialize(value: Long): Int = {
    state(0) = (value & 0xFFFFFFFF).toInt
    var i = 1
    while (i < N) {
      state(i) = (1812433253 * (state(i - 1) ^ (state(i - 1) >>> 30)) + i)
      i += 1
    }
    i
  }

  /**
   * Initializes this pseudorandom number generator with a seed derived from at most the first 624 elements of an
   * integer sequence.
   */
  private def initialize(value: Seq[Int]): Int = {
    require(value.length > 0, "Sequence length must be greater than zero.")
    val result = initialize(19650218)
    var i = 1
    var j = 0
    var k = if (N > value.length) N else value.length
    while (k != 0) {
      state(i) = (state(i) ^ ((state(i - 1) ^ (state(i - 1) >>> 30)) * 1664525)) + value(j) + j
      i += 1
      j += 1
      if (i >= N) {
        state(0) = state(N - 1)
        i = 1
      }
      if (j >= value.length) j = 0
      k -= 1
    }
    k = N - 1
    while (k != 0) {
      state(i) = (state(i) ^ ((state(i - 1) ^ (state(i - 1) >>> 30)) * 1566083941)) - i
      i += 1
      if (i >= N) {
        state(0) = state(N - 1)
        i = 1
      }
      k -= 1
    }
    state(0) = 0x80000000
    result
  }

  /** Returns a pseudorandom boolean with each outcome having equal probability. */
  def nextBoolean(): Boolean = next(1) != 0

  /**
   * Returns a pseudorandom boolean with the true outcome having the specified probability which must be in `[0, 1]`.
   */
  def nextBoolean(probability: Float): Boolean = {
    require(probability >= 0f && probability <= 1f,
      s"""Probability "$probability" is not between 0 and 1 inclusive.""")
    if (probability == 0f) false
    else if (probability == 1f) true
    else nextFloat() < probability
  }

  /**
   * Returns a pseudorandom boolean with the true outcome having the specified probability which must be in `[0, 1]`.
   */
  def nextBoolean(probability: Double): Boolean = {
    require(probability >= 0.0 && probability <= 1.0,
      s"""Probability "$probability" is not between 0 and 1 inclusive.""")
    if (probability == 0.0) false
    else if (probability == 1.0) true
    else nextDouble() < probability
  }

  /** Returns a pseudorandom byte value. */
  def nextByte(): Byte = next(8).toByte

  /** Returns a pseudorandom short value. */
  def nextShort(): Short = next(16).toShort

  /** Returns a pseudorandom character value. */
  def nextChar(): Char = next(16).toChar

  /** Returns a pseudorandom integer value. */
  def nextInt(): Int = next(32)

  /** Returns a pseudorandom integer value in the range `[0, limit)`, where `limit` must be greater than zero. */
  def nextInt(limit: Int): Int = {
    require(limit > 0, s"""Limit "$limit" is not greater than zero.""")
    if ((limit & -limit) == limit) ((limit * next(31).toLong) >> 31).toInt
    else {
      var bits = 0
      var result = 0
      do {
        bits = next(31)
        result = bits % limit
      } while (bits - result + (limit - 1) < 0)
      result
    }
  }

  /** Returns a pseudorandom long value. */
  def nextLong(): Long = (next(32).toLong << 32) + next(32)

  /** Returns a pseudorandom long value in the range `[0, limit)`, where `limit` must be greater than zero. */
  def nextLong(limit: Long): Long = {
    require(limit > 0L, s"""Limit "$limit" is not greater than zero.""")
    var bits = 0L
    var result = 0L
    do {
      bits = nextLong() >>> 1
      result = bits % limit
    } while (bits - result + (limit - 1) < 0)
    result
  }

  /** Returns a pseudorandom float value in the range `[0, 1)`. */
  def nextFloat(): Float = next(24) / (1 << 24).toFloat

  /**
   * Returns a pseudorandom float value between `0` and `1`, possibly including `0` and `1` depending on the values of
   * `includeZero` and `includeOne`.
   */
  def nextFloat(includeZero: Boolean, includeOne: Boolean): Float = {
    var result = 0f
    do {
      result = nextFloat()
      if (includeOne && nextBoolean()) result += 1f
    } while ((result > 1f) || (!includeZero && result == 0f))
    result
  }

  /** Returns a pseudorandom double value in the range `[0, 1)`. */
  def nextDouble(): Double = ((next(26).toLong << 27) + next(27)) / (1L << 53).toDouble

  /**
   * Returns a pseudorandom double value between `0` and `1`, possibly including `0` and `1` depending on the values of
   * `includeZero` and `includeOne`.
   */
  def nextDouble(includeZero: Boolean, includeOne: Boolean): Double = {
    var result = 0.0
    do {
      result = nextDouble()
      if (includeOne && nextBoolean()) result += 1.0
    } while ((result > 1.0) || (!includeZero && result == 0.0))
    result
  }

  /** Returns a pseudorandom, Gaussian distributed double value with mean `0` and standard deviation `1`. */
  def nextGaussian(): Double = {
    if (hasNextGaussianResult) {
      hasNextGaussianResult = false
      nextGaussianResult
    } else {
      var v1 = 0.0
      var v2 = 0.0
      var s = 0.0
      do {
        v1 = 2 * nextDouble() - 1
        v2 = 2 * nextDouble() - 1
        s = v1 * v1 + v2 * v2
      } while (s >= 1 || s == 0.0)
      val multiplier = StrictMath.sqrt(-2.0 * StrictMath.log(s) / s)
      nextGaussianResult = v2 * multiplier
      hasNextGaussianResult = true
      v1 * multiplier
    }
  }

  /** Fills an array with pseudorandom byte values. */
  def nextBytes(bytes: Array[Byte]): Unit = {
    var x = 0
    while (x < bytes.length) {
      bytes(x) = next(8).toByte
      x += 1
    }
  }

  /** Fills a range of an array with pseudorandom byte values. */
  def nextBytes(bytes: Array[Byte], offset: Int, count: Int): Unit = {
    require(offset >= 0 && offset < bytes.length, s"""Offset "$offset" is invalid for the specified array.""")
    require(count >= 1 && offset + count <= bytes.length, s"""Count "$count" is invalid for the specified array.""")
    var x = 0
    while (x < count) {
      bytes(offset + x) = next(8).toByte
      x += 1
    }
  }

  /** Returns an integer with `bits` bits filled with a pseudorandom number. */
  protected def next(bits: Int): Int = {
    var y: Int = 0
    if (cursor >= N) {
      var kk: Int = 0
      val state = this.state
      val matrix = MATRIX
      while (kk < N - M) {
        y = (state(kk) & UPPER_MASK) | (state(kk + 1) & LOWER_MASK)
        state(kk) = state(kk + M) ^ (y >>> 1) ^ matrix(y & 0x1)
        kk += 1
      }
      while (kk < N - 1) {
        y = (state(kk) & UPPER_MASK) | (state(kk + 1) & LOWER_MASK)
        state(kk) = state(kk + (M - N)) ^ (y >>> 1) ^ matrix(y & 0x1)
        kk += 1
      }
      y = (state(N - 1) & UPPER_MASK) | (state(0) & LOWER_MASK)
      state(N - 1) = state(M - 1) ^ (y >>> 1) ^ matrix(y & 0x1)
      cursor = 0
    }
    y = state(cursor)
    cursor += 1
    y ^= y >>> 11
    y ^= (y << 7) & TEMPERING_MASK_B
    y ^= (y << 15) & TEMPERING_MASK_C
    y ^= (y >>> 18)
    y >>> (32 - bits)
  }

}

/**
 * A factory for pseudorandom number generators.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
object Rng {

  //
  // Period parameters.
  //

  private val N = 624

  private val M = 397

  private val UPPER_MASK = 0x80000000

  private val LOWER_MASK = 0x7fffffff

  private val MATRIX = Array(0, 0x9908b0df)

  //
  // Tempering parameters.
  //

  private val TEMPERING_MASK_B = 0x9d2c5680

  private val TEMPERING_MASK_C = 0xefc60000

  //
  // Factory methods.
  //

  /** Creates a new pseudorandom number generator using a seed derived from the system time. */
  def apply(): Rng = apply(System.currentTimeMillis ^ System.nanoTime)

  /** Creates a new pseudorandom number generator using the least-significant 32 bits of a long value as a seed. */
  def apply(seed: Long): Rng = new Rng(LongSeed(seed))

  /**
   * Creates a new pseudorandom number generator using at most the first 624 elements of an integer sequence as a seed.
   */
  def apply(seed: Seq[Int]): Rng = new Rng(SeqSeed(seed))

  //
  // Seed types.
  //

  /**
   * Base class that is extended by the supported seed values.
   */
  sealed abstract class Seed

  /**
   * A seed derived from the least-significant 32 bits of a long value.
   */
  final case class LongSeed(value: Long) extends Seed

  /**
   * A seed derived from at most the first 624 elements of an integer sequence.
   */
  final case class SeqSeed(value: Seq[Int]) extends Seed

}
