package com.app.focusindepth.views.readlater

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.focusindepth.R
import com.app.focusindepth.databinding.FragmentReadLaterBinding

class ReadLaterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReadLaterBinding.inflate(layoutInflater)
        return binding.root
    }

}