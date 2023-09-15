package com.templatemela.instagramzy.interfaces;

import com.templatemela.instagramzy.models.TMHashTagModel;
import java.util.List;

public interface HashTagInterface {
    void onRelatedListComplete(List<TMHashTagModel> list);

    void onSearchComplete(List<TMHashTagModel> list);
}
