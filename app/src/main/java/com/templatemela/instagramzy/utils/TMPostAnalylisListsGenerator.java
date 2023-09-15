package com.templatemela.instagramzy.utils;

import android.content.Context;
import android.util.Log;

import com.templatemela.instagramzy.models.TMPostModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TMPostAnalylisListsGenerator {
    Context context;
    List<TMPostModel> list;
    int mediatype = 0;
    Pref pref;
    String tag = "PostAnalylisListsGenerator";

    public TMPostAnalylisListsGenerator(List<TMPostModel> list2) {
        list = list2;
        Log.i("PostStats", list2.size() + " ");
    }

    public TMPostAnalylisListsGenerator(Context context2) {
        context = context2;
        Pref pref2 = new Pref(context2);
        pref = pref2;
        list = pref2.getAllSavedPost();
    }

    public List<TMPostModel> getPostList(int i, int i2) {
        mediatype = i2;
        return new Sort(list).getSortedList(i);
    }

    public class Sort {
        List<TMPostModel> list;

        public Sort(List<TMPostModel> list2) {
            list = list2;
            pref = new Pref(context);
        }

        class SortByViewUp implements Comparator<TMPostModel> {
            SortByViewUp() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return postModel2.getViews() - postModel.getViews();
            }
        }

        class SortByViewDown implements Comparator<TMPostModel> {
            SortByViewDown() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return -(postModel2.getViews() - postModel.getViews());
            }
        }

        class SortByLikeUp implements Comparator<TMPostModel> {
            SortByLikeUp() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return postModel2.getLikeCount() - postModel.getLikeCount();
            }
        }

        class SortByLikeDown implements Comparator<TMPostModel> {
            SortByLikeDown() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return -(postModel2.getLikeCount() - postModel.getLikeCount());
            }
        }

        class SortByCommentUp implements Comparator<TMPostModel> {
            SortByCommentUp() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return postModel2.getCommentCount() - postModel.getCommentCount();
            }
        }

        class SortByCommentDown implements Comparator<TMPostModel> {
            SortByCommentDown() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return -(postModel2.getCommentCount() - postModel.getCommentCount());
            }
        }

        class SortByPopularUp implements Comparator<TMPostModel> {
            SortByPopularUp() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return ((postModel2.getLikeCount() + postModel2.getCommentCount()) + postModel2.getViews()) - ((postModel.getLikeCount() + postModel.getCommentCount()) + postModel.getViews());
            }
        }

        class SortByPopularDown implements Comparator<TMPostModel> {
            SortByPopularDown() {
            }

            public int compare(TMPostModel postModel, TMPostModel postModel2) {
                return -(((postModel2.getLikeCount() + postModel2.getCommentCount()) + postModel2.getViews()) - ((postModel.getLikeCount() + postModel.getCommentCount()) + postModel.getViews()));
            }
        }

        public List<TMPostModel> getSortedList(int i) {
            ArrayList arrayList = new ArrayList();
            Log.i("PostStats", list.size() + " " + i);
            if (list.size() == 0) {
                return arrayList;
            }
            if (mediatype == 1) {
                list = getImageList(list);
            } else if (mediatype == 2) {
                list = getVideoList(list);
            }
            int i2 = 0;
            switch (i) {
                case 0:
                    Collections.sort(list, new SortByViewUp());
                    list = getVideoList(list);
                    long access$000 = getAvgViews(list) + 1;
                    while (i2 < list.size() && ((long) list.get(i2).getViews()) >= access$000) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 1:
                    Collections.sort(list, new SortByViewDown());
                    list= getVideoList(list);
                    long access$0002 = getAvgViews(list);
                    while (i2 < list.size() && ((long) list.get(i2).getViews()) <= access$0002) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 2:
                    Collections.sort(list, new SortByLikeUp());
                    long access$100 = getAvgLikes(list) + 1;
                    while (i2 < list.size() && ((long) list.get(i2).getLikeCount()) >= access$100) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 3:
                    Collections.sort(list, new SortByLikeDown());
                    while (i2 < list.size() && ((long) list.get(i2).getLikeCount()) <= getAvgLikes(list) / 2) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 4:
                    Collections.sort(list, new SortByPopularUp());
                    long access$200 = getAvgComments(list) + getAvgLikes(list) + getAvgViews(list) + 1;
                    while (i2 < list.size() && ((long) (list.get(i2).getLikeCount() + list.get(i2).getCommentCount() + list.get(i2).getViews())) >= access$200) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 5:
                    Collections.sort(list, new SortByPopularDown());
                    long access$2002 = getAvgComments(list) + getAvgLikes(list) + getAvgViews(list);
                    while (i2 < list.size() && ((long) (list.get(i2).getLikeCount() + list.get(i2).getCommentCount() + list.get(i2).getViews())) <= access$2002) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 6:
                    Collections.sort(list, new SortByCommentUp());
                    long access$2003 = getAvgComments(list) + 1;
                    while (i2 < list.size() && ((long) list.get(i2).getCommentCount()) >= access$2003) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
                case 7:
                    Collections.sort(list, new SortByCommentDown());
                    long access$2004 = getAvgComments(list);
                    while (i2 < list.size() && ((long) list.get(i2).getCommentCount()) <= access$2004) {
                        arrayList.add(list.get(i2));
                        i2++;
                    }
                    break;
            }
            return arrayList;
        }
    }

   
    public long getAvgLikes(List<TMPostModel> list2) {
        long j = 0;
        if (list2.size() == 0) {
            return 0;
        }
        for (TMPostModel likeCount : list2) {
            j += (long) likeCount.getLikeCount();
        }
        long size = j / ((long) list2.size());
        String str = tag;
        Log.i(str, "avg likes " + size + "");
        return size;
    }

   
    public long getAvgComments(List<TMPostModel> list2) {
        long j = 0;
        if (list2.size() == 0) {
            return 0;
        }
        for (TMPostModel commentCount : list2) {
            j += (long) commentCount.getCommentCount();
        }
        long size = j / ((long) list2.size());

        Log.i(tag, "avg comments " + size + "");
        return size;
    }

   
    public long getAvgViews(List<TMPostModel> list2) {
        long j = 0;
        if (list2.size() == 0) {
            return 0;
        }
        for (TMPostModel views : list2) {
            j += (long) views.getViews();
        }
        long size = j / ((long) list2.size());
        String str = tag;
        Log.i(str, "avg vies " + size + "");
        return size;
    }

    
    public List<TMPostModel> getVideoList(List<TMPostModel> list2) {
        ArrayList arrayList = new ArrayList();
        if (list2 == null) {
            return arrayList;
        }
        for (TMPostModel next : list2) {
            if (next.getType() == 2) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    
    public List<TMPostModel> getImageList(List<TMPostModel> list2) {
        ArrayList arrayList = new ArrayList();
        if (list2 == null) {
            return arrayList;
        }
        for (TMPostModel next : list2) {
            if (next.getType() != 2) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
