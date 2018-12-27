/* -------------------------------------------------------------------
 * Copyright (c) 2014 PerfectSearch Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * -------------------------------------------------------------------
 */
package by.artsoimiv.nexttry.exception;

/**
 * Stuff like no key when found an array. This is either a syntax
 * error, or our parser is broken.
 */
public class ParserBrokenException extends Exception {
    public ParserBrokenException() {
        super();
    }

    public ParserBrokenException(String message) {
        super(message);
    }

    public ParserBrokenException(String message, Throwable exception) {
        super(message, exception);
    }
}
