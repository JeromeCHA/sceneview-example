package cha.jerome.sceneviewexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cha.jerome.sceneviewexample.adapter.ListAdapter
import cha.jerome.sceneviewexample.databinding.ActivityMainBinding
import com.google.android.filament.Engine
import com.google.android.filament.Filament
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // Shared Engine instance to be used across multiple SceneViews
    private val sharedEngine by lazy {
        Filament.init()
        Engine.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var list = mutableListOf<Int>()
        for (i in 0 until 20) {
            // Generate random rotations for our PlaneNode between -100 and 100
            list.add(Random.nextInt(-100, 100))
        }

        binding.mainRecyclerView.adapter = ListAdapter(
            sharedEngine = sharedEngine,
            list = list
        )
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)

        setupEdgeToEdge()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        sharedEngine.destroy()
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
