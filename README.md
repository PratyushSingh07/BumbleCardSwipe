# BumbleCardSwipe
Jetpack Compose Modifier that allows you to achieve bumble like swipe gesture

### Demo

https://github.com/openMF/mifos-mobile/assets/90026952/fea1c49a-1cff-487c-a5e8-93b027738015

### How to enable swipe gesture
- Follow the below steps to use the bumble like swipe gesture
  
**1. Add dependency**

*Kotlin Scripts*
- Add the below to your app `build.gradle` :
  
```kotlin
  dependencies {
    .....
    implementation("com.github.PratyushSingh07:BumbleCardSwipe:1.0.0")
  }
```
- After you have done this make sure to add the below snippet to your `settings.gradle`:
  
```kotlin
  dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}
```

*Groovy*
- Add the dependency to your app `build.gradle`:

```kotlin
  dependencies {
	        implementation 'com.github.PratyushSingh07:BumbleCardSwipe:Tag'
	}
```
  
  - Add below snippet in your root `build.gradle` at the end of repositories:
    
```kotlin
    dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
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
