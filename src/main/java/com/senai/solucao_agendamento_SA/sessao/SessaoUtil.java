package com.senai.solucao_agendamento_SA.sessao;

import jakarta.servlet.http.HttpSession;

    public class SessaoUtil {

    // Chave usada para guardar o usuario logado dentro da HttpSession
    private static final String CHAVE_USUARIO_LOGADO = "usuarioLogado";

    private SessaoUtil() {
        // Classe utilitaria, nao deve ser instanciada
    }

    public static void logar(HttpSession session, SessaoDto usuario) {
        session.setAttribute(CHAVE_USUARIO_LOGADO, usuario);
    }

    // Recupera o usuario logado da sessao (retorna null se ninguem estiver logado)
    public static SessaoDto usuarioLogado(HttpSession session) {
        return (SessaoDto) session.getAttribute(CHAVE_USUARIO_LOGADO);
    }

    // Verifica se existe um usuario logado nessa sessao
    public static boolean estaLogado(HttpSession session) {
        return usuarioLogado(session) != null;
    }

    // Remove o usuario da sessao
    public static void deslogar(HttpSession session) {
        session.removeAttribute(CHAVE_USUARIO_LOGADO);
    }
}