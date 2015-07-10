![Logo 1][10]

[![Build Status](https://api.travis-ci.org/ppamorim/Cult.svg?branch=master)](https://travis-ci.org/ppamorim/Prism)
[![JitPack](https://img.shields.io/github/release/ppamorim/Prism.svg?label=JitPack%20Maven)](https://jitpack.io/#ppamorim/Prism)

Prism provides animations for your views, similar to [Dragger][4], but with fragments!
You can change the fragment of the PrismView any time.*

*Needs to improve.



Usage
-----

* 1. Extend your activity with PrismActivity, use the the method `setContentView`:

```java
public class BaseActivity extends PrismActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_base);
  }
}
```

* 2. Then, call the method `load(Fragment)`:

```java
  load(new YourFragment());
```

Import dependency
--------------------------------

* This library is under development yet, use at your own risk.

This library uses `appcompat-v7:22.1.1` and `rebound:0.3.8`.

But why not to add it in MavenCentral?
Because it is so much bureaucratic.

JitPack is there and it is the future!

Into your build.gradle:

```groovy

repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.ppamorim:prism:0.1'
}
```

Todo
----

* Support multiples fragments

Contributors
------------

* [Pedro Paulo de Amorim][3]
* [Marcelo Camargo][666]

Developed By
------------

* Pedro Paulo de Amorim - <pp.amorim@hotmail.com>

<a href="https://www.linkedin.com/profile/view?id=185411359">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>

Libraries used on the sample project
------------------------------------

* [Butterknife][5]
* [Fresco][6]

License
-------

    Copyright 2015 Pedro Paulo de Amorim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[3]: https://github.com/ppamorim/
[4]: https://github.com/ppamorim/Dragger
[5]: https://github.com/JakeWharton/butterknife
[6]: https://github.com/facebook/fresco
[10]: ./art/logo.png
[11]: ./art/logo.png
[666]: https://github.com/haskellcamargo/
