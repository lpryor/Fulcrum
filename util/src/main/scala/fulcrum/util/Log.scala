/*
 * Log.scala
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

import language.experimental.macros
import reflect.macros.Context

import java.util.logging.{
  Level => JLevel,
  Logger => JLogger
}

/**
 * An inlined wrapper around a logger.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
final class Log(val jLogger: JLogger) extends AnyVal {

  /** Returns true if the `Error` level is enabled. */
  def isErrorEnabled: Boolean = macro LogMacros.isErrorEnabled

  /** Returns true if the `Warn` level is enabled. */
  def isWarnEnabled: Boolean = macro LogMacros.isWarnEnabled

  /** Returns true if the `Info` level is enabled. */
  def isInfoEnabled: Boolean = macro LogMacros.isInfoEnabled

  /** Returns true if the `Debug` level is enabled. */
  def isDebugEnabled: Boolean = macro LogMacros.isDebugEnabled

  /** Returns true if the specified level is enabled. */
  def isEnabled(level: Log.Level): Boolean = macro LogMacros.isLevelEnabled

  /** Logs a message at the `Error` level if it is enabled. */
  def error(message: String): Unit = macro LogMacros.logError

  /** Logs a message and throwable at the `Error` level if it is enabled. */
  def error(message: String, thrown: Throwable): Unit = macro LogMacros.logErrorWith

  /** Logs a message at the `Warn` level if it is enabled. */
  def warn(message: String): Unit = macro LogMacros.logWarn

  /** Logs a message and throwable at the `Warn` level if it is enabled. */
  def warn(message: String, thrown: Throwable): Unit = macro LogMacros.logWarnWith

  /** Logs a message at the `Info` level if it is enabled. */
  def info(message: String): Unit = macro LogMacros.logInfo

  /** Logs a message and throwable at the `Info` level if it is enabled. */
  def info(message: String, thrown: Throwable): Unit = macro LogMacros.logInfoWith

  /** Logs a message at the `Debug` level if it is enabled. */
  def debug(message: String): Unit = macro LogMacros.logDebug

  /** Logs a message and throwable at the `Debug` level if it is enabled. */
  def debug(message: String, thrown: Throwable): Unit = macro LogMacros.logDebugWith

  /** Logs a message at the specified level if it is enabled. */
  def apply(level: Log.Level, message: String): Unit = macro LogMacros.log

  /** Logs a message and throwable at the specified level if it is enabled. */
  def apply(level: Log.Level, message: String, thrown: Throwable): Unit = macro LogMacros.logWith

}

/**
 * Factory for `Log` instances.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
object Log {

  /** Creates a `Log` using the name of the calling type. */
  def apply()(implicit factory: Log.Factory): Log = macro LogMacros.create

  /** Creates a `Log` using the specified name. */
  def apply(name: String)(implicit factory: Log.Factory): Log = macro LogMacros.createWithName

  /**
   * An inlined wrapper around log levels.
   */
  final class Level(val jLevel: JLevel) extends AnyVal

  /**
   * Definitions of the available log levels.
   */
  object Level {

    /** The error level. */
    def Error: Log.Level = macro LogMacros.errorLevel

    /** The warn level. */
    def Warn: Log.Level = macro LogMacros.warnLevel

    /** The info level. */
    def Info: Log.Level = macro LogMacros.infoLevel

    /** The debug level. */
    def Debug: Log.Level = macro LogMacros.debugLevel

  }

  /**
   * Factory type used to instantiate `Log` instances.
   */
  trait Factory extends (String => JLogger)

  /**
   * Definition of the default log factory.
   */
  object Factory {

    /**
     * The default log factory.
     */
    implicit object Default extends Factory {

      /** @inheritdoc */
      override def apply(name: String) = JLogger.getLogger(name)

    }

  }

}

/**
 * Definitions of the logging macro logic.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
object LogMacros {

  /** Macro that expands to the creation of the logger object. */
  def create(c: Context)()(factory: c.Expr[Log.Factory]): c.Expr[Log] =
    createWithName(c)(c.literal(c.enclosingClass.symbol.fullName))(factory)

  /** Macro that expands to the creation of the logger object. */
  def createWithName(c: Context)(name: c.Expr[String])(factory: c.Expr[Log.Factory]): c.Expr[Log] =
    c.universe.reify(new Log(factory.splice(name.splice)))

  /** Macro that expands to the creation of the error level. */
  def errorLevel(c: Context): c.Expr[Log.Level] =
    c.universe.reify(new Log.Level(JLevel.SEVERE))

  /** Macro that expands to the creation of the warn level. */
  def warnLevel(c: Context): c.Expr[Log.Level] =
    c.universe.reify(new Log.Level(JLevel.WARNING))

  /** Macro that expands to the creation of the info level. */
  def infoLevel(c: Context): c.Expr[Log.Level] =
    c.universe.reify(new Log.Level(JLevel.INFO))

  /** Macro that expands to the creation of the debug level. */
  def debugLevel(c: Context): c.Expr[Log.Level] =
    c.universe.reify(new Log.Level(JLevel.CONFIG))

  /** Macro that expands to a test of the log's level. */
  def isErrorEnabled(c: Context { type PrefixType = Log }): c.Expr[Boolean] =
    isLevelEnabled(c)(c.universe.reify(new Log.Level(JLevel.SEVERE)))

  /** Macro that expands to a test of the log's level. */
  def isWarnEnabled(c: Context { type PrefixType = Log }): c.Expr[Boolean] =
    isLevelEnabled(c)(c.universe.reify(new Log.Level(JLevel.WARNING)))

  /** Macro that expands to a test of the log's level. */
  def isInfoEnabled(c: Context { type PrefixType = Log }): c.Expr[Boolean] =
    isLevelEnabled(c)(c.universe.reify(new Log.Level(JLevel.INFO)))

  /** Macro that expands to a test of the log's level. */
  def isDebugEnabled(c: Context { type PrefixType = Log }): c.Expr[Boolean] =
    isLevelEnabled(c)(c.universe.reify(new Log.Level(JLevel.CONFIG)))

  /** Macro that expands to a test of the log's level. */
  def isLevelEnabled(c: Context { type PrefixType = Log })(level: c.Expr[Log.Level]): c.Expr[Boolean] =
    c.universe.reify(c.prefix.splice.jLogger.isLoggable(level.splice.jLevel))

  /** Macro that expands to the conditional submission of an error log entry. */
  def logError(c: Context { type PrefixType = Log })(message: c.Expr[String]): c.Expr[Unit] =
    log(c)(c.universe.reify(new Log.Level(JLevel.SEVERE)), message)

  /** Macro that expands to the conditional submission of an error log entry. */
  def logErrorWith(c: Context { type PrefixType = Log }) //
  (message: c.Expr[String], thrown: c.Expr[Throwable]): c.Expr[Unit] =
    logWith(c)(c.universe.reify(new Log.Level(JLevel.SEVERE)), message, thrown)

  /** Macro that expands to the conditional submission of a warn log entry. */
  def logWarn(c: Context { type PrefixType = Log })(message: c.Expr[String]): c.Expr[Unit] =
    log(c)(c.universe.reify(new Log.Level(JLevel.WARNING)), message)

  /** Macro that expands to the conditional submission of a warn log entry. */
  def logWarnWith(c: Context { type PrefixType = Log }) //
  (message: c.Expr[String], thrown: c.Expr[Throwable]): c.Expr[Unit] =
    logWith(c)(c.universe.reify(new Log.Level(JLevel.WARNING)), message, thrown)

  /** Macro that expands to the conditional submission of an info log entry. */
  def logInfo(c: Context { type PrefixType = Log })(message: c.Expr[String]): c.Expr[Unit] =
    log(c)(c.universe.reify(new Log.Level(JLevel.INFO)), message)

  /** Macro that expands to the conditional submission of an info log entry. */
  def logInfoWith(c: Context { type PrefixType = Log }) //
  (message: c.Expr[String], thrown: c.Expr[Throwable]): c.Expr[Unit] =
    logWith(c)(c.universe.reify(new Log.Level(JLevel.INFO)), message, thrown)

  /** Macro that expands to the conditional submission of a debug log entry. */
  def logDebug(c: Context { type PrefixType = Log })(message: c.Expr[String]): c.Expr[Unit] =
    log(c)(c.universe.reify(new Log.Level(JLevel.CONFIG)), message)

  /** Macro that expands to the conditional submission of a debug log entry. */
  def logDebugWith(c: Context { type PrefixType = Log }) //
  (message: c.Expr[String], thrown: c.Expr[Throwable]): c.Expr[Unit] =
    logWith(c)(c.universe.reify(new Log.Level(JLevel.CONFIG)), message, thrown)

  /** Macro that expands to the conditional submission of a log entry. */
  def log(c: Context { type PrefixType = Log })(level: c.Expr[Log.Level], message: c.Expr[String]): c.Expr[Unit] =
    c.universe.reify {
      val jLogger = c.prefix.splice.jLogger
      val jLevel = level.splice.jLevel
      if (jLogger.isLoggable(jLevel))
        jLogger.logp(jLevel, enclosingClass(c).splice, enclosingMethod(c).splice, message.splice)
    }

  /** Macro that expands to the conditional submission of a log entry with a throwable. */
  def logWith(c: Context { type PrefixType = Log }) //
  (level: c.Expr[Log.Level], message: c.Expr[String], thrown: c.Expr[Throwable]): c.Expr[Unit] =
    c.universe.reify {
      val jLogger = c.prefix.splice.jLogger
      val jLevel = level.splice.jLevel
      if (jLogger.isLoggable(jLevel))
        jLogger.logp(jLevel, enclosingClass(c).splice, enclosingMethod(c).splice, message.splice, thrown.splice)
    }

  /** Captures the name of the enclosing class of the call. */
  def enclosingClass(c: Context) =
    c.literal(c.enclosingClass.symbol.fullName)

  /** Captures the name of the enclosing method of the call. */
  def enclosingMethod(c: Context) =
    c.literal(if (c.enclosingMethod == null) "<init>" else c.enclosingMethod.symbol.name.decoded)

}