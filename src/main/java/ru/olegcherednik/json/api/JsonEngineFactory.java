/*
 * Copyright Oleg Cherednik
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

package ru.olegcherednik.json.api;

/**
 * The entry point of the concrete implementation of the {@link JsonEngine}. There are several restrictions of the
 * implementations of this interface.
 * <ul>
 * <li>Exactly one implementation of the {@link JsonEngineFactory} should be available;</li>
 * <li>The implementation should have full name like <tt>ru.olegcherednik.json.impl.StaticJsonEngineFactory</tt>
 * (i.e. all implementations have exactly the same class name);</li>
 * <li>The implementation should additionally contain following methods:
 * <ul>
 * <li><tt>public static JsonEngineFactory getInstance()</tt> - retrieves a singleton instance of
 * {@link JsonEngine};</li>
 * <li><tt>public static String getMainClass()</tt> - retrieves a name of the main json parser class
 * (e.g. for Gson this method should retrieve <tt>"com.google.gson.Gson"</tt>).</li>
 * </ul>
 * </li>
 * <li>The implementations should not have any dependencies from the json parser (e.g. for Gson the class should not
 * have any imports of <tt>com.google.gson.*</tt>) classes). All these code should be moved to another class.</li>
 * </ul>
 *
 * @author Oleg Cherednik
 * @since 19.11.2023
 */
public interface JsonEngineFactory {

    // this methods should be defined in implementations
    // public static JsonEngineFactory getInstance()
    // public static String getMainClass()

    JsonEngine createJsonEngine(JsonSettings settings);

    JsonEngine createPrettyPrintJsonEngine(JsonSettings settings);

}
