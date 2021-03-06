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
 * When encountering characters outside of an IDENT, complain that
 * the token being scanned is asemantic or asyntactic.
 */
public class IllegalCharacterOutsideOfTokenException extends Exception {

    public IllegalCharacterOutsideOfTokenException() {
        super();
    }

    public IllegalCharacterOutsideOfTokenException(String message) {
        super(message);
    }

    public IllegalCharacterOutsideOfTokenException(String message, Throwable exception) {
        super(message, exception);
    }
}
