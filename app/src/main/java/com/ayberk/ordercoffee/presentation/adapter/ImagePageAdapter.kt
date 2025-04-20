package com.ayberk.ordercoffee.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ayberk.ordercoffee.presentation.ui.ImageFragment

class ImagePagerAdapter(fragment: Fragment, private val images: List<String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return images.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(images[position])
    }
}

