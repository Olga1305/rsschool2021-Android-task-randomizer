package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: ButtonListener? = null

    private var minInput: EditText? = null
    private var maxInput: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        "Previous result: ${result.toString()}".also { previousResult?.text = it }

        minInput = view.findViewById(R.id.min_value)
        maxInput = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val minStr = minInput?.text.toString()
            val maxStr = maxInput?.text.toString()

            if(validateInput(minStr, maxStr)) {
                val min = Integer.valueOf(minStr)
                val max = Integer.valueOf(maxStr)
                listener?.onButtonClicked(min, max)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ButtonListener
    }

    private fun validateInput(minStr: String, maxStr: String): Boolean {
        return if (minStr.isEmpty() || maxStr.isEmpty()) {
            Toast.makeText(requireContext(), "The fields can not be empty", Toast.LENGTH_SHORT)
                .show()
            false
        }
        else if (minStr.toInt() >= maxStr.toInt()) {
            Toast.makeText(requireContext(), "The minimal value must be less than the maximal", Toast.LENGTH_SHORT)
                .show()
            false
        } else true
    }



    interface ButtonListener {
        fun onButtonClicked(min: Int, max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}