/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CreditCardInfo;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author giovannirojas
 */
public class ReadCreditCardSIM {
    
    private Card card = null;
    private CardChannel channel;
    private Image photo; // Have to work on it . 
    private CreditCardInfo cardReaded;
    
    private boolean readed= false;
    
    
    private static final byte[][] visa_BI_ATR = {
        {(byte) 0x3B, (byte) 0x7F, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x31, (byte) 0xC0, (byte) 0x52, (byte) 0x16, (byte) 0x01, (byte) 0x64, (byte) 0x05, (byte) 0x69, (byte) 0x93, (byte) 0x70, (byte) 0x83, (byte) 0x83, (byte) 0x90, (byte) 0x00 } 
    };
    
    private static final byte[][] visa_City_ATR = {
        { (byte) 0x3B, (byte) 0x7F, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x31, (byte) 0xC0, (byte) 0x52, (byte) 0x15, (byte) 0x83, (byte) 0x64, (byte) 0x02, (byte) 0xB3, (byte) 0x02, (byte) 0x70, (byte) 0x83, (byte) 0x83, (byte) 0x90, (byte) 0x00 }
    };
    
    
    // Lo que debo de meter para reconocer las tarjetas. 
    
    private static final byte[] firstLine = new byte []{
                 (byte) 0x00,
                 (byte) 0xA4,
                 (byte) 0X04,
                 (byte) 0X00,
                 (byte) 0X0E,
                 (byte) 0X31,
                 (byte) 0X50,
                 (byte) 0X41,
                 (byte) 0X59,
                 (byte) 0X2E,
                 (byte) 0X53,
                 (byte) 0X59,
                 (byte) 0X53,
                 (byte) 0X2E,
                 (byte) 0X44,
                 (byte) 0X44,
                 (byte) 0X46,
                 (byte) 0X30,
                 (byte) 0X31
            };
    
    private static final byte[] secondLine = new byte []{
                 (byte) 0x00,
                 (byte) 0xB2,
                 (byte) 0X01,
                 (byte) 0X0C,
                 (byte) 0X00,
                 
            };
    
            
    private static final byte[] thirdLine = new byte []{
                 (byte) 0x00,
                 (byte) 0xA4,
                 (byte) 0x04,
                 (byte) 0x00,
                 (byte) 0x07,
                 (byte) 0xA0,
                 (byte) 0x00,
                 (byte) 0x00,
                 (byte) 0x00,
                 (byte) 0x03,
                 (byte) 0x10,
                 (byte) 0x10
                 
                 
            };
    
                     
            
    private static final byte[] forthLine = new byte []{
                 (byte) 0x00, 
                 (byte) 0xB2,
                 (byte) 0x01,
                 (byte) 0x0C,
                 (byte) 0x4F
                 
                
            };
    
    
    
    public CreditCardInfo readCard(){
        doCreditCardReaderCommunication();
        
        return cardReaded;
        
    }
    
    private CreditCardInfo readCardInfo() throws CardException{
        
        ResponseAPDU response;
        CreditCardInfo creditCard = null;
        
        try {
            
            card.beginExclusive();
            channel = card.getBasicChannel();
            
// "1PAY.SYS.DDF01"    
            
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
            response = channel.transmit(new CommandAPDU(firstLine));
            
            if (response.getSW1()==144){  // Quiere decir que la respuesta es correcta 144 es 90 en hexadecimal
                
                
                response = channel.transmit(new CommandAPDU(secondLine));
                
                
                
                if (response.getSW1()==144){
                    
                   
                
                    response = channel.transmit(new CommandAPDU(thirdLine));
                    
                    
                    if (response.getSW1()==144){
                        
                        
              
                        response = channel.transmit(new CommandAPDU(forthLine));
                        
                        
                        
                        if (response.getSW1()==144){
                            
                            
                            // Separa el numero de la tarjeta de todo. 
                            StringBuilder sb = new StringBuilder();
                            for(byte b: response.getData())
                                sb.append(String.format("%02x", b&0xff).toUpperCase());
                            
                            
                            String cardNumber = sb.substring(8, 24);
                            String dateEnd = sb.substring(25, 29);
                        
                        
                            // Aqui es donde obtendre el nombre de la tarjeta. 
                            String data = "";
                        
                            for (int i = 0; i<response.getData().length; i++)
                                data+=(char)(response.getData()[i]);
                        
                            String cardName = data.substring(26, 52);
                            
                            creditCard = new CreditCardInfo (cardName,cardNumber,Integer.parseInt(dateEnd.substring(2,4)),Integer.parseInt(dateEnd.substring(0,2)),true);
                            
                        } // Fin de la cuarta 
                        
                    }// Fin de la tercer
                    
                } // Fin de la segunda linea. 
               
            } // Fin de la primera linea. 
        
        return creditCard;
        
        }finally {
            card.endExclusive();
            readed=true;
                       
        }
    }
    
    private void doCreditCardReaderCommunication() {
        
                TerminalFactory terminalFactory = TerminalFactory.getDefault();
                try {
                    List<CardTerminal> cardTerminalList = terminalFactory.terminals().list();
                    if (cardTerminalList.size() > 0) {
                        System.out.println("Congratulations, setup is working. At least 1 cardreader is detected");
                        CardTerminal cardTerminal = cardTerminalList.get(0);
                        while (true) {
                            cardTerminal.waitForCardPresent(1000);
                            System.out.println("Inserted card");
                            handleCard(cardTerminal);
                            cardTerminal.waitForCardAbsent(1000);
                            System.out.println("Removed card");
                            break; // Force to stop reading. ! evitando que se enciclie. !
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se detecto lector de tarjetas","Sin lector de tarjetas",0);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Sucedió un error al momento de leer la tarjeta ","Error en lectura",0);
                    
                    e.printStackTrace();
                    cardReaded = null; // por si da problemas
                }
                finally{
                    readed=true;
                }
                
     }
    
    private void handleCard(CardTerminal cardTerminal) throws InterruptedException {
        card = null;
        try {
            card = cardTerminal.connect("*");
            
            if (knownATR(card.getATR().getBytes())) {
                try {
                  
                    cardReaded = readCardInfo();
                    
                    
                } catch (CardException e) {
                    JOptionPane.showMessageDialog(null, "Sucedió un error al momento de leer el ATR de la tarjeta","Error en lectura",0);
                    
                    e.printStackTrace();
                    cardReaded = null; // por si da problemas. 
                }
                finally {
                    readed = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No es una tarjeta de crédito válida, favor verificar y volverlo a intentar","Error en lectura",0);
                    
            }
        } catch (CardException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer la tarjeta de crédito, vuelvalo a intentar","No hay tarjeta de crédito",0);
            cardReaded = null; // por si da problemas. 
        }
        finally {
            readed = true;
        }
    }
    
    /*
     * reciviendo como parametro el atr de la tarjeta de crédito que esta metiendo. 
     */
    private boolean knownATR(byte[] card_atr)
            
    {
        
        /* Util para obtener el ATR e imprimirlo en HEX. */
       /* StringBuilder sb = new StringBuilder();
        for(byte b: card.getATR().getBytes())
            sb.append("(byte) 0x").append(String.format("%02x", b&0xff).toUpperCase()).append(", ");
        System.out.println(sb.toString()); 
        */
        
        
        // Aqui verifica si es visa del Banco Industrial 
        for(byte[] eid_atr:visa_BI_ATR)
        {
            if(Arrays.equals(card_atr, eid_atr)) return true;
        }
        
        // Aqui verifica si es visa del Banco CitiBank 
        for(byte[] eid_atr:visa_City_ATR)
        {
            if(Arrays.equals(card_atr, eid_atr)) return true;
        }
        
        
        
        return false;
    }
    
    
    //Getter del Readed
    public boolean isReaded() {
        return readed;
    }
    
    
}
