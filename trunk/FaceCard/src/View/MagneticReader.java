/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MagStripeCard;
import Model.CreditCardInfo;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author giovannirojas
 */
public class MagneticReader extends javax.swing.JDialog {

    /**
     * Creates new form MagneticReader
     */
    
    private CreditCardInfo infoCard;
    private boolean isReaded;
    private javax.swing.JTextField name;
    private javax.swing.JTextField number;
    private javax.swing.JTextField expiration;
    
    public MagneticReader(java.awt.Frame parent, boolean modal,javax.swing.JTextField name, javax.swing.JTextField number, javax.swing.JTextField expiration) {
        
        super(parent, modal);
        this.setUndecorated(true);
        initComponents();
        this.isReaded = false;
        this.name = name;
        this.number = number;
        this.expiration = expiration;
    }

    private MagneticReader(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        in = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/magneticCard.png"))); // NOI18N

        jLabel1.setText("Deslice la tarjeta magnética:");

        in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(in)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1)
                        .add(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(in, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inActionPerformed
        
        CreditCardInfo ccr = readMagneticCard();
        if(ccr!=null)
        putData(ccr);
        isReaded = true;
        this.dispose();
    }//GEN-LAST:event_inActionPerformed
/*
 * Metodos para leer la tarjeta 
 */
    public CreditCardInfo readMagneticCard(){
        CreditCardInfo cardInfo = null;
        try{
            MagStripeCard msc = new MagStripeCard(in.getText());
            String name = msc.getLastName()+" "+msc.getFirstName();
            String cardNumber = msc.getAccountNumber();
            int expirationMonth = Integer.parseInt(msc.getExpirationMonth());
            int expirationYear = Integer.parseInt(msc.getExpirationYear());
            String cardType = Controller.CreditCardInfo.cardType(msc.getAccountNumber().toString());
            boolean isValid = Controller.CreditCardInfo.validateChecksum(msc.getAccountNumber().toString());
            
            cardInfo = new CreditCardInfo(name,cardNumber,expirationMonth,expirationYear,false);
            
        }catch(Exception e){
            System.out.println(e);
            if(e.toString().equals("Controller.MagstripeParseException: Missing delimiter [/] in account holder name data")){
                JOptionPane.showMessageDialog(null,"Tarjeta de Chip","",2);
            }
            
        }
    return cardInfo;
    }
    
    public boolean isReaded(){
        return isReaded;
    }
    
    private void putData(CreditCardInfo info){
        name.setText(info.getNombreTarjeta().replace("  ", ""));
        
        String cardNumber = String.valueOf(info.getNumeroTarjeta());
        number.setText(cardNumber.substring(0,4)+"-"+cardNumber.substring(4,8)+"-"+cardNumber.substring(8,12)+"-"+cardNumber.substring(12,16));
        
        
        expiration.setText(String.valueOf(info.getMesFin())+"/"+String.valueOf(info.getAnioFin()));
        
        
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
            java.util.logging.Logger.getLogger(MagneticReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MagneticReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MagneticReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MagneticReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MagneticReader dialog = new MagneticReader(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField in;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
