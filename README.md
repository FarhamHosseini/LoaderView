# What is `LoaderView` ?
Provide both TextView and ImageView the ability to show shimmer (animation loader) before any text or image is shown. Useful when waiting for data to be loaded from the network.

## Usage
#### Set up the dependency
1. Add the mavenCentral() repository to your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		mavenCentral()
	}
}
```
2. Add the LoaderView dependency in the build.gradle:
```
implementation group: 'com.apachat', name: 'loaderview-android', version: '1.12.18'
```

Badge:
-----
[![Maven Central](https://img.shields.io/maven-central/v/com.apachat/loaderview-android.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.apachat%22%20AND%20a:%22loaderview-android%22)

## Features
1. LoaderView for TextView defined in layout XML
    ```xml
    <com.apachat.loaderview.core.TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content" />
    ```

2. LoaderView for ImageView defined in layout XML
    ```xml
    <com.apachat.loaderview.core.ImageView
         android:layout_width="100dp"
         android:layout_height="100dp" />
    ```

3. Define the % width of the TextView that shows the loading animation with `width_weight`
    ```xml
    <com.apachat.loaderview.core.TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:width_weight="0.4" />
    ```

4. Define the % height of the TextView that shows the loading animation with `height_weight`
    ```xml
    <com.apachat.loaderview.core.TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:height_weight="0.8" />
    ```

5. Define use gradient of the TextView or ImageView that shows the gradient with `use_gradient`
    ```xml
    <com.apachat.loaderview.core.TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:use_gradient="true" />
    ```

6. Define rectangle round radius using `corner`. The default corner is 0.
    ```xml
    <com.apachat.loaderview.core.TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:corners="16" />
    ```

7. Setting the Text Style as `BOLD` would darken the loading shimmer.

8. Use a custom shimmer color (note: if set, point 7 will not apply, your color will be used even if the Text Style is `BOLD`)
    ```xml
    <com.apachat.loaderview.core.TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         app:custom_color="@android:color/holo_green_dark" />
    ```
    
9. Reset and show shimmer (animation loader) again by calling the below API
    ```java
    myLoaderTextView.resetLoader();
    myLoaderImageView.resetLoader();
    ```

## Requirement
Android SDK API Version 21 and above.

### And you're done, easy-peasy. ^_^

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/FarhamHosseini/LoaderView/issues).
