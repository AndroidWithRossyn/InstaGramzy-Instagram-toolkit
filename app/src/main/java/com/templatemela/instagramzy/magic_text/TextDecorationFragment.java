package com.templatemela.instagramzy.magic_text;

import android.app.Activity;
import android.content.Context;
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


public class TextDecorationFragment extends Fragment {


    public Activity activity;


    public final ArrayList arrayList = new ArrayList();


    public EditText edit_text_DF;


    public class C3187a implements TextWatcher {


        public final TextDecorationAdepeter f16414r;

        public C3187a(TextDecorationAdepeter bVar) {
            this.f16414r = bVar;
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String obj = TextDecorationFragment.this.edit_text_DF.getText().toString();
            if (obj.isEmpty()) {
                obj = "Preview text";
            }
            for (int i4 = 0; i4 < TextDecorationFragment.this.arrayList.size(); i4++) {
                ((MagicItem) TextDecorationFragment.this.arrayList.get(i4)).previewText = obj;
                this.f16414r.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TextDecorationFragment textDecorationFragment;
        View inflate = layoutInflater.inflate(R.layout.fragment_magic_text_decoration, viewGroup, false);
        String str = "Decoration 22";
        String str2 = "Decoration 21";
        String str3 = "Decoration 20";
        String str4 = "Decoration 19";
        String str5 = "Decoration 18";
        String str6 = "Decoration 17";
        String str7 = "Decoration 16";
        if (this.arrayList.isEmpty()) {
            MagicItem magicItem = new MagicItem("Decoration 1");
            MagicItem magicItem2 = new MagicItem("Decoration 2");
            MagicItem magicItem3 = new MagicItem("Decoration 3");
            MagicItem magicItem4 = new MagicItem("Decoration 4");
            MagicItem magicItem5 = new MagicItem("Decoration 5");
            MagicItem magicItem6 = new MagicItem("Decoration 6");
            MagicItem magicItem7 = new MagicItem("Decoration 7");
            MagicItem magicItem8 = new MagicItem("Decoration 8");
            MagicItem magicItem9 = new MagicItem("Decoration 9");
            MagicItem magicItem10 = new MagicItem("Decoration 10");
            MagicItem magicItem11 = new MagicItem("Decoration 11");
            MagicItem magicItem12 = new MagicItem("Decoration 12");
            MagicItem magicItem13 = new MagicItem("Decoration 13");
            MagicItem magicItem14 = new MagicItem("Decoration 14");
            MagicItem magicItem15 = new MagicItem("Decoration 15");
            MagicItem magicItem16 = new MagicItem(str7);
            MagicItem magicItem17 = new MagicItem(str6);
            MagicItem magicItem18 = new MagicItem(str5);
            MagicItem magicItem19 = new MagicItem(str4);
            MagicItem magicItem20 = new MagicItem(str3);
            MagicItem magicItem21 = new MagicItem(str2);
            MagicItem magicItem22 = new MagicItem(str);
            MagicItem magicItem23 = new MagicItem("Decoration 23");
            MagicItem magicItem24 = new MagicItem("Decoration 24");
            MagicItem magicItem25 = new MagicItem("Decoration 25");
            MagicItem magicItem26 = new MagicItem("Decoration 26");
            MagicItem magicItem27 = new MagicItem("Decoration 27");
            MagicItem magicItem28 = new MagicItem("Decoration 28");
            MagicItem magicItem29 = new MagicItem("Decoration 29");
            MagicItem magicItem30 = new MagicItem("Decoration 30");
            MagicItem magicItem31 = new MagicItem("Decoration 31");
            MagicItem magicItem32 = new MagicItem("Decoration 32");
            MagicItem magicItem33 = new MagicItem("Decoration 33");
            MagicItem magicItem34 = new MagicItem("Decoration 34");
            MagicItem magicItem35 = new MagicItem("Decoration 35");
            MagicItem magicItem36 = new MagicItem("Decoration 36");
            MagicItem magicItem37 = new MagicItem("Decoration 37");
            MagicItem magicItem38 = new MagicItem("Decoration 38");
            MagicItem magicItem39 = new MagicItem("Decoration 39");
            MagicItem magicItem40 = new MagicItem("Decoration 40");
            MagicItem magicItem41 = new MagicItem("Decoration 41");
            MagicItem magicItem42 = new MagicItem("Decoration 42");
            MagicItem magicItem43 = new MagicItem("Decoration 43");
            MagicItem magicItem44 = new MagicItem("Decoration 44");
            MagicItem magicItem45 = new MagicItem("Decoration 45");
            MagicItem magicItem46 = new MagicItem("Decoration 46");
            MagicItem magicItem47 = new MagicItem("Decoration 47");
            MagicItem magicItem48 = new MagicItem("Decoration 48");
            MagicItem magicItem49 = new MagicItem("Decoration 49");
            MagicItem magicItem50 = new MagicItem("Decoration 50");
            MagicItem magicItem51 = new MagicItem("Decoration 51");
            MagicItem magicItem52 = new MagicItem("Decoration 52");
            MagicItem magicItem53 = new MagicItem("Decoration 53");
            MagicItem magicItem54 = new MagicItem("Decoration 54");
            MagicItem magicItem55 = new MagicItem("Decoration 55");
            MagicItem magicItem56 = new MagicItem("Decoration 56");
            MagicItem magicItem57 = new MagicItem("Decoration 57");
            MagicItem magicItem58 = new MagicItem("Decoration 58");
            MagicItem magicItem59 = new MagicItem("Decoration 59");
            MagicItem magicItem60 = new MagicItem("Decoration 60");
            MagicItem magicItem61 = new MagicItem("Decoration 61");
            MagicItem magicItem62 = new MagicItem("Decoration 62");
            MagicItem magicItem63 = new MagicItem("Decoration 63");
            MagicItem magicItem64 = new MagicItem("Decoration 64");
            MagicItem magicItem65 = new MagicItem("Decoration 65");
            MagicItem magicItem66 = new MagicItem("Decoration 66");
            MagicItem magicItem67 = new MagicItem("Decoration 67");
            MagicItem magicItem68 = new MagicItem("Decoration 68");
            MagicItem magicItem69 = new MagicItem("Decoration 69");
            MagicItem magicItem70 = new MagicItem("Decoration 70");
            MagicItem magicItem71 = new MagicItem("Decoration 71");
            MagicItem magicItem72 = new MagicItem("Decoration 72");
            MagicItem magicItem73 = new MagicItem("Decoration 73");
            MagicItem magicItem74 = new MagicItem("Decoration 74");
            MagicItem magicItem75 = new MagicItem("Decoration 75");
            MagicItem magicItem76 = new MagicItem("Decoration 76");
            MagicItem magicItem77 = new MagicItem("Decoration 77");
            MagicItem magicItem78 = new MagicItem("Decoration 78");
            MagicItem magicItem79 = new MagicItem("Decoration 79");
            MagicItem magicItem80 = new MagicItem("Decoration 80");
            MagicItem magicItem81 = new MagicItem("Decoration 81");
            MagicItem magicItem82 = new MagicItem("Decoration 82");
            MagicItem magicItem83 = new MagicItem("Decoration 83");
            MagicItem magicItem84 = new MagicItem("Decoration 84");
            MagicItem magicItem85 = new MagicItem("Decoration 85");
            MagicItem magicItem86 = new MagicItem("Decoration 86");
            MagicItem magicItem87 = new MagicItem("Decoration 87");
            MagicItem magicItem88 = new MagicItem("Decoration 88");
            MagicItem magicItem89 = new MagicItem("Decoration 89");
            MagicItem magicItem90 = new MagicItem("Decoration 90");
            MagicItem magicItem91 = new MagicItem("Decoration 91");
            MagicItem magicItem92 = new MagicItem("Decoration 92");
            MagicItem magicItem93 = new MagicItem("Decoration 93");
            MagicItem magicItem94 = new MagicItem("Decoration 94");
            MagicItem magicItem95 = new MagicItem("Decoration 95");
            MagicItem magicItem96 = new MagicItem("Decoration 96");
            MagicItem magicItem97 = new MagicItem("Decoration 97");
            MagicItem magicItem98 = new MagicItem("Decoration 98");
            MagicItem magicItem99 = new MagicItem("Decoration 99");
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
            this.arrayList.add(magicItem33);
            this.arrayList.add(magicItem34);
            this.arrayList.add(magicItem35);
            this.arrayList.add(magicItem36);
            this.arrayList.add(magicItem37);
            this.arrayList.add(magicItem38);
            this.arrayList.add(magicItem39);
            this.arrayList.add(magicItem40);
            this.arrayList.add(magicItem41);
            this.arrayList.add(magicItem42);
            this.arrayList.add(magicItem43);
            this.arrayList.add(magicItem44);
            this.arrayList.add(magicItem45);
            this.arrayList.add(magicItem46);
            this.arrayList.add(magicItem47);
            this.arrayList.add(magicItem48);
            this.arrayList.add(magicItem49);
            this.arrayList.add(magicItem50);
            this.arrayList.add(magicItem51);
            this.arrayList.add(magicItem52);
            this.arrayList.add(magicItem53);
            this.arrayList.add(magicItem54);
            this.arrayList.add(magicItem55);
            this.arrayList.add(magicItem56);
            this.arrayList.add(magicItem57);
            this.arrayList.add(magicItem58);
            this.arrayList.add(magicItem59);
            this.arrayList.add(magicItem60);
            this.arrayList.add(magicItem61);
            this.arrayList.add(magicItem62);
            this.arrayList.add(magicItem63);
            this.arrayList.add(magicItem64);
            this.arrayList.add(magicItem65);
            this.arrayList.add(magicItem66);
            this.arrayList.add(magicItem67);
            this.arrayList.add(magicItem68);
            this.arrayList.add(magicItem69);
            this.arrayList.add(magicItem70);
            this.arrayList.add(magicItem71);
            this.arrayList.add(magicItem72);
            this.arrayList.add(magicItem73);
            this.arrayList.add(magicItem74);
            this.arrayList.add(magicItem75);
            this.arrayList.add(magicItem76);
            this.arrayList.add(magicItem77);
            this.arrayList.add(magicItem78);
            this.arrayList.add(magicItem79);
            this.arrayList.add(magicItem80);
            this.arrayList.add(magicItem81);
            this.arrayList.add(magicItem82);
            this.arrayList.add(magicItem83);
            this.arrayList.add(magicItem84);
            this.arrayList.add(magicItem85);
            this.arrayList.add(magicItem86);
            this.arrayList.add(magicItem87);
            this.arrayList.add(magicItem88);
            this.arrayList.add(magicItem89);
            this.arrayList.add(magicItem90);
            this.arrayList.add(magicItem91);
            this.arrayList.add(magicItem92);
            this.arrayList.add(magicItem93);
            this.arrayList.add(magicItem94);
            this.arrayList.add(magicItem95);
            this.arrayList.add(magicItem96);
            this.arrayList.add(magicItem97);
            this.arrayList.add(magicItem98);
            this.arrayList.add(magicItem99);
            textDecorationFragment = this;
        } else {
            textDecorationFragment = this;
            textDecorationFragment.arrayList.clear();
            MagicItem magicItem100 = new MagicItem("Decoration 1");
            MagicItem magicItem101 = new MagicItem("Decoration 2");
            MagicItem magicItem102 = new MagicItem("Decoration 3");
            MagicItem magicItem103 = new MagicItem("Decoration 4");
            MagicItem magicItem104 = new MagicItem("Decoration 5");
            MagicItem magicItem105 = new MagicItem("Decoration 6");
            MagicItem magicItem106 = new MagicItem("Decoration 7");
            MagicItem magicItem107 = new MagicItem("Decoration 8");
            MagicItem magicItem108 = new MagicItem("Decoration 9");
            MagicItem magicItem109 = new MagicItem("Decoration 10");
            MagicItem magicItem110 = new MagicItem("Decoration 11");
            MagicItem magicItem111 = new MagicItem("Decoration 12");
            MagicItem magicItem112 = new MagicItem("Decoration 13");
            MagicItem magicItem113 = new MagicItem("Decoration 14");
            MagicItem magicItem114 = new MagicItem("Decoration 15");
            MagicItem magicItem115 = new MagicItem(str7);
            MagicItem magicItem116 = new MagicItem(str6);
            MagicItem magicItem117 = new MagicItem(str5);
            MagicItem magicItem118 = new MagicItem(str4);
            MagicItem magicItem119 = new MagicItem(str3);
            MagicItem magicItem120 = new MagicItem(str2);
            MagicItem magicItem121 = new MagicItem(str);
            MagicItem magicItem122 = new MagicItem("Decoration 23");
            MagicItem magicItem123 = new MagicItem("Decoration 24");
            MagicItem magicItem124 = new MagicItem("Decoration 25");
            MagicItem magicItem125 = new MagicItem("Decoration 26");
            MagicItem magicItem126 = new MagicItem("Decoration 27");
            MagicItem magicItem127 = new MagicItem("Decoration 28");
            MagicItem magicItem128 = new MagicItem("Decoration 29");
            MagicItem magicItem129 = new MagicItem("Decoration 30");
            MagicItem magicItem130 = new MagicItem("Decoration 31");
            MagicItem magicItem131 = new MagicItem("Decoration 32");
            MagicItem magicItem132 = new MagicItem("Decoration 33");
            MagicItem magicItem133 = new MagicItem("Decoration 34");
            MagicItem magicItem134 = new MagicItem("Decoration 35");
            MagicItem magicItem135 = new MagicItem("Decoration 36");
            MagicItem magicItem136 = new MagicItem("Decoration 37");
            MagicItem magicItem137 = new MagicItem("Decoration 38");
            MagicItem magicItem138 = new MagicItem("Decoration 39");
            MagicItem magicItem139 = new MagicItem("Decoration 40");
            MagicItem magicItem140 = new MagicItem("Decoration 41");
            MagicItem magicItem141 = new MagicItem("Decoration 42");
            MagicItem magicItem142 = new MagicItem("Decoration 43");
            MagicItem magicItem143 = new MagicItem("Decoration 44");
            MagicItem magicItem144 = new MagicItem("Decoration 45");
            MagicItem magicItem145 = new MagicItem("Decoration 46");
            MagicItem magicItem146 = new MagicItem("Decoration 47");
            MagicItem magicItem147 = new MagicItem("Decoration 48");
            MagicItem magicItem148 = new MagicItem("Decoration 49");
            MagicItem magicItem149 = new MagicItem("Decoration 50");
            MagicItem magicItem150 = new MagicItem("Decoration 51");
            MagicItem magicItem151 = new MagicItem("Decoration 52");
            MagicItem magicItem152 = new MagicItem("Decoration 53");
            MagicItem magicItem153 = new MagicItem("Decoration 54");
            MagicItem magicItem154 = new MagicItem("Decoration 55");
            MagicItem magicItem155 = new MagicItem("Decoration 56");
            MagicItem magicItem156 = new MagicItem("Decoration 57");
            MagicItem magicItem157 = new MagicItem("Decoration 58");
            MagicItem magicItem158 = new MagicItem("Decoration 59");
            MagicItem magicItem159 = new MagicItem("Decoration 60");
            MagicItem magicItem160 = new MagicItem("Decoration 61");
            MagicItem magicItem161 = new MagicItem("Decoration 62");
            MagicItem magicItem162 = new MagicItem("Decoration 63");
            MagicItem magicItem163 = new MagicItem("Decoration 64");
            MagicItem magicItem164 = new MagicItem("Decoration 65");
            MagicItem magicItem165 = new MagicItem("Decoration 66");
            MagicItem magicItem166 = new MagicItem("Decoration 67");
            MagicItem magicItem167 = new MagicItem("Decoration 68");
            MagicItem magicItem168 = new MagicItem("Decoration 69");
            MagicItem magicItem169 = new MagicItem("Decoration 70");
            MagicItem magicItem170 = new MagicItem("Decoration 71");
            MagicItem magicItem171 = new MagicItem("Decoration 72");
            MagicItem magicItem172 = new MagicItem("Decoration 73");
            MagicItem magicItem173 = new MagicItem("Decoration 74");
            MagicItem magicItem174 = new MagicItem("Decoration 75");
            MagicItem magicItem175 = new MagicItem("Decoration 76");
            MagicItem magicItem176 = new MagicItem("Decoration 77");
            MagicItem magicItem177 = new MagicItem("Decoration 78");
            MagicItem magicItem178 = new MagicItem("Decoration 79");
            MagicItem magicItem179 = new MagicItem("Decoration 80");
            MagicItem magicItem180 = new MagicItem("Decoration 81");
            MagicItem magicItem181 = new MagicItem("Decoration 82");
            MagicItem magicItem182 = new MagicItem("Decoration 83");
            MagicItem magicItem183 = new MagicItem("Decoration 84");
            MagicItem magicItem184 = new MagicItem("Decoration 85");
            MagicItem magicItem185 = new MagicItem("Decoration 86");
            MagicItem magicItem186 = new MagicItem("Decoration 87");
            MagicItem magicItem187 = new MagicItem("Decoration 88");
            MagicItem magicItem188 = new MagicItem("Decoration 89");
            MagicItem magicItem189 = new MagicItem("Decoration 90");
            MagicItem magicItem190 = new MagicItem("Decoration 91");
            MagicItem magicItem191 = new MagicItem("Decoration 92");
            MagicItem magicItem192 = new MagicItem("Decoration 93");
            MagicItem magicItem193 = new MagicItem("Decoration 94");
            MagicItem magicItem194 = new MagicItem("Decoration 95");
            MagicItem magicItem195 = new MagicItem("Decoration 96");
            MagicItem magicItem196 = new MagicItem("Decoration 97");
            MagicItem magicItem197 = new MagicItem("Decoration 98");
            MagicItem magicItem198 = new MagicItem("Decoration 99");
            textDecorationFragment.arrayList.add(magicItem100);
            textDecorationFragment.arrayList.add(magicItem101);
            textDecorationFragment.arrayList.add(magicItem102);
            textDecorationFragment.arrayList.add(magicItem103);
            textDecorationFragment.arrayList.add(magicItem104);
            textDecorationFragment.arrayList.add(magicItem105);
            textDecorationFragment.arrayList.add(magicItem106);
            textDecorationFragment.arrayList.add(magicItem107);
            textDecorationFragment.arrayList.add(magicItem108);
            textDecorationFragment.arrayList.add(magicItem109);
            textDecorationFragment.arrayList.add(magicItem110);
            textDecorationFragment.arrayList.add(magicItem111);
            textDecorationFragment.arrayList.add(magicItem112);
            textDecorationFragment.arrayList.add(magicItem113);
            textDecorationFragment.arrayList.add(magicItem114);
            textDecorationFragment.arrayList.add(magicItem115);
            textDecorationFragment.arrayList.add(magicItem116);
            textDecorationFragment.arrayList.add(magicItem117);
            textDecorationFragment.arrayList.add(magicItem118);
            textDecorationFragment.arrayList.add(magicItem119);
            textDecorationFragment.arrayList.add(magicItem120);
            textDecorationFragment.arrayList.add(magicItem121);
            textDecorationFragment.arrayList.add(magicItem122);
            textDecorationFragment.arrayList.add(magicItem123);
            textDecorationFragment.arrayList.add(magicItem124);
            textDecorationFragment.arrayList.add(magicItem125);
            textDecorationFragment.arrayList.add(magicItem126);
            textDecorationFragment.arrayList.add(magicItem127);
            textDecorationFragment.arrayList.add(magicItem128);
            textDecorationFragment.arrayList.add(magicItem129);
            textDecorationFragment.arrayList.add(magicItem130);
            textDecorationFragment.arrayList.add(magicItem131);
            textDecorationFragment.arrayList.add(magicItem132);
            textDecorationFragment.arrayList.add(magicItem133);
            textDecorationFragment.arrayList.add(magicItem134);
            textDecorationFragment.arrayList.add(magicItem135);
            textDecorationFragment.arrayList.add(magicItem136);
            textDecorationFragment.arrayList.add(magicItem137);
            textDecorationFragment.arrayList.add(magicItem138);
            textDecorationFragment.arrayList.add(magicItem139);
            textDecorationFragment.arrayList.add(magicItem140);
            textDecorationFragment.arrayList.add(magicItem141);
            textDecorationFragment.arrayList.add(magicItem142);
            textDecorationFragment.arrayList.add(magicItem143);
            textDecorationFragment.arrayList.add(magicItem144);
            textDecorationFragment.arrayList.add(magicItem145);
            textDecorationFragment.arrayList.add(magicItem146);
            textDecorationFragment.arrayList.add(magicItem147);
            textDecorationFragment.arrayList.add(magicItem148);
            textDecorationFragment.arrayList.add(magicItem149);
            textDecorationFragment.arrayList.add(magicItem150);
            textDecorationFragment.arrayList.add(magicItem151);
            textDecorationFragment.arrayList.add(magicItem152);
            textDecorationFragment.arrayList.add(magicItem153);
            textDecorationFragment.arrayList.add(magicItem154);
            textDecorationFragment.arrayList.add(magicItem155);
            textDecorationFragment.arrayList.add(magicItem156);
            textDecorationFragment.arrayList.add(magicItem157);
            textDecorationFragment.arrayList.add(magicItem158);
            textDecorationFragment.arrayList.add(magicItem159);
            textDecorationFragment.arrayList.add(magicItem160);
            textDecorationFragment.arrayList.add(magicItem161);
            textDecorationFragment.arrayList.add(magicItem162);
            textDecorationFragment.arrayList.add(magicItem163);
            textDecorationFragment.arrayList.add(magicItem164);
            textDecorationFragment.arrayList.add(magicItem165);
            textDecorationFragment.arrayList.add(magicItem166);
            textDecorationFragment.arrayList.add(magicItem167);
            textDecorationFragment.arrayList.add(magicItem168);
            textDecorationFragment.arrayList.add(magicItem169);
            textDecorationFragment.arrayList.add(magicItem170);
            textDecorationFragment.arrayList.add(magicItem171);
            textDecorationFragment.arrayList.add(magicItem172);
            textDecorationFragment.arrayList.add(magicItem173);
            textDecorationFragment.arrayList.add(magicItem174);
            textDecorationFragment.arrayList.add(magicItem175);
            textDecorationFragment.arrayList.add(magicItem176);
            textDecorationFragment.arrayList.add(magicItem177);
            textDecorationFragment.arrayList.add(magicItem178);
            textDecorationFragment.arrayList.add(magicItem179);
            textDecorationFragment.arrayList.add(magicItem180);
            textDecorationFragment.arrayList.add(magicItem181);
            textDecorationFragment.arrayList.add(magicItem182);
            textDecorationFragment.arrayList.add(magicItem183);
            textDecorationFragment.arrayList.add(magicItem184);
            textDecorationFragment.arrayList.add(magicItem185);
            textDecorationFragment.arrayList.add(magicItem186);
            textDecorationFragment.arrayList.add(magicItem187);
            textDecorationFragment.arrayList.add(magicItem188);
            textDecorationFragment.arrayList.add(magicItem189);
            textDecorationFragment.arrayList.add(magicItem190);
            textDecorationFragment.arrayList.add(magicItem191);
            textDecorationFragment.arrayList.add(magicItem192);
            textDecorationFragment.arrayList.add(magicItem193);
            textDecorationFragment.arrayList.add(magicItem194);
            textDecorationFragment.arrayList.add(magicItem195);
            textDecorationFragment.arrayList.add(magicItem196);
            textDecorationFragment.arrayList.add(magicItem197);
            textDecorationFragment.arrayList.add(magicItem198);
        }
        View view = inflate;
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_DF);
        TextDecorationAdepeter bVar = new TextDecorationAdepeter(textDecorationFragment.arrayList, textDecorationFragment.activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(textDecorationFragment.activity));
        recyclerView.setAdapter(bVar);
        EditText editText = (EditText) view.findViewById(R.id.edit_text_DF);
        textDecorationFragment.edit_text_DF = editText;
        editText.addTextChangedListener(new C3187a(bVar));
        return view;
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }
}
