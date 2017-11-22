package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BeansAgenda;

public class DAOAgenda {

    BeansAgenda agenda = new BeansAgenda();
    ConexaoBD conex = new ConexaoBD();
    ConexaoBD conexPaciente = new ConexaoBD();
    ConexaoBD conexMedico = new ConexaoBD();

    int codMed;
    int codPac;

    public void Salvar(BeansAgenda agenda) {
        BuscaMedico(agenda.getNomeMed());
        BuscaPaciente(agenda.getNomePac());
        conex.conexao();

        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO agenda(agenda_codpac, agenda_codmedico, agenda_motivo, agenda_turno, agenda_data, agenda_status) VALUES (?,?,?,?,?,?)");
            pst.setInt(1, codPac);
            pst.setInt(2, codMed);
            pst.setString(3, agenda.getMotivo());
            pst.setString(4, agenda.getTurno());
            pst.setDate(5, new java.sql.Date(agenda.getData().getTime()));
            pst.setString(6, agenda.getStatus());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Agendamento marcado com Suceso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar agendamento");
        }

        conex.desconecta();

    }

    public void BuscaMedico(String nomeMedico) {

        conexMedico.conexao();
        conexMedico.executaSql("SELECT * FROM medicos WHERE nome_medico='" + nomeMedico + "'");
        try {
            conexMedico.rs.first();
            codMed = conexMedico.rs.getInt("cod_medico");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Médico não cadastrado");
        }
    }

    public int BuscaCodMedico(String nomeMedico) {

        conexMedico.conexao();
        conexMedico.executaSql("SELECT * FROM medicos WHERE nome_medico='" + nomeMedico + "'");
        try {
            conexMedico.rs.first();
            codMed = conexMedico.rs.getInt("cod_medico");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Médico não cadastrado");
        }
        return codMed;
    }

    public void BuscaPaciente(String nomePaciente) {
        conexPaciente.conexao();
        conexPaciente.executaSql("SELECT * FROM pacientes WHERE paci_nome='" + nomePaciente + "'");
        try {
            conexPaciente.rs.first();
            codPac = conexPaciente.rs.getInt("paci_codigo");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Médico não cadastrado");
        }

    }

    public void Alterar(BeansAgenda agenda) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("UPDATE agenda SET agenda_status=? WHERE agenda_cod=?");
            pst.setString(1, agenda.getStatus());
            pst.setInt(2, agenda.getAgendaCod());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Agendamento em antendimento!");

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao atender agendamento" + ex);
        }
        conex.desconecta();

    }

}
