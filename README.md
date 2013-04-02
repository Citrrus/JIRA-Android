![Citrrus](http://www.citrrus.com/wp-content/uploads/2012/10/CitrrusOrangeIcon2.png "Citrrus")

# JIRA for Android

### Brought to you in part by "20% time" at [Citrrus](http://citrrus.com/)

This repository contains the source code for [JIRA-Android](http://open.citrrus.com/jira), an Android JIRA client that
Android app that will be available from [Google Play](https://play.google.com/store/apps/details?id=com.citrrus.jira).

Simplicity and minimalism are JIRA-Android's lifeblood. JIRA is immensely extensible, but
that power does not translate well to smaller screens.

Please see the [issues](https://github.com/Citrrus/Jira-Android/issues) section
to report any bugs or feature requests and to see the list of known issues.

## Building

The build requires [Maven](http://maven.apache.org/download.html)
v3.0.3+ and the [Android SDK](http://developer.android.com/sdk/index.html)
to be installed in your development environment. In addition you'll need to set
the `ANDROID_HOME` environment variable to the location of your SDK:

    export ANDROID_HOME=/home/donnfelker/tools/android-sdk

After satisfying those requirements, the build is pretty simple:

* Run `mvn clean package` from the `app` directory to build the APK only
* Run `mvn clean install` from the root directory to build the app and also run
  the integration tests, this requires a connected Android device or running
  emulator

The app was primarily developed in [IntelliJ](http://www.jetbrains.com/idea/), and using it is highly recommended
but it is not required. Safety not guaranteed for other development environments!

You might find that your device doesn't let you install your build if you
already have the version from the Android Market installed.  This is standard
Android security as it it won't let you directly replace an app that's been
signed with a different key.  Manually uninstall JIRA from your device and
you will then be able to install your own built version.

## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/Citrrus/Jira-Android/pulls).

Any contributions, large or small, major features, bug fixes, additional
language translations, unit/integration tests are welcomed and appreciated.

## Acknowledgements

JIRA is built on top of the disgustingly helpful [Android Bootstrap](http://androidbootstrap.com/)
and uses many great open-source libraries from the Android dev community:

* [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock) for a
  consistent, great looking header across all Android platforms,
  [ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)
  for swiping between fragments and
  [NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids) for 
  view animations - all from [Jake Wharton](http://jakewharton.com/).
* [Dagger](https://github.com/square/dagger) for dependency-injection.
* [ButterKnife](https://github.com/JakeWharton/butterknife) for view injection
* [Otto](https://github.com/square/otto) as the event bus
* [Robotium](http://code.google.com/p/robotium/)
  for driving our app during integration tests.
* [android-maven-plugin](https://github.com/jayway/maven-android-plugin)
  for automating our build and producing release-ready APKs.
* [http-request](https://github.com/kevinsawicki/http-request) for interacting with
  remote HTTP resources (API's in this case).
* [google-gson](http://code.google.com/p/google-gson/) for consuming JSON and hydrating
  POJO's for use in the app.
* [Holo Everywhere](https://github.com/Prototik/HoloEverywhere)

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


Copyright 2013 Citrrus


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
