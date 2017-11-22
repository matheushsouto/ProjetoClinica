package controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexaoBD {

    public Statement stm;
    public ResultSet rs;
    private String driver = "com.mysql.jdbc.Driver";
    private String caminho = "jdbc:mysql://localhost:3306/projetoclinica";
    // private String caminho = "jdbc:mysql://192.168.1.22/projetoclinica";
    private String usuario = "root";
    private String senha = "";
    Connection con;

    public void conexao() {

        try {
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(caminho, usuario, senha);
            //       JOptionPane.showMessageDialog(null, "Conexao efetuada com sucesso! :)");
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro da Conexao com o banco! :(\n  " + ex.getMessage());
        }
    }

    public void executaSql(String sql) {
        try {
            // Diferenca maisculo e minusco
            stm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar sql! :(\n  " + ex.getMessage());
        }
    }

    public void desconecta() {
        try {
            con.close();
            //       JOptionPane.showMessageDialog(null,"O Banco foi desconectado com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
            //     JOptionPane.showMessageDialog(null,"Erro ao fechar a conexão! \n:)"+ex.getMessage());
        }
    }

}
