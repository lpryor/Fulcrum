/*
 * OptSpec.scala
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

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Test case for [[fulcrum.util.Opt]].
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
@org.junit.runner.RunWith(classOf[org.scalatest.junit.JUnitRunner])
class OptSpec extends FunSpec with ShouldMatchers {

  val none = new Opt[String](null)
  val some = new Opt("Hello")

  describe("Opt") {

    it("should use nullable references for its internal representation") {
      none.value should be(null)
      some.value should be("Hello")
      none should equal(Opt.empty[String])
      some should equal(Opt("Hello"))
      none should not equal (some)
      some should not equal (none)
      none.toString should be("Opt.empty")
      some.toString should be("Opt(Hello)")
    }

    it("should report being empty and/or undefined only for null values") {
      none.isEmpty should be(true)
      none.nonEmpty should be(false)
      none.isDefined should be(false)
      some.isEmpty should be(false)
      some.nonEmpty should be(true)
      some.isDefined should be(true)
    }

    it("should only return non-null values from get") {
      evaluating(none.get) should produce[NoSuchElementException]
      some.get should be("Hello")
    }

    it("should return non-null values or an alternative from getOrElse") {
      none getOrElse "World" should be("World")
      some getOrElse "World" should be("Hello")
      some getOrElse {
        throw new IllegalStateException
      } should be("Hello")
    }

    it("should return the underlying value directly from orNull") {
      none.orNull should be(null)
      some.orNull should be("Hello")
    }

    it("should apply functions to non-null values in map") {
      none map (_.toUpperCase) should equal(Opt.empty[String])
      some map (_.toUpperCase) should equal(Opt("HELLO"))
    }

    it("should apply functions to non-null values or return an alternative from fold") {
      none.fold("World")(_.toUpperCase) should be("World")
      some.fold("World")(_.toUpperCase) should be("HELLO")
      some.fold {
        throw new IllegalStateException
      }(_.toUpperCase) should be("HELLO")
    }

    it("should apply functions to non-null values and flatten results in flatMap") {
      none flatMap (s => Opt.empty[String]) should equal(Opt.empty[String])
      none flatMap (s => Opt(s.toUpperCase)) should equal(Opt.empty[String])
      some flatMap (s => Opt.empty[String]) should equal(Opt.empty[String])
      some flatMap (s => Opt(s.toUpperCase)) should equal(Opt("HELLO"))
    }

    it("should evaluate predicates on non-null values and return matching values from filter") {
      none filter (_ => false) should equal(Opt.empty[String])
      none filter (_ => true) should equal(Opt.empty[String])
      some filter (_ => false) should equal(Opt.empty[String])
      some filter (_ => true) should equal(Opt("Hello"))
    }

    it("should evaluate predicates on non-null values and return non-matching values from filterNot") {
      none filterNot (_ => false) should equal(Opt.empty[String])
      none filterNot (_ => true) should equal(Opt.empty[String])
      some filterNot (_ => false) should equal(Opt("Hello"))
      some filterNot (_ => true) should equal(Opt.empty[String])
    }

    it("should evaluate predicates on non-null values and return the result from exists") {
      none exists (_ => false) should be(false)
      none exists (_ => true) should be(false)
      some exists (_ => false) should be(false)
      some exists (_ => true) should be(true)
    }

    it("should evaluate predicates on non-null values and return the result from forall") {
      none forall (_ => false) should be(true)
      none forall (_ => true) should be(true)
      some forall (_ => false) should be(false)
      some forall (_ => true) should be(true)
    }

    it("should execute actions on non-null values in foreach") {
      var called = false
      none foreach (_ => called = true)
      called should be(false)
      some foreach { s =>
        s should equal("Hello")
        called = true
      }
      called should be(true)
    }

    it("should apply a partial functions to non-null values and return the result (if defined) in collect") {
      none collect {
        case "Hello" => "H"
        case "World" => "W"
        case _ => "_"
      } should equal(Opt.empty[String])
      none collect {
        case "" => ""
      } should equal(Opt.empty[String])
      some collect {
        case "Hello" => "H"
        case "World" => "W"
        case _ => "_"
      } should equal(Opt("H"))
      some collect {
        case "" => ""
      } should equal(Opt.empty[String])
    }

    it("should return non-empty inline options or an alternative from orElse") {
      none orElse Opt("World") should equal(Opt("World"))
      some orElse Opt("World") should equal(Opt("Hello"))
      some orElse {
        throw new IllegalStateException
      } should equal(Opt("Hello"))
    }

    it("should convert to other types of standard collections") {
      none.iterator.toList should be(Nil)
      some.iterator.toList should be(List("Hello"))
      none.toOption should be(None)
      some.toOption should be(Some("Hello"))
      none.toList should be(Nil)
      some.toList should be(List("Hello"))
      none toRight "World" should be(Left("World"))
      some toRight "World" should be(Right("Hello"))
      none toLeft "World" should be(Right("World"))
      some toLeft "World" should be(Left("Hello"))
    }

    it("should operate correctly when used in a for comprehension") {
      assert({ for (x <- none if x.length == 0) yield x.toUpperCase } === Opt.empty[String])
      assert({ for (x <- none if x.length > 0) yield x.toUpperCase } === Opt.empty[String])
      assert({ for (x <- some if x.length == 0) yield x.toUpperCase } === Opt.empty[String])
      assert({ for (x <- some if x.length > 0) yield x.toUpperCase } === Opt("HELLO"))
    }

  }

}