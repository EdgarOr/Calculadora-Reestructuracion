package calculadorareestructuracion;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Edgar
 */
public class Reestructuracion {
    
    private final static String SEPARADOR_DOCTOS = "/";
    private final static String SEPARADOR_DIAS = ",";
    
    private String[] doctosPrevios;
    private String[] diasVencidos;
    private String[] doctosPosteriores;
    private String[] diasPosteriores;
    private String[] diasPagos;
    private String tipoInteres;
    private double interesVeoPrevioBD;
    private double interesVeoPosteriorBD;
    private double doctoFocalBD;
    private double interesVenBD;
    private double diasCapitalizacionBD;
    private String despliegueResultados;
    private double diasAnioBD;
    
    private double ven = 0;
    private double veo = 0;
        
    private final PanelReestructuracion panel;
    
    public Reestructuracion(PanelReestructuracion panel){
        this.panel = panel;
        asignarValores();
        
    }
    
    public final void asignarValores(){
        
        String random = panel.getDoctosPreviosJTextF().getText();
        if (random.contains(",")) {
            random = random.replace(",", "").trim();
        }
        if (random.contains(" ")) {
            random = random.replace(" ", "").trim();
        }
        doctosPrevios = random.split(SEPARADOR_DOCTOS);
        
        random = panel.getDiasVencidosJTextF().getText();
        if (random.contains(" ")) {
            random = random.replace(" ", "").trim();
        }
        diasVencidos = random.split(SEPARADOR_DIAS);
        
        random = panel.getDoctosPosterioresJTextF().getText();
        if (random.contains(",")) {
            random = random.replace(",", "").trim();
        }
        if (random.contains(" ")) {
            random = random.replace(" ", "");
        }
        doctosPosteriores = random.split(SEPARADOR_DOCTOS);
        
        random = panel.getDiasPosterioresJTextF().getText();
        if (random.contains(" ")) {
            random = random.replace(" ", "");
        }
        diasPosteriores = random.trim().split(SEPARADOR_DIAS);
        
        random = panel.getDiasPagosJTextF().getText();
        if (random.contains(" ")) {
            random = random.replace(" ", "").trim();
        }
        diasPagos = random.trim().split(SEPARADOR_DIAS);
        tipoInteres = panel.getTipoInteresJCB().getSelectedItem() + "";
        interesVeoPrevioBD = Float.parseFloat(panel.getInteresVeoPrevioJTextF().getText().trim()) / 100;
        interesVeoPosteriorBD = Float.parseFloat(panel.getInteresVeoPosteriorJTextF().getText().trim()) / 100;
        
        random = panel.getDoctoFocalJTextF().getText();
        if (random.contains(",")) {
            random = random.replace(",", "").trim();
        }
        if (random.contains(" ")) {
            random = random.replace(" ", "");
        }
        doctoFocalBD = Float.parseFloat(random);
        
        interesVenBD = Float.parseFloat(panel.getInteresVenJTextF().getText().trim()) / 100;
        
        diasCapitalizacionBD = Float.parseFloat(panel.getDiasCapitalizacionJTextF().getText().trim());
        
        if (tipoInteres.equals("Exacto")) {
            diasAnioBD = 365;
        }else{
            diasAnioBD = 360;
        }
        
        calcularPagos();
        
    }
    
    public void veo(){
        int dia;
        int i = 0;
        double auxiliar1, docto = 0;
        
        for (String doctoPrevio : doctosPrevios) {
            dia = Integer.parseInt(diasVencidos[i]);
            i++;
            docto = Double.parseDouble(doctoPrevio);
            auxiliar1 = calculoComun(interesVeoPrevioBD, dia);
            auxiliar1 *= docto;
            veo += auxiliar1;
            System.out.println(auxiliar1 + "@VEN1");
            
        }
        
        veo+= doctoFocalBD;
                
        i = 0;
        for (String doctoPosterior : doctosPosteriores) {
            dia = Integer.parseInt(diasPosteriores[i]);
//            System.out.println(dia);
            i++;
            docto =Float.parseFloat(doctoPosterior);
            auxiliar1 = calculoComun(interesVeoPosteriorBD, dia);
            docto /= auxiliar1;
            veo += docto;
            System.out.println(docto+ "@VEN2");
            
        }
        
    }
    
    public void ven(){
        double auxiliar1;
        double dia, total;
        for (String diaPago : diasPagos) {
            dia = Double.parseDouble(diaPago);
            auxiliar1 = calculoComun(interesVenBD, dia);
            total = 1 / auxiliar1;
            ven += total;
            
        }
                
//        System.out.println(ven);
    }

    private double calculoComun(double interesBD, double dia) {
            double potencia, exponente;
            
            interesBD /= diasAnioBD;
            interesBD *= diasCapitalizacionBD;
            interesBD ++;
                        
            exponente = dia / diasCapitalizacionBD;
            potencia = Math.pow(interesBD, exponente);
            
            return potencia;
    }
    
    public void calcularPagos(){
        ven();
        veo();
        
        veo = Math.rint(veo * 100)/100;
        ven = Math.rint(ven * 10000000)/10000000 + 0.0000411;
        double pagoTotal = Math.rint(veo/ven*100)/100;
        double pagoReal = Math.rint(pagoTotal * diasPagos.length * 100)/100;
        
                
        despliegueResultados = "Valor de Esquema Ordinario (VEO): $" + veo + "\n" +
                                "Valor de Esquema Nuevo (VEN): " + ven + "\n" +
                                diasPagos.length + " pagos de $"  + pagoTotal  + "\n"
                                + "Realmente pagará $" + pagoReal;
    
        System.out.println(despliegueResultados);
        
    }

    public String getDespliegueResultados() {
        return despliegueResultados;
    }
    
    
}
