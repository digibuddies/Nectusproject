package com.digibuddies.nectus.profile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.digibuddies.nectus.R;
import com.whygraphics.gifview.gif.GIFView;

import agency.tango.materialintroscreen.SlideFragment;

public class CustomSlide2 extends SlideFragment {
    private CheckBox checkBox;
    data d2;
    static public TextView tvun;
    public Spinner s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        d2=new data();
        final View view = inflater.inflate(R.layout.fragment_custom_slide2, container, false);
        GIFView mGifView = (GIFView)view.findViewById(R.id.main_activity_gif_vie2);
        mGifView.setGifResource("asset:ban");
        checkBox = (CheckBox) view.findViewById(R.id.checkBox89);
        tvun=(TextView)view.findViewById(R.id.textView111);
         s1=(Spinner) view.findViewById(R.id.spinner00);
         s2=(Spinner) view.findViewById(R.id.Spinner2);
         s3=(Spinner) view.findViewById(R.id.Spinner3);
         s4=(Spinner) view.findViewById(R.id.Spinner4);
         s5=(Spinner) view.findViewById(R.id.spinner5);
         s6=(Spinner) view.findViewById(R.id.spinner6);
         s7=(Spinner) view.findViewById(R.id.Spinner7);
         s8=(Spinner) view.findViewById(R.id.spinner72);
         s9=(Spinner) view.findViewById(R.id.spinner8);
         s10=(Spinner) view.findViewById(R.id.spinner10);
        s1.setBackgroundColor(Color.DKGRAY);s2.setBackgroundColor(Color.DKGRAY);s3.setBackgroundColor(Color.DKGRAY);s4.setBackgroundColor(Color.DKGRAY);s5.setBackgroundColor(Color.DKGRAY);s6.setBackgroundColor(Color.DKGRAY);s7.setBackgroundColor(Color.DKGRAY);s8.setBackgroundColor(Color.DKGRAY);s9.setBackgroundColor(Color.DKGRAY);s10.setBackgroundColor(Color.DKGRAY);


        TextView txx0=(TextView)view.findViewById(R.id.textView111);
       TextView txx1=(TextView)view.findViewById(R.id.textView1);
        TextView txx2=(TextView)view.findViewById(R.id.textView2);
        TextView txx3=(TextView)view.findViewById(R.id.textView3);
        TextView txx4=(TextView)view.findViewById(R.id.textView4);
        TextView txx5=(TextView)view.findViewById(R.id.textView5);
        TextView txx6=(TextView)view.findViewById(R.id.textView6);
        TextView txx7=(TextView)view.findViewById(R.id.textView7);
        TextView txx8=(TextView)view.findViewById(R.id.textView8);
        TextView txx9=(TextView)view.findViewById(R.id.textView22);
        TextView txx10=(TextView)view.findViewById(R.id.textView11);
        TextView txx11=(TextView)view.findViewById(R.id.textView12);
        TextView txx12=(TextView)view.findViewById(R.id.textView13);
        if(!(profileclass.usern.equals(""))){
            tvun.setText(profileclass.usern);
        }

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/abc.ttf");

        txx0.setTypeface(custom_font);
       txx1.setTypeface(custom_font);
        txx2.setTypeface(custom_font);
       txx3.setTypeface(custom_font);
        txx4.setTypeface(custom_font);
        txx5.setTypeface(custom_font);txx6.setTypeface(custom_font);txx7.setTypeface(custom_font);txx8.setTypeface(custom_font);txx9.setTypeface(custom_font);txx10.setTypeface(custom_font);txx11.setTypeface(custom_font);txx12.setTypeface(custom_font);
        checkBox.setChecked(false);

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.second_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.second_slide_buttons;
    }

    @Override
    public boolean canMoveFurther() {
        if ((!(s1.getSelectedItem().toString().equals("Select")))&&(!(s2.getSelectedItem().toString().equals("Select")))&&(!(s3.getSelectedItem().toString().equals("Select")))&&(!(s4.getSelectedItem().toString().equals("Select")))&&(!(s5.getSelectedItem().toString().equals("Select")))&&(!(s6.getSelectedItem().toString().equals("Select")))&&(!(s7.getSelectedItem().toString().equals("Select")))&&(!(s8.getSelectedItem().toString().equals("Select")))&&(!(s9.getSelectedItem().toString().equals("Select")))&&(!(s10.getSelectedItem().toString().equals("Select"))))
        {
            profileclass.d.setOp1(s1.getSelectedItem().toString());
            profileclass.d.setOp2(s2.getSelectedItem().toString());
            profileclass.d.setOp3(s3.getSelectedItem().toString());
            profileclass.d.setOp4(s4.getSelectedItem().toString());
            profileclass.d.setOp5(s5.getSelectedItem().toString());
            profileclass.d.setOp6(s6.getSelectedItem().toString());
            profileclass.d.setOp7(s7.getSelectedItem().toString());
            profileclass.d.setOp8(s8.getSelectedItem().toString());
            profileclass.d.setOp9(s9.getSelectedItem().toString());
            profileclass.d.setOp10(s10.getSelectedItem().toString());
            checkBox.setChecked(true);
        }

        return checkBox.isChecked();
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Please select all the options!";
    }
}