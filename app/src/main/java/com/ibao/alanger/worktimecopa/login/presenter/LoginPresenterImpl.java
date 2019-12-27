package com.ibao.alanger.worktimecopa.login.presenter;


import android.content.Context;

import com.ibao.alanger.worktimecopa.login.interactor.LoginInteractor;
import com.ibao.alanger.worktimecopa.login.interactor.LoginInteractorImpl;
import com.ibao.alanger.worktimecopa.login.view.LoginView;


public class LoginPresenterImpl implements LoginPresenter{

    private LoginView loginView;
    private LoginInteractor interactor;
    private Context ctx;
    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.ctx = (Context) loginView;
        interactor = new LoginInteractorImpl(ctx,this);
    }

    @Override
    public void signIn(String user, String password) {
        loginView.disableInputs();
        loginView.showProgressBar();
        interactor.signIn(user,password);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();
        loginView.enableInputs();
        loginView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }
}
