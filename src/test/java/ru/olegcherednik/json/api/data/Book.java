/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package ru.olegcherednik.json.api.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.olegcherednik.json.api.ListUtils;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 08.01.2021
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {

    public static final Book THINKING_IN_JAVA = new Book("Thinking in Java",
                                                         ZonedDateTime.parse("2017-07-23T13:57:14.225Z"),
                                                         1998,
                                                         ListUtils.of("Bruce Eckel"));
    public static final Book READY_FOR_A_VICTORY = new Book("Ready for a victory",
                                                            ZonedDateTime.parse("2020-07-23T13:57:14.225Z"),
                                                            2020,
                                                            ListUtils.of("Oleg Cherednik"));


    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

}