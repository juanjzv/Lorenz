/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author John & Vanoushka
 */
public class Encrypter {
    private String considered_text;
    private String key;
    private String result_text;
    private boolean encrypt;

    //	En el constructor asignamos los valores a considered_text y a key, que se utilizarán en cualquiera de los métodos.
    public Encrypter (String p_considered_text, String p_key, boolean p_encrypt) {

    	this.considered_text = p_considered_text;
    	this.key = p_key;
    	this.result_text = "";
    	this.encrypt = p_encrypt;
    }

    public String caesar () {

    	boolean valid_data = true;

        String message = this.considered_text;
        int imovement = 3;
        try {
        	imovement = Integer.parseInt(this.key);
        } catch (NumberFormatException e) {
        	imovement = (int)(this.key.charAt(0)- 65);
        }
        if (!this.encrypt) {
        	imovement = 26 - imovement;
        }

        String res = "";

        String message_validation = "[A-Z]+";

        message = message.toUpperCase();

        if (!message.matches(message_validation)) {
            valid_data = false;
            res += "Sólo puedes introducir letras de A a la Z en el campo de mensaje. <br>";
        } else if (message.length() > 500) {
            valid_data = false;
            res += "El tamaño máximo del mensaje es de 500 caractéres";
        }

        if (!(imovement < 26 && imovement > 0)) {
            valid_data = false;
            res += "Ingresa una letra entre la B y la Z (Sin incluir la 'Ñ') en la llave para este algoritmo o un numero del 1 al 25";
        }

        if (valid_data) {

            char characters[] = new char[message.length() + 1];

            for (int i = 0; i < message.length(); i++) {

                int aux;

                aux = ((int) message.charAt(i)) - 65;
                aux = (aux + imovement) % 26;
                characters[i] = (char) (aux + 65);
            }

            String encoded = new String(characters);
            res = encoded;
        }
        	this.result_text = res;
            return this.result_text;
    }

    public String vigenere () {
        String message = this.considered_text.toUpperCase();
        int current_of_key;
        int current_of_message;
        int aux;
        char result[] = new char[message.length()+1];

        //  Nos dirá si procederemos a cifrar o no
        boolean valid_data = false;

        String message_validation = "[A-Z]+";
        if (!message.matches(message_validation) || !key.matches(message_validation)) {
            valid_data = false;
            System.out.println("Mensaje y/o clave no validos");
            this.result_text += "Sólo puedes introducir letras de A a la Z en el mensaje y la clave.";
        } else if (message.length() > 500) {
            System.out.println("Mensaje muy largo");
            valid_data = false;
            this.result_text += "El tamaño máximo del mensaje es de 500 caractéres";
        } else if (message.length() < key.length()) {
            System.out.println("Clave más grande que el mensaje.");
            this.result_text += "La longitud de la clave no puede ser más grande que el mensaje";
            valid_data= false;
        } else {
            valid_data = true;
        }

        if (valid_data) {
            for (int i = 0; i < message.length(); i ++) { // Recorremos todo el mensaje letra por letra
                current_of_key = (int)( key.charAt( i % key.length() ) ) - 65; // Obtenemos la llave correspondiente para esta vuelta haciendo módulo con la longitud de la llave, obtenemos su código ASCII y restamos 65 para trabajar con números del 0 al 25
                current_of_message = (int)( message.charAt(i) ) - 65; // Obtenemos la letra del mensaje para esta vuelta e igualmente obtenemos su código ASCII y restamos 65
                aux = this.encrypt ? current_of_message + current_of_key : current_of_message - current_of_key; // Si encrypt es verdadero sumamos la llave para encriptar, de lo contrario la restamos para desencriptar
                if (aux < 0) aux = aux + 26; // De la operación de arriba a veces obtendremos números negativos, y Java dummy no sabe hacer módulos negativos, por lo que primero validamos si es menor que cero
                result[i] = (char)( (aux % 26) + 65 ); // Hacemos módulo de 26 + 65 para obtener el código ASCII y casteamos a char
            }

            this.result_text = new String(result);
        }

        return this.result_text;
    }

    public String aes () {
        String message = this.considered_text;
        key = this.key;

        //  Nos dirá si procedemos a cifrar/descifrar o no
        boolean valid_data = false;

        if (key.length() != 16 && key.length() != 24 && key.length() != 32 ) {
           this.result_text += "La longitud de la llave debe ser de 16, 24 ó 32 caracteres";
           valid_data = false;
        } else {
            valid_data = true;
        }

        if (valid_data) {
            SecretKeySpec secret_key = new SecretKeySpec(this.key.getBytes(), "AES");
            Cipher cipher;
            try {
                cipher = Cipher.getInstance("AES");

                if (encrypt) {
                    System.out.println("AES va a cifrar");
                    //Comienzo a encriptar
                    cipher.init(Cipher.ENCRYPT_MODE, secret_key);
                    /*
                    * TODO: Representar los bytes como string vía base64, así será
                    * humanamente leíble. La otra opción es expresar como hexadecimal
                    *
                    * En este caso lo imprimo en pantalla como bytes.
                    */
                    byte[] encryptedText = cipher.doFinal(message.getBytes());
                    this.result_text = new String(encryptedText);
                } else {
                    System.out.println("AES va a descifrar");
                    cipher.init(Cipher.DECRYPT_MODE, secret_key);
                    byte[] decryptedText = cipher.doFinal(message.getBytes());
                    this.result_text = new String(decryptedText);
                }
            } catch (Exception e) {
                this.result_text = "Ha ocurrido un error inesperado, inténtalo de nuevo más tarde";
                e.printStackTrace();
            }
        }

        return this.result_text;
    }
}
