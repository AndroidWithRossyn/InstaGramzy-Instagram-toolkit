package com.templatemela.instagramzy.utils;

import android.util.Log;
import com.templatemela.instagramzy.models.TMLikerCommenterModel;
import com.templatemela.instagramzy.models.TMPersonModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TMInteractionAnalysisListGenerator {
    int avgComments;
    int avgLike;
    List<TMPersonModel> fList;
    List<TMLikerCommenterModel> mainList;
    List<TMLikerCommenterModel> temp;
    int type = 0;

    public TMInteractionAnalysisListGenerator(List<TMLikerCommenterModel> list, List<TMPersonModel> list2) {
        temp = list;
        fList = list2;
        avgLike = getAvgLikes(list);
        avgComments = getAvgComments(list);
        Log.i("InteractionAnalysisListGenerator", avgComments + "avg comment");
        Log.i("InteractionAnalysisListGenerator", avgLike + " avg like");
    }

    
    public int getAvgLikes(List<TMLikerCommenterModel> list) {
        int i = 0;
        for (TMLikerCommenterModel likeCount : list) {
            i += likeCount.getLikeCount();
        }
        try {
            return i / list.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    
    public int getAvgComments(List<TMLikerCommenterModel> list) {
        int i = 0;
        int i2 = 0;
        for (TMLikerCommenterModel next : list) {
            if (next.getCommentCount() == 0) {
                i2++;
            } else {
                i += next.getCommentCount();
            }
        }
        try {
            return i / (list.size() - i2);
        } catch (Exception unused) {
            return 0;
        }
    }

    public List<TMLikerCommenterModel> getInteractionList(int i) {
        ArrayList arrayList = new ArrayList();
        mainList = temp;
        Log.i("InteractionAnalysisListGenerator", i + "");
        if (i == 0) {
            return getByLikeUp();
        }
        if (i == 1) {
            List<TMLikerCommenterModel> byCommentUp = getByCommentUp();
            Log.i("InteractionAnalysisListGenerator", byCommentUp.size() + " flist s");
            return byCommentUp;
        } else if (i == 2) {
            return getByInteractionUp();
        } else {
            if (i == 3) {
                return getFollowerWithLessLike();
            }
            if (i == 4) {
                return getFollowerWithLessComment();
            }
            if (i == 5) {
                return getFollowerWithLessInteraction();
            }
            if (i == 6) {
                return getMutualFollowerWithLessInteraction();
            }
            return i == 7 ? getNotFollowedButInteraction() : arrayList;
        }
    }

    
    public List<TMLikerCommenterModel> getByLikeUp() {
        List<TMLikerCommenterModel> sortedList = new Sort(mainList).getSortedList(type);
        int i = 0;
        while (i < sortedList.size() && sortedList.get(i).getLikeCount() >= avgLike) {
            i++;
        }
        mainList = sortedList.subList(0, i);

        return mainList;
    }

    
    public List<TMLikerCommenterModel> getByCommentUp() {
        List<TMLikerCommenterModel> sortedList = new Sort(mainList).getSortedList(1);
        int i = 0;
        while (i < sortedList.size() && sortedList.get(i).getCommentCount() >= avgComments) {
            i++;
        }
        mainList= sortedList.subList(0, i);
        return mainList;
    }

    
    public List<TMLikerCommenterModel> getByInteractionUp() {
        List<TMLikerCommenterModel> sortedList = new Sort(mainList).getSortedList(2);
        int i = 0;
        while (i < sortedList.size() && sortedList.get(i).getCommentCount() + sortedList.get(i).getLikeCount() >= avgLike + avgComments) {
            i++;
        }
        mainList= sortedList.subList(0, i);

        return mainList;
    }

    
    public List<TMLikerCommenterModel> getFollowerWithLessLike() {
        List<TMLikerCommenterModel> list = mainList;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fList.size(); i++) {
            int i2 = 0;
            while (true) {
                if (i2 < list.size()) {
                    if (list.get(i2).getId().equals(fList.get(i).getId()) && fList.get(i).getFollowing().booleanValue()) {
                        arrayList.add(list.get(i2));
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (i2 == list.size()) {
                TMPersonModel person = fList.get(i);
                if (person.getFollowing().booleanValue()) {
                    arrayList.add(new TMLikerCommenterModel(person.getId(), person.getUsername(), person.getName(), person.getDp(), 0, 0));
                }
            }
        }
        List<TMLikerCommenterModel> sortedList = new Sort(arrayList).getSortedList(3);
        int i3 = 0;
        while (i3 < sortedList.size() && sortedList.get(i3).getLikeCount() < avgLike) {
            i3++;
        }
        mainList = sortedList.subList(0, i3);
        return mainList;
    }

    
    public List<TMLikerCommenterModel> getFollowerWithLessComment() {
        List<TMLikerCommenterModel> list = mainList;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fList.size(); i++) {
            int i2 = 0;
            while (true) {
                if (i2 < list.size()) {
                    if (list.get(i2).getId().equals(fList.get(i).getId()) && fList.get(i).getFollowing().booleanValue()) {
                        arrayList.add(list.get(i2));
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (i2 == list.size()) {
                TMPersonModel person = fList.get(i);
                if (person.getFollowing().booleanValue()) {
                    arrayList.add(new TMLikerCommenterModel(person.getId(), person.getUsername(), person.getName(), person.getDp(), 0, 0));
                }
            }
        }
        List<TMLikerCommenterModel> sortedList = new Sort(arrayList).getSortedList(4);
        int i3 = 0;
        while (i3 < sortedList.size() && sortedList.get(i3).getCommentCount() < avgComments) {
            i3++;
        }
        mainList = sortedList.subList(0, i3);

        return mainList;
    }

    
    public List<TMLikerCommenterModel> getFollowerWithLessInteraction() {
        List<TMLikerCommenterModel> list = mainList;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fList.size(); i++) {
            int i2 = 0;
            while (true) {
                if (i2 < list.size()) {
                    if (list.get(i2).getId().equals(fList.get(i).getId()) && fList.get(i).getFollowing().booleanValue()) {
                        arrayList.add(list.get(i2));
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (i2 == list.size()) {
                TMPersonModel person = fList.get(i);
                if (person.getFollowing().booleanValue()) {
                    arrayList.add(new TMLikerCommenterModel(person.getId(), person.getUsername(), person.getName(), person.getDp(), 0, 0));
                }
            }
        }
        List<TMLikerCommenterModel> sortedList = new Sort(arrayList).getSortedList(5);
        int i3 = 0;
        while (i3 < sortedList.size() && sortedList.get(i3).getLikeCount() + sortedList.get(i3).getCommentCount() < avgLike + avgComments) {
            i3++;
        }
        mainList = sortedList.subList(0, i3);

        return mainList;
    }

    
    public List<TMLikerCommenterModel> getMutualFollowerWithLessInteraction() {
        List<TMLikerCommenterModel> list = mainList;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fList.size(); i++) {
            TMPersonModel person = fList.get(i);
            int i2 = 0;
            while (true) {
                if (i2 < list.size()) {
                    if (list.get(i2).getId().equals(fList.get(i).getId()) && person.getFollowing().booleanValue() && person.getFollowedByMe().booleanValue()) {
                        arrayList.add(list.get(i2));
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (i2 == list.size() && person.getFollowing().booleanValue() && person.getFollowedByMe().booleanValue()) {
                arrayList.add(new TMLikerCommenterModel(person.getId(), person.getUsername(), person.getName(), person.getDp(), 0, 0));
            }
        }
        List<TMLikerCommenterModel> sortedList = new Sort(arrayList).getSortedList(5);
        int i3 = 0;
        while (i3 < sortedList.size() && sortedList.get(i3).getLikeCount() + sortedList.get(i3).getCommentCount() < avgLike + avgComments) {
            i3++;
        }
        mainList = sortedList.subList(0, i3);
        return mainList;
    }

    
    public List<TMLikerCommenterModel> getNotFollowedButInteraction() {
        List<TMLikerCommenterModel> list = mainList;
        new ArrayList();
        for (int i = 0; i < fList.size(); i++) {
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    break;
                } else if (list.get(i2).getId().equals(fList.get(i).getId())) {
                    list.remove(i2);
                    break;
                } else {
                    i2++;
                }
            }
        }
        mainList = new Sort(list).getSortedList(2);
        return mainList;
    }

    public class Sort {
        List<TMLikerCommenterModel> list;

        public Sort(List<TMLikerCommenterModel> list2) {
            list = list2;
        }

        class SortByLikeUp implements Comparator<TMLikerCommenterModel> {
            SortByLikeUp() {
            }

            public int compare(TMLikerCommenterModel likerCommenterModel, TMLikerCommenterModel likerCommenterModel2) {
                return likerCommenterModel2.getLikeCount() - likerCommenterModel.getLikeCount();
            }
        }

        class SortByLikeDown implements Comparator<TMLikerCommenterModel> {
            SortByLikeDown() {
            }

            public int compare(TMLikerCommenterModel likerCommenterModel, TMLikerCommenterModel likerCommenterModel2) {
                return -(likerCommenterModel2.getLikeCount() - likerCommenterModel.getLikeCount());
            }
        }

        class SortByCommentUp implements Comparator<TMLikerCommenterModel> {
            SortByCommentUp() {
            }

            public int compare(TMLikerCommenterModel likerCommenterModel, TMLikerCommenterModel likerCommenterModel2) {
                return likerCommenterModel2.getCommentCount() - likerCommenterModel.getCommentCount();
            }
        }

        class SortByCommentDown implements Comparator<TMLikerCommenterModel> {
            SortByCommentDown() {
            }

            public int compare(TMLikerCommenterModel likerCommenterModel, TMLikerCommenterModel likerCommenterModel2) {
                return -(likerCommenterModel2.getCommentCount() - likerCommenterModel.getCommentCount());
            }
        }

        class SortByInteractionUp implements Comparator<TMLikerCommenterModel> {
            SortByInteractionUp() {
            }

            public int compare(TMLikerCommenterModel likerCommenterModel, TMLikerCommenterModel likerCommenterModel2) {
                return (likerCommenterModel2.getLikeCount() + likerCommenterModel2.getCommentCount()) - (likerCommenterModel.getLikeCount() + likerCommenterModel.getCommentCount());
            }
        }

        class SortByInteractionDown implements Comparator<TMLikerCommenterModel> {
            SortByInteractionDown() {
            }

            public int compare(TMLikerCommenterModel likerCommenterModel, TMLikerCommenterModel likerCommenterModel2) {
                return -((likerCommenterModel2.getLikeCount() + likerCommenterModel2.getCommentCount()) - (likerCommenterModel.getLikeCount() + likerCommenterModel.getCommentCount()));
            }
        }

        public List<TMLikerCommenterModel> getSortedList(int i) {
            if (i == 0) {
                Collections.sort(list, new SortByLikeUp());
            } else if (i == 1) {
                Collections.sort(list, new SortByCommentUp());
            } else if (i == 2) {
                Collections.sort(list, new SortByInteractionUp());
            } else if (i == 3) {
                Collections.sort(list, new SortByLikeDown());
            } else if (i == 4) {
                Collections.sort(list, new SortByCommentDown());
            } else if (i == 5) {
                Collections.sort(list, new SortByInteractionDown());
            }
            return list;
        }
    }
}
