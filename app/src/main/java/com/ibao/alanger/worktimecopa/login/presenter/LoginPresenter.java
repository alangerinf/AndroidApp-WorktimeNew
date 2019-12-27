package com.ibao.alanger.worktimecopa.login.presenter;

public interface LoginPresenter {
    void signIn(String user, String password);//Interactor
    void loginSuccess();
    void loginError(String error);


}
