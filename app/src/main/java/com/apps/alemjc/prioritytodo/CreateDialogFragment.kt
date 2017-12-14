package com.apps.alemjc.prioritytodo

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.apps.alemjc.prioritytodo.content.Todo


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreateDialogFragment.OnDialogInteractionListener] interface
 * to handle interaction events.
 * Use the [CreateDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CreateDialogFragment : DialogFragment() {

    private var mListener: OnDialogInteractionListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(parentFragment.context)
        val layoutInflater: LayoutInflater = activity.layoutInflater
        val viewLayout: View = layoutInflater.inflate(R.layout.fragment_create_dialog, null)

        val prioritySpinner: Spinner = viewLayout.findViewById<Spinner>(R.id.prioritySpinner)
        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context, R.array.user_choices, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        prioritySpinner.adapter = arrayAdapter
        builder.setView(viewLayout)

        val onCreateCB: (DialogInterface, Int) -> Unit = fun(_, _) {
            val description: String = viewLayout.findViewById<TextView>(R.id.description).text.toString()
            val priorityString: String = viewLayout.findViewById<Spinner>(R.id.prioritySpinner).selectedItem as String
            val priority: Long = priorityString.toLong()
            val item = Todo(-1, description, priority)

            mListener!!.onItemCreated(item)

        }

        builder.setTitle("Make todo")

        builder.setPositiveButton("Create", onCreateCB)

        builder.setNegativeButton("Cancel") { _, _ ->
            mListener!!.onCancel()
        }

        return builder.create()

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val pFragment = parentFragment
        if (pFragment is OnDialogInteractionListener) {
            mListener = pFragment
        } else {
            throw RuntimeException("Parent Fragment must implement OnDialogInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnDialogInteractionListener {
        // TODO: Update argument type and name
        fun onItemCreated(item: Todo)

        fun onCancel()
    }

}// Required empty public constructor
