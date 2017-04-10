package com.digibuddies.nectus.profile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

import com.digibuddies.nectus.R;
import com.whygraphics.gifview.gif.GIFView;

import agency.tango.materialintroscreen.SlideFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomSlide extends SlideFragment {
    private CheckBox checkBox;
    private Spinner spinner;
    public String a="sa";
    public CircleImageView av;
    SQLiteDatabase db;
    int flag=0;
    EditText ett;
    private static final String PICKER_TAG = "PICKER_TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide, container, false);
        GIFView mGifView = (GIFView)view.findViewById(R.id.main_activity_gif_vie2);
        mGifView.setGifResource("asset:ban");
        checkBox = (CheckBox) view.findViewById(R.id.checkBox89);
        checkBox.setChecked(false);
        ett=(EditText)view.findViewById(R.id.editText2);
        if(!(profileclass.usern.equals(""))){
            ett.setText(profileclass.usern);
        }
        RelativeLayout cont = (RelativeLayout)view.findViewById(R.id.container);
        cont.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ett.clearFocus();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ett.getWindowToken(), 0);

                return false;
            }
        });
        ett.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(flag==1){
                    checkBox.setChecked(true);}
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ett.getWindowToken(), 0);

            }
        });
        av = (CircleImageView)view.findViewById(R.id.avatar);
        avp();

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.first_slide_background;
    }

    public void avp(){
        av.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimple();
                flag=1;
            }
        });
    }

    public void showSimple() {
        new SimpleFragment().show(getFragmentManager(), PICKER_TAG);
    }
    @Override
    public int buttonsColor() {
        return R.color.first_slide_buttons;
    }

    @Override
    public boolean canMoveFurther() {
        if(flag==1&&(!(ett.getText().toString().equals(""))))
        {   profileclass.d.setUname(ett.getText().toString());
            CustomSlide2.tvun.setText(ett.getText().toString());
            checkBox.setChecked(true);

        }
        return checkBox.isChecked();
    }

    protected void insertIntoDB() {
        String uname = String.valueOf(ett.getText().toString());
        String query = "INSERT OR REPLACE INTO mypro (uname) VALUES('" + uname + "')";
        db.execSQL(query);
        db.close();
    }


    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.error_message2);
    }
}