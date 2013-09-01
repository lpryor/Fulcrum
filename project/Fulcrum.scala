/*
 * Fulcrum.scala
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
import sbt._
import Keys._

/**
 * Global settings.
 */
object Settings {

  /** Default settings for all projects. */
  val defaults = Defaults.defaultSettings ++ Seq(
    organization := "fulcrum",
    version := "0.3",
    scalaVersion := "2.10.2",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"))

  val macros = Seq(
    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-reflect" % _))

  val testing = Seq(
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.11" % "test",
      "org.scalatest" %% "scalatest" % "1.9.1" % "test"))

}

/**
 * Project descriptors.
 */
object Fulcrum extends Build {

  /** Root project. */
  lazy val root: Project = Project(
    id = "fulcrum",
    base = file("."),
    settings = Settings.defaults //
    ) aggregate (math, util, util_tests)

  /** The math project. */
  lazy val math: Project = Project(
    id = "fulcrum-math",
    base = file("math"),
    settings = Settings.defaults
      ++ Settings.testing)

  /** The utilities project. */
  lazy val util: Project = Project(
    id = "fulcrum-util",
    base = file("util"),
    settings = Settings.defaults
      ++ Settings.macros
      ++ Settings.testing)

  /** The test project for macros in the utilities project. */
  lazy val util_tests: Project = Project(
    id = "fulcrum-util-tests",
    base = file("util-tests"),
    settings = Settings.defaults
      ++ Settings.testing //
      ) dependsOn (util)

}