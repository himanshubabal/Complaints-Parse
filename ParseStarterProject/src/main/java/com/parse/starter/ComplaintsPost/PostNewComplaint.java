package com.parse.starter.ComplaintsPost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.starter.R;


public class PostNewComplaint extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    RadioButton indiv_type, hostel_type, insti_type;
    Button select_comp_type;
    RadioGroup select_comp_type_radioGroup;
    String compType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_new_complaint,container,false);

        indiv_type = (RadioButton)v.findViewById(R.id.indiv_comp_radio);
        hostel_type = (RadioButton)v.findViewById(R.id.hostel_comp_radio);
        insti_type = (RadioButton)v.findViewById(R.id.insti_comp_radio);
        select_comp_type = (Button)v.findViewById(R.id.comp_type_select_button);
        select_comp_type.setOnClickListener(this);
        select_comp_type_radioGroup = (RadioGroup)v.findViewById(R.id.radio_group_comp_type);
        select_comp_type_radioGroup.setOnCheckedChangeListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.comp_type_select_button){
            if(compType.equals("indiv_type")){
                Fragment indiv_comp = new PostIndivComp();
                FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                fragmentT.replace(R.id.frame, indiv_comp);
                fragmentT.addToBackStack(null);
                fragmentT.commit();
            }
            else if (compType.equals("hostel_type")){
                Fragment hostel_comp = new PostHostelComp();
                FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                fragmentT.replace(R.id.frame, hostel_comp);
                fragmentT.addToBackStack(null);
                fragmentT.commit();
            }
            else if (compType.equals("insti_type")){
                Fragment insti_comp = new PostInstiComp();
                FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                fragmentT.replace(R.id.frame, insti_comp);
                fragmentT.addToBackStack(null);
                fragmentT.commit();
            }
            else {
            }
        }
        else {
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.indiv_comp_radio){
            compType = "indiv_type";
        }
        else if (checkedId == R.id.hostel_comp_radio){
            Toast.makeText(getActivity(), "hostel_radio", Toast.LENGTH_SHORT).show();
            compType = "hostel_type";

        }
        else if (checkedId == R.id.insti_comp_radio){
            Toast.makeText(getActivity(), "insti_radio", Toast.LENGTH_SHORT).show();
            compType = "insti_type";

        }
        else {
            Log.i("parse-post_new_comp", "radio_other");
        }
    }
}
