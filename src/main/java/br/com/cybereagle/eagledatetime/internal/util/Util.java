/*
 * Copyright 2013 Cyber Eagle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package br.com.cybereagle.eagledatetime.internal.util;

import java.lang.reflect.Array;
import java.util.logging.Logger;

public final class Util {

    private static final String SINGLE_QUOTE = "'";

    public static boolean textHasContent(String aText) {
        return (aText != null) && (aText.trim().length() > 0);
    }

    public static String quote(Object aObject) {
        return SINGLE_QUOTE + String.valueOf(aObject) + SINGLE_QUOTE;
    }

}
