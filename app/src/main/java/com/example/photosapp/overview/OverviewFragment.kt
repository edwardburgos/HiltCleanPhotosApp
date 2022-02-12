package com.example.photosapp.overview

import com.example.photosapp.databinding.FragmentOverviewBinding
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.data.database.model.PhotoDatabase
import com.example.data.network.model.PhotoApi
import com.example.domain.ApiStatus
import com.example.photosapp.recyclerview.adapters.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    val viewModel: OverviewViewModel by viewModels()

    lateinit var adapter: PhotosAdapter
    var disposables: CompositeDisposable? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        disposables = CompositeDisposable()
        adapter = PhotosAdapter(PhotosAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        binding.postsGrid.adapter = adapter

        viewModel.navigateToSelectedPhoto.observe(viewLifecycleOwner, {
            if (null != it) {
                Navigation.findNavController(requireView())
                    .navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        viewModel.getPhotosApi().subscribe(observerCreator(true))

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPhotosApi().subscribe(observerCreator(false))
            binding.swipeRefreshLayout.isRefreshing = false
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroy() {
        disposables?.clear()
        super.onDestroy()
    }

    private fun observerCreator(showLoading: Boolean): io.reactivex.Observer<List<PhotoApi>> {
        return object : io.reactivex.Observer<List<PhotoApi>> {
            override fun onSubscribe(d: Disposable) {
                if (showLoading && (viewModel.photos.value?.size == 0 || viewModel.photos.value == null)) viewModel.statusObservable.onNext(ApiStatus.LOADING)
                disposables?.add(d)
            }

            override fun onError(e: Throwable) {
                viewModel.statusObservable.onNext(ApiStatus.DONE)
                viewModel.getPhotosDatabase().subscribe(databaseObserverCreator())
            }

            override fun onComplete() {
                viewModel.statusObservable.onNext(ApiStatus.DONE)
            }

            override fun onNext(t: List<PhotoApi>) {
                val convertedList = viewModel.apiMapper.fromEntityList(t)
                viewModel.updatePhotos(convertedList.sortedBy { it.id }.reversed())
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        viewModel.insertPhotos(convertedList)
                    }
                }
            }
        }
    }

    fun databaseObserverCreator(): io.reactivex.Observer<List<PhotoDatabase>> {
        return object: io.reactivex.Observer<List<PhotoDatabase>> {
            override fun onSubscribe(d: Disposable) {
                disposables?.add(d)
            }

            override fun onError(e: Throwable) {
                viewModel.statusObservable.onNext(ApiStatus.ERROR)
            }

            override fun onComplete() { }

            override fun onNext(t: List<PhotoDatabase>) {
                if (t.isEmpty())  {
                    viewModel.statusObservable.onNext(ApiStatus.ERROR)
                } else {
                    viewModel.updatePhotos(viewModel.databaseMapper.fromEntityList(t))
                }
            }
        }
    }
}