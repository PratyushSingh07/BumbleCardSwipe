# BumbleCardSwipe
Compose Multiplatform Modifier that allows you to achieve a Bumble like swipe gesture on Android, iOS, desktop and web

### Demo

https://github.com/openMF/mifos-mobile/assets/90026952/fea1c49a-1cff-487c-a5e8-93b027738015

### Supported platforms

| Platform | Target |
| --- | --- |
| Android | `android` (minSdk 24) |
| iOS | `iosArm64`, `iosSimulatorArm64` |
| Desktop | `jvm` |
| Web | `wasmJs` |

### How to enable swipe gesture

**1. Add dependency**

The library is published on Maven Central, so no extra repository is needed.

*Kotlin Multiplatform*
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.pratyushsingh07:bumblecardswipe:3.0.0")
        }
    }
}
```

*Android only*
```kotlin
dependencies {
    implementation("io.github.pratyushsingh07:bumblecardswipe:3.0.0")
}
```

*Groovy*
```groovy
dependencies {
    implementation 'io.github.pratyushsingh07:bumblecardswipe:3.0.0'
}
```

**2. Usage**
- Create a `SwipeableCardState` and pass it to `Modifier.swipeableCard()` to enable Bumble like card gestures:
```kotlin
val state = rememberSwipeableCardState()

Box(
    modifier = Modifier
        .fillMaxSize()
        .swipeableCard(
            state = state,
            onSwipe = { direction ->
                println("The card was swiped towards $direction")
            }
        )
)
```
- `onSwipe` is called once the card has been completely swiped off the screen
- A card is swiped when it is dragged past `distanceThreshold` (a fraction of its width, `0.35f` by default) or flung faster than `velocityThreshold` (`800.dp` per second by default)
- Released cards that don't pass either threshold spring back to the center
- `maxRotation` controls how far the card tilts while dragging, and the tilt direction follows whether the card was grabbed from its top or bottom half
- `state.progress` goes from `-1f` to `1f` as the card moves left or right, which can drive overlays or scale the next card
- `state.swipe(Direction.LEFT)` / `state.swipe(Direction.RIGHT)` swipe the card programmatically and `state.reset()` brings it back, which is useful for buttons and undo
- The API is marked with `@ExperimentalSwipeGestureApi`, so opt in with `@OptIn(ExperimentalSwipeGestureApi::class)`

**3. A stack of cards**
- When cards are shown in a list, wrap each one in `key()` so every card keeps its own state, and only compose the top few cards:
```kotlin
val profiles = remember { profileList.toMutableStateList() }

Box {
    profiles.take(3).asReversed().forEach { profile ->
        key(profile.id) {
            val state = rememberSwipeableCardState()
            ProfileCard(
                profile = profile,
                modifier = Modifier.swipeableCard(
                    state = state,
                    onSwipe = { profiles.remove(profile) }
                )
            )
        }
    }
}
```
- See `composeApp/src/commonMain/kotlin/com/github/cardswipe/App.kt` for a full example with Like/Nope buttons, undo and next-card scaling

### Running the sample

| Platform | How to run |
| --- | --- |
| Android | Run the `androidApp` configuration in Android Studio |
| Desktop | `./gradlew :composeApp:run` |
| Web | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` |
| iOS | Open `iosApp/iosApp.xcodeproj` in Xcode and run on a simulator |

### Older versions (JitPack)
Versions up to `2.0.0` are Android only and stay available on JitPack under the old coordinates:
```kotlin
implementation("com.github.PratyushSingh07:BumbleCardSwipe:2.0.0")
```
They need `maven { setUrl("https://jitpack.io") }` in your repositories. `3.0.0` and later are published only on Maven Central.

**Migrating from 2.0.0 to 3.0.0**
- Replace `com.github.PratyushSingh07:BumbleCardSwipe` with `io.github.pratyushsingh07:bumblecardswipe`
- Remove the JitPack repository if nothing else uses it
- The Kotlin API is unchanged

### Developers
- This was built by [Pratyush Singh](https://www.github.com/pratyushsingh07) & [Aditya Gupta](https://www.github.com/aditya-gupta99)

### License 
```
MIT License

Copyright (c) 2023 Pratyush Singh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
