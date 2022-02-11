package com.example.photosapp.overview

import com.example.photosapp.databinding.FragmentOverviewBinding
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.photosapp.recyclerview.adapters.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

       binding.viewModel = viewModel

        binding.postsGrid.adapter = PhotosAdapter(PhotosAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedPhoto.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                Navigation.findNavController(requireView())
                    .navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPhotosOverview(false)
            binding.swipeRefreshLayout.setRefreshing(false);
        }

        setHasOptionsMenu(true)

        return binding.root
    }
}
