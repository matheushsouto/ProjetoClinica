package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BeansMedico;
import model.BeansUsuario;

public class DAOUsuario {

    ConexaoBD conex = new ConexaoBD();
    BeansUsuario mod = new BeansUsuario();

    public void Salvar(BeansUsuario mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO usuarios(usu_nome,usu_senha,usu_tipo) VALUES (?,?,?)");
            pst.setString(1, mod.getUsuNome());
            pst.setString(2, mod.getUsuSenha());
            pst.setString(3, mod.getUsuTipo());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Usuario Inserido com Sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao inserir usuario! /n" + ex);
        }
        conex.desconecta();
    }

    public void Alterar(BeansUsuario mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("UPDATE usuarios SET usu_nome=?, usu_senha=?, usu_tipo=? WHERE usu_cod=?");
            pst.setString(1, mod.getUsuNome());
            pst.setString(2, mod.getUsuSenha());
            pst.setString(3, mod.getUsuTipo());
            pst.setInt(4, mod.getUsuCod());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Usuario alterado com sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na alteração do usuario" + ex);

        }

        conex.desconecta();
    }

    public BeansUsuario buscaUsuario(BeansUsuario mod) {
        conex.conexao();
        conex.executaSql("SELECT * FROM usuarios WHERE usu_nome LIKE '%" + mod.getUsuPesquisa() + "%'");

        try {
            //Capturando primeiro resultado do banco
            conex.rs.first();
            mod.setUsuCod(conex.rs.getInt("usu_cod"));
            mod.setUsuNome(conex.rs.getString("usu_nome"));
            mod.setUsuSenha(conex.rs.getString("usu_senha"));
            mod.setUsuTipo(conex.rs.getString("usu_tipo"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Usuario não cadastrado!" );
        }
        conex.desconecta();

        return mod;
    }

    public void Excluir(BeansUsuario mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("DELETE FROM usuarios WHERE usu_cod=?");
            pst.setInt(1, mod.getUsuCod());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Usuario excluido com sucesso!");

        } catch (SQLException ex) {
            Logger.getLogger(DAOMedico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao excluir usuario!");
        }
        conex.desconecta();
    }
}
