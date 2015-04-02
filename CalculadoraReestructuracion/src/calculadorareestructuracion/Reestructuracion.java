package calculadorareestructuracion;

import java.math.BigDecimal;
import java.text.DecimalFormat;


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
    private BigDecimal interesVeoPrevioBD;
    private BigDecimal interesVeoPosteriorBD;
    private BigDecimal doctoFocalBD;
    private BigDecimal interesVenBD;
    private BigDecimal diasCapitalizacionBD;
    private String despliegueResultados;
    private BigDecimal diasAnioBD;
    
    private BigDecimal ven= new BigDecimal(0);
    private BigDecimal veo = diasCapitalizacionBD;
        
    private final PanelReestructuracion panel;
    
    public Reestructuracion(PanelReestructuracion panel){
        this.panel = panel;
        asignarValores();
        
    }
    
    public final void asignarValores(){
        
        String random = panel.getDoctosPreviosJTextF().getText();
        if (random.contains(",")) {
            random = random.replace(",", "");
        }
        doctosPrevios = random.split(SEPARADOR_DOCTOS);
        
        diasVencidos = panel.getDiasVencidosJTextF().getText().split(SEPARADOR_DIAS);
        
        random = panel.getDoctosPosterioresJTextF().getText();
        if (random.contains(",")) {
            random = random.replace(",", "");
        }
        doctosPosteriores = random.split(SEPARADOR_DOCTOS);
        
        diasPosteriores = panel.getDiasPosterioresJTextF().getText().split(SEPARADOR_DIAS);
        diasPagos = panel.getDiasPagosJTextF().getText().split(SEPARADOR_DIAS);
        tipoInteres = panel.getTipoInteresJCB().getSelectedItem() + "";
        double interesVeoPrevio = Double.parseDouble(panel.getInteresVeoPrevioJTextF().getText()) / 100;
        interesVeoPrevioBD = new BigDecimal(interesVeoPrevio);
        double interesVeoPosterior = Double.parseDouble(panel.getInteresVeoPosteriorJTextF().getText()) / 100;
        interesVeoPosteriorBD = new BigDecimal(interesVeoPosterior);
        
        random = panel.getDoctoFocalJTextF().getText();
        if (random.contains(",")) {
            random = random.replace(",", "");
        }
        doctoFocalBD = new BigDecimal(random);
        
        double interesVen = Double.parseDouble(panel.getInteresVenJTextF().getText()) / 100;
        interesVenBD = new BigDecimal(interesVen);
        
        diasCapitalizacionBD = new BigDecimal(panel.getDiasCapitalizacionJTextF().getText());
        
        if (tipoInteres.equals("Exacto")) {
            diasAnioBD = new BigDecimal(365);
        }else{
            diasAnioBD = new BigDecimal(360);
        }
        
    }
    
    public void veo(){
        double dia;
        int i = 0;
        BigDecimal auxiliar, docto = new BigDecimal(0);
        docto.setScale(7, BigDecimal.ROUND_HALF_UP);
            
        for (String doctoPrevio : doctosPrevios) {
            dia = Double.parseDouble(diasVencidos[i]);
            i++;
            docto = new BigDecimal(doctoPrevio);
            auxiliar = calculoComun(interesVeoPrevioBD, dia);
            auxiliar.multiply(docto);
            veo.add(auxiliar);
        }
                
        i = 0;
        for (String doctoPosterior : doctosPosteriores) {
                    
            dia = Double.parseDouble(diasPosteriores[i]);
            i++;
            docto = new BigDecimal(doctoPosterior);
            auxiliar = calculoComun(interesVeoPosteriorBD, dia);
            docto.divide(auxiliar);
            veo.add(auxiliar);
            
            
            
        }
        
        
    }
    
    public void ven(){
        BigDecimal auxiliar;
        double dia, total;
        for (String diaPago : diasPagos) {
            dia = Double.parseDouble(diaPago);
            auxiliar = calculoComun(interesVenBD, dia);
            total = 1 / auxiliar.doubleValue();
            auxiliar = new BigDecimal(total);
            ven.add(auxiliar);
            
            
        }
    }

    private BigDecimal calculoComun(BigDecimal interesBD, double dia) {
            BigDecimal potenciaBD;
            double potencia, exponente = 0;
            interesBD.setScale(7, BigDecimal.ROUND_HALF_UP);

            interesBD.divide(diasAnioBD);
            interesBD.multiply(diasCapitalizacionBD);
            interesBD.add(BigDecimal.ONE);
            
            exponente = dia / diasCapitalizacionBD.intValue();
            potencia = Math.pow(interesBD.doubleValue(), exponente);
            potenciaBD = new BigDecimal(potencia);
            potenciaBD.setScale(7, BigDecimal.ROUND_HALF_UP);
            
            return potenciaBD;
    }
    
    public void calcularPagos(){
        ven();
        veo();
        
        BigDecimal pagoTotal = veo;
        
        pagoTotal.divide(ven);
        
        despliegueResultados = "";
    }
    
}
