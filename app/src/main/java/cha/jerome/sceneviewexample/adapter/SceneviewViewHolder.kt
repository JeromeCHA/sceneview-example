package cha.jerome.sceneviewexample.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import cha.jerome.sceneviewexample.databinding.RowSceneviewBinding
import cha.jerome.sceneviewexample.sceneview.CustomSceneView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.filament.Engine
import io.github.sceneview.SceneView
import io.github.sceneview.geometries.Plane
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Size
import io.github.sceneview.node.PlaneNode
import io.github.sceneview.texture.ImageTexture

class SceneviewViewHolder(
    private val binding: RowSceneviewBinding,
    private val sharedEngine: Engine
) : RecyclerView.ViewHolder(binding.root) {

    private var sceneview: SceneView? = null

    init {
        addSceneView()
    }

    // Add the sceneview via code so we can share the Engine instance
    // and avoid creating multiple instances of the SceneView
    private fun addSceneView() {
        if (sceneview != null) return
        val sceneview = CustomSceneView(
            context = itemView.context,
            sharedEngine = sharedEngine
        ).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(0, 0)
        }
        binding.root.addView(sceneview)
        ConstraintSet()
            .apply {
                clone(binding.root)
                connect(
                    sceneview.id,
                    ConstraintSet.TOP,
                    binding.sceneViewBackground.id,
                    ConstraintSet.TOP
                )
                connect(
                    sceneview.id,
                    ConstraintSet.BOTTOM,
                    binding.sceneViewBackground.id,
                    ConstraintSet.BOTTOM
                )
                connect(
                    sceneview.id,
                    ConstraintSet.START,
                    binding.sceneViewBackground.id,
                    ConstraintSet.START
                )
                connect(
                    sceneview.id,
                    ConstraintSet.END,
                    binding.sceneViewBackground.id,
                    ConstraintSet.END
                )
                applyTo(binding.root)
            }
        sceneview.isClickable = false
        sceneview.isFocusable = false
        sceneview.isEnabled = false
        sceneview.setOnClickListener(null)
        sceneview.cameraNode.position = Position(0f, 0f, 0f)
        this.sceneview = sceneview
    }

    // Display a random image on the PlaneNode
    // and rotate it based on the @param rotation
    fun bind(rotation: Int) {
        sceneview?.clearChildNodes()
        Glide.with(itemView.context)
            .asBitmap()
            .load(RANDOM_IMG_URL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val imageTexture = ImageTexture.Builder()
                        .bitmap(resource)
                        .build(sharedEngine)
                    val materialInstance =
                        sceneview?.materialLoader?.createImageInstance(imageTexture)
                    val node = PlaneNode(
                        engine = sharedEngine,
                        geometry = Plane.Builder().size(Size(2f, 1f)).build(sharedEngine),
                        materialInstance = materialInstance
                    )
                    node.position = Position(0f, 0f, -3f)
                    node.rotation = Rotation(y = rotation.toFloat())
                    sceneview?.addChildNode(node)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

    companion object {
        private const val RANDOM_IMG_URL = "https://picsum.photos/800/400"
    }
}
