package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BeansMedico;

public class DAOMedico {

    ConexaoBD conex = new ConexaoBD();
    BeansMedico mod = new BeansMedico();

    public void Salvar(BeansMedico mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO medicos(nome_medico,especialidade_medico,crm_medico) VALUES (?,?,?)");
            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEspecialidade());
            pst.setString(3, mod.getCrm());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Dados Inseridos com Sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao inserir dados! /n" + ex);
        }
        conex.desconecta();
    }

    //Alterar medico
    public void Editar(BeansMedico mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("UPDATE medicos SET nome_medico=?, especialidade_medico=?, crm_medico=? WHERE cod_medico=?");
            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEspecialidade());
            pst.setString(3, mod.getCrm());
            pst.setInt(4, mod.getCod());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na alteração dos dados"+ex);

        }

        conex.desconecta();
    }
    
    //Excluindo Medico
    public void Excluir(BeansMedico mod){
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("DELETE FROM medicos WHERE cod_medico=?");
            pst.setInt(1, mod.getCod());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados excluidos com sucesso!");
           
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedico.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Erro ao excluir dados!"+ex);
        }
        conex.desconecta();
    }

    // Pesquisando medico
    public BeansMedico buscaMedico(BeansMedico mod) {
        conex.conexao();
        conex.executaSql("SELECT * FROM medicos WHERE nome_medico LIKE '%" + mod.getPesquisa() + "%'");

        try {
            //Capturando primeiro resultado do banco
            conex.rs.first();
            mod.setCod(conex.rs.getInt("cod_medico"));
            mod.setNome(conex.rs.getString("nome_medico"));
            mod.setCrm(conex.rs.getString("crm_medico"));
            mod.setEspecialidade(conex.rs.getString("especialidade_medico"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Médico não cadastrado!");
        }
        conex.desconecta();

        return mod;
    }
}
