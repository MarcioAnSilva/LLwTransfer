package br.com.llwtransfer.util;

import android.util.Base64;

public abstract  class Codigicar_Decodificar {

    public static String codificarDado(String strTexto)
    {
         String strencode = null;
        try{
            //Necessita retirar do resultado do decode as letras N e R que são inseridas automaticamente.
            strencode = Base64.encodeToString(strTexto.getBytes(), Base64.DEFAULT).replaceAll(("\\n|\\r"),"");

        }catch(Exception exception) {

       }
        return strencode;

    }

    public static String decodificarDado(String strTexto)
    {
        byte[] decodeValue = null;
        try{
            decodeValue = Base64.decode(strTexto, Base64.DEFAULT);

        }catch(Exception exception) {

        }
        return new String(decodeValue);

    }
}
