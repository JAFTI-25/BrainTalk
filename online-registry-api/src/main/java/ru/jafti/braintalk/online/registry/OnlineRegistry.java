package ru.jafti.braintalk.online.registry;

public interface OnlineRegistry extends ActiveTalkersHolder {
    void goIn(GoInRequest request);
    void goOut(GoOutRequest request);
}
