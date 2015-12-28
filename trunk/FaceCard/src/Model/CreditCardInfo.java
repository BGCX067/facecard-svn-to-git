/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Image;

/**
 *
 * @author giovannirojas
 */
public class CreditCardInfo {
    
    private String nombreTarjeta = "";
    private int mesFin=-1;
    private int anioFin=-1;
    private String numeroTarjeta="";
    private Image photo;
    private boolean SIMCARD = false;

    public CreditCardInfo(String nameCard,String numberCard, int monthEnd, int yearEnd,boolean simCard) {
        this.anioFin = yearEnd;
        this.mesFin = monthEnd;
        this.nombreTarjeta = nameCard;
        this.numeroTarjeta = numberCard;
        this.SIMCARD = simCard;
        
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public int getMesFin() {
        return mesFin;
    }

    public int getAnioFin() {
        return anioFin;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public Image getPhoto() {
        return photo;
    }

    public boolean isSIMCARD() {
        return SIMCARD;
    }
    
    
    
    
    
    
    
    
    
}
