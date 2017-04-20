package com.digibuddies.nectus.profile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.digibuddies.nectus.R;

/**
 * Created by vikram singh on 04-04-2017.
 */

public class SimpleFragment extends DialogFragment {
    final static int[] imagesOnly = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27, R.drawable.a28};

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imagesOnly.length;
            }

            @Override
            public Object getItem(int i) {
                return imagesOnly[i];
            }

            @Override
            public long getItemId(int i) {
                return imagesOnly[i];
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = new ImageView(getContext());
                ((ImageView) view).setImageResource((int) getItem(i));
                return view;
            }
        };
        return new AlertDialog.Builder(getContext()).setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((profileclass) getContext()).OnSelected(SimpleFragment.this.getClass(), (int) adapter.getItem(i));
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setCancelable(true).setTitle("Pick image(simple)").create();
    }
}

