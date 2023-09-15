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


public class TextDecorationAdepeter extends RecyclerView.Adapter<TextDecorationAdepeter.ViewHolder1> {

    public TextDecorationAdepeter(ArrayList arrayList, Activity activity) {

        this.arrayList = arrayList;
        this.activity = activity;
    }


    public final ArrayList arrayList;


    public final Activity activity;

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder1( LayoutInflater.from(this.activity).inflate(R.layout.adapter_decoration, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {


        d(holder,position);

    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }


    public class ViewHolder1 extends RecyclerView.ViewHolder {


        public TextView title_text_view_DF;


        public TextView description_text_view_DF;


        public LinearLayout card_view_DF;

        public ViewHolder1(View view) {
            super(view);
            this.title_text_view_DF = (TextView) view.findViewById(R.id.title_text_view_DF);
            this.description_text_view_DF = (TextView) view.findViewById(R.id.description_text_view_DF);
            this.card_view_DF = (LinearLayout) view.findViewById(R.id.card_view_DF);
        }
    }

    public void d(ViewHolder1 var1, int var2) {
        ViewHolder1 var4;
        StringBuilder var5;
        MagicItem var6;
        label962: {
            String var8;
            label963: {
                label826: {
                    label825: {
                        var4 = var1;
                        var6 = (MagicItem) arrayList.get(var2);
                        var4.title_text_view_DF.setText(var6.getTitleText());
                       
                        var5 = new StringBuilder(var6.getPreview());
                        var8 = "❤╰། ◉ ◯ ◉ །╯❤";
                        boolean var3;
                        String var7;
                        switch (var2) {
                            case 0:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "★·.·´¯`·.·★";
                                if (!var3 && var6.getPreview().contains("★·.·´¯`·.·★")) {
                                    break label962;
                                }
                                break label826;
                            case 1:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✦͙͙͙*͙*❥⃝∗⁎.ʚ")) {
                                    break label962;
                                }

                                var5.insert(0, "✦͙͙͙*͙*❥⃝∗⁎.ʚ");
                                var2 = var5.length();
                                var8 = "ɞ.⁎∗❥⃝**͙✦͙͙͙";
                                break label963;
                            case 2:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "▁ ▂ ▄ ▅ ▆ ▇ █ ";
                                } else {
                                    if (var6.getPreview().contains("█ ▇ ▆ ▅ ▄ ▂ ▁")) {
                                        break label962;
                                    }

                                    var8 = "▁ ▂ ▄ ▅ ▆ ▇ █";
                                }

                                var5.insert(0, var8);
                                var2 = var5.length();
                                var8 = "█ ▇ ▆ ▅ ▄ ▂ ▁";
                                break label963;
                            case 3:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("¸¸♬·¯·♩¸¸♪·¯·♫¸¸")) {
                                    break label962;
                                }

                                var5.insert(0, "¸¸♬·¯·♩¸¸♪·¯·♫¸¸");
                                var2 = var5.length();
                                var8 = "¸¸♫·¯·♪¸¸♩·¯·♬¸¸";
                                break label963;
                            case 4:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "{♥‿♥}";
                                if (!var3 && var6.getPreview().contains("{♥‿♥}")) {
                                    break label962;
                                }
                                break label826;
                            case 5:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "✿◕ ‿ ◕✿";
                                if (!var3 && var6.getPreview().contains("✿◕ ‿ ◕✿")) {
                                    break label962;
                                }
                                break label826;
                            case 6:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("웃❤유")) {
                                    break label962;
                                }

                                var5.insert(0, "웃❤유");
                                var2 = var5.length();
                                var8 = "유❤웃";
                                break label963;
                            case 7:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✿.｡.:* ☆:**:.")) {
                                    break label962;
                                }

                                var5.insert(0, "✿.｡.:* ☆:**:.");
                                var2 = var5.length();
                                var8 = ".:**:.☆*.:｡.✿";
                                break label963;
                            case 8:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("¤¸¸.•´¯`•¸¸.•..>>")) {
                                    break label962;
                                }

                                var5.insert(0, "¤¸¸.•´¯`•¸¸.•..>>");
                                var2 = var5.length();
                                var8 = "<<..•.¸¸•´¯`•.¸¸¤";
                                break label963;
                            case 9:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("◦•●◉✿")) {
                                    break label962;
                                }

                                var5.insert(0, "◦•●◉✿");
                                var2 = var5.length();
                                var8 = "✿◉●•◦";
                                break label963;
                            case 10:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("▀▄▀▄▀▄")) {
                                    break label962;
                                }

                                var5.insert(0, "▀▄▀▄▀▄");
                                var2 = var5.length();
                                var8 = "▄▀▄▀▄▀";
                                break label963;
                            case 11:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains(".•°¤*(¯`★´¯)*¤°")) {
                                    break label962;
                                }
                                break;
                            case 12:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "☆(❁‿❁)☆";
                                if (!var3 && var6.getPreview().contains("☆(❁‿❁)☆")) {
                                    break label962;
                                }
                                break label826;
                            case 13:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("ღ(¯`◕‿◕´¯) ♫ ♪ ♫")) {
                                    break label962;
                                }

                                var5.insert(0, "ღ(¯`◕‿◕´¯) ♫ ♪ ♫");
                                var2 = var5.length();
                                var8 = "♫ ♪ ♫ (¯`◕‿◕´¯)ღ";
                                break label963;
                            case 14:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("«-(¯`v´¯)-«")) {
                                    break label962;
                                }

                                var5.insert(0, "«-(¯`v´¯)-«");
                                var2 = var5.length();
                                var8 = "»-(¯`v´¯)-»";
                                break label963;
                            case 15:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "=。:.ﾟ(●ö◡ö●):.｡+ﾟ";
                                if (!var3 && var6.getPreview().contains("=。:.ﾟ(●ö◡ö●):.｡+ﾟ")) {
                                    break label962;
                                }
                                break label826;
                            case 16:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "❤(｡◕‿◕｡)❤";
                                if (!var3 && var6.getPreview().contains("❤(｡◕‿◕｡)❤")) {
                                    break label962;
                                }
                                break label826;
                            case 17:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("ლ❣☆(❁‿❁)")) {
                                    break label962;
                                }

                                var5.insert(0, "ლ❣☆(❁‿❁)");
                                var2 = var5.length();
                                var8 = "(❁‿❁)☆❣ლ";
                                break label963;
                            case 18:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("๑۞๑,¸¸,ø¤º°`°๑۩")) {
                                    break label962;
                                }

                                var5.insert(0, "๑۞๑,¸¸,ø¤º°`°๑۩");
                                var2 = var5.length();
                                var8 = "๑۩ ,¸¸,ø¤º°`°๑۞๑";
                                break label963;
                            case 19:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("*:;,．★ ～☆・:.,;*")) {
                                    break label962;
                                }

                                var5.insert(0, "*:;,．★ ～☆・:.,;*");
                                var2 = var5.length();
                                var8 = "*:;,．☆ ～★・:.,;*";
                                break label963;
                            case 20:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "╚»★«╝";
                                if (!var3 && var6.getPreview().contains("╚»★«╝")) {
                                    break label962;
                                }
                                break label826;
                            case 21:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("➶➶➶➶➶")) {
                                    break label962;
                                }

                                var5.insert(0, "➶➶➶➶➶");
                                var2 = var5.length();
                                var8 = "➷➷➷➷➷";
                                break label963;
                            case 22:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "(｡◕‿‿◕｡)";
                                if (!var3 && var6.getPreview().contains("(｡◕‿‿◕｡)")) {
                                    break label962;
                                }
                                break label826;
                            case 23:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("`•.¸¸.•´´¯`••._.•")) {
                                    break label962;
                                }

                                var5.insert(0, "`•.¸¸.•´´¯`••._.•");
                                var2 = var5.length();
                                var8 = "•._.••`¯´´•.¸¸.•`";
                                break label963;
                            case 24:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☮▁▂▃▄☾ ♛")) {
                                    break label962;
                                }

                                var5.insert(0, "☮▁▂▃▄☾ ♛");
                                var2 = var5.length();
                                var8 = "♛ ☽▄▃▂▁☮";
                                break label963;
                            case 25:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("¸,ø¤º°`°º¤ø,¸¸,ø¤º°")) {
                                    break label962;
                                }

                                var5.insert(0, "¸,ø¤º°`°º¤ø,¸¸,ø¤º°");
                                var2 = var5.length();
                                var8 = "°º¤ø,¸¸,ø¤º°`°º¤ø,¸";
                                break label963;
                            case 26:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("╰☆☆")) {
                                    break label962;
                                }

                                var5.insert(0, "╰☆☆");
                                var2 = var5.length();
                                var8 = "☆☆╮";
                                break label963;
                            case 27:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("]|I{•------»")) {
                                    break label962;
                                }

                                var5.insert(0, "]|I{•------»");
                                var2 = var5.length();
                                var8 = "«------•}I|[";
                                break label963;
                            case 28:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "(ღ˘⌣˘ღ)";
                                if (!var3 && var6.getPreview().contains("(ღ˘⌣˘ღ)")) {
                                    break label962;
                                }
                                break label826;
                            case 29:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("(¯`·.¸¸.·´¯`·.¸¸.->")) {
                                    break label962;
                                }

                                var5.insert(0, "(¯`·.¸¸.·´¯`·.¸¸.->");
                                var2 = var5.length();
                                var8 = "<-.¸¸.·´¯`·.¸¸.·´¯)";
                                break label963;
                            case 30:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("↤↤❤↤↤")) {
                                    break label962;
                                }

                                var5.insert(0, "↤↤❤↤↤");
                                var2 = var5.length();
                                var8 = "↦↦❤↦↦";
                                break label963;
                            case 31:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("↫↫↫↫↫")) {
                                    break label962;
                                }

                                var5.insert(0, "↫↫↫↫↫");
                                var2 = var5.length();
                                var8 = "↬↬↬↬↬";
                                break label963;
                            case 32:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "【｡_｡】";
                                if (!var3 && var6.getPreview().contains("【｡_｡】")) {
                                    break label962;
                                }
                                break label826;
                            case 33:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("░▒▓█►─═")) {
                                    break label962;
                                }

                                var5.insert(0, "░▒▓█►─═");
                                var2 = var5.length();
                                var8 = "═─◄█▓▒░";
                                break label963;
                            case 34:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "|!¤*'~``~'*¤!|";
                                if (!var3 && var6.getPreview().contains("|!¤*'~``~'*¤!|")) {
                                    break label962;
                                }
                                break label826;
                            case 35:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "._|.<(+_+)>.|_.";
                                if (!var3 && var6.getPreview().contains("._|.<(+_+)>.|_.")) {
                                    break label962;
                                }
                                break label826;
                            case 36:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "❤(❁´◡`❁)❤";
                                if (!var3 && var6.getPreview().contains("❤(❁´◡`❁)❤")) {
                                    break label962;
                                }
                                break label826;
                            case 37:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("-漫~*'¨¯¨'*·舞~")) {
                                    break label962;
                                }

                                var5.insert(0, "-漫~*'¨¯¨'*·舞~");
                                var2 = var5.length();
                                var8 = "~舞*'¨¯¨'*·~漫-";
                                break label963;
                            case 38:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains(".•°¤*(¯`★´¯)*¤°")) {
                                    break label962;
                                }
                                break;
                            case 39:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "⊂◉‿◉つ";
                                if (!var3 && var6.getPreview().contains("⊂◉‿◉つ")) {
                                    break label962;
                                }
                                break label826;
                            case 40:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("●▬▬▬▬๑۩")) {
                                    break label962;
                                }

                                var5.insert(0, "●▬▬▬▬๑۩");
                                var2 = var5.length();
                                var8 = "۩๑▬▬▬▬▬●";
                                break label963;
                            case 41:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "╚═| ~ ಠ ₒ ಠ ~ |═╝";
                                if (!var3 && var6.getPreview().contains("╚═| ~ ಠ ₒ ಠ ~ |═╝")) {
                                    break label962;
                                }
                                break label826;
                            case 42:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✿◡‿◡")) {
                                    break label962;
                                }

                                var5.insert(0, "✿◡‿◡");
                                var2 = var5.length();
                                var8 = "◡‿◡✿";
                                break label963;
                            case 43:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "<(▰˘◡˘▰)>";
                                if (!var3 && var6.getPreview().contains("<(▰˘◡˘▰)>")) {
                                    break label962;
                                }
                                break label826;
                            case 44:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "ლ༼ ▀̿ Ĺ̯ ▀̿ ლ༽";
                                if (!var3 && var6.getPreview().contains("ლ༼ ▀̿ Ĺ̯ ▀̿ ლ༽")) {
                                    break label962;
                                }
                                break label826;
                            case 45:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "乁། ˵ ◕ – ◕ ˵ །ㄏ";
                                if (!var3 && var6.getPreview().contains("乁། ˵ ◕ – ◕ ˵ །ㄏ")) {
                                    break label962;
                                }
                                break label826;
                            case 46:
                                var3 = "Preview text".equals(var6.getPreview());
                                var8 = "❤☆(◒‿◒)☆❤";
                                if (!var3 && var6.getPreview().contains("❤☆(◒‿◒)☆❤")) {
                                    break label962;
                                }
                                break label826;
                            case 47:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("╰། ◉ ◯ ◉ །╯")) {
                                    break label962;
                                }

                                var5.insert(0, "╰། ◉ ◯ ◉ །╯");
                                var2 = var5.length();
                                var8 = "╰། ◉ ◯ ◉ །╯";
                                break label963;
                            case 48:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "⋋⁞ ◔ ﹏ ◔ ⁞⋌";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "⋋⁞ ◔ ﹏ ◔ ⁞⋌";
                                    if (var7.contains("⋋⁞ ◔ ﹏ ◔ ⁞⋌")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 49:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("ᕕ༼✪ل͜✪༽ᕗ")) {
                                    break label962;
                                }

                                var5.insert(0, "ᕕ༼✪ل͜✪༽ᕗ");
                                var2 = var5.length();
                                var8 = "ᕗ༼✪ل͜✪༽ᕕ";
                                break label963;
                            case 50:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("(Ɔ ˘⌣˘)♥")) {
                                    break label962;
                                }

                                var5.insert(0, "(Ɔ ˘⌣˘)♥");
                                var2 = var5.length();
                                var8 = "♥(˘⌣˘ C)";
                                break label963;
                            case 51:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("*•.¸♡")) {
                                    break label962;
                                }

                                var5.insert(0, "*•.¸♡");
                                var2 = var5.length();
                                var8 = "♡¸.•*";
                                break label963;
                            case 52:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "(❁´◡`❁)";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "(❁´◡`❁)";
                                    if (var7.contains("(❁´◡`❁)")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 53:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "☜♡☞";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "☜♡☞";
                                    if (var7.contains("☜♡☞")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 54:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "-`ღ´--`ღ´-";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "-`ღ´--`ღ´-";
                                    if (var7.contains("-`ღ´--`ღ´-")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 55:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "♡＾▽＾♡";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "♡＾▽＾♡";
                                    if (var7.contains("♡＾▽＾♡")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 56:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "(。♡‿♡。)";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "(。♡‿♡。)";
                                    if (var7.contains("(。♡‿♡。)")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 57:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "♥‿♥";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "♥‿♥";
                                    if (var7.contains("♥‿♥")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 58:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "♥╣[-_-]╠♥";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "♥╣[-_-]╠♥";
                                    if (var7.contains("♥╣[-_-]╠♥")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 59:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "(✿◠‿◠✿)";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "(✿◠‿◠✿)";
                                    if (var7.contains("(✿◠‿◠✿)")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 60:
                                if ("Preview text".equals(var6.getPreview())) {
                                    var8 = "❣♥(｡◕‿◕｡)♥❣";
                                } else {
                                    var7 = var6.getPreview();
                                    var8 = "❣♥(｡◕‿◕｡)♥❣";
                                    if (var7.contains("❣♥(｡◕‿◕｡)♥❣")) {
                                        break label962;
                                    }
                                }
                                break label825;
                            case 61:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("ლ❣☆(❁‿❁)☆❣ლ")) {
                                    break label962;
                                }

                                var5.insert(0, "ლ❣☆(❁‿❁)☆❣ლ");
                                var2 = var5.length();
                                var8 = "ლ❣☆(❁‿❁)☆❣ლ";
                                break label963;
                            case 62:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✿❀❁(◠‿◠)❁❀✿")) {
                                    break label962;
                                }

                                var5.insert(0, "✿❀❁(◠‿◠)❁❀✿");
                                var2 = var5.length();
                                var8 = "✿❀❁(◠‿◠)❁❀✿";
                                break label963;
                            case 63:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✌✌(•ิ‿•ิ)✌✌")) {
                                    break label962;
                                }

                                var5.insert(0, "✌✌(•ิ‿•ิ)✌✌");
                                var2 = var5.length();
                                var8 = "✌✌(•ิ‿•ิ)✌✌";
                                break label963;
                            case 64:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❆❆≧◠‿◠≦❆❆")) {
                                    break label962;
                                }

                                var5.insert(0, "❆❆≧◠‿◠≦❆❆");
                                var2 = var5.length();
                                var8 = "❆❆≧◠‿◠≦❆❆";
                                break label963;
                            case 65:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❤❣♥‿♥❣❤")) {
                                    break label962;
                                }

                                var5.insert(0, "❤❣♥‿♥❣❤");
                                var2 = var5.length();
                                var8 = "❤❣♥‿♥❣❤";
                                break label963;
                            case 66:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("【★】【★】")) {
                                    break label962;
                                }

                                var5.insert(0, "【★】【★】");
                                var2 = var5.length();
                                var8 = "【★】【★】";
                                break label963;
                            case 67:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☜☆☞")) {
                                    break label962;
                                }

                                var5.insert(0, "☜☆☞");
                                var2 = var5.length();
                                var8 = "☜☆☞";
                                break label963;
                            case 68:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("●♡▬▬♡")) {
                                    break label962;
                                }

                                var5.insert(0, "●♡▬▬♡");
                                var2 = var5.length();
                                var8 = "♡▬▬♡●";
                                break label963;
                            case 69:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❣❤---» [")) {
                                    break label962;
                                }

                                var5.insert(0, "❣❤---» [");
                                var2 = var5.length();
                                var8 = "] «---❤❣";
                                break label963;
                            case 70:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("(▰˘◡˘▰)")) {
                                    break label962;
                                }

                                var5.insert(0, "(▰˘◡˘▰)");
                                var2 = var5.length();
                                var8 = "(▰˘◡˘▰)";
                                break label963;
                            case 71:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☀(ღ˘⌣˘ღ)☀")) {
                                    break label962;
                                }

                                var5.insert(0, "☀(ღ˘⌣˘ღ)☀");
                                var2 = var5.length();
                                var8 = "☀(ღ˘⌣˘ღ)☀";
                                break label963;
                            case 72:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☀❤◕ ‿ ◕❤☀")) {
                                    break label962;
                                }

                                var5.insert(0, "☀❤◕ ‿ ◕❤☀");
                                var2 = var5.length();
                                var8 = "☀❤◕ ‿ ◕❤☀";
                                break label963;
                            case 73:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❤╰། ◉ ◯ ◉ །╯❤")) {
                                    break label962;
                                }
                                break label826;
                            case 74:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❄♥‿♥❄")) {
                                    break label962;
                                }

                                var5.insert(0, "❄♥‿♥❄");
                                var2 = var5.length();
                                var8 = "❄♥‿♥❄";
                                break label963;
                            case 75:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☀☀｡◕‿‿◕｡☀☀")) {
                                    break label962;
                                }

                                var5.insert(0, "☀☀｡◕‿‿◕｡☀☀");
                                var2 = var5.length();
                                var8 = "☀☀｡◕‿‿◕｡☀☀";
                                break label963;
                            case 76:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("↫❣(◕ω◕)❣↬")) {
                                    break label962;
                                }

                                var5.insert(0, "↫❣(◕ω◕)❣↬");
                                var2 = var5.length();
                                var8 = "↫❣(◕ω◕)❣↬";
                                break label963;
                            case 77:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☜☆☞(◠‿◠)")) {
                                    break label962;
                                }

                                var5.insert(0, "☜☆☞(◠‿◠)");
                                var2 = var5.length();
                                var8 = "(◠‿◠)☜☆☞";
                                break label963;
                            case 78:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☀웃❤유☀")) {
                                    break label962;
                                }

                                var5.insert(0, "☀웃❤유☀");
                                var2 = var5.length();
                                var8 = "☀웃❤유☀";
                                break label963;
                            case 79:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❤ᶫᵒᵛᵉᵧₒᵤ❤")) {
                                    break label962;
                                }

                                var5.insert(0, "❤ᶫᵒᵛᵉᵧₒᵤ❤");
                                var2 = var5.length();
                                var8 = "❤ᶫᵒᵛᵉᵧₒᵤ❤";
                                break label963;
                            case 80:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❤ℐɕℎ ℒ℩ℯɓℯ ɗ℩ɕℎ❤")) {
                                    break label962;
                                }

                                var5.insert(0, "❤ℐɕℎ ℒ℩ℯɓℯ ɗ℩ɕℎ❤");
                                var2 = var5.length();
                                var8 = "❤ℐɕℎ ℒ℩ℯɓℯ ɗ℩ɕℎ❤";
                                break label963;
                            case 81:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("➳♥➳♥➳")) {
                                    break label962;
                                }

                                var5.insert(0, "➳♥➳♥➳");
                                var2 = var5.length();
                                var8 = "➳♥➳♥➳";
                                break label963;
                            case 82:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✿◡‿◡✿")) {
                                    break label962;
                                }

                                var5.insert(0, "✿◡‿◡✿");
                                var2 = var5.length();
                                var8 = "✿◡‿◡✿";
                                break label963;
                            case 83:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("●▬ൠൠ▬")) {
                                    break label962;
                                }

                                var5.insert(0, "●▬ൠൠ▬");
                                var2 = var5.length();
                                var8 = "▬ൠൠ▬●";
                                break label963;
                            case 84:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("[̲̅ə̲̅٨̲̅٥̲̅٦̲̅]")) {
                                    break label962;
                                }

                                var5.insert(0, "[̲̅ə̲̅٨̲̅٥̲̅٦̲̅]");
                                var2 = var5.length();
                                var8 = "[̲̅ə̲̅٨̲̅٥̲̅٦̲̅]";
                                break label963;
                            case 85:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✰(❤˘⌣˘❤)✰")) {
                                    break label962;
                                }

                                var5.insert(0, "✰(❤˘⌣˘❤)✰");
                                var2 = var5.length();
                                var8 = "✰(❤˘⌣˘❤)✰";
                                break label963;
                            case 86:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❤◉◡◉❤")) {
                                    break label962;
                                }

                                var5.insert(0, "❤◉◡◉❤");
                                var2 = var5.length();
                                var8 = "❤◉◡◉❤";
                                break label963;
                            case 87:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✬✬（⌒▽⌒）✬✬")) {
                                    break label962;
                                }

                                var5.insert(0, "✬✬（⌒▽⌒）✬✬");
                                var2 = var5.length();
                                var8 = "✬✬（⌒▽⌒）✬✬";
                                break label963;
                            case 88:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❣⍣◕‿◕⍣❣")) {
                                    break label962;
                                }

                                var5.insert(0, "❣⍣◕‿◕⍣❣");
                                var2 = var5.length();
                                var8 = "❣⍣◕‿◕⍣❣";
                                break label963;
                            case 89:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☀☀♡♡")) {
                                    break label962;
                                }

                                var5.insert(0, "☀☀♡♡");
                                var2 = var5.length();
                                var8 = "♡♡☀☀";
                                break label963;
                            case 90:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("ღღ(∩_∩)ღღ")) {
                                    break label962;
                                }

                                var5.insert(0, "ღღ(∩_∩)ღღ");
                                var2 = var5.length();
                                var8 = "ღღ(∩_∩)ღღ";
                                break label963;
                            case 91:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("☃（*^_^*）☃")) {
                                    break label962;
                                }

                                var5.insert(0, "☃（*^_^*）☃");
                                var2 = var5.length();
                                var8 = "☃（*^_^*）☃";
                                break label963;
                            case 92:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("✮{◕ ◡ ◕}✮")) {
                                    break label962;
                                }

                                var5.insert(0, "✮{◕ ◡ ◕}✮");
                                var2 = var5.length();
                                var8 = "✮{◕ ◡ ◕}✮";
                                break label963;
                            case 93:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("⁑☾˙❀‿❀˙☽⁑")) {
                                    break label962;
                                }

                                var5.insert(0, "⁑☾˙❀‿❀˙☽⁑");
                                var2 = var5.length();
                                var8 = "⁑☾˙❀‿❀˙☽⁑";
                                break label963;
                            case 94:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("♥♡◙‿◙♡♥")) {
                                    break label962;
                                }

                                var5.insert(0, "♥♡◙‿◙♡♥");
                                var2 = var5.length();
                                var8 = "♥♡◙‿◙♡♥";
                                break label963;
                            case 95:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❄｡◕ ‿ ◕｡❄")) {
                                    break label962;
                                }

                                var5.insert(0, "❄｡◕ ‿ ◕｡❄");
                                var2 = var5.length();
                                var8 = "❄｡◕ ‿ ◕｡❄";
                                break label963;
                            case 96:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❣ლʘ‿ʘლ❣")) {
                                    break label962;
                                }

                                var5.insert(0, "❣ლʘ‿ʘლ❣");
                                var2 = var5.length();
                                var8 = "❣ლʘ‿ʘლ❣";
                                break label963;
                            case 97:
                                if (!"Preview text".equals(var6.getPreview()) && var6.getPreview().contains("❤(●'◡'●)❤")) {
                                    break label962;
                                }

                                var5.insert(0, "❤(●'◡'●)❤");
                                var2 = var5.length();
                                var8 = "❤(●'◡'●)❤";
                                break label963;
                            case 98:
                                if ("Preview text".equals(var6.getPreview()) || !var6.getPreview().contains("♪┏(°.°)┛")) {
                                    var5.insert(0, "♪┏(°.°)┛");
                                    var2 = var5.length();
                                    var8 = "♪┏(°.°)┛";
                                    break label963;
                                }
                            default:
                                break label962;
                        }

                        var5.insert(0, ".•°¤*(¯`★´¯)*¤°");
                        var2 = var5.length();
                        var8 = "°¤*(¯´★`¯)*¤°•.";
                        break label963;
                    }

                    var5.insert(0, var8);
                    var2 = var5.length();
                    break label963;
                }

                var5.insert(0, var8);
                var2 = var5.length();
            }

            var5.insert(var2, var8);
        }

        var6.setPreview(var5.toString());
        var4.description_text_view_DF.setText(var6.getPreview());
        var4.card_view_DF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                String charSequence = ((ViewHolder1) var4).description_text_view_DF.getText().toString();
                Activity activity = TextDecorationAdepeter.this.activity;
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
