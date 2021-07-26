package com.gb.projectnasa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.gb.projectnasa.R
import com.gb.projectnasa.databinding.FragmentPagerBinding
import com.gb.projectnasa.network.NasaProperty

class PagerFragment : Fragment() {

    private lateinit var binding: FragmentPagerBinding
    private lateinit var viewModel: PagerViewModel
    private lateinit var property: NasaProperty
    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pager, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            property = it.getParcelable<NasaProperty>("position")!!
        }
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PagerViewModel::class.java)
        binding.lifecycleOwner = this
        binding.nasaProperty = property

        val image_view = binding.imageView
        image_view.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.frameLayout, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = image_view.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            image_view.layoutParams = params
            image_view.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }

}