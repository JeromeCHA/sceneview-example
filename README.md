# SceneView Example

In my current project, I have encounted a serious memory and performance issue using [SceneView](https://github.com/SceneView/sceneview-android).
To show it clearly, I made a sample project that "mimic" the main project's behavior.

This project has been made with :
```
- SceneView 2.3.0
- Kotlin 2.0.21
- No JetpackCompose
```

## Problem

In the current project, I need to display a list of different 3d models which is here represents by simply display a list of PlaneNodes with an image to "simulate" a api call.

To achieve this, I use a custom Sceneview + a shared Engine.

- SharedEngine

https://github.com/JeromeCHA/sceneview-example/blob/aead60d71e466d1aeece1ccb7dcfa46becd5c258/app/src/main/java/cha/jerome/sceneviewexample/MainActivity.kt#L20-L24

- Custom SceneView

https://github.com/JeromeCHA/sceneview-example/blob/aead60d71e466d1aeece1ccb7dcfa46becd5c258/app/src/main/java/cha/jerome/sceneviewexample/sceneview/CustomSceneView.kt#L8-L28

The purpose of the custom sceneview here, is just to override the destroy function and avoid the engine to get destroyed each time the view is recycled in the RecyclerView.

However, I assume by overriding the destroy function, that lead to a memory issue and later a crash.

![Screen Recording 2025-06-23 at 16 42 36](https://github.com/user-attachments/assets/9a6108e3-7f62-4898-b9b1-8398c0ca56b3)

