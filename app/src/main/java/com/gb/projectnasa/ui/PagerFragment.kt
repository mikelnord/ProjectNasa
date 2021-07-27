package com.gb.projectnasa.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
            property = it.getParcelable("position")!!
        }
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PagerViewModel::class.java)
        binding.lifecycleOwner = this
        binding.nasaProperty = property

        binding.mediaType.setText(property.title, TextView.BufferType.SPANNABLE)
        val spannableText = binding.mediaType.text as Spannable
        spannableText.setSpan(
            ForegroundColorSpan(Color.RED),
            0, spannableText.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )
        binding.data.setText(property.date, TextView.BufferType.SPANNABLE)
        val spannableTextDate = binding.data.text as Spannable
        spannableTextDate.setSpan(
            BackgroundColorSpan(Color.GREEN),
            0,
            spannableTextDate.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.explanation.setText(property.explanation, TextView.BufferType.SPANNABLE)
        val spannableTextExp = binding.explanation.text as Spannable
        val matchResult = property.explanation?.let { str -> Regex("""Mars|Earth""").find(str) }
        if (matchResult != null) {
            spannableTextExp.setSpan(
                BackgroundColorSpan(Color.GREEN),
                matchResult.range.first,
                matchResult.range.last,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableTextExp.setSpan(
                RelativeSizeSpan(1.2f),
                matchResult.range.first,
                matchResult.range.last,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

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