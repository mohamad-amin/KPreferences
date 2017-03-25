[![Bintray](https://img.shields.io/bintray/v/mohamad-amin/maven/kpreferences.svg?maxAge=2592000)](https://bintray.com/mohamad-amin/maven/kpreferences) [![Methods Count](https://img.shields.io/badge/Methods%20and%20size-core:%20113%20%7C%20deps:%206322%20%7C%2017%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.mohamadamin.kpreferences%3Akpreferences%3A0.1)

# KPreferences
A **Kotlin** library for **reactive** and **boilerplate-free** Shared Preferences in Android.
With KPreferences you can use Kotlin's marvelous **delegate** concept to easily use Android's Shared Preferences. Features:

 - **Boilerplate-free**: Use `SharedPreferences` properties with one line and without `edit`, `apply` or ... boilerplate codes
 - **Reactive**: Use observables to listen for `SharedPreferences` changes without any memory leak
 - **Custom types**: You are not limited to a set of limited types that can be saved to the `SharedPreferences` . 

If you have any issues or need more features, you can submit an issue in the [issue tracker](https://github.com/mohamad-amin/KPreferences/issues) or make a [pull request](https://github.com/mohamad-amin/KPreferences/pulls).

  * [Importing](#importing)
  * [Usage](#usage)
      * [Reactive Preferences](#reactive-preferences)
	  * [Custom Types](#custom-types)
  * [Demo](#demo)
  * [Licence](#licence)

![Demo](https://github.com/mohamad-amin/KPreferences/blob/master/art/demo.gif)
 
## Importing 
Add this line to your module's `build.gradle` file:
```groovy
dependencies {
    compile 'com.mohamadamin.kpreferences:kpreferences:0.1'
}
```
## Usage

First you need to initialize `KPreferenceManager` in your application class. You can also provide `name` of the `SharedPreference` and `mode` for the operating mode but it's not needed (defaults to `"default"` for file name and `Context.MODE_PRIVATE` for mood):
```kotlin
override fun onCreate() {
    super.onCreate()
    KPreferenceManager.initialize(this)
}
```

Use `SharedPreferences` in your project with only one line:
```kotlin
val intPreference: Int by Preference("IntPreference", -1)
```
it creates a `SharedPreferences` property with `IntPreference` name and `-1` as its default value.

Then the `intPreference` property  is automatically saved when you change the `intPreference` property and is directly retrieved from `SharedPreferences` when you want to access its value:

```kotlin
// the age value is directly retrieved from SharedPreferences property named "IntPreference"
someClass.age = intPreference
// automatically is saved in shared preferences with no need to edit or ...
intPreference = 21
```
### Reactive Preferences 
KPreferences also provides **Observable Preferences** that lets you observe changes to a **single preference**. It uses `SharedPreferences`'s `registerOnSharedPreferenceChangeListener` method.

**But unlike** that method that requires you to observe changes on all fields `ObservablePreference` lets you only listen to the changes that you need:

```kotlin
// Composite destroyer to destroy all observables at once to avoid memory leaks
val destroyers = CompositeDestroyer()
var toolbarTitle: Int by ObservablePreference(
            "ToolbarTitle", // SharedPreferences key
            "Title", // SharedPreferences default value
            subscriber = object: Subscriber<Int> { // observable for possible changes
                override val subscriber: (Int) -> Unit
                    get() = {
	                    // changing toolbar title when the preference is changed
                        setToolbarTitle("$it") 
                    }
                override fun setDestroyListener(callback: () -> Unit) {
                    // call this callback later to unregister the listener and avoid memory leaks
                    destroyers.add(callback) 
                }
            }
    )
```
but you need to destroy the `destroyable callback` in your `onDestroy` to **avoid possible memory leaks**. The `CompositeDestroyer` is a helper class to destroy all callbacks at once:
```kotlin
override fun onDestroy() {
    super.onDestroy()
    destroyers.invoke()
}
```
### Custom Types
You can use the [`adapter`](https://github.com/mohamad-amin/KPreferences/blob/master/kpreferences/src/main/kotlin/com/mohamadamin/kpreferences/base/Adapter.kt) abstraction to **store and retrieve values of an arbitrary type**.

As you see below you should override `decode` and `encode` functions of the `Adapter<T>` class to create an adapter for the variable of type `T` and save/restore it in `SharedPreferences`. There's [an example of an adapter which uses Gson to serialize/deserialize the custom type](https://github.com/mohamad-amin/KPreferences/blob/master/app/src/main/kotlin/com/mohamadamin/kpreferencesdemo/base/DummyAdapter.kt) in the demo app.

```kotlin
/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 * Converter for storing and retrieving objects of type [T] in [android.content.SharedPreferences]
 */
interface Adapter<T> {

    /**
     * Decode the string retrieved from [android.content.SharedPreferences] to an object of type [T]
     * @param result the string retrieved from {@link SharedPreferences}
     * @return the decoded object of type [T]
     */
    fun decode(result: String): T?

    /**
     * Encode an object of type [T] to a string that can be saved in [android.content.SharedPreferences]
     * @param value the object of type [T] that wants to be saved in [android.content.SharedPreferences]
     * @return the encoded string from the input #value that can be saved in [android.content.SharedPreferences]
     */
    fun encode(value: T): String

}
``` 
## Demo
You can see a full demo of the library in the [app](https://github.com/mohamad-amin/KPreferences/tree/master/app) module.

## Licence
```
Copyright 2017 Mohamad Amin Mohamadi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
