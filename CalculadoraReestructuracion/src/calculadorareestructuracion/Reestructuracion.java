package calculadorareestructuracion;


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
    private float interesVeoPrevio;
    private float interesVeoPosterior;
    private float doctoFocal;
    private float interesVen;
    private int diasCapitalizacion;
    private String despliegueResultados;
    
    double ven, veo;
        
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
        interesVeoPrevio = Float.parseFloat(panel.getInteresVeoPrevioJTextF().getText()) / 100;
        interesVeoPosterior = Float.parseFloat(panel.getInteresVeoPosteriorJTextF().getText()) / 100;
        doctoFocal = Float.parseFloat(panel.getDoctoFocalJTextF().getText());
        interesVen = Float.parseFloat(panel.getInteresVenJTextF().getText()) / 100;
        diasCapitalizacion = Integer.parseInt(panel.getDiasCapitalizacionJTextF().getText());
        
    }
    
    public void ven(){
        
        
        
    }
    
    public void veo(){
        
    }
    
    
    
}
