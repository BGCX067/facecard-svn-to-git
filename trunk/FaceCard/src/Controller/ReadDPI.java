/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DPIinfo;
import com.google.common.primitives.Bytes;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author giovannirojas <geovaroma@gmail.com>
 */
public class ReadDPI {
    
    private Card card = null;
    private CardChannel channel;
    private Image photo;
    private DPIinfo DPIReaded;
    
    private boolean readed = false;
    /* 
     * Esto es para todos los DPI, deben de tener este formato de ATR, que es la manera de respueseta. 
     */
    private static final byte[][] know_dpi_tars = {
            {(byte) 0x3B, (byte) 0xDB, (byte) 0x96, (byte) 0x00, (byte) 0x80, (byte) 0xB1, (byte) 0xFE, (byte) 0x45, (byte) 0x1F, (byte) 0x83, (byte) 0x00, (byte) 0x31, (byte) 0xC0, (byte) 0x64, (byte) 0xC3, (byte) 0x08, (byte) 0x01, (byte) 0x00, (byte)0x0F, (byte)0x90, (byte)0x00, (byte)0x9B }
    };
    
    
    private static final byte[] firstLine = new byte[]{
         (byte) 160,
         (byte) 0,
         (byte) 0,
         (byte) 0,
         (byte) 119,
         (byte) 1,
         (byte) 131,
         (byte) 131,
         (byte) 8,
         (byte) 16,
         (byte) 0,
         (byte) 241,
         (byte) 0,
         (byte) 0,
         (byte) 0,
         (byte) 1
        
    };
    
    private static final byte[] secondLine = new byte []{
                 (byte) 63,
                 (byte) 0
                 
            };
    
         
            
    private static final byte[] thirdLine = new byte []{
         (byte) 16,
         (byte) 1

    };
    
     private static final byte[] forthLine = new byte []{
                 (byte) 1,
                 (byte) 1
                 
     };
    
     private static final byte[] fifthLine = new byte []{
                 (byte) 0,
                 (byte) 176,
                 (byte) 0,
                 (byte) 0,
                 (byte) 0
                 
    };
     
     
     private static final byte[] sixthLine = new byte []{
                 (byte) 0,
                 (byte) 176,
                 (byte) 0,
                 (byte) 255,
                 (byte) 0
                 
            };
     
     private static final byte[] seventhLine = new byte []{
                 (byte) 1,
                 (byte) 2
                 
            };
     
     private static final byte[] eightLine = new byte []{
                 (byte) 0,
                 (byte) 176,
                 (byte) 0,
                 (byte) 0,
                 (byte) 0
                 
            };
             
     private static final byte[] nineLine = new byte []{
                 (byte) 0,
                 (byte) 0xB0,
                 (byte) 0x00,
                 (byte) 0XFF,
                 (byte) 0
                 
            };
             
     private static final byte[] tenLine = new byte []{
                 (byte) 0x00,
                 (byte) 0xA4,
                 (byte) 0x04,
                 (byte) 0x0C,
                 (byte) 0x07,
                 (byte) 0xA0,
                 (byte) 0x00,
                 (byte) 0x00,
                 (byte) 0x02,
                 (byte) 0x47,
                 (byte) 0x10,
                 (byte) 0x01,
                 
            };        
     
    public DPIinfo readDPI(){
        doDPICardReaderCommunication();
        return DPIReaded;
        
    }
    
    private DPIinfo readDPIinfo() throws CardException
    {
        
        ResponseAPDU response;
        String[] dataDpi= null;
        String dataPrueba = null;
        
            
        try {
            
            card.beginExclusive();
            channel = card.getBasicChannel();
            
// Primera linea      
            
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
            response = channel.transmit(new CommandAPDU(0,164,4,12,firstLine,0));
            
            if (response.getSW1()==144){  // Quiere decir que la respuesta es correcta 144 es 90 en hexadecimal
                // Continuo con la segunda linea
                
                
// Segunda linea      
            
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
            response = channel.transmit(new CommandAPDU(0,164,0,0,secondLine,0));
            
            if (response.getSW1()==144){
                // Continuo con la tercera linea
// Terecera linea   
                 
           
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
            response = channel.transmit(new CommandAPDU(0,164,0,0,thirdLine,0));
            
            if (response.getSW1()==144){
                // Continuo con la cuarta linea
                
// Cuarta linea   
                 
                
           
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
            response = channel.transmit(new CommandAPDU(0,164,2,0,forthLine,0));
            
            if (response.getSW1()==144){
                // Continuo con la quinta linea 
                
// Quinta linea   
                 
                
            
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2
            
            response   = channel.transmit(new CommandAPDU(fifthLine));
            
            if (response.getSW1()==144){
                
                // En este momento es donde se empiezan a ver datos por lo que los convertire. 
                String data = "";
                StringBuilder stringBuilder = null;
                for (int i = 0; i<response.getData().length; i++){
                    data+=(char)(response.getData()[i]);
                    
                }
                
                // Creo mi variable de lista 
                ArrayList<String> dataList = new ArrayList<String>(); 
                // Divido Los datos, lo hago de esta manera por orden. 
                
                String dpi = data.substring(0, 13);
                dataList.add(dpi);
                String primerNombre = data.substring(13,38);
                dataList.add(primerNombre);
                String segundoNombre = data.substring(38,46);
                dataList.add(segundoNombre);
                String tercerNombre = data.substring(46,63);
                dataList.add(tercerNombre);
                String primerApellido = data.substring(63,88);
                dataList.add(primerApellido);
                String segundoApellido = data.substring(88,138);
                dataList.add(segundoApellido);
                String sexo = data.substring(138,147);
                dataList.add(sexo);
                String vecindadMunicipio = data.substring(147,177);
                dataList.add(vecindadMunicipio);
                String vecindadDepartamento = data.substring(177,207);
                dataList.add(vecindadDepartamento);
                String nacionalidad = data.substring(207,237);
                dataList.add(nacionalidad);
                String nacimiento = data.substring(237,247);
                dataList.add(nacimiento);
                String emisionFecha = data.substring(247,256);
                dataList.add(emisionFecha);
                
                //Transformo de lista a array de String 
                Object[] objectList = dataList.toArray();
                dataDpi = Arrays.copyOf(objectList,objectList.length,String[].class);
            
                
                
                
                // Continuo con la sexta linea 
              
// Sexta linea   
                 
                
            
            // Solo de referencia para mantener el formato de las tarjetas dpi, 
            // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
            response = channel.transmit(new CommandAPDU(sixthLine));
            
            if (response.getSW1()==144){
                
           
 // Septima Linea               
            
            response = channel.transmit(new CommandAPDU(0,164,2,0,seventhLine,0));
            
            if (response.getSW1()==144){
                dataPrueba = "";
                for (int i = 0; i<response.getData().length; i++){
                    dataPrueba+=(char)(response.getData()[i]);
                    
                }
               
            
// Octava Linea               
            
            response = channel.transmit(new CommandAPDU(eightLine));
            
            if (response.getSW1()==144){
                dataPrueba = "";
                for (int i = 0; i<response.getData().length; i++){
                    dataPrueba+=(char)(response.getData()[i]);
                    
                }
               
            

 // Novena Linea               
            
            response = channel.transmit(new CommandAPDU(nineLine));
            
            if (response.getSW1()==144){
                dataPrueba = "";
                for (int i = 0; i<response.getData().length; i++){
                    dataPrueba+=(char)(response.getData()[i]);
                    
                }
                
// Decima Linea   

            
            response = channel.transmit(new CommandAPDU(tenLine));
            
            if (response.getSW1()==144){
                dataPrueba = "";
                for (int i = 0; i<response.getData().length; i++){
                    dataPrueba+=(char)(response.getData()[i]);
                    
                }
                              
                
                
                photo = readDPIPhoto(); // de aqui en adelante es informacion de la foto. 
                
                
            }// Fin de la decima linea
            
            }// Fin de la novena linea
            
            }// Fin de la octava linea
            
            }// Fin de la septima linea
                
            }// Fin de la sexta linea
                
            }// fin de la quinta linea    
                
            }// fin de la cuarta linea               
                
            }// fin de la tercera linea 
                      
            }// fin de la segunda linea 
            
            } // fin de la primera linea
            
                        
            
            
            return new DPIinfo(dataDpi,photo);
            

        } finally {
            card.endExclusive();
            readed = true;
                       
        }
    }
    
    
    private Image readDPIPhoto() 
    {
        try {
            // Variables que utilizo para leer el DPI
            byte[] photoAPDUArray = null;
            ResponseAPDU response;
            String[] dataDpi= null;
            String dataPrueba = null;
                 
                 
            
            ArrayList<Byte> fotoBytes = new ArrayList<Byte>();
            
            // Onceava Linea   

                photoAPDUArray = new byte []{
                     (byte) 0x00,
                     (byte) 0xB0,
                     (byte) 0x82,
                     (byte) 0x00,
                     (byte) 0x00
                        
                     
                     
                
                     
                     
                };
                response = channel.transmit(new CommandAPDU(photoAPDUArray));

                /* En formato de JPG la imagen debe de iniciar con el byte 0xFF luego de 0xD8 */
                
                if (response.getSW1()==144){
                    System.out.println(response.getData().length);
                    dataPrueba = "";
                    for (int i = 104; i<response.getData().length; i++){
                        dataPrueba+=(char)(response.getData()[i]);
                        fotoBytes.add(response.getData()[i]);

                    }
               
                    
                        
     // Aqui corresponde los bits de la foto. 
                 for (int b=1; b<90; b++){
                  
                    
                    photoAPDUArray = new byte []{

                        (byte) 0x00,
                        (byte) 0xB0,
                        (byte) b,
                        (byte) 0x00,
                        (byte) 0x00
                            
                    };
                    response = channel.transmit(new CommandAPDU(photoAPDUArray));

                    if (response.getSW1()==144){
                        dataPrueba = "";
                        for (int i = 0; i<response.getData().length; i++){
                            dataPrueba+=(char)(response.getData()[i]);
                            fotoBytes.add(response.getData()[i]);

                        }


                       
              

                    }            
                 }
                  
                        
                    
                } // Fin de la 11 linea 
            
             byte[] photoBytes = Bytes.toArray(fotoBytes);
             return new ImageIcon(photoBytes).getImage();  // retorno la imagen. 
            
        } catch (CardException ex) {
            // Esto es para que de un error. 
            DPIReaded=null;
            return null;
        }
        finally{
            readed = true;
        }
    }
    
    private void doDPICardReaderCommunication() {
        
                TerminalFactory terminalFactory = TerminalFactory.getDefault();
                try {
                    List<CardTerminal> cardTerminalList = terminalFactory.terminals().list();
                    if (cardTerminalList.size() > 0) {
                        System.out.println("Congratulations, setup is working. At least 1 cardreader is detected");
                        CardTerminal cardTerminal = cardTerminalList.get(0);
                        while (true) {
                            cardTerminal.waitForCardPresent(1000);
                            System.out.println("Inserted card");
                            handleDPI(cardTerminal);
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
                    // Esto es para que de un error. 
                    DPIReaded=null;
                }
                finally{
                    readed = true;
                }
                
     }
    
    private void handleDPI(CardTerminal cardTerminal) throws InterruptedException {
        card = null;
        try {
            card = cardTerminal.connect("*");
            ATR atr = card.getATR();
            if (knownATR(card.getATR().getBytes())) {
                try {
                   /*  channel = card.getBasicChannel();
            // Primera linea      
           
                    // Solo de referencia para mantener el formato de las tarjetas dpi, 
                    // lo que se esta mandando es cl_cla, cl_ins_get_uid, p1, p2, datos de referencia, tamanio de vuelta
                    CommandAPDU command = new CommandAPDU(0,164,4,12,firstLine,0);
                    
                    
                    ResponseAPDU response = channel.transmit(command);
                    byte[] uidBytes = response.getData();
                    final String uid = readable(uidBytes);
                    // Aqui debo de cambiar algo. !! 
                    
                    
                    JOptionPane.showMessageDialog(null, uid);*/
                    DPIReaded = readDPIinfo();
                    
                    
                } catch (CardException e) {
                    JOptionPane.showMessageDialog(null, "Sucedió un error al momento de leer el ATR de la tarjeta","Error en lectura",0);
                    
                    e.printStackTrace();
                    // Esto es para que de un error. 
                    DPIReaded=null;
                }
                finally {
                    readed = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No es un DPI, favor verificar y volverlo a intentar","Error en lectura",0);
                    
            }
        } catch (CardException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer el DPI, vuelvalo a intentar","No hay DPI",0);
            // Esto es para que de un error. 
            DPIReaded=null;
        }
        finally{
            readed = true;
        }
    }
    
    /*
     * Este metodo mira el atr de la tarjeta y evalua si es un DPI, 
     * reciviendo como parametro el atr de la tarjeta que esta metiendo. 
     */
    private boolean knownATR(byte[] card_atr)
    {
        for(byte[] eid_atr:know_dpi_tars)
        {
            if(Arrays.equals(card_atr, eid_atr)) return true;
        }
        return false;
    }
    
      
    // Getter
    public boolean isReaded() {
        return readed;
    }
    
   
    
    
}
