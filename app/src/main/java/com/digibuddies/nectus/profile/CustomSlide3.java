package com.digibuddies.nectus.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.digibuddies.nectus.R;
import com.whygraphics.gifview.gif.GIFView;

import agency.tango.materialintroscreen.SlideFragment;

public class CustomSlide3 extends SlideFragment {
    private CheckBox checkBox;
    private Spinner spin,spin2;
    EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide3, container, false);

        TextView tx = (TextView) view.findViewById(R.id.editText);
        TextView tx2 = (TextView) view.findViewById(R.id.textView5);
        TextView tx3 = (TextView) view.findViewById(R.id.textView10);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/abc.ttf");

        tx.setTypeface(custom_font);
        tx2.setTypeface(custom_font);
        tx3.setTypeface(custom_font);


        GIFView mGifView = (GIFView) view.findViewById(R.id.main_activity_gif_vie2);
        mGifView.setGifResource("asset:ban");
        checkBox = (CheckBox) view.findViewById(R.id.checkBox89);
        checkBox.setChecked(false);
        et = (EditText) view.findViewById(R.id.editText);
        if(!(profileclass.mail.equals(""))){
            et.setText(profileclass.mail);
        }
        RelativeLayout rll=(RelativeLayout)view.findViewById(R.id.rl);

        rll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                return false;
            }
        });
        spin = (Spinner) view.findViewById(R.id.Spinnerr1);

        spin2 = (Spinner) view.findViewById(R.id.Spinnerr2);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            }
        });
        return view;
    }
    @Override
    public int backgroundColor() {
        return R.color.third_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.third_slide_buttons;
    }

    @Override
    public boolean canMoveFurther() {
        if((!(spin.getSelectedItem().equals("Select")))&&(!(spin2.getSelectedItem().equals("Select")))&&(!(et.getText().equals(""))))
        {   profileclass.d.setOp11(spin.getSelectedItem().toString());
            profileclass.d.setOp12(spin2.getSelectedItem().toString());
            profileclass.d.setEmail(et.getText().toString());
            checkBox.setChecked(true);
        }
        return checkBox.isChecked();
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.error_message);
    }
}