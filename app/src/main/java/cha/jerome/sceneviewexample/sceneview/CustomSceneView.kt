package cha.jerome.sceneviewexample.sceneview

import android.content.Context
import android.util.AttributeSet
import com.google.android.filament.Engine
import io.github.sceneview.SceneView

class CustomSceneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    private val sharedEngine: Engine? = null
) : SceneView(
    context = context,
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    defStyleRes = defStyleRes,
    sharedEngine = sharedEngine
) {

    override fun destroy() {
        // If a shared Engine is provided, do not destroy it here
        // This allows sharing the Engine instance across multiple SceneViews
        if (sharedEngine == null && !isDestroyed) {
            super.destroy()
        }
    }
}
