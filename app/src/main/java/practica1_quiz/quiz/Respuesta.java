package practica1_quiz.quiz;

public class Respuesta {
    private boolean validacion;
    private String respuesta;
    public Respuesta(String respuesta, boolean validacion){
        this.respuesta=respuesta;
        this.validacion=validacion;
    }

    public void setValidacion(boolean validacion){
        this.validacion=validacion;
    }
    public void setString(String respuesta){
        this.respuesta=respuesta;
    }
    public boolean getValidacion(){
        return this.validacion;
    }
    public String getRespuesta(){
        return this.respuesta;
    }
}
