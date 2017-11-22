package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BeansPacientes;

public class DAOPaciente {

    ConexaoBD conex = new ConexaoBD();
    ConexaoBD conexBairro = new ConexaoBD();
    String nomeBairro;
    int codBai;

    public void Salvar(BeansPacientes pac) {
        buscaBaiCod(pac.getNomeBairro());
        conex.conexao();

        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO pacientes (paci_nome, paci_rg, paci_telefone, paci_rua, paci_cep, paci_complemento, paci_baicodigo, paci_nasc) VALUES (?,?,?,?,?,?,?,?)");
            pst.setString(1, pac.getNomePac());
            pst.setString(2, pac.getRg());
            pst.setString(3, pac.getTelefone());
            pst.setString(4, pac.getRua());
            pst.setString(5, pac.getCep());
            pst.setString(6, pac.getComplemento());
            pst.setInt(7, codBai);
            pst.setString(8, pac.getNasc());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Paciente salvo com sucesso");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao salvar paciente" + ex);
        }
        conex.desconecta();
    }

    public void buscaBaiCod(String nome) {
        conex.conexao();
        conex.executaSql("SELECT * FROM bairro WHERE bainome ='" + nome + "'");
        try {
            conex.rs.first();
            codBai = conex.rs.getInt("baicodigo");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar bairro" + ex);
        }
        conex.desconecta();
    }

    public void Alterar(BeansPacientes pac) {
        buscaBaiCod(pac.getNomeBairro());
        conex.conexao();

        try {
            PreparedStatement pst = conex.con.prepareStatement("UPDATE pacientes SET paci_nome=?, paci_rg=?, paci_telefone=?, paci_rua=?, paci_cep=?, paci_complemento=?, paci_baicodigo=?, paci_nasc=? WHERE paci_codigo=?");
            pst.setString(1, pac.getNomePac());
            pst.setString(2, pac.getRg());
            pst.setString(3, pac.getTelefone());
            pst.setString(4, pac.getRua());
            pst.setString(5, pac.getCep());
            pst.setString(6, pac.getComplemento());
            pst.setInt(7, codBai);
            pst.setString(8, pac.getNasc());
            pst.setInt(9, pac.getCodPac());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Paciente alterado com sucesso");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao alterar paciente" + ex);
        }
        conex.desconecta();
    }

    public void buscaNomeBairro(int cod) {
        conexBairro.conexao();

        try {
            conexBairro.executaSql("SELECT * FROM bairro WHERE baicodigo=" + cod);
            conexBairro.rs.first();
            nomeBairro = conexBairro.rs.getString("bainome");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar nome bairro" + ex);
        }

        conexBairro.desconecta();
    }

    public BeansPacientes buscaPaciente(BeansPacientes pac) {
        conex.conexao();

        try {
            conex.executaSql("SELECT * FROM pacientes WHERE paci_nome LIKE'%" + pac.getPesquisa() + "%'");
            conex.rs.first();
            buscaNomeBairro(conex.rs.getInt("paci_baicodigo"));
            pac.setNomePac(conex.rs.getString("paci_nome"));
            pac.setCep(conex.rs.getString("paci_cep"));
            pac.setCodPac(conex.rs.getInt("paci_codigo"));
            pac.setComplemento(conex.rs.getString("paci_complemento"));
            pac.setNasc(conex.rs.getString("paci_nasc"));
            pac.setRg(conex.rs.getString("paci_rg"));
            pac.setTelefone(conex.rs.getString("paci_telefone"));
            pac.setRua(conex.rs.getString("paci_rua"));
            pac.setNomeBairro(nomeBairro);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar paciente" + ex);
        }

        conex.desconecta();
        return pac;
    }

    public void Excluir(BeansPacientes pac) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("DELETE FROM pacientes WHERE paci_codigo=?");
            pst.setInt(1, pac.getCodPac());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Paciente excluido com sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir paciente" + ex);
        }

        conex.desconecta();
    }
}
