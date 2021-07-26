package com.gb.projectnasa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.gb.projectnasa.R
import com.gb.projectnasa.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val pagerAdapter = ViewPagerFragmentStateAdapter(this)
        binding.lifecycleOwner = this
        viewModel.response.observe(viewLifecycleOwner, {
            binding.viewPager2.adapter = pagerAdapter
            TabLayoutMediator(binding.tabl, binding.viewPager2) { tab, position ->
            }.attach()
            val viewPager: ViewPager2 = binding.viewPager2
            viewPager.setPageTransformer(ZoomOutPageTransformer())
        })
    }

    private inner class ViewPagerFragmentStateAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun createFragment(position: Int): Fragment = PagerFragment().apply {
            arguments = bundleOf(
                "position" to viewModel.getPosition(position)
            )
        }

        override fun getItemCount(): Int = viewModel.getSize()
    }

    class ZoomOutPageTransformer : ViewPager2.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        alpha = 0f
                    }
                }
            }
        }
    }
}

