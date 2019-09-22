package com.example.c2paplicationmobile;

/**
 * Created by anyeli on 18/09/17.
 */

public class  Constants {

    /*Data de Produccion*/
    public static final boolean CONSTANT_IS_PRODUCTION = false;


    /*PRODUCCION*/
    public static final String  CONSTANT_IP = "192.168.3.20";
    public static final String  CONSTANT_URL_PROD = "http://192.168.3.20:8080/RegistroUnificado/APIRegistroUnificadoService";
    public static final String  CONSTANT_SECURE_URL = "http://192.168.3.20:8080/RegistroUnificado/APIRegistroUnificadoService";


    //QA
    //public static final String  CONSTANT_IP = "201.249.236.187";
    //public static final String  CONSTANT_URL_PROD = "http://201.249.236.187:8080/P2PWSServicesProviderService/P2PWSServicesProvider";
    //public static final String  CONSTANT_SECURE_URL = "https://201.249.236.187:8181/P2PWSServicesProviderService/P2PWSServicesProvider";



    public static final String  RESUM_OPERATION_PHONE = "getResumOperationByPhoneNumberSender";
    public static final String  RESUM_CLOSE_SESSION = "appCloseSession";
    public static final String  RESUM_LAST_TRANSACTION = "getLastOperationByPhoneNumber";



    public static final boolean CONSTANT_IS_SECURE_PORT = false;
    public static final String  CONSTANT_SECURE_PORT = "8181";

    /**
     * Data de QA
     */
    public static final String CONSTANT_URL_QA = "http://192.168.3.20:8080/RegistroUnificado/APIRegistroUnificadoService";
    public static final String CONSTANT_NAMESPACE = "http://ws.alodiga.ericsson.com/";

    public static final String CONSTANT_SEPARATOR = ":";
    public static final String CONSTANT_PORT = "8080";
    public static final String CI_SEPARATOR = "-";

    /**
     * Nombre de los metodos utilizados para llamar a un Web Service
     */
    public static final String METHOD_GET_BANK = "getBanks";
    public static final String METHOD_GET_DOCUMENT_TYPES = "getDocumentTypes";
    public static final String METHOD_GET_FAVORITES_BY_PHONE_NUMBERS = "getFavoritesByPhoneNumber";
    public static final String METHOD_PROCESS_PAYMENT_P2P = "processP2PTransaction";
    public static final String METHOD_SEND_CUSTOMER_TOKEN = "sendCustomerToken";

    /**
     * Categoria de Servicios
     */
    public static final String CATEGORY_SERVICE_OTHER_BANKS = "2";
    public static final String CATEGORY_SERVICE_SAME_BANK = "1";
    public static final String BANCO_AGRICOLA_ID = "1";
    public static final String BANCO_AGRICOLA_ABA = "0166";
    public static final String CONSTANT_NATIONAL_CURRENCY = "Bs. ";
    public static final String CONSTANT_CHARACTER_SEPARATOR = ";";
    public static final Integer ID_IDIOMA_ES = 4;

    /**
     * Tiempo de inactividad
     */
    public static final String WEB_SERVICES_USUARIOWS = "usuarioWS";
    public static final String WEB_SERVICES_PASSWORDWS = "passwordWS";



    public static final long SECONDS = 180; //In seconds
    public static final long STARTIME = SECONDS * 1000; //In miliseconds
    public static final long INTERVAL = 1000;
    public static final String WEB_SERVICES_RESPONSE_CODE_EXITO = "00";
    public static final String WEB_SERVICES_RESPONSE_CODE_DATOS_INVALIDOS = "01";
    public static final String WEB_SERVICES_RESPONSE_CODE_CONTRASENIA_EXPIRADA = "03";
    public static final String WEB_SERVICES_RESPONSE_CODE_IP_NO_CONFIANZA = "04";
    public static final String WEB_SERVICES_RESPONSE_CODE_CREDENCIALES_INVALIDAS = "05";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_BLOQUEADO = "06";
    public static final String WEB_SERVICES_RESPONSE_CODE_CODIGO_VALIDACION_INVALIDO = "07";
    public static final String WEB_SERVICES_RESPONSE_CODE_NUMERO_TELEFONO_YA_EXISTE = "08";
    public static final String WEB_SERVICES_RESPONSE_CODE_ENVIO_CORREO_FALLIDO= "09";
    public static final String WEB_SERVICES_RESPONSE_CODE_CORREO_YA_EXISTE= "10";
    public static final String WEB_SERVICES_RESPONSE_CODE_DATOS_NULOS = "11";
    public static final String WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO= "12";
    public static final String WEB_SERVICES_RESPONSE_CODE_EIN_YA_EXISTE = "14";
    public static final String WEB_SERVICES_RESPONSE_CODE_FEDERAL_TAX_YA_EXISTE = "15";
    public static final String WEB_SERVICES_RESPONSE_CODE_CUENTA_BANCARIA_YA_EXISTE = "17";
    public static final String WEB_SERVICES_RESPONSE_CODE_GENERAR_CODIGO = "18";
    public static final String WEB_SERVICES_RESPONSE_CODE_TOKEN_EXPIRADO = "19";
    public static final String WEB_SERVICES_RESPONSE_CODE_SOLICITUD_TARJETA_ACTIVA = "20";
    public static final String WEB_SERVICES_RESPONSE_CODE_AFILIACIONES_MAXIMAS_ALCANZADAS = "21";
    public static final String WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_PHONE_NUMBER = "22";
    public static final String WEB_SERVICES_RESPONSE_CODE_APLICACION_NO_EXISTE = "94";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO = "95";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE = "96";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE = "97";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES = "98";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO = "99";


    public static final String WEB_SERVICES_METHOD_NAME_SAVE_USER = "guardarUsuarioAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS = "generarCodigoMovilSMS";
    public static final String WEB_SERVICES_METHOD_NAME_LOGIN_APP_MOBILE = "loginAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_NAME_SECRET_AMSWER = "getPreguntasSecretas";





}
