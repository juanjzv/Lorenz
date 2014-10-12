/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Encryption.Encrypter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author John & Vanoushka
 */
public class Encrypt extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //  output_text es la respuesta que regresará el Servlet
        String output_text = "";

        Encrypter encrypter;

        String considered_text = "";
        String key = "";
        String algorithm = "";

        //  Ciframos por defecto
        boolean encrypt = true;

        //  Dira si continuar o no
        boolean proceed = false;

        /*  Obtener valores del formulario, con el try catch se proteje en caso de que el
            html haya sido cambiado*/

        try{
            considered_text = request.getParameter("considered-text").toUpperCase();
            key = request.getParameter("key");
            algorithm = request.getParameter("algorithm").toUpperCase();
            System.out.println("NUEVA EJECUCIÓN DE LORENZ");
            System.out.println("El mensaje que recibe LORENZ es " +  considered_text);
            System.out.println("La llave que recibe LORENZ es " + key);
            System.out.println("El algoritmo que recibe LORENZ es "+ algorithm);

            // Verificamos que los valores contengan algo
            if (!(considered_text.equals("") || key.equals(""))) {
                proceed = true;
            } else {
                output_text = "Uno o más datos están vacíos";
                proceed = false;
            }

            //  null para cifrar, on para descrifrar
            if (request.getParameter("mode") != null) {

                //  Se descifrará entonces
                encrypt = false;
            }
        } catch (NullPointerException e) {
            proceed = false;
            output_text = "Los datos no se enviaron correctamente";

        }

        if (proceed) {
            // Ya que sabemos que procederemos a cifrar, se inicializa en cifrador.
            encrypter = new Encrypter(considered_text, key.toUpperCase(), encrypt);

            //  Se cifra según el algoritmo.
            switch (algorithm) {
                case "CAESAR":
                    output_text = encrypter.caesar();
                    break;
                case "VIGENERE":

                    output_text = encrypter.vigenere();

                    break;
                case "AES":

                    output_text = encrypter.aes();

                    break;
                case "MD2":

                    output_text = "Aún no se implementa este algoritmo, estamos trabajando en ello";

                    break;
                case "MD5":

                    output_text = "Aún no se implementa este algoritmo, estamos trabajando en ello";

                    break;
                case "SHA1":

                    output_text = "Aún no se implementa este algoritmo, estamos trabajando en ello";

                    break;
                case "SHA256":

                    output_text = "Aún no se implementa este algoritmo, estamos trabajando en ello";

                    break;
                case "DES":

                    output_text = "Aún no se implementa este algoritmo, estamos trabajando en ello";

                    break;
                default:

                    output_text = "No se selecciono un algoritmo valido";

                    break;
            }
        }
        try (PrintWriter out = response.getWriter()) {

            //Se envía el texto obtenido
            out.println(output_text);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
