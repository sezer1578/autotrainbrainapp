package com.hms.atbotizmozel.util;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;

/**
 * Created by Batuhan on 16.01.2018.
 */

public class SoapUtils {

    /*private final static String URL = "http://dmzws.ew.com.tr/turkpos.ws/service_turkpos_test.asmx";
    private final static String NAMESPACE = "https://turkpos.com.tr/";
    private final static String SOAP_ACTION_URL = "https://turkpos.com.tr/";
    public final static int CLIENT_CODE = 10738;
    private final static String CLIENT_USERNAME = "Test";
    private final static String CLIENT_PASSWORD = "Test";
    public final static String GUID = "0c13d406-873b-403b-9c09-a5766840d98c";*/

    private final static String URL = "https://dmzws.ew.com.tr/turkpos.ws/service_turkpos_prod.asmx";
    private final static String NAMESPACE = "https://turkpos.com.tr/";
    private final static String SOAP_ACTION_URL = "https://turkpos.com.tr/";
    public final static int CLIENT_CODE = 15076;
    private final static String CLIENT_USERNAME = "TP10024343";
    private final static String CLIENT_PASSWORD = "5746098E6C1EEA4A";
    public final static String GUID = "E7A4CC43-DCD7-4D5B-BFDB-25441AC48F74";

    public static SoapObject makeSoapCall(String methodName, HashMap<String, String> properties) {
        SoapObject result = null;

        SoapObject soapRequest = new SoapObject(NAMESPACE, methodName);

        SoapObject clientObject = new SoapObject(NAMESPACE, "G");
        clientObject.addProperty("CLIENT_CODE", CLIENT_CODE);
        clientObject.addProperty("CLIENT_USERNAME", CLIENT_USERNAME);
        clientObject.addProperty("CLIENT_PASSWORD", CLIENT_PASSWORD);
        soapRequest.addSoapObject(clientObject);

        for (String key : properties.keySet()) {
            soapRequest.addProperty(key, properties.get(key));
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.dotNet = true;
        soapEnvelope.setAddAdornments(false);
        soapEnvelope.implicitTypes = true;

        soapEnvelope.setOutputSoapObject(soapRequest);

        try {
            new HttpTransportSE(URL).call(SOAP_ACTION_URL + methodName, soapEnvelope);

            SoapObject soapResult = (SoapObject) soapEnvelope.bodyIn;
            result = (SoapObject) soapResult.getProperty(methodName + "Result");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String makeSoapCallHash(String methodName, HashMap<String, String> properties) {
        String result = null;

        SoapObject soapRequest = new SoapObject(NAMESPACE, methodName);
        for (String key : properties.keySet()) {
            soapRequest.addProperty(key, properties.get(key));
        }

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(soapRequest);

        try {
            new HttpTransportSE(URL).call(SOAP_ACTION_URL + methodName, soapEnvelope);

            SoapObject soapResult = (SoapObject) soapEnvelope.bodyIn;
            result = soapResult.getPropertyAsString(methodName + "Result");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
