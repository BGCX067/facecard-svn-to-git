/*
 Giovanni Rojas Mazarieogs 
 */

package Model;

import java.awt.Image;





/**
 * 
 * 
 * @author Giovanni  Rojas <geovaroma@gmail.com>
 */
public class DPIinfo {

    
    
    /*Datos del Dpi Los siguientes son a los correspondientes de la linea 5 */
    private String dpi ;
    private String primerNombre ;
    private String segundoNombre ;
    private String tercerNombre ;
    private String primerApellido ;
    private String segundoApellido ;
    private String sexo ;
    private String vecindadMunicipio ;
    private String vecindadDepartamento ;
    private String nacionalidad ;
    private String nacimiento  ;
    private String emisionFecha ;
    private Image photo;

    
    /*Constructor of DPIinfo */
    
    public DPIinfo(String [] dataDPI, Image photo){
        setDataInfo(dataDPI);
        // Instancio la foto. 
        this.photo = photo;
        
    }
    
    private void setDataInfo(String [] dataDPI){
        
        this.dpi = dataDPI[0];
        this.primerNombre = dataDPI[1];
        this.segundoNombre = dataDPI[2];
        this.tercerNombre = dataDPI[3];
        this.primerApellido = dataDPI[4];
        this.segundoApellido = dataDPI[5];
        this.sexo = dataDPI[6];
        this.vecindadMunicipio = dataDPI[7];
        this.vecindadDepartamento = dataDPI[8];
        this.nacionalidad = dataDPI[9];
        this.nacimiento = dataDPI[10];
        this.emisionFecha = dataDPI[11];
        
        
        
    }

    public Image getPhoto() {
        return photo;
    }

    public String getDpi() {
        return dpi;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getTercerNombre() {
        return tercerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getSexo() {
        return sexo;
    }

    public String getVecindadMunicipio() {
        return vecindadMunicipio;
    }

    public String getVecindadDepartamento() {
        return vecindadDepartamento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public String getEmisionFecha() {
        return emisionFecha;
    }
    
    
    

    

    
}
