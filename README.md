# SceneView Performance & Memory Issue Example

In my current project, I encountered a serious **memory** and **performance** issue while using [SceneView](https://github.com/SceneView/sceneview-android).  
To better illustrate the problem, I created a minimal sample project that mimics the main project's behavior and reproduces the issue.

## Problem Description

The goal is to display a list of various 3D models. In this example, each model is simulated by a `PlaneNode` with an image and a random rotation to represent an API call response.

To render a list of `SceneView` instances inside a `RecyclerView`, I use a **custom `SceneView`** implementation along with a **shared `Engine`** instance.

### Shared Engine

To avoid recreating the engine every time a `SceneView` is recycled, I implemented a shared engine to share it between each cells.

https://github.com/JeromeCHA/sceneview-example/blob/aead60d71e466d1aeece1ccb7dcfa46becd5c258/app/src/main/java/cha/jerome/sceneviewexample/MainActivity.kt#L20-L24

### Custom `SceneView`

I created a custom `SceneView` to override the default `destroy()` behavior and prevent the engine from being destroyed during view recycling.

https://github.com/JeromeCHA/sceneview-example/blob/aead60d71e466d1aeece1ccb7dcfa46becd5c258/app/src/main/java/cha/jerome/sceneviewexample/sceneview/CustomSceneView.kt#L8-L28

### Suspected Memory Leak

While this setup prevents the engine from being recreated repeatedly, I suspect that overriding the `destroy()` method without properly cleaning up other resources leads to a **memory leak**, which eventually causes a crash.

![Screen Recording 2025-06-23 at 16 42 36](https://github.com/user-attachments/assets/9a6108e3-7f62-4898-b9b1-8398c0ca56b3)

As the gif above shows, by scrolling multiple times up and down, the memory usage is increasing which will lead to a OOM crash later

---

## Tech Stack

- SceneView 2.3.0
- Kotlin 2.0.21
- No Jetpack Compose
