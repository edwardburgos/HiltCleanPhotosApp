package com.example.photosapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.*
import com.example.data.database.model.PhotoDatabase
import com.example.domain.ApiStatus
import com.example.photosapp.databinding.FragmentDetailBinding
import com.example.photosapp.recyclerview.adapters.PhotosSnapAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    val viewModel: DetailViewModel by viewModels()

    private val snapHelper = PagerSnapHelper()

    lateinit var adapter: PhotosSnapAdapter
    var layoutManager: RecyclerView.LayoutManager? = null

    private var disposables: CompositeDisposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        val postProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedPhoto

        if (viewModel.currentPhotoPosition == 0) {
            viewModel.currentPhotoPosition = 100 - postProperty.id
        }

        disposables = CompositeDisposable()

        binding.viewModel = viewModel

        layoutManager = binding.photosSnap.layoutManager

        snapHelper.attachToRecyclerView(binding.photosSnap)
        adapter = PhotosSnapAdapter()
        binding.photosSnap.adapter = adapter

        viewModel.getPhotosDatabase().subscribe(databaseObserverCreator())

       binding.topAppBar.setNavigationOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigateUp()
        }
        return binding.root

    }

    override fun onPause() {
        binding.photosSnap.layoutManager?.let {
            snapHelper.findSnapView(it)?.let { position ->
                viewModel.currentPhotoPosition = it.getPosition(position)
            }
        }
        super.onPause()
    }

    override fun onDestroy() {
        disposables?.clear()
        super.onDestroy()
    }

    private fun databaseObserverCreator(): io.reactivex.Observer<List<PhotoDatabase>> {
        return object: io.reactivex.Observer<List<PhotoDatabase>> {
            override fun onSubscribe(d: Disposable) { }

            override fun onError(e: Throwable) {
                viewModel.statusObservable.onNext(ApiStatus.ERROR)
            }

            override fun onComplete() { }

            override fun onNext(t: List<PhotoDatabase>) {
                if (t.isEmpty())  {
                    viewModel.statusObservable.onNext(ApiStatus.ERROR)
                } else {
                    viewModel.updatePhotos(viewModel.databaseMapper.fromEntityList(t))
                    layoutManager?.scrollToPosition(viewModel.currentPhotoPosition)
                }
            }
        }
    }
}