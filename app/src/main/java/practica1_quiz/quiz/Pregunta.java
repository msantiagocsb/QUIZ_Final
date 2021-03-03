package practica1_quiz.quiz;

import java.util.ArrayList;

public class Pregunta {
    private String pregunta;
    private ArrayList<Respuesta> respuestas=new ArrayList<>();
    private String imagen;//aqui se guarda si la pregunta es con imagen
    private boolean hayImagen;//guarda un true si la pregunta tiene imagen;
    private boolean preguntada;
    private boolean respuestaImagenes;

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean getPreguntada() {
        return preguntada;
    }

    public void setPreguntada(boolean preguntada) {
        this.preguntada = preguntada;
    }

    public boolean getHayImagen() {
        return hayImagen;
    }

    public void setHayImagen(boolean hayImagen) {
        this.hayImagen = hayImagen;
    }

    //aquí hay que poner el atributo imagen?
    public Pregunta(String pregunta, String respuesta1,boolean r1, String respuesta2,boolean r2, String respuesta3,boolean r3, String respuesta4, boolean r4){//constructor pregunta sin imagen
        this.pregunta=pregunta;
        respuestas.add(new Respuesta(respuesta1,r1));
        respuestas.add(new Respuesta(respuesta2,r2));
        respuestas.add(new Respuesta(respuesta3,r3));
        respuestas.add(new Respuesta(respuesta4,r4));
        this.imagen=null;
        this.hayImagen=false;
        this.preguntada=false;
        respuestaImagenes=false;
    }
    public Pregunta(String pregunta, String respuesta1,boolean r1, String respuesta2,boolean r2, String respuesta3,boolean r3, String respuesta4, boolean r4,String imagen){//constructor de pregunta con imagen
        this.pregunta=pregunta;
        respuestas.add(new Respuesta(respuesta1,r1));
        respuestas.add(new Respuesta(respuesta2,r2));
        respuestas.add(new Respuesta(respuesta3,r3));
        respuestas.add(new Respuesta(respuesta4,r4));
        this.imagen=imagen;
        this.hayImagen=true;
        preguntada=false;
        respuestaImagenes=false;
    }





    public boolean getRespuestaImagenes() {
        return respuestaImagenes;
    }

    public void setRespuestaImagenes(boolean respuestaImagenes) {
        this.respuestaImagenes = respuestaImagenes;
    }

    public Pregunta(String pregunta, String respuesta1, boolean r1, String respuesta2, boolean r2, String respuesta3, boolean r3, String respuesta4, boolean r4, boolean respuestaImagenes){//constructor de pregunta con imagen
        this.pregunta=pregunta;
        respuestas.add(new Respuesta(respuesta1,r1));
        respuestas.add(new Respuesta(respuesta2,r2));
        respuestas.add(new Respuesta(respuesta3,r3));
        respuestas.add(new Respuesta(respuesta4,r4));
        this.imagen=imagen;
        this.hayImagen=false;
        preguntada=false;
        this.respuestaImagenes=respuestaImagenes;
    }

    public void setPregunta(String pregunta){
        this.pregunta=pregunta;
    }
    public String getPregunta(){
        return this.pregunta;
    }
    public Respuesta getRespuesta(int i){//devuelve una a una las 4 posibles respuestas
        return respuestas.get(i);
    }
    public boolean comprobar(Respuesta elegida){//no se si hay que hacerlo en otro lado pero es para comprobar si la elegida es la correcta
        return elegida.getValidacion();
        }
    }


