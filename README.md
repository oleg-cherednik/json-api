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
testImplementation 'com.fasterxml.jackson.core:jackson-databind:2.16.1'
```

In case you want to use additional features of the framework, e.g. work with
jdk8 data-types, you have to add additional dependencies. `json-api` detects it
and these features will be added to it's configuration as well.

```groovy
testImplementation 'com.fasterxml.jackson.module:jackson-module-afterburner:2.16.1'
testImplementation 'com.fasterxml.jackson.module:jackson-module-parameter-names:2.16.1'
testImplementation 'com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.16.1'
testImplementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1'
```

### Main classes

There are following classes to work with json using `json-api`:

*   [Json](#json-class) - utility class with set of methods to use json transformation;
*   [EnumId](#work-with-enum) - advanced enum serialization support.

### Json class

#### Read json from `String`

##### `String` to a custom object type (but not a collection)

<details><summary>example</summary>
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

##### `String` to a list of custom object type

<details><summary>example</summary>
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

#### Read json from `ByteBuffer`

       xcv

#### Read json from `InputStream`

         zxcv

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
