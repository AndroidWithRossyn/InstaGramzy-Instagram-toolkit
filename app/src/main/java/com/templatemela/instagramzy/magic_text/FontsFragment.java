package com.templatemela.instagramzy.magic_text;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.templatemela.instagramzy.R;


import java.util.ArrayList;


public class FontsFragment extends Fragment {

    
    public Activity activity;

   
    public RecyclerView recycle_view_FF;


    public ArrayList arrayList = new ArrayList();


    public EditText edit_text_FF;


    public class onTextChnage implements TextWatcher {

        
        public final FontsAdepter f16420r;

        public onTextChnage(FontsAdepter dVar) {
            this.f16420r = dVar;
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }


        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String obj = FontsFragment.this.edit_text_FF.getText().toString();
            if (obj.isEmpty() || obj.equals(" ")) {
                obj = "Preview text";
            }
            for (int i4 = 0; i4 < FontsFragment.this.arrayList.size(); i4++) {
                ((MagicItem) FontsFragment.this.arrayList.get(i4)).previewText = obj;
                this.f16420r.notifyDataSetChanged();
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FontsFragment bVar;
        View inflate = inflater.inflate(R.layout.fragment_magic_text_font, container, false);
        String str = "Taurus";
        String str2 = "Aries";
        String str3 = "Fancy style 15";
        String str4 = "Fancy style 14";
        String str5 = "Fancy style 13";
        String str6 = "Fancy style 12";
        String str7 = "Fancy style 11";
        if (this.arrayList.isEmpty()) {
            MagicItem magicItem = new MagicItem("Bubble");
            MagicItem magicItem2 = new MagicItem("Currency");
            MagicItem magicItem3 = new MagicItem("Magic");
            MagicItem magicItem4 = new MagicItem("Knight");
            MagicItem magicItem5 = new MagicItem("Antrophobia");
            MagicItem magicItem6 = new MagicItem("Fancy style 1");
            MagicItem magicItem7 = new MagicItem("Fancy style 2");
            MagicItem magicItem8 = new MagicItem("Fancy style 3");
            MagicItem magicItem9 = new MagicItem("Fancy style 4");
            MagicItem magicItem10 = new MagicItem("Fancy style 5");
            MagicItem magicItem11 = new MagicItem("Fancy style 6");
            MagicItem magicItem12 = new MagicItem("Fancy style 7");
            MagicItem magicItem13 = new MagicItem("Fancy style 8");
            MagicItem magicItem14 = new MagicItem("Fancy style 9");
            MagicItem magicItem15 = new MagicItem("Fancy style 10");
            MagicItem magicItem16 = new MagicItem(str7);
            MagicItem magicItem17 = new MagicItem(str6);
            MagicItem magicItem18 = new MagicItem(str5);
            MagicItem magicItem19 = new MagicItem(str4);
            MagicItem magicItem20 = new MagicItem(str3);
            MagicItem magicItem21 = new MagicItem(str2);
            MagicItem magicItem22 = new MagicItem(str);
            MagicItem magicItem23 = new MagicItem("Gemini");
            MagicItem magicItem24 = new MagicItem("Cancer");
            MagicItem magicItem25 = new MagicItem("Leo");
            MagicItem magicItem26 = new MagicItem("Virgo");
            MagicItem magicItem27 = new MagicItem("Libra");
            MagicItem magicItem28 = new MagicItem("Scorpius");
            MagicItem magicItem29 = new MagicItem("Sagittarius");
            MagicItem magicItem30 = new MagicItem("Capricorn");
            MagicItem magicItem31 = new MagicItem("Aquarius");
            MagicItem magicItem32 = new MagicItem("Pisces");
            this.arrayList.add(magicItem);
            this.arrayList.add(magicItem2);
            this.arrayList.add(magicItem3);
            this.arrayList.add(magicItem4);
            this.arrayList.add(magicItem5);
            this.arrayList.add(magicItem6);
            this.arrayList.add(magicItem7);
            this.arrayList.add(magicItem8);
            this.arrayList.add(magicItem9);
            this.arrayList.add(magicItem10);
            this.arrayList.add(magicItem11);
            this.arrayList.add(magicItem12);
            this.arrayList.add(magicItem13);
            this.arrayList.add(magicItem14);
            this.arrayList.add(magicItem15);
            this.arrayList.add(magicItem16);
            this.arrayList.add(magicItem17);
            this.arrayList.add(magicItem18);
            this.arrayList.add(magicItem19);
            this.arrayList.add(magicItem20);
            this.arrayList.add(magicItem21);
            this.arrayList.add(magicItem22);
            this.arrayList.add(magicItem23);
            this.arrayList.add(magicItem24);
            this.arrayList.add(magicItem25);
            this.arrayList.add(magicItem26);
            this.arrayList.add(magicItem27);
            this.arrayList.add(magicItem28);
            this.arrayList.add(magicItem29);
            this.arrayList.add(magicItem30);
            this.arrayList.add(magicItem31);
            this.arrayList.add(magicItem32);
            bVar = this;
        } else {
            bVar = this;
            bVar.arrayList.clear();
            MagicItem magicItem33 = new MagicItem("Bubble");
            MagicItem magicItem34 = new MagicItem("Currency");
            MagicItem magicItem35 = new MagicItem("Magic");
            MagicItem magicItem36 = new MagicItem("Knight");
            MagicItem magicItem37 = new MagicItem("Antrophobia");
            MagicItem magicItem38 = new MagicItem("Fancy style 1");
            MagicItem magicItem39 = new MagicItem("Fancy style 2");
            MagicItem magicItem40 = new MagicItem("Fancy style 3");
            MagicItem magicItem41 = new MagicItem("Fancy style 4");
            MagicItem magicItem42 = new MagicItem("Fancy style 5");
            MagicItem magicItem43 = new MagicItem("Fancy style 6");
            MagicItem magicItem44 = new MagicItem("Fancy style 7");
            MagicItem magicItem45 = new MagicItem("Fancy style 8");
            MagicItem magicItem46 = new MagicItem("Fancy style 9");
            MagicItem magicItem47 = new MagicItem("Fancy style 10");
            MagicItem magicItem48 = new MagicItem(str7);
            MagicItem magicItem49 = new MagicItem(str6);
            MagicItem magicItem50 = new MagicItem(str5);
            MagicItem magicItem51 = new MagicItem(str4);
            MagicItem magicItem52 = new MagicItem(str3);
            MagicItem magicItem53 = new MagicItem(str2);
            MagicItem magicItem54 = new MagicItem(str);
            MagicItem magicItem55 = new MagicItem("Gemini");
            MagicItem magicItem56 = new MagicItem("Cancer");
            MagicItem magicItem57 = new MagicItem("Leo");
            MagicItem magicItem58 = new MagicItem("Virgo");
            MagicItem magicItem59 = new MagicItem("Libra");
            MagicItem magicItem60 = new MagicItem("Scorpius");
            MagicItem magicItem61 = new MagicItem("Sagittarius");
            MagicItem magicItem62 = new MagicItem("Capricorn");
            MagicItem magicItem63 = new MagicItem("Aquarius");
            MagicItem magicItem64 = new MagicItem("Pisces");
            bVar.arrayList.add(magicItem33);
            bVar.arrayList.add(magicItem34);
            bVar.arrayList.add(magicItem35);
            bVar.arrayList.add(magicItem36);
            bVar.arrayList.add(magicItem37);
            bVar.arrayList.add(magicItem38);
            bVar.arrayList.add(magicItem39);
            bVar.arrayList.add(magicItem40);
            bVar.arrayList.add(magicItem41);
            bVar.arrayList.add(magicItem42);
            bVar.arrayList.add(magicItem43);
            bVar.arrayList.add(magicItem44);
            bVar.arrayList.add(magicItem45);
            bVar.arrayList.add(magicItem46);
            bVar.arrayList.add(magicItem47);
            bVar.arrayList.add(magicItem48);
            bVar.arrayList.add(magicItem49);
            bVar.arrayList.add(magicItem50);
            bVar.arrayList.add(magicItem51);
            bVar.arrayList.add(magicItem52);
            bVar.arrayList.add(magicItem53);
            bVar.arrayList.add(magicItem54);
            bVar.arrayList.add(magicItem55);
            bVar.arrayList.add(magicItem56);
            bVar.arrayList.add(magicItem57);
            bVar.arrayList.add(magicItem58);
            bVar.arrayList.add(magicItem59);
            bVar.arrayList.add(magicItem60);
            bVar.arrayList.add(magicItem61);
            bVar.arrayList.add(magicItem62);
            bVar.arrayList.add(magicItem63);
            bVar.arrayList.add(magicItem64);
        }
        View view = inflate;
        bVar.recycle_view_FF = (RecyclerView) view.findViewById(R.id.recycle_view_FF);
        FontsAdepter dVar = new FontsAdepter(bVar.arrayList, getActivity());
        bVar.recycle_view_FF.setLayoutManager(new LinearLayoutManager(getContext()));
        bVar.recycle_view_FF.setAdapter(dVar);
        EditText editText = (EditText) view.findViewById(R.id.edit_text_FF);
        bVar.edit_text_FF = editText;
        editText.addTextChangedListener(new onTextChnage(dVar));

        return view;
    }

}
