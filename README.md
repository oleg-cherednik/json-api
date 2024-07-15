[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.json/json-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.json/json-api)
[![javadoc](https://javadoc.io/badge2/ru.oleg-cherednik.json/json-api/javadoc.svg)](https://javadoc.io/doc/ru.oleg-cherednik.json/json-api)
[![java8](https://badgen.net/badge/java/8+/blue)](https://badgen.net/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

[![github-ci](https://github.com/oleg-cherednik/json-api/actions/workflows/github-ci.yml/badge.svg?branch=master&event=push)](https://github.com/oleg-cherednik/json-api/actions)
[![vulnerabilities](https://snyk.io/test/github/oleg-cherednik/json-api/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/oleg-cherednik/json-api?targetFile=build.gradle)
[![license-scan](https://app.fossa.com/api/projects/git%2Bgithub.com%2Foleg-cherednik%2Fjson-api.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Foleg-cherednik%2Fjson-api?ref=badge_shield)
[![quality](https://app.codacy.com/project/badge/Grade/7645235119a749c79dd1bfb22b78dc34?branch=master)](https://app.codacy.com/gh/oleg-cherednik/json-api/dashboard?branch=master)
[![coverage](https://app.codacy.com/project/badge/Coverage/7645235119a749c79dd1bfb22b78dc34?branch=master)](https://app.codacy.com/gh/oleg-cherednik/json-api/coverage/dashboard?branch=master)

<details><summary>develop</summary>

[![github-ci](https://github.com/oleg-cherednik/json-api/actions/workflows/github-ci.yml/badge.svg?branch=develop&event=push)](https://github.com/oleg-cherednik/json-api/actions)
[![quality](https://app.codacy.com/project/badge/Grade/7645235119a749c79dd1bfb22b78dc34?branch=develop)](https://app.codacy.com/gh/oleg-cherednik/json-api/dashboard?branch=develop)
[![coverage](https://app.codacy.com/project/badge/Coverage/7645235119a749c79dd1bfb22b78dc34?branch=develop)](https://app.codacy.com/gh/oleg-cherednik/json-api/coverage/dashboard?branch=develop)

</details>

[![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Fira+Code&pause=1000&random=false&width=435&lines=One+json-api+to+rule+them+all)](https://git.io/typing-svg)

# Table of contest

* [Getting Started](#getting-started)
* [Features](#features)
* [Glossary](#glossary)
* [Requirements](#requirements)
* [Usage](#usage)
    * [Json](#json-class) - utility class with set of methods to use json transformation;
        * [Read json from `String`](#read-json-from-string) - read json from `String`;
        * [Read json from `ByteBuffer`](#read-json-from-bytebuffer) - read json from `ByteBuffer`;
            * [Read json from `ByteBuffer` lazy](#read-json-from-bytebuffer-lazy)
                - read json from `ByteBuffer` lazy;
        * [Read json from `InputStream`](#read-json-from-inputstream) - read json from `InputStream`;
            *   [Read json from `InputStream` lazy](#read-json-from-inputstream-lazy) - read json from `InputStream` lazy;
        * [Read json from `Reader`](#read-json-from-reader) - read json from `Reader`;
            * [Read json from `Reader` lazy](#read-json-from-reader-lazy) - read json from `Reader` lazy;
        * [Write json](#write-json) - write json to `String`, `OutputStream` or `Writer`;
        * [Get default decorators](#get-default-decorators) - get current instances of `JsonReader` and `JsonWriter`;
        * [Create copy of default decorators](#create-copy-of-default-decorators) - create a new
          instance of `JsonReader` and `JsonWriter` with default setting;
        * [Create custom decorators](#create-custom-decorators) - create an instance of `JsonReader`
          and `JsonWriter` with custom setting.
        * [Convert object](#convert-object) - convert given `Object` to another object.
    * [JsonHelper](#jsonhelper-class) - utility class with set of methods to
      update actual settings;
    * [EnumId](#work-with-enum) - advanced enum serialization support.
* [Custom `json-api` implementation](#custom-json-api-implementation)
* [Links](#links)

---

# Getting Started

Imagine you would like to use [jackson 2.16.1](https://github.com/FasterXML/jackson) as a **json framework** in your application. In this case, you
have
several options:

1.   Add [jackson](https://github.com/FasterXML/jackson) dependencies and use it directly;
2.   Along with [jackson](https://github.com/FasterXML/jackson) dependencies add
     [jackson-json-api](https://github.com/oleg-cherednik/json-jackson-impl) and use Jackson via **json-api**.

If you choose 2<sup>nd</sup> option, you should add **json-api implementation** for [jackson](https://github.com/FasterXML/jackson)
(which is [json-jackson-impl](https://github.com/oleg-cherednik/json-jackson-impl)) along with existed Jackson dependencies, because **json-api
implementation** does not
contain concrete version of the **json framework**. The version should be additionally specified. I.e. version of
**json-api implementation** does not depend on the version of the **json framework**.

### Gradle

```groovy
implementation 'ru.oleg-cherednik.json:json-jackson-impl:3.0'
implementation 'com.fasterxml.jackson.core:jackson-databind:2.16.0'
```

### Maven

```xml

<dependencies>
    <dependency>
        <groupId>ru.oleg-cherednik.json</groupId>
        <artifactId>json-jackson-impl</artifactId>
        <version>3.0</version>
    </dependency>
    <dependency>
        <groupId>ru.oleg-cherednik.json</groupId>
        <artifactId>json-jackson-impl</artifactId>
        <version>3.0</version>
    </dependency>
</dependencies>
```

# Features

*   Single file API for all json actions;
*   Give an easy way to provide custom engine implementation;
*   It's free of any engine's specific code;
*   It's fully open-source and does not depend on any limited licenses.

# Glossary

*   `json framework` is a framework for working with json files like [jackson](https://github.com/FasterXML/jackson),
    [gson](https://github.com/google/gson), [json-simple](https://github.com/fangyidong/json-simple), etc. Usually, we
    use **json framework** in the application directly by adding required dependencies. In general, all these
    **json frameworks** have its own API and style of coding.
*   `json engine` is an abstraction above all **json framework**. The main idea is to provide a unified API over all
    **json frameworks**. I.e., using this unified API (**json engine**), the client is not able to use some specific
    logic of concrete **json framework**, but the most common use-cases are available.
*   `json decorator` is a decorator over **json engine**. There are **read** and **write** decorators that contain the
    complete set of not static methods for json manipulation. You can use default **decorators** or create custom once
    with required settings. The decorator is used to work with **json engine** with given settings.
*   `JSON-API` is an abstraction over various **json decorators**. It provides a simple way to do the most common
    use-cases of json manipulations. Moreover, it provides the way of single point configuration and exception handling.
    Using this **json-api** you are able to not depend on the specific **json framework** directly and use any of them
    via given **json engine**.
*   `json-api-impl` is a concreted implementation of **json-api** for given **json framework**. E.g., an implementation
    of **json-api** for [jackson](https://github.com/FasterXML/jackson) called
    [json-jackson-impl](https://github.com/oleg-cherednik/json-jackson-impl) and contains instance of **json engine**
    called `JacksonEngine`.

# Requirements

# Usage

**json-api** provides set of classes to work with json. You should use only these classes for any json manipulations.

### Json class

#### Read json from `String`

<details><summary><code>String</code> to a custom object type (except a collection)</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;
        Data data = Json.readValue(json, Data.class);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>List</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        List<Object> res = Json.readList(json);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>Set</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Set<Object> res = Json.readSet(json);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>List</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        List<Data> res = Json.readList(json, Data.class);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>Set</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Set<Data> res = Json.readSet(json, Data.class);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>List</code> of <code>Map</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        List<Map<String, Object>> res = Json.readListOfMap(json);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        Map<String, Object> res = Json.readMap(json);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        Map<String, Book> res = Json.readMap(json, Book.class);
    }

}
```

</details>

<details><summary><code>String</code> to a <code>Map</code> of custom key and value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "1": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "2": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        Map<Integer, Book> res = Json.readMap(json, Integer.class, Book.class);
    }

}
```

</details>

#### Read json from `ByteBuffer`

<details><summary><code>ByteBuffer</code> to a custom object type (except a collection)</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Data data = Json.readValue(buf, Data.class);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>List</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        List<Object> res = Json.readList(buf);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>Set</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Set<Object> res = Json.readSet(buf);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>List</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        List<Data> res = Json.readList(buf, Data.class);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>Set</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Set<Data> res = Json.readSet(buf, Data.class);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>List</code> of <code>Map</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        List<Map<String, Object>> res = Json.readListOfMap(buf);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> res = Json.readMap(buf);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Map<String, Book> res = Json.readMap(buf, Book.class);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to a <code>Map</code> of custom key and value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "1": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "2": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Map<Integer, Book> res = Json.readMap(buf, Integer.class, Book.class);
    }

}
```

</details>

##### Read json from `ByteBuffer` lazy

<details><summary><code>ByteBuffer</code> to an <code>Iterator</code> of <code>Object</code> type with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Iterator<Object> it = Json.readListLazy(buf);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to an <code>Iterator</code> of custom object type with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Iterator<Data> it = Json.readListLazy(buf, Data.class);
    }

}
```

</details>

<details><summary><code>ByteBuffer</code> to an <code>Iterator</code> of <code>Map</code> with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        ByteBuffer buf = ByteBuffer.wrap(json.getBytes(StandardCharsets.UTF_8));
        Iterator<Map<String, Object>> it = Json.readListOfMapLazy(buf);
    }

}
```

</details>

#### Read json from `InputStream`

<details><summary><code>InputStream</code> to a custom object type (except a collection)</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        Data data = Json.readValue(in, Data.class);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>List</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        List<Object> res = Json.readList(in);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>Set</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        Set<Object> res = Json.readSet(in);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>List</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        List<Data> res = Json.readList(in, Data.class);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>Set</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        Set<Data> res = Json.readSet(in, Data.class);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>List</code> of <code>Map</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        List<Map<String, Object>> res = Json.readListOfMap(in);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        Map<String, Object> res = Json.readMap(in);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        Map<String, Book> res = Json.readMap(in, Book.class);
    }

}
```

</details>

<details><summary><code>InputStream</code> to a <code>Map</code> of custom key and value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "1": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "2": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        Map<Integer, Book> res = Json.readMap(in, Integer.class, Book.class);
    }

}
```

</details>

##### Read json from `InputStream` lazy

<details><summary><code>InputStream</code> to an <code>Iterator</code> of <code>Object</code> type with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        AutoCloseableIterator<Object> it = Json.readListLazy(in);
    }

}
```

</details>


<details><summary><code>InputStream</code> to an <code>Iterator</code> of custom object type with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        AutoCloseableIterator<Data> it = Json.readListLazy(in, Data.class);
    }

}
```

</details>

<details><summary><code>InputStream</code> to an <code>Iterator</code> of <code>Map</code> with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        InputStream in = new ByteArrayInputStream(json.getBytes());
        AutoCloseableIterator<Map<String, Object>> it = Json.readListOfMapLazy(in);
    }

}
```

</details>

#### Read json from `Reader`

<details><summary><code>Reader</code> to a custom object type (except a collection)</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;
        Reader reader = new ByteArrayInputStream(json.getBytes());
        Data data = Json.readValue(reader, Data.class);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>List</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        List<Object> res = Json.readList(reader);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>Set</code> of <code>Object</code> type</summary>

```java
class Data {

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        Set<Object> res = Json.readSet(reader);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>List</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        List<Data> res = Json.readList(reader, Data.class);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>Set</code> of custom object type</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        Set<Data> res = Json.readSet(reader, Data.class);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>List</code> of <code>Map</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        List<Map<String, Object>> res = Json.readListOfMap(reader);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        Reader reader = new StringReader(json);
        Map<String, Object> res = Json.readMap(reader);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "one": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "two": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        Reader reader = new StringReader(json);
        Map<String, Book> res = Json.readMap(reader, Book.class);
    }

}
```

</details>

<details><summary><code>Reader</code> to a <code>Map</code> of custom key and value type</summary>

```java
public class Book {

    private String title;
    private ZonedDateTime date;
    private int year;
    private List<String> authors;

    public static void demo() {
        String json = """
                {
                    "1": {
                        "title": "Thinking in Java",
                        "date": "2017-07-23T13:57:14.225Z",
                        "year": 1998,
                        "authors": [
                            "Bruce Eckel"
                        ]
                    },
                    "2": {
                        "title": "Ready for a victory",
                        "date": "2020-07-23T13:57:14.225Z",
                        "year": 2020,
                        "authors": [
                            "Oleg Cherednik"
                        ]
                    }
                }
                """;
        Reader reader = new StringReader(json);
        Map<Integer, Book> res = Json.readMap(reader, Integer.class, Book.class);
    }

}
```

</details>

##### Read json from `Reader` lazy

<details><summary><code>Reader</code> to an <code>Iterator</code> of <code>Object</code> type with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        AutoCloseableIterator<Object> it = Json.readListLazy(reader);
    }

}
```

</details>

<details><summary><code>Reader</code> to an <code>Iterator</code> of custom object type with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        AutoCloseableIterator<Data> it = Json.readListLazy(reader, Data.class);
    }

}
```

</details>

<details><summary><code>Reader</code> to an <code>Iterator</code> of <code>Map</code> with lazy reading</summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        String json = """
                [
                    {
                        "intVal" : 555,
                        "strVal" : "victory"
                    },
                    {
                        "intVal" : 666,
                        "strVal" : "omen"
                    }
                ]
                """;
        Reader reader = new StringReader(json);
        AutoCloseableIterator<Map<String, Object>> it = Json.readListOfMapLazy(reader);
    }

}
```

</details>

#### Write json

<details><summary>Write to <code>String</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        Data data = new Data(666, "omen");
        String json = Json.writeValue(data);
    }

}
```

</details>

<details><summary>Write to <code>OutputStream</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        Data data = new Data(666, "omen");

        try (OutputStream out = new ByteArrayOutputStream()) {
            Json.writeValue(data, out);
        }
    }

}
```

</details>

<details><summary>Write to <code>Writer</code></summary>

```java
class Data {

    int intVal;
    String strVal;

    public static void demo() {
        Data data = new Data(666, "omen");

        try (Writer out = new StringWriter()) {
            Json.writeValue(data, out);
        }
    }

}
```

</details>

#### Get default decorators

<details><summary>Get current default instance of <code>JsonReader</code></summary>

```java
class Data {

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;

        // using static method
        Data data1 = Json.readValue(json, Data.class);

        // alternative: using JsonReader instance
        JsonReader reader = Json.reader();
        Data data2 = reader.readValue(json, Data.class);
    }

}
```

</details>

<details><summary>Get current default instance of <code>JsonWriter</code></summary>

```java
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");

        // using static method
        String json1 = Json.writeValue(data);

        // alternative: using JsonWriter instance
        JsonWriter writer = Json.writer();
        String json2 = writer.writeValue(data);
    }

}
```

</details>

<details><summary>Get current default instance of <code>JsonWriter</code> with enabled <code>pretty-print</code> option</summary>

```java
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");

        // there is no way for pretty-print using static method

        // using JsonWriter instance with pretty-print option
        JsonWriter writer = Json.prettyPrint();
        String json = writer.writeValue(data);
    }

}
```

</details>

#### Create copy of default decorators

<details><summary>Create a new instance of <code>JsonReader</code> with default settings</summary>

```java
class Data {

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;

        // using static method
        Data data1 = Json.readValue(json, Data.class);

        // alternative: using new JsonReader instance
        JsonReader reader = Json.createReader();
        Data data2 = reader.readValue(json, Data.class);
    }

}
```

</details>

<details><summary>Create a new instance of <code>JsonWriter</code> with default settings</summary>

```java
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");

        // using static method
        String json1 = Json.writeValue(data);

        // alternative: using new JsonWriter instance
        JsonWriter writer = Json.createWriter();
        String json2 = writer.writeValue(data);
    }

}
```

</details>

<details><summary>Create a new instance of <code>JsonWriter</code> with default settings and enabled
<code>pretty-print</code> option</summary>

```java
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");

        // there is no way for pretty-print using static method

        // using new JsonWriter instance with pretty-print option
        JsonWriter writer = Json.createPrettyPrint();
        String json = writer.writeValue(data);
    }

}
```

</details>

#### Create custom decorators

<details><summary>Create a new instance of <code>JsonReader</code> with custom settings</summary>

```java
class Data {

    public static void demo() {
        String json = """
                 {
                    "intVal" : 666,
                    "strVal" : "omen"
                 }
                """;

        // there is no way for pretty-print using static method

        // using new JsonReader instance with custom settings
        JsonSettings settings = new JsonSettings();
        JsonReader reader = Json.createReader(settings);
        Data data = reader.readValue(json, Data.class);
    }

}
```

</details>

<details><summary>Create a new instance of <code>JsonWriter</code> with custom settings</summary>

```java
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");

        // there is no way for pretty-print using static method

        // using new JsonWriter instance with custom settings
        JsonSettings settings = new JsonSettings();
        JsonWriter writer = Json.createWriter(settings);
        String json = writer.writeValue(data);
    }

}
```

</details>

<details><summary>Create a new instance of <code>JsonWriter</code> with custom settings and enabled
<code>pretty-print</code> option</summary>

```java=
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");

        // there is no way for pretty-print using static method

        // using new JsonWriter instance with custom settings and pretty-print option
        JsonSettings settings = new JsonSettings();
        JsonWriter writer = Json.createPrettyPrint(settings);
        String json = writer.writeValue(data);
    }

}
```

</details>

#### Convert object

<details><summary>Convert any <code>Object</code> instance to <code>Map</code></summary>

```java
class Data {

    public static void demo() {
        Data data = new Data(666, "omen");
        Map<String, Object> map = Json.convertToMap(data);
    }

}
```

</details>

# Custom `json-api` implementation

For now there are two implementations: [json-jackson-impl](https://github.com/oleg-cherednik/json-jackson-impl)
and [json-gson-impl](https://github.com/oleg-cherednik/json-gson-impl). To create another implementation, you
have to a few steps. Let's make an example for Jackson framework.

### `JacksonFactory`

The name of the class is up to you, but it's better to call this class similar to existed implementations:
`JacksonFactory` for Jackson or `GsonFactory` for Gson.

This class is responsible

```java
// TODO my recommendation to keep the package like this
package ru.olegcherednik.json.impl;

/**
 * The class is responsible to create two instances of `JsonEngine`: normal and pretty print using given settings.
 */
final class JacksonFactory {

    /**
     * Retrieves a new instance of `JsonEngine` based on the give settings. The instance should be completely new
     * (not cached).
     *
     * @param settings not `null` settings configuration
     * @return not `null` instance of `JsonEngine` for current json framework
     */
    public static JacksonEngine createJsonEngine(JsonSettings settings) {
    }

    /**
     * Retrieves a new instance of `JsonEngine` with pretty print option based on the given settings.
     * The instance should be completely new (not cached).
     *
     * @param settings not `null` settings configuration
     * @return not `null` instance of `JsonEngine` with pretty print for current json framework
     */
    public static JacksonEngine createPrettyPrintJsonEngine(JsonSettings settings) {
    }
}
```

### `StaticJsonEngineFactory`

This full name of this class is `ru.olegcherednik.json.impl.StaticJsonEngineFactory` and this is an implementation of
the `JsonEngineFactory` interface. `json-api` does not scan the whole project for the factory class, it just tries to
find the one with predefined name. So this is an entry point to the implementation.

```java
// TODO the package name is predefined
package ru.olegcherednik.json.impl;

public final class StaticJsonEngineFactory implements JsonEngineFactory {

    private static final StaticJsonEngineFactory INSTANCE = new StaticJsonEngineFactory();

    /**
     * Mandatory method.
     * Retrieves a singleton instance of the factory. The method must have the signature like this. Do not change it!
     * @return not `null` singleton instance of the factory
     */
    public static StaticJsonEngineFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Mandatory method.
     * Retrieves a full name of the main class of the json framework. You should not use class' declaration like
     * `ObjectMapper.class.getName()`. You should use only simple string instead. This is very important not to load
     * the class instance at this step.
     * @return not `null` string containing the full name of the main class of the json framework
     */
    public static String getMainClass() {
        return "com.fasterxml.jackson.databind.ObjectMapper";
    }

    @Override
    public JsonEngine createJsonEngine(JsonSettings settings) {
        return JacksonFactory.createJsonEngine(settings);
    }

    @Override
    public JsonEngine createPrettyPrintJsonEngine(JsonSettings settings) {
        return JacksonFactory.createPrettyPrintJsonEngine(settings);
    }
}
```

There are two main classes. All other code of the implementation is under your control. As an example you can use any
of existed implementations, e.g. [json-jackson-impl](https://github.com/oleg-cherednik/json-jackson-impl)
or [json-gson-impl](https://github.com/oleg-cherednik/json-gson-impl).

# Links

*   Home page: https://github.com/oleg-cherednik/json-api

*   Maven:
    *   __central:__ https://mvnrepository.com/artifact/ru.oleg-cherednik.json/json-api
    *   __download:__ https://repo1.maven.org/maven2/ru/oleg-cherednik/json/json-api
