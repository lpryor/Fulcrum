/*
 * package.scala
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
package fulcrum

/**
 * Global definitions for the `math` package.
 *
 * @author Lonnie Pryor III (lonnie@pryor.us.com)
 */
package object math {
  
  /** An alias for two-element boolean vectors. */
  type Vector2b = Vector2[Boolean]
  
  /** An alias for two-element float vectors. */
  type Vector2f = Vector2[Float]
  
  /** An alias for two-element integer vectors. */
  type Vector2i = Vector2[Int]

}