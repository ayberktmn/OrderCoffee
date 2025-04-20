package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentImageBinding
import com.bumptech.glide.Glide

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding

    companion object {
        private const val ARG_IMAGE = "image_url"

        fun newInstance(imageUrl: String): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageUrl = arguments?.getString(ARG_IMAGE) ?: return

        val context = binding.imageView.context

        if (imageUrl.startsWith("http")) {
            Glide.with(context)
                .load(imageUrl)
                .into(binding.imageView)
        } else {
            val resId = context.resources.getIdentifier(imageUrl, "drawable", context.packageName)
            Glide.with(context)
                .load(resId)
                .error(R.drawable.kahveee) // yedek görsel
                .into(binding.imageView)
        }
    }
}
