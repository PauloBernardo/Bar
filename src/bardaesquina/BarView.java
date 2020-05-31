/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bardaesquina;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author estagio2
 */
public class BarView extends javax.swing.JFrame {

    /**
     * Creates new form BarView
     */
    Semaphore s = null;
    Semaphore mutex = new Semaphore(1, true);
    Semaphore mutex2 = new Semaphore(1, true);
    int qntCadeiras = 0;
    int actualIndex = 0;
    JFrame isto = this;
    int numeroClientes = 0;

    public BarView(){
        initComponents();
    }

    public BarView(String barClub, int qntCadeiras) {
        super(barClub);
        initComponents();
        String[] bar = new String[10];
        bar[0] = "/imagens/bar-backgrounds/bar1.png";
        bar[1] = "/imagens/bar-backgrounds/bar2.png";
        bar[2] = "/imagens/bar-backgrounds/bar3.png";
        bar[3] = "/imagens/bar-backgrounds/bar4.png";
        bar[4] = "/imagens/bar-backgrounds/bar5.png";
        bar[5] = "/imagens/bar-backgrounds/bar6.png";
        bar[6] = "/imagens/bar-backgrounds/bar7.png";
        bar[7] = "/imagens/bar-backgrounds/bar8.png";
        bar[8] = "/imagens/bar-backgrounds/bar9.png";
        bar[9] = "/imagens/bar-backgrounds/bar10.png";
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(bar[qntCadeiras - 1])));
        setSize(1360, 450);
        this.s = new Semaphore(qntCadeiras, true);
        this.qntCadeiras = qntCadeiras;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cliente1 = new javax.swing.JLabel();
        cliente2 = new javax.swing.JLabel();
        cliente3 = new javax.swing.JLabel();
        cliente4 = new javax.swing.JLabel();
        cliente5 = new javax.swing.JLabel();
        cliente6 = new javax.swing.JLabel();
        cliente7 = new javax.swing.JLabel();
        cliente8 = new javax.swing.JLabel();
        cliente9 = new javax.swing.JLabel();
        cliente10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cliente1)
                    .addComponent(cliente2)
                    .addComponent(cliente3)
                    .addComponent(cliente4)
                    .addComponent(cliente5)
                    .addComponent(cliente6)
                    .addComponent(cliente7)
                    .addComponent(cliente8)
                    .addComponent(cliente9)
                    .addComponent(cliente10))
                .addContainerGap(250, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cliente1)
                .addGap(6, 6, 6)
                .addComponent(cliente2)
                .addGap(6, 6, 6)
                .addComponent(cliente3)
                .addGap(6, 6, 6)
                .addComponent(cliente4)
                .addGap(6, 6, 6)
                .addComponent(cliente5)
                .addGap(6, 6, 6)
                .addComponent(cliente6)
                .addGap(6, 6, 6)
                .addComponent(cliente7)
                .addGap(6, 6, 6)
                .addComponent(cliente8)
                .addGap(6, 6, 6)
                .addComponent(cliente9)
                .addGap(6, 6, 6)
                .addComponent(cliente10)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(1080, 0, 260, 310);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/outros/pista.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 240, 1110, 70);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/bar-backgrounds/bar10.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1084, 245);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/outros/casas.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, 310, 1080, 105);

        jButton1.setText("Criar bebum");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(1080, 313, 260, 100);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//       Comente as proximas 10 linhas se quiser adicionar manualmente
//        this.createCliente("Paulo", 5, 10);
//        this.createCliente("Ricardo", 5, 10);
//        this.createCliente("Bernardo", 5, 10);
//        this.createCliente("Nivardo", 5, 10);
//        this.createCliente("João", 5, 10);
//        this.createCliente("Marcus", 5, 10);
//        this.createCliente("Elysson", 5, 10);
//        this.createCliente("Gabriel", 5, 10);
//        this.createCliente("Paulo8", 5, 10);
//        this.createCliente("Paulo9", 5, 10);
//        Descomente a proxima linha se quiser adicionar manualmente
        if(this.actualIndex < 10) {
            new ClienteForm(this).setVisible(true);
        }
//            this.cliente1.setText("Cliente 1");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public void createCliente(String identificador, float tempoCasa, float tempoBar) {
        ArrayList<JLabel> list = new ArrayList();
        list.add(cliente1);
        list.add(cliente2);
        list.add(cliente3);
        list.add(cliente4);
        list.add(cliente5);
        list.add(cliente6);
        list.add(cliente7);
        list.add(cliente8);
        list.add(cliente9);
        list.add(cliente10);
        Cliente cliente = new Cliente(
                list.get(this.actualIndex++),
                identificador,
                tempoCasa,
                tempoBar,
                s,
                mutex,
                mutex2,
                this
        );
        cliente.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BarView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cliente1;
    private javax.swing.JLabel cliente10;
    private javax.swing.JLabel cliente2;
    private javax.swing.JLabel cliente3;
    private javax.swing.JLabel cliente4;
    private javax.swing.JLabel cliente5;
    private javax.swing.JLabel cliente6;
    private javax.swing.JLabel cliente7;
    private javax.swing.JLabel cliente8;
    private javax.swing.JLabel cliente9;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    public JPanel getjPanel2() {
        return jPanel1;
    }

    public void setjPanel2(JPanel jPanel) {
        this.jPanel1 = jPanel;
    }
}
