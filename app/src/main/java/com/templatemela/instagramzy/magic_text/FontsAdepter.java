package com.templatemela.instagramzy.magic_text;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.templatemela.instagramzy.R;

import java.util.ArrayList;


public class FontsAdepter extends RecyclerView.Adapter<FontsAdepter.ViewHolder1> {


    public ArrayList arrayList;


    public Activity activity;

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder1( LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_font, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        this.m(holder, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder1 extends RecyclerView.ViewHolder {


        public TextView titleTV;


        public TextView descriptionTV;


        public LinearLayout card_view;

        public ViewHolder1(View view) {
            super(view);
            this.titleTV = (TextView) view.findViewById(R.id.titleTV);
            this.descriptionTV = (TextView) view.findViewById(R.id.descriptionTV);
            this.card_view = (LinearLayout) view.findViewById(R.id.card_view);
        }
    }

    public FontsAdepter(ArrayList arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }






    public final char mo13047f(char c) {
        if (c == 'A' || c == 'a') {
            return 945;
        }
        if (c == 'B' || c == 'b') {
            return 1074;
        }
        if (c == 'C' || c == 'c') {
            return 162;
        }
        if (c == 'D' || c == 'd') {
            return 8706;
        }
        if (c == 'E' || c == 'e') {
            return 1108;
        }
        if (c == 'F' || c == 'f') {
            return 'f';
        }
        if (c == 'G' || c == 'g') {
            return 'g';
        }
        if (c == 'H' || c == 'h') {
            return 1085;
        }
        if (c == 'I' || c == 'i') {
            return 953;
        }
        if (c == 'J' || c == 'j') {
            return 1504;
        }
        if (c == 'K' || c == 'k') {
            return 1082;
        }
        if (c == 'L' || c == 'l') {
            return 8467;
        }
        if (c == 'M' || c == 'm') {
            return 1084;
        }
        if (c == 'N' || c == 'n') {
            return 1080;
        }
        if (c == 'O' || c == 'o') {
            return 963;
        }
        if (c == 'P' || c == 'p') {
            return 961;
        }
        if (c == 'Q' || c == 'q') {
            return 'q';
        }
        if (c == 'R' || c == 'r') {
            return 1103;
        }
        if (c == 'S' || c == 's') {
            return 's';
        }
        if (c == 'T' || c == 't') {
            return 1090;
        }
        if (c == 'U' || c == 'u') {
            return 965;
        }
        if (c == 'V' || c == 'v') {
            return 957;
        }
        if (c == 'W' || c == 'w') {
            return 969;
        }
        if (c == 'X' || c == 'x') {
            return 967;
        }
        if (c == 'Y' || c == 'y') {
            return 1091;
        }
        if (c == 'Z' || c == 'z') {
            return 'z';
        }
        return c;
    }


    public final char mo13048g(char c) {
        if (c == 'A') {
            return 9398;
        }
        if (c == 'B') {
            return 9399;
        }
        if (c == 'C') {
            return 9400;
        }
        if (c == 'D') {
            return 9401;
        }
        if (c == 'E') {
            return 9402;
        }
        if (c == 'F') {
            return 9403;
        }
        if (c == 'G') {
            return 9404;
        }
        if (c == 'H') {
            return 9405;
        }
        if (c == 'I') {
            return 9406;
        }
        if (c == 'J') {
            return 9407;
        }
        if (c == 'K') {
            return 9408;
        }
        if (c == 'L') {
            return 9409;
        }
        if (c == 'M') {
            return 9410;
        }
        if (c == 'N') {
            return 9411;
        }
        if (c == 'O') {
            return 9412;
        }
        if (c == 'P') {
            return 9413;
        }
        if (c == 'Q') {
            return 9414;
        }
        if (c == 'R') {
            return 9415;
        }
        if (c == 'S') {
            return 9416;
        }
        if (c == 'T') {
            return 9417;
        }
        if (c == 'U') {
            return 9418;
        }
        if (c == 'V') {
            return 9419;
        }
        if (c == 'W') {
            return 9420;
        }
        if (c == 'X') {
            return 9421;
        }
        if (c == 'Y') {
            return 9422;
        }
        if (c == 'Z') {
            return 9423;
        }
        if (c == 'a') {
            return 9424;
        }
        if (c == 'b') {
            return 9425;
        }
        if (c == 'c') {
            return 9426;
        }
        if (c == 'd') {
            return 9427;
        }
        if (c == 'e') {
            return 9428;
        }
        if (c == 'f') {
            return 9429;
        }
        if (c == 'g') {
            return 9430;
        }
        if (c == 'h') {
            return 9431;
        }
        if (c == 'i') {
            return 9432;
        }
        if (c == 'j') {
            return 9433;
        }
        if (c == 'k') {
            return 9434;
        }
        if (c == 'l') {
            return 9435;
        }
        if (c == 'm') {
            return 9436;
        }
        if (c == 'n') {
            return 9437;
        }
        if (c == 'o') {
            return 9438;
        }
        if (c == 'p') {
            return 9439;
        }
        if (c == 'q') {
            return 9440;
        }
        if (c == 'r') {
            return 9441;
        }
        if (c == 's') {
            return 9442;
        }
        if (c == 't') {
            return 9443;
        }
        if (c == 'u') {
            return 9444;
        }
        if (c == 'v') {
            return 9445;
        }
        if (c == 'w') {
            return 9446;
        }
        if (c == 'x') {
            return 9447;
        }
        if (c == 'y') {
            return 9448;
        }
        if (c == 'z') {
            return 9449;
        }
        if (c == '0') {
            return 9450;
        }
        if (c == '1') {
            return 9312;
        }
        if (c == '2') {
            return 9313;
        }
        if (c == '3') {
            return 9314;
        }
        if (c == '4') {
            return 9315;
        }
        if (c == '5') {
            return 9316;
        }
        if (c == '6') {
            return 9317;
        }
        if (c == '7') {
            return 9318;
        }
        if (c == '8') {
            return 9319;
        }
        if (c == '9') {
            return 9320;
        }
        return c;
    }

    /* renamed from: h */
    public final char mo13049h(char c) {
        if (c != 'A') {
            if (c == 'B') {
                return 7682;
            }
            if (c == 'C') {
                return 7688;
            }
            if (c != 'D') {
                if (c == 'E') {
                    return 7700;
                }
                if (c == 'F') {
                    return 7710;
                }
                if (c != 'G') {
                    if (c == 'H') {
                        return 7718;
                    }
                    if (c == 'I') {
                        return 7724;
                    }
                    if (c != 'K') {
                        if (c != 'L') {
                            if (c == 'M') {
                                return 7744;
                            }
                            if (c != 'N') {
                                if (c == 'O') {
                                    return 7758;
                                }
                                if (c != 'P') {
                                    if (c == 'R') {
                                        return 7768;
                                    }
                                    if (c != 'S') {
                                        if (c != 'T') {
                                            if (c == 'U') {
                                                return 7794;
                                            }
                                            if (c != 'V') {
                                                if (c == 'W') {
                                                    return 7814;
                                                }
                                                if (c != 'X') {
                                                    if (c == 'Y') {
                                                        return 7822;
                                                    }
                                                    if (c != 'Z') {
                                                        if (c != 'a') {
                                                            if (c == 'b') {
                                                                return 7683;
                                                            }
                                                            if (c == 'c') {
                                                                return 7689;
                                                            }
                                                            if (c != 'd') {
                                                                if (c == 'e') {
                                                                    return 7701;
                                                                }
                                                                if (c == 'f') {
                                                                    return 7711;
                                                                }
                                                                if (c != 'g') {
                                                                    if (c == 'h') {
                                                                        return 7719;
                                                                    }
                                                                    if (c == 'i') {
                                                                        return 7725;
                                                                    }
                                                                    if (c != 'k') {
                                                                        if (c != 'l') {
                                                                            if (c == 'm') {
                                                                                return 7745;
                                                                            }
                                                                            if (c != 'n') {
                                                                                if (c == 'o') {
                                                                                    return 7759;
                                                                                }
                                                                                if (c != 'p') {
                                                                                    if (c == 'r') {
                                                                                        return 7769;
                                                                                    }
                                                                                    if (c != 's') {
                                                                                        if (c != 't') {
                                                                                            if (c == 'u') {
                                                                                                return 7795;
                                                                                            }
                                                                                            if (c != 'v') {
                                                                                                if (c == 'w') {
                                                                                                    return 7815;
                                                                                                }
                                                                                                if (c != 'x') {
                                                                                                    if (c == 'y') {
                                                                                                        return 7823;
                                                                                                    }
                                                                                                    if (c == 'z') {
                                                                                                        return 7826;
                                                                                                    }
                                                                                                    return c;
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return 7826;
                                                }
                                                return 7820;
                                            }
                                            return 7804;
                                        }
                                        return 7790;
                                    }
                                    return 7776;
                                }
                                return 7766;
                            }
                            return 7750;
                        }
                        return 7734;
                    }
                    return 7730;
                }
                return 7712;
            }
            return 7690;
        }
        return 7680;
    }

    /* renamed from: i */
    public final char mo13050i(char c) {
        char c2 = c;
        if (c2 != 'A') {
            if (c2 != 'B') {
                if (c2 != 'C') {
                    if (c2 != 'D') {
                        if (c2 != 'E') {
                            if (c2 != 'F') {
                                if (c2 != 'G') {
                                    if (c2 != 'H') {
                                        if (c2 != 'I') {
                                            if (c2 != 'K') {
                                                if (c2 != 'L') {
                                                    if (c2 != 'M') {
                                                        if (c2 != 'N') {
                                                            if (c2 != 'O') {
                                                                if (c2 != 'P') {
                                                                    if (c2 != 'R') {
                                                                        if (c2 != 'S') {
                                                                            if (c2 != 'T') {
                                                                                if (c2 != 'U') {
                                                                                    if (c2 != 'W') {
                                                                                        if (c2 != 'X') {
                                                                                            if (c2 != 'Y') {
                                                                                                if (c2 != 'Z') {
                                                                                                    if (c2 != 'a') {
                                                                                                        if (c2 != 'b') {
                                                                                                            if (c2 != 'c') {
                                                                                                                if (c2 != 'd') {
                                                                                                                    if (c2 != 'e') {
                                                                                                                        if (c2 != 'f') {
                                                                                                                            if (c2 != 'g') {
                                                                                                                                if (c2 != 'h') {
                                                                                                                                    if (c2 != 'i') {
                                                                                                                                        if (c2 == 'j') {
                                                                                                                                            return 'J';
                                                                                                                                        }
                                                                                                                                        if (c2 != 'k') {
                                                                                                                                            if (c2 != 'l') {
                                                                                                                                                if (c2 != 'm') {
                                                                                                                                                    if (c2 != 'n') {
                                                                                                                                                        if (c2 != 'o') {
                                                                                                                                                            if (c2 != 'p') {
                                                                                                                                                                if (c2 == 'q') {
                                                                                                                                                                    return 'Q';
                                                                                                                                                                }
                                                                                                                                                                if (c2 != 'r') {
                                                                                                                                                                    if (c2 != 's') {
                                                                                                                                                                        if (c2 != 't') {
                                                                                                                                                                            if (c2 != 'u') {
                                                                                                                                                                                if (c2 == 'v') {
                                                                                                                                                                                    return 'V';
                                                                                                                                                                                }
                                                                                                                                                                                if (c2 != 'w') {
                                                                                                                                                                                    if (c2 != 'x') {
                                                                                                                                                                                        if (c2 != 'y') {
                                                                                                                                                                                            if (c2 == 'z') {
                                                                                                                                                                                                return 11371;
                                                                                                                                                                                            }
                                                                                                                                                                                            return c2;
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                return 11371;
                                                                                            }
                                                                                            return 590;
                                                                                        }
                                                                                        return 1278;
                                                                                    }
                                                                                    return 8361;
                                                                                }
                                                                                return 580;
                                                                            }
                                                                            return 8366;
                                                                        }
                                                                        return 8372;
                                                                    }
                                                                    return 11364;
                                                                }
                                                                return 8369;
                                                            }
                                                            return 216;
                                                        }
                                                        return 8358;
                                                    }
                                                    return 8357;
                                                }
                                                return 11360;
                                            }
                                            return 8365;
                                        }
                                        return 322;
                                    }
                                    return 11367;
                                }
                                return 8370;
                            }
                            return 8355;
                        }
                        return 582;
                    }
                    return 272;
                }
                return 8373;
            }
            return 3647;
        }
        return 8371;
    }


    public final char mo13051j(char c) {
        if (c == 'A' || c == 'a') {
            return 'A';
        }
        if (c == 'B' || c == 'b') {
            return 385;
        }
        if (c == 'C' || c == 'c') {
            return 391;
        }
        if (c == 'D' || c == 'd') {
            return 394;
        }
        if (c == 'E' || c == 'e') {
            return 1028;
        }
        if (c == 'F' || c == 'f') {
            return 401;
        }
        if (c == 'G' || c == 'g') {
            return 403;
        }
        if (c == 'H' || c == 'h') {
            return 1223;
        }
        if (c == 'I' || c == 'i') {
            return 406;
        }
        if (c == 'J' || c == 'j') {
            return 646;
        }
        if (c == 'K' || c == 'k') {
            return 408;
        }
        if (c == 'L' || c == 'l') {
            return 1340;
        }
        if (c == 'M' || c == 'm') {
            return 'M';
        }
        if (c == 'N' || c == 'n') {
            return 413;
        }
        if (c == 'O' || c == 'o') {
            return 416;
        }
        if (c == 'P' || c == 'p') {
            return 420;
        }
        if (c == 'Q' || c == 'q') {
            return 418;
        }
        if (c == 'R' || c == 'r') {
            return 422;
        }
        if (c == 'S' || c == 's') {
            return 423;
        }
        if (c == 'T' || c == 't') {
            return 428;
        }
        if (c == 'U' || c == 'u') {
            return 434;
        }
        if (c == 'V' || c == 'v') {
            return 404;
        }
        if (c == 'W' || c == 'w') {
            return 412;
        }
        if (c == 'X' || c == 'x') {
            return 1202;
        }
        if (c == 'Y' || c == 'y') {
            return 435;
        }
        if (c == 'Z' || c == 'z') {
            return 548;
        }
        return c;
    }


    public final char mo13052k(char c) {
        if (c == 'A' || c == 'a') {
            return 195;
        }
        if (c == 'B' || c == 'b') {
            return 946;
        }
        if (c == 'C' || c == 'c') {
            return 268;
        }
        if (c == 'D' || c == 'd') {
            return 270;
        }
        if (c == 'E' || c == 'e') {
            return 7864;
        }
        if (c == 'F' || c == 'f') {
            return 401;
        }
        if (c == 'G' || c == 'g') {
            return 286;
        }
        if (c == 'H' || c == 'h') {
            return 292;
        }
        if (c == 'I' || c == 'i') {
            return 302;
        }
        if (c == 'J' || c == 'j') {
            return 308;
        }
        if (c == 'K' || c == 'k') {
            return 1036;
        }
        if (c == 'L' || c == 'l') {
            return 313;
        }
        if (c == 'M' || c == 'm') {
            return 1019;
        }
        if (c == 'N' || c == 'n') {
            return 327;
        }
        if (c == 'O' || c == 'o') {
            return 7894;
        }
        if (c == 'P' || c == 'p') {
            return 420;
        }
        if (c == 'Q' || c == 'q') {
            return 490;
        }
        if (c == 'R' || c == 'r') {
            return 344;
        }
        if (c == 'S' || c == 's') {
            return 348;
        }
        if (c == 'T' || c == 't') {
            return 356;
        }
        if (c == 'U' || c == 'u') {
            return 471;
        }
        if (c == 'V' || c == 'v') {
            return 971;
        }
        if (c == 'W' || c == 'w') {
            return 372;
        }
        if (c == 'X' || c == 'x') {
            return 1046;
        }
        if (c == 'Y' || c == 'y') {
            return 1038;
        }
        if (c == 'Z' || c == 'z') {
            return 379;
        }
        return c;
    }


    public final char mo13053l(char c) {
        if (c == 'A' || c == 'a') {
            return 5034;
        }
        if (c == 'B' || c == 'b') {
            return 'b';
        }
        if (c == 'C' || c == 'c') {
            return 5087;
        }
        if (c == 'D' || c == 'd') {
            return 5024;
        }
        if (c == 'E' || c == 'e') {
            return 5036;
        }
        if (c == 'F' || c == 'f') {
            return 'f';
        }
        if (c == 'G' || c == 'g') {
            return 5046;
        }
        if (c == 'H' || c == 'h') {
            return 'h';
        }
        if (c == 'I' || c == 'i') {
            return 5029;
        }
        if (c == 'J' || c == 'j') {
            return 'j';
        }
        if (c == 'K' || c == 'k') {
            return 5094;
        }
        if (c == 'L' || c == 'l') {
            return 5086;
        }
        if (c == 'M' || c == 'm') {
            return 'm';
        }
        if (c == 'N' || c == 'n') {
            return 5057;
        }
        if (c == 'O' || c == 'o') {
            return 5054;
        }
        if (c == 'P' || c == 'p') {
            return 5090;
        }
        if (c == 'Q' || c == 'q') {
            return 'q';
        }
        if (c == 'R' || c == 'r') {
            return 5074;
        }
        if (c == 'S' || c == 's') {
            return 's';
        }
        if (c == 'T' || c == 't') {
            return 5062;
        }
        if (c == 'U' || c == 'u') {
            return 'u';
        }
        if (c == 'V' || c == 'v') {
            return 5065;
        }
        if (c == 'W' || c == 'w') {
            return 5043;
        }
        if (c == 'X' || c == 'x') {
            return 'x';
        }
        if (c == 'Y' || c == 'y') {
            return 5053;
        }
        if (c == 'Z' || c == 'z') {
            return 5059;
        }
        return c;
    }


   /* public void m(final C4382a a, int i) {
        MagicItem c = (MagicItem) arrayList.get(i);
        a.titleTV.setText((CharSequence)c.mo11310a());
        final StringBuilder sb = new StringBuilder(c.mo11311b());
        final int n = 0;
        final int n2 = 0;
        final int n3 = 0;
        final int n4 = 0;
        final int n5 = 0;
        final int n6 = 0;
        final int n7 = 0;
        final int n8 = 0;
        final int n9 = 0;
        final int n10 = 0;
        final int n11 = 0;
        final int n12 = 0;
        final int n13 = 0;
        final int n14 = 0;
        final int n15 = 0;
        final int n16 = 0;
        final int n17 = 0;
        final int n18 = 0;
        final int n19 = 0;
        final int n20 = 0;
        final int n21 = 0;
        final int n22 = 0;
        final int n23 = 0;
        final int n24 = 0;
        final int n25 = 0;
        final int n26 = 0;
        final int n27 = 0;
        final int n28 = 0;
        final int n29 = 0;
        final int n30 = 0;
        final int n31 = 0;
        final int n32 = 0;
        final int n33 = 0;
        final int n34 = 0;
        final int n35 = 0;
        final int n36 = 0;
        final int n37 = 0;
        final int n38 = 0;
        final int n39 = 0;
        final int n40 = 0;
        final int n41 = 0;
        final int n42 = 0;
        final int n43 = 0;
        final int n44 = 0;
        final int n45 = 0;
        final int n46 = 0;
        final int n47 = 0;
        final int n48 = 0;
        final int n49 = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int n50 = 0;
        int n51 = 0;
        int n52 = 0;
        int n53 = 0;
        final int n54 = 0;
        switch (i) {
            case 31:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n54; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains( "♓")) {
                    for (j = n; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 30:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n2; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("♓")) {
                    for (j = n3; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '♓');
                            }
                        }
                    }
                    break;
                }
                break;
            case 29:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n4; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("♑")) {
                    for (j = n5; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 28:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n6; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n7; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 27:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n8; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n9; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 26:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n10; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n11; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 25:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n12; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n13; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 24:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n14; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n15; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 23:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n16; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n17; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 22:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n18; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n19; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 21:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n20; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n21; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 20:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n22; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n23; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 19:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n24; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n25; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 18:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n26; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n27; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 17:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n28; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n29; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 16:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n30; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n31; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 15:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n32; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n33; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 14:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n34; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n35; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 13:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n36; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n37; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 12:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n38; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n39; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 11:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n40; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n41; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 10:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n42; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n43; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 9:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n44; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n45; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 8:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n46; j < sb.length(); j = i + 1) {
                        if ((i = j) == 0) {
                            sb.insert(j, '?');
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                ++i;
                                sb.insert(i, '?');
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                        i = j;
                        if (sb.charAt(j) == ' ') {
                            i = j - 1;
                            sb.deleteCharAt(i);
                            ++i;
                            sb.insert(i, '?');
                        }
                        if (sb.length() - 1 == i) {
                            sb.deleteCharAt(i);
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?") && !c.mo11311b().contains("?")) {
                    for (i = n47; i <= sb.length() - 1; i = j + 1) {
                        if ((j = i) == 0) {
                            sb.insert(i, '?');
                            j = i + 1;
                        }
                        if (sb.length() - 1 != j && sb.charAt(j) != ' ') {
                            i = j + 1;
                            sb.insert(i, '?');
                            ++i;
                            sb.insert(i, '?');
                        }
                        else if (sb.length() - 1 == (i = j)) {
                            i = j;
                            if (sb.charAt(j) != ' ') {
                                i = j + 1;
                                sb.insert(i, '?');
                            }
                        }
                        if (sb.charAt(i) == ' ' && i != 0) {
                            --i;
                            sb.deleteCharAt(i);
                            j = i + 1;
                            sb.insert(j, '?');
                        }
                        else if (sb.length() - 1 == (j = i)) {
                            j = i;
                            if (sb.charAt(i) == ' ' && (j = i) != 0) {
                                j = i + 1;
                                sb.deleteCharAt(j);
                            }
                        }
                    }
                    break;
                }
                break;
            case 7:
                if ("Preview text".equals(c.mo11311b())) {
                    for (j = n48; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                if (!c.mo11311b().contains("?")) {
                    for (j = n49; j < sb.length(); ++j) {
                        i = j;
                        if (sb.charAt(j) == ' ' && sb.length() - 1 != (i = j)) {
                            i = j + 1;
                        }
                        if (sb.length() != (j = i)) {
                            j = i;
                            if (sb.charAt(i) != ' ') {
                                j = i + 1;
                                sb.insert(j, '?');
                            }
                        }
                    }
                    break;
                }
                break;
            case 6:
                while (j <= sb.length() - 1) {
                    sb.setCharAt(j, this.mo13051j(sb.charAt(j)));
                    ++j;
                }
                break;
            case 5:
                while (k <= sb.length() - 1) {
                    sb.setCharAt(k, this.mo13052k(sb.charAt(k)));
                    ++k;
                }
                break;
            case 4:
                while (l <= sb.length() - 1) {
                    sb.setCharAt(l, this.mo13047f(sb.charAt(l)));
                    ++l;
                }
                break;
            case 3:
                while (n50 <= sb.length() - 1) {
                    sb.setCharAt(n50, this.mo13049h(sb.charAt(n50)));
                    ++n50;
                }
                break;
            case 2:
                while (n51 <= sb.length() - 1) {
                    sb.setCharAt(n51, this.mo13053l(sb.charAt(n51)));
                    ++n51;
                }
                break;
            case 1:
                while (n52 <= sb.length() - 1) {
                    sb.setCharAt(n52, this.mo13050i(sb.charAt(n52)));
                    ++n52;
                }
                break;
            case 0:
                while (n53 <= sb.length() - 1) {
                    sb.setCharAt(n53, this.mo13048g(sb.charAt(n53)));
                    ++n53;
                }
                break;
        }
        c.mo11312c(sb.toString());
        a.descriptionTV.setText((CharSequence)c.mo11311b());

        ((FrameLayout)a.card_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = activity;
                ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                String charSequence = a.descriptionTV.getText().toString();
                Toast.makeText(activity, "Copied to clipboard! Your copied text is " + charSequence, Toast.LENGTH_LONG).show();
                ClipData newPlainText = ClipData.newPlainText("simple text", charSequence);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(newPlainText);
                    return;
                }
            }
        });
    }*/


    public void m(ViewHolder1 var1, int var2) {
        MagicItem var60;
        StringBuilder var61;
        var60 = (MagicItem) arrayList.get(var2);;
        var1.titleTV.setText((CharSequence)var60.getTitleText());
        
        var61 =  new StringBuilder(var60.getPreview());
        byte var33 = 0;
        byte var34 = 0;
        byte var35 = 0;
        byte var37 = 0;
        byte var39 = 0;
        byte var54 = 0;
        byte var23 = 0;
        byte var40 = 0;
        byte var36 = 0;
        byte var49 = 0;
        byte var14 = 0;
        byte var59 = 0;
        byte var43 = 0;
        byte var48 = 0;
        byte var21 = 0;
        byte var55 = 0;
        byte var28 = 0;
        byte var52 = 0;
        byte var41 = 0;
        byte var47 = 0;
        byte var45 = 0;
        byte var46 = 0;
        byte var31 = 0;
        byte var51 = 0;
        byte var24 = 0;
        byte var11 = 0;
        byte var26 = 0;
        byte var17 = 0;
        byte var38 = 0;
        byte var44 = 0;
        byte var30 = 0;
        byte var57 = 0;
        byte var29 = 0;
        byte var10 = 0;
        byte var53 = 0;
        byte var18 = 0;
        byte var25 = 0;
        byte var50 = 0;
        byte var58 = 0;
        byte var12 = 0;
        byte var32 = 0;
        byte var15 = 0;
        byte var13 = 0;
        byte var56 = 0;
        byte var19 = 0;
        byte var42 = 0;
        byte var20 = 0;
        byte var27 = 0;
        byte var16 = 0;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        byte var22 = 0;
        label1171:
        switch (var2) {
            case 0:
                while(true) {
                    if (var9 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var9, this.mo13048g(var61.charAt(var9)));
                    ++var9;
                }
            case 1:
                while(true) {
                    if (var8 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var8, this.mo13050i(var61.charAt(var8)));
                    ++var8;
                }
            case 2:
                while(true) {
                    if (var7 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var7, mo13053l(var61.charAt(var7)));
                    ++var7;
                }
            case 3:
                while(true) {
                    if (var6 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var6, this.mo13049h(var61.charAt(var6)));
                    ++var6;
                }
            case 4:
                while(true) {
                    if (var5 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var5, this.mo13047f(var61.charAt(var5)));
                    ++var5;
                }
            case 5:
                while(true) {
                    if (var4 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var4, this.mo13052k(var61.charAt(var4)));
                    ++var4;
                }
            case 6:
                while(true) {
                    if (var3 > var61.length() - 1) {
                        break label1171;
                    }

                    var61.setCharAt(var3, this.mo13051j(var61.charAt(var3)));
                    ++var3;
                }
            case 7:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var27;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♥');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♥")) {
                        for(var3 = var16; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♥');
                                }
                            }
                        }
                    }
                    break;
                }
            case 8:
                if ("Preview text".equals(var60.getPreview())) {
                    var2 = var42;

                    while(true) {
                        if (var2 >= var61.length()) {
                            break label1171;
                        }

                        var3 = var2;
                        if (var2 == 0) {
                            var61.insert(var2, '【');
                            var3 = var2 + 1;
                        }

                        var2 = var3;
                        if (var61.length() != var3) {
                            var2 = var3;
                            if (var61.charAt(var3) != ' ') {
                                var2 = var3 + 1;
                                var61.insert(var2, '】');
                                ++var2;
                                var61.insert(var2, '【');
                            }
                        }

                        var3 = var2;
                        if (var61.charAt(var2) == ' ') {
                            --var2;
                            var61.deleteCharAt(var2);
                            var3 = var2 + 1;
                            var61.insert(var3, '【');
                        }

                        if (var61.length() - 1 == var3) {
                            var61.deleteCharAt(var3);
                        }

                        var2 = var3 + 1;
                    }
                } else {
                    if (!var60.getPreview().contains("【") && !var60.getPreview().contains("】")) {
                        for(var2 = var20; var2 <= var61.length() - 1; var2 = var3 + 1) {
                            var3 = var2;
                            if (var2 == 0) {
                                var61.insert(var2, '【');
                                var3 = var2 + 1;
                            }

                            if (var61.length() - 1 != var3 && var61.charAt(var3) != ' ') {
                                var2 = var3 + 1;
                                var61.insert(var2, '】');
                                ++var2;
                                var61.insert(var2, '【');
                            } else {
                                var2 = var3;
                                if (var61.length() - 1 == var3) {
                                    var2 = var3;
                                    if (var61.charAt(var3) != ' ') {
                                        var2 = var3 + 1;
                                        var61.insert(var2, '】');
                                    }
                                }
                            }

                            if (var61.charAt(var2) == ' ' && var2 != 0) {
                                --var2;
                                var61.deleteCharAt(var2);
                                var3 = var2 + 1;
                                var61.insert(var3, '【');
                            } else {
                                var3 = var2;
                                if (var61.length() - 1 == var2) {
                                    var3 = var2;
                                    if (var61.charAt(var2) == ' ') {
                                        var3 = var2;
                                        if (var2 != 0) {
                                            var3 = var2 + 1;
                                            var61.deleteCharAt(var3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            case 9:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var56;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '☆');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("☆")) {
                        for(var3 = var19; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '☆');
                                }
                            }
                        }
                    }
                    break;
                }
            case 10:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var15;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '❦');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("❦")) {
                        for(var3 = var13; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '❦');
                                }
                            }
                        }
                    }
                    break;
                }
            case 11:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var12;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '❄');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("❄")) {
                        for(var3 = var32; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '❄');
                                }
                            }
                        }
                    }
                    break;
                }
            case 12:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var50;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '✾');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("✾")) {
                        for(var3 = var58; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '✾');
                                }
                            }
                        }
                    }
                    break;
                }
            case 13:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var18;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '☀');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("☀")) {
                        for(var3 = var25; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '☀');
                                }
                            }
                        }
                    }
                    break;
                }
            case 14:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var10;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '☃');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("☃")) {
                        for(var3 = var53; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '☃');
                                }
                            }
                        }
                    }
                    break;
                }
            case 15:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var57;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '❤');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("❤")) {
                        for(var3 = var29; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '❤');
                                }
                            }
                        }
                    }
                    break;
                }
            case 16:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var44;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '☘');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("☘")) {
                        for(var3 = var30; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '☘');
                                }
                            }
                        }
                    }
                    break;
                }
            case 17:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var17;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '☕');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("☕")) {
                        for(var3 = var38; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '☕');
                                }
                            }
                        }
                    }
                    break;
                }
            case 18:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var11;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '☝');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("☝")) {
                        for(var3 = var26; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '☝');
                                }
                            }
                        }
                    }
                    break;
                }
            case 19:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var51;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '❁');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("❁")) {
                        for(var3 = var24; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '❁');
                                }
                            }
                        }
                    }
                    break;
                }
            case 20:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var46;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♈');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♈")) {
                        for(var3 = var31; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♈');
                                }
                            }
                        }
                    }
                    break;
                }
            case 21:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var47;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♉');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♉")) {
                        for(var3 = var45; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♉');
                                }
                            }
                        }
                    }
                    break;
                }
            case 22:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var52;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♊');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♊")) {
                        for(var3 = var41; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♊');
                                }
                            }
                        }
                    }
                    break;
                }
            case 23:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var55;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♋');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♋")) {
                        for(var3 = var28; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♋');
                                }
                            }
                        }
                    }
                    break;
                }
            case 24:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var48;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♌');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♌")) {
                        for(var3 = var21; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♌');
                                }
                            }
                        }
                    }
                    break;
                }
            case 25:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var59;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♍');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♍")) {
                        for(var3 = var43; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♍');
                                }
                            }
                        }
                    }
                    break;
                }
            case 26:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var49;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♎');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♎")) {
                        for(var3 = var14; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♎');
                                }
                            }
                        }
                    }
                    break;
                }
            case 27:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var40;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♏');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♏")) {
                        for(var3 = var36; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♏');
                                }
                            }
                        }
                    }
                    break;
                }
            case 28:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var54;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♐');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♐")) {
                        for(var3 = var23; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♐');
                                }
                            }
                        }
                    }
                    break;
                }
            case 29:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var37;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♑');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♑")) {
                        for(var3 = var39; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♑');
                                }
                            }
                        }
                    }
                    break;
                }
            case 30:
                if ("Preview text".equals(var60.getPreview())) {
                    var3 = var34;

                    while(true) {
                        if (var3 >= var61.length()) {
                            break label1171;
                        }

                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♒');
                            }
                        }

                        ++var3;
                    }
                } else {
                    if (!var60.getPreview().contains("♒")) {
                        for(var3 = var35; var3 < var61.length(); ++var3) {
                            var2 = var3;
                            if (var61.charAt(var3) == ' ') {
                                var2 = var3;
                                if (var61.length() - 1 != var3) {
                                    var2 = var3 + 1;
                                }
                            }

                            var3 = var2;
                            if (var61.length() != var2) {
                                var3 = var2;
                                if (var61.charAt(var2) != ' ') {
                                    var3 = var2 + 1;
                                    var61.insert(var3, '♒');
                                }
                            }
                        }
                    }
                    break;
                }
            case 31:
                if ("Preview text".equals(var60.getPreview())) {
                    for(var3 = var22; var3 < var61.length(); ++var3) {
                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {

                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♓');
                            }
                        }
                    }
                } else if (!var60.getPreview().contains("♓")) {
                    for(var3 = var33; var3 < var61.length(); ++var3) {
                        var2 = var3;
                        if (var61.charAt(var3) == ' ') {
                            var2 = var3;
                            if (var61.length() - 1 != var3) {
                                var2 = var3 + 1;
                            }
                        }

                        var3 = var2;
                        if (var61.length() != var2) {
                            var3 = var2;
                            if (var61.charAt(var2) != ' ') {
                                var3 = var2 + 1;
                                var61.insert(var3, '♓');
                            }
                        }
                    }
                }
        }

        var60.setPreview(var61.toString());
        var1.descriptionTV.setText(var60.getPreview());
        var1.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = FontsAdepter.this.activity;
                ClipboardManager clipboardManager = (ClipboardManager) FontsAdepter.this.activity.getSystemService(Context.CLIPBOARD_SERVICE);
                String charSequence = var1.descriptionTV.getText().toString();
                Toast.makeText(activity, "Copied to clipboard! Your copied text is " + charSequence, Toast.LENGTH_LONG).show();
                ClipData newPlainText = ClipData.newPlainText("simple text", charSequence);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(newPlainText);
                    return;
                }
            }
        });
    }


}
