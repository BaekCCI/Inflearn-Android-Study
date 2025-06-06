package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.R
import com.example.coco.databinding.FragmentCoinListBinding
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.view.adapter.CoinListRVAdapter
import timber.log.Timber

class CoinListFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unSelectedList = ArrayList<InterestCoinEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllInterestCoinData()
        viewModel.selectedCoinList.observe(viewLifecycleOwner, Observer {

            selectedList.clear()
            unSelectedList.clear()
            for (item in it) {
                if (item.selected) {
                    selectedList.add(item)
                } else {
                    unSelectedList.add(item)
                }
            }
            Timber.d(selectedList.toString())
            Timber.d(unSelectedList.toString())
            setSelectedListRV()

        })
    }

    private fun setSelectedListRV(){
        val selectRVAdapter = CoinListRVAdapter(requireContext(),selectedList)
        binding.selectedCoinRV.adapter = selectRVAdapter
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        selectRVAdapter.itemClick = object :CoinListRVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                Timber.d(selectedList[position].toString())
            }
        }

        val unSelectRVAdapter = CoinListRVAdapter(requireContext(),unSelectedList)
        binding.unSelectedCoinRV.adapter = unSelectRVAdapter
        binding.unSelectedCoinRV.layoutManager = LinearLayoutManager(requireContext())
        unSelectRVAdapter.itemClick = object :CoinListRVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                Timber.d(unSelectedList[position].toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}