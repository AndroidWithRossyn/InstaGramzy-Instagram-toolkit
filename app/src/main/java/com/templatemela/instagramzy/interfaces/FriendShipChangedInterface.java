package com.templatemela.instagramzy.interfaces;

import com.templatemela.instagramzy.models.TMPersonModel;

public interface FriendShipChangedInterface {
    void onFriendShipChangedError();

    void onFriendShipSuccessfullyChanged(TMPersonModel person);
}
