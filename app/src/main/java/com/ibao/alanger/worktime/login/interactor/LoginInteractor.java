package com.ibao.alanger.worktime.login.interactor;


import com.ibao.alanger.worktime.models.User;

public interface LoginInteractor {

    void signIn(String user, String password);

    void signSuccess(User userTemp);

    void signError(String mensajeRespuesta);
}
