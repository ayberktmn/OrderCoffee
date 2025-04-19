package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentImageBinding

class ImageFragment : Fragment() {

    private var imageResId: Int = 0
    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ❗ arguments'tan image id'yi önce alıyorum
        arguments?.let {
            imageResId = it.getInt("image_res")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        // imageResId burada kullanıyoruz
        binding.imageView.setImageResource(imageResId)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(imageResId: Int): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putInt("image_res", imageResId)
            fragment.arguments = args
            return fragment
        }
    }
}
