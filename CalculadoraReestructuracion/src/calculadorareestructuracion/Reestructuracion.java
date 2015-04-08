package calculadorareestructuracion;

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
        try{
        asignarValores();
        }catch(Exception e){
            despliegueResultados = "Revise los datos que esta introduciendo, hay un error.";
        }
        
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
            auxiliar1 = redondeo(auxiliar1);
            veo += auxiliar1;
            veo = redondeo(veo);
            
        }
        
        veo+= doctoFocalBD;
        veo = redondeo(veo);
                
        i = 0;
        for (String doctoPosterior : doctosPosteriores) {
            dia = Integer.parseInt(diasPosteriores[i]);
            i++;
            docto =Float.parseFloat(doctoPosterior);
            auxiliar1 = calculoComun(interesVeoPosteriorBD, dia);
            docto /= auxiliar1;
            docto = redondeo(docto);
            veo += docto;
            veo = redondeo(veo);
            
        }
        
    }
    
    public void ven(){
        double dia, total, potencia;
        for (String diaPago : diasPagos) {
            dia = Double.parseDouble(diaPago);
            
            potencia = calculoComun(interesVenBD, dia);
            total = 1 / potencia;
            total = redondeo(total);
            ven += total;
            ven = redondeo(ven);
            
        }
                
    }
    
    private double redondeo(double numero){
        DecimalFormat formato = new DecimalFormat("#.########");
        String auxiliar = formato.format(numero);
        int cont = 1;
        for (int i = auxiliar.length(); i == 0 ; i--) {
            if (cont == 9 && auxiliar.charAt(i) == '.') {
                auxiliar = auxiliar.replace((auxiliar.charAt(auxiliar.length()-1) +""), "");
                numero = Double.parseDouble(auxiliar);
            }
            if (cont > 9) {
                break;
            }
            cont++;
        }
        
        return numero;
    }

    private double calculoComun(double interesBD, double dia) {
            double potencia, exponente;
            
            interesBD /= diasAnioBD;
            interesBD *= diasCapitalizacionBD;
            interesBD ++;
            interesBD = redondeo(interesBD);
                        
            exponente = dia / diasCapitalizacionBD;
            
            exponente = redondeo(exponente);
            
            potencia = Math.pow(interesBD, exponente);
            potencia = redondeo(potencia);
            
            return potencia;
    }
    
    public void calcularPagos(){
        ven();
        veo();
        
        veo = Math.rint(veo * 100)/100;
        ven = Math.rint(ven * 10000000)/10000000;
        double pagoTotal = Math.rint(veo/ven*100)/100;
        pagoTotal = redondeo(pagoTotal);
        double pagoReal = Math.rint(pagoTotal * diasPagos.length * 100)/100;
        
                
        despliegueResultados = "Valor de Esquema Ordinario (VEO): $" + veo + "\n" +
                                "Valor de Esquema Nuevo (VEN): " + ven + "\n" +
                                diasPagos.length + " pagos de $"  + pagoTotal  + "\n"
                                + "Realmente pagará $" + pagoReal;
       
    }

    public String getDespliegueResultados() {
        return despliegueResultados;
    }
    
    
}
