package com.ibao.alanger.worktime.login.interactor;


import android.content.Context;
import android.util.Log;

import com.ibao.alanger.worktime.login.presenter.LoginPresenter;
import com.ibao.alanger.worktime.login.repository.LoginRepository;
import com.ibao.alanger.worktime.login.repository.LoginRepositoryImpl;
import com.ibao.alanger.worktime.database.SharedPreferencesManager;
import com.ibao.alanger.worktime.models.User;


public class LoginInteractorImpl implements LoginInteractor {

    private Context ctx;
    private LoginPresenter presenter;
    private LoginRepository repository;

    String TAG = LoginInteractorImpl.class.getSimpleName();

    public LoginInteractorImpl(Context ctx, LoginPresenter presenter) {
        this.presenter = presenter;
        this.ctx = ctx;
        this.repository = new LoginRepositoryImpl(this);
    }

    @Override
    public void signIn(String user, String password) {
        repository.signIn(user,password);
    }

    @Override
    public void signSuccess(User userTemp) {
        Log.d(TAG,userTemp.toString());
        if(SharedPreferencesManager.saveUser(ctx,userTemp)){
            presenter.loginSuccess();
        }else {
            presenter.loginError("No se pudo guardar el usuario");
        }


    }

    @Override
    public void signError(String mensajeRespuesta) {
        presenter.loginError(mensajeRespuesta);
    }




}
