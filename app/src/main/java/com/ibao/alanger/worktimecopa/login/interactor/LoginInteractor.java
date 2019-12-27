package com.ibao.alanger.worktimecopa.login.interactor;


import com.ibao.alanger.worktimecopa.models.User;

public interface LoginInteractor {

    void signIn(String user, String password);

    void signSuccess(User userTemp);

    void signError(String mensajeRespuesta);
}
