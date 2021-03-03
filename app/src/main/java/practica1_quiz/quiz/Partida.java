package practica1_quiz.quiz;

import java.util.ArrayList;

public class Partida {
    private int puntuacion;
    private int numero_pregunta;
    private Usuario usuario;
    private ArrayList<Pregunta> preguntas_partida=new ArrayList<>();
    private int numeroDePreguntas;
    private int acertadas;
    private int falladas;
    private int numerodePreguntasConText;
    private Musica sonidoAcierto, sonidoFallo,sonidoTiempo, sonidoMejorPuntuacion,sonidoSinMejora,musicaDePartida;
    private boolean musicaMenu,musicaPreguntas, efectosPreguntas;



    public Partida(String info, int puntuacion){
        this.puntuacion=puntuacion;
        numero_pregunta=0;
        //this.preguntas_partida=new ArrayList<>();
        this.usuario =new Usuario(info,"");
        this.numeroDePreguntas=0;
        this.acertadas=0;
        this.falladas=0;
        musicaMenu=true;
        musicaPreguntas=true;
        efectosPreguntas=true;



    }

    public Partida(ArrayList<Pregunta> preguntas_partida, Usuario usuario,int numeroDePreguntas){
        puntuacion=0;
        numero_pregunta=0;
        //this.preguntas_partida=new ArrayList<>();
        for(int i=0;i<preguntas_partida.size();i++){
            this.preguntas_partida.add(preguntas_partida.get(i));

        }
        this.usuario =usuario;
        this.numeroDePreguntas=numeroDePreguntas;
        this.acertadas=0;
        this.falladas=0;
        musicaMenu=true;
        musicaPreguntas=true;
        efectosPreguntas=true;
        //this.ranking=new Ranking();


    }

    public Partida(Partida partida){
        puntuacion=0;
        numero_pregunta=0;
        //this.preguntas_partida=new ArrayList<>();
        for(int i=0;i<partida.getPreguntas_partida().size();i++){
            this.preguntas_partida.add(partida.getPreguntas_partida().get(i));

        }
        this.usuario =new Usuario(partida.getUsuario());
        this.numeroDePreguntas=partida.getNumeroDePreguntas();
        this.acertadas=0;
        this.falladas=0;
        musicaMenu=partida.getMusicaMenu();
        musicaPreguntas=partida.getMusicaPreguntas();
        efectosPreguntas=partida.getEfectosPreguntas();
        this.sonidoAcierto=partida.getSonidoAcierto();
        this.sonidoFallo=partida.getSonidoFallo();
        this.sonidoMejorPuntuacion=partida.getSonidoMejorPuntuacion();
        this.sonidoSinMejora=partida.getSonidoSinMejora();
        this.musicaDePartida=partida.getMusicaDePartida();
        this.musicaMenu=partida.getMusicaMenu();
        //this.ranking=new Ranking();


    }



    public int getAcertadas() {
        return acertadas;
    }

    public void setAcertadas(int acertadas) {
        this.acertadas = acertadas;
    }

    public int getFalladas() {
        return falladas;
    }

    public void setFalladas(int falladas) {
        this.falladas = falladas;
    }

    public int getNumeroDePreguntas() {
        return numeroDePreguntas;
    }

    public void setNumeroDePreguntas(int numeroDePreguntas) {
        this.numeroDePreguntas = numeroDePreguntas;
    }

    public int getNumero_pregunta() {
        return numero_pregunta;
    }

    public void setNumero_pregunta(int numero_pregunta) {
        this.numero_pregunta = numero_pregunta;
    }

    public ArrayList<Pregunta> getPreguntas_partida() {
        return preguntas_partida;
    }

    public void setPreguntas_partida(ArrayList<Pregunta> preguntas_partida) {
        this.preguntas_partida = preguntas_partida;
    }

    public int getPuntuacion() {

        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    public void incrementarNumPregunta(){
        this.numero_pregunta++;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return this.usuario.getNick();
    }

    public void setNombre(String nombre) {
        this.usuario.setNick(nombre);
    }
    public void setSonidoAcierto(Musica sonidoAcierto) {
        this.sonidoAcierto = sonidoAcierto;
    }

    public void setSonidoFallo(Musica sonidoFallo) {
        this.sonidoFallo = sonidoFallo;
    }

    public void setSonidoTiempo(Musica sonidoTiempo) {
        this.sonidoTiempo = sonidoTiempo;
    }

    public void setSonidoMejorPuntuacion(Musica sonidoMejorPuntuacion) {
        this.sonidoMejorPuntuacion = sonidoMejorPuntuacion;
    }

    public void setSonidoSinMejora(Musica sonidoSinMejora) {
        this.sonidoSinMejora = sonidoSinMejora;
    }

    public void setMusicaDePartida(Musica musicaDePartida) {
        this.musicaDePartida = musicaDePartida;
    }

    public Musica getSonidoAcierto() {

        return sonidoAcierto;
    }

    public Musica getSonidoFallo() {
        return sonidoFallo;
    }

    public Musica getSonidoTiempo() {
        return sonidoTiempo;
    }

    public Musica getSonidoMejorPuntuacion() {
        return sonidoMejorPuntuacion;
    }

    public Musica getSonidoSinMejora() {
        return sonidoSinMejora;
    }

    public Musica getMusicaDePartida() {
        return musicaDePartida;
    }

    public int getNumerodePreguntasConText() {
        return numerodePreguntasConText;
    }

    public void setNumerodePreguntasConText(int numerodePreguntasConText) {
        this.numerodePreguntasConText = numerodePreguntasConText;
    }

    public boolean getMusicaMenu() {
        return musicaMenu;
    }

    public void setMusicaMenu(boolean musicaMenu) {
        this.musicaMenu = musicaMenu;
    }

    public boolean getMusicaPreguntas() {
        return musicaPreguntas;
    }

    public void setMusicaPreguntas(boolean musicaPreguntas) {
        this.musicaPreguntas = musicaPreguntas;
    }

    public boolean getEfectosPreguntas() {
        return efectosPreguntas;
    }

    public void setEfectosPreguntas(boolean efectosPreguntas) {
        this.efectosPreguntas = efectosPreguntas;
    }
}
