package com.example.appdrisonet.DialogoFratment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.appdrisonet.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottonShetFragment2 extends BottomSheetDialogFragment {


    public String ruta_archivo;
    public BottonShetFragment2() {
        // Required empty public constructor
    }
    public static BottonShetFragment2 newInstance() {
        BottonShetFragment2 fragment = new BottonShetFragment2();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void setupDialog(Dialog dialog, int style) {
        final View contentView = View.inflate(getContext(), R.layout.fragment_botton_sheet2, null);
        TextView txtdescargar;

        txtdescargar=(TextView)contentView.findViewById(R.id.id_tvdescargar);

        TextView nombrearchivo;
        ImageView imgg;
        imgg=(ImageView)contentView.findViewById(R.id.imgprevia);
        txtdescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Descargar(ruta_archivo);

            }
        });
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        // loadAddNotesFragments();
    }
    private void Descargar(String ruta) {

        if (TextUtils.isEmpty(ruta)){
            //    Toast.makeText(getContext(), "no hay atchivo", Toast.LENGTH_SHORT).show();

        }else{

            Uri uri = Uri.parse(ruta);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
