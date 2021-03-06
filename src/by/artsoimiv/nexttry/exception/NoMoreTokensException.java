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
 * When there are no more tokens...
 * <p/>
 * ...including that the last token scanned was incomplete or otherwise bad.
 */
public class NoMoreTokensException extends Exception {
    public NoMoreTokensException() {
        super();
    }

    public NoMoreTokensException(String message) {
        super(message);
    }

    public NoMoreTokensException(String message, Throwable exception) {
        super(message, exception);
    }
}
