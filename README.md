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
<p>

[![github-ci](https://github.com/oleg-cherednik/json-api/actions/workflows/github-ci.yml/badge.svg?branch=develop&event=push)](https://github.com/oleg-cherednik/json-api/actions)
[![quality](https://app.codacy.com/project/badge/Grade/7645235119a749c79dd1bfb22b78dc34?branch=develop)](https://app.codacy.com/gh/oleg-cherednik/json-api/dashboard?branch=develop)
[![coverage](https://app.codacy.com/project/badge/Coverage/7645235119a749c79dd1bfb22b78dc34?branch=develop)](https://app.codacy.com/gh/oleg-cherednik/json-api/coverage/dashboard?branch=develop)

</p>
</details>

# JSON-API

*   __json framework__ is a framework for working with json files like
    [jackson](https://github.com/FasterXML/jackson) or
    [gson](https://github.com/google/gson). Usually we use __json framework__
    in the application directly by adding dependencies required for it. In
    general all these __json frameworks__ have its own API and style of coding.

*   __json engine__ is an abstraction of all __json framework__. The main
    idea is to provide a unified API over all __json frameworks__. I.e. using
    this unified API (i.e. __json engine__), the client is able to use some
    specific logic of concrete __json framework__, and use common way to work
    with it (indeed, some specific feature of the concrete __json framework__
    will be ignored).

*   __JSON-API__ is a wrapper over various __json engines__. It provides a
    simple way to do the most common actions of json manipulations. Moreover, it
    provides the way of single point configuration and exception handling.

## Features

*   Single file API for all json actions;
*   Give an easy way to provide custom engine implementation;
*   It's free of any engine's specific code;
*   It's fully open-source and does not depend on any limited licenses.

## Gradle

```groovy
implementation 'ru.oleg-cherednik.json:json-api:3.0'
```

_* additionally an engine implementation should be added_

## Maven

```xml

<dependency>
    <groupId>ru.oleg-cherednik.json</groupId>
    <artifactId>json-api</artifactId>
    <version>3.0</version>
</dependency>
```

_* additionally an engine implementation should be added_

## Usage

### Add dependency with required engine

E.g. you would like to use [jackson 2.16.1](https://github.com/FasterXML/jackson)
as a json framework in your application. In this case, you have several options:

*   Add [jackson](https://github.com/FasterXML/jackson) dependencies directly;
*   Or use [json-api](https://github.com/oleg-cherednik/json-api) with
    [jackson-json-api](https://github.com/oleg-cherednik/json-jackson-impl)
    implementation.

If you choose 2nd option, you should add following dependencies.

#### Gradle

```groovy
implementation 'ru.oleg-cherednik.json:json-jackson-impl:3.0'
```

__Note:__ `jackson-utils` does not contain dependency to the specific
`Jackson Project` version, so you have to add any version additionally

```groovy
implementation 'com.fasterxml.jackson.core:jackson-databind:2.16.1'
```

In case you want to use additional features of the framework, e.g. work with
jdk8 data-types, you have to add additional dependencies. `json-api` detects it
and these features will be added to it's configuration as well.

```groovy
implementation 'com.fasterxml.jackson.module:jackson-module-afterburner:2.16.1'
implementation 'com.fasterxml.jackson.module:jackson-module-parameter-names:2.16.1'
implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.16.1'
implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1'
```

### Main classes

There are following classes to work with json using `json-api`:

*   [Json](#json-class) - utility class with set of methods to use json transformation;
*   [EnumId](#work-with-enum) - advanced enum serialization support.

### Json class

#### Read json from `String`

<details><summary><code>String</code> to a custom object type (but not a collection)</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>List</code> of <code>Object</code> type</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>Set</code> of <code>Object</code> type</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>List</code> of custom object type</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>Set</code> of custom object type</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>List</code> of <code>Map</code></summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>
<p>

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

</p>
</details>

<details><summary><code>String</code> to a <code>Map</code> of custom key and value type</summary>
<p>

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

</p>
</details>

#### Read json from `ByteBuffer`

<details><summary><code>ByteBuffer</code> to a custom object type (but not a collection)</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>List</code> of <code>Object</code> type</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>Set</code> of <code>Object</code> type</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>List</code> of custom object type</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>Set</code> of custom object type</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>List</code> of <code>Map</code></summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to a <code>Map</code> of custom key and value type</summary>
<p>

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

</p>
</details>

##### Read json from `ByteBuffer` lazy

<details><summary><code>ByteBuffer</code> to an <code>Iterator</code> of <code>Object</code> type with lazy reading</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to an <code>Iterator</code> of custom object type with lazy reading</summary>
<p>

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

</p>
</details>

<details><summary><code>ByteBuffer</code> to an <code>Iterator</code> of <code>Map</code> with lazy reading</summary>
<p>

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

</p>
</details>

#### Read json from `InputStream`

<details><summary><code>InputStream</code> to a custom object type (but not a collection)</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>List</code> of <code>Object</code> type</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>Set</code> of <code>Object</code> type</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>List</code> of custom object type</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>Set</code> of custom object type</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>List</code> of <code>Map</code></summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>Map</code> of <code>String</code> key and <code>Object</code> value type</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>Map</code> of <code>String</code> key and custom value type</summary>
<p>

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

</p>
</details>

<details><summary><code>InputStream</code> to a <code>Map</code> of custom key and value type</summary>
<p>

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

</p>
</details>

##### Read json from `InputStream` lazy

<details><summary><code>InputStream</code> to an <code>Iterator</code> of <code>Object</code> type with lazy reading</summary>
<p>

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
        Iterator<Object> it = Json.readListLazy(in);
    }

}
```

</p>
</details>


<details><summary><code>InputStream</code> to an <code>Iterator</code> of custom object type with lazy reading</summary>
<p>

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
        Iterator<Data> it = Json.readListLazy(in, Data.class);
    }

}
```

</p>
</details>

<details><summary><code>InputStream</code> to an <code>Iterator</code> of <code>Map</code> with lazy reading</summary>
<p>

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
        Iterator<Map<String, Object>> it = Json.readListOfMapLazy(in);
    }

}
```

</p>
</details>

#### Read json from `Reader`

          zxv

#### Write json to `String`

      zxv

#### Write json to `OutputStream`

          zxcv

#### Write json to `Writer`

##### `String` to a list of custom object type

```java
class Data {

    int intVal;
    String strVal;

}
```

```java
String json="""
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
        List<Data> res=Json.readList(json,Data.class);
```

##### `String` to a map of custom object type

###### `String` to a map with `String` keys and given type as value

```java
class Data {

    int intVal;
    String strVal;

}
```

```java
String json="""
              {
                  "victory" : {
                      "intVal" : 555,
                      "strVal" : "victory"
                  },
                  "omen" : {
                      "intVal" : 666,
                      "strVal" : "omen"
                  }
              }
              """;
        Map<String, Data> map=Json.readMap(json,Data.class);
```

##### Links

*   Home page: https://github.com/oleg-cherednik/jackson-utils

*   Maven:
    *   __central:__ https://mvnrepository.com/artifact/ru.oleg-cherednik.jackson/jackson-utils
    *   __download:__ https://repo1.maven.org/maven2/ru/oleg-cherednik/jackson/jackson-utils
