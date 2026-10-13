package com.s2.tecnostore;

public class TecnoStore {
    public static void main(String[] args) {
    System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
    new controller.GestionGeneral().menuGeneral();
}
}
