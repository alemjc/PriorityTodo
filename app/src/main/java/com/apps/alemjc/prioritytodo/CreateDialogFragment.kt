package com.apps.alemjc.prioritytodo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 class CreateDialogFragment:DialogFragment() {

    private var mView:View? = null

    private var mListener:OnDialogInteractionListener? = null

    override fun onCreateView(inflater:LayoutInflater?, container:ViewGroup?,
    savedInstanceState:Bundle?):View? {
        // Inflate the layout for this fragment
        mView = inflater!!.inflate(R.layout.fragment_create_dialog, container, false)
        val prioritySpinner: Spinner = mView!!.findViewById(R.id.priority)
        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context, R.array.user_choices, R.layout.support_simple_spinner_dropdown_item)
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        prioritySpinner.adapter = arrayAdapter
        return mView
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder:AlertDialog.Builder = AlertDialog.Builder(context)
        val layoutInflater:LayoutInflater = activity.layoutInflater
        val viewLayout:View = layoutInflater.inflate(R.layout.fragment_create_dialog, null)
        builder.setView(viewLayout)

        val onCreateCB: (DialogInterface, Int)->Unit = fun (_, _){
            val description:String = view!!.findViewById<TextView>(R.id.description).toString()
            val priorityString:String = view!!.findViewById<Spinner>(R.id.priority).selectedItem as String
            val priority:Int = Integer.parseInt(priorityString)
            val item = Todo(-1, description, priority)

            mListener!!.onItemCreated(item)

        }


        builder.setPositiveButton("Create", onCreateCB)

        builder.setNegativeButton("Cancel"){
            _, _ -> mListener!!.onCancel()
        }

        return builder.create()

    }

    override fun onAttach(context:Context?) {
        super.onAttach(context)
        if (context is OnDialogInteractionListener) {
            mListener = context
        }
        else {
            throw RuntimeException((context!!.toString() + " must implement OnDialogInteractionListener"))
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
        fun onItemCreated(item:Todo)
        fun onCancel()
    }

}// Required empty public constructor
