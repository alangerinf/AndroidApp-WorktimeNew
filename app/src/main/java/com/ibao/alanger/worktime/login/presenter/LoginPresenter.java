package com.ibao.alanger.worktime.login.presenter;

public interface LoginPresenter {
    void signIn(String user, String password);//Interactor
    void loginSuccess();
    void loginError(String error);


}
