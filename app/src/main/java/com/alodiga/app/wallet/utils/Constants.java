package com.alodiga.app.wallet.utils;

/**
 * Created by Kerwin on 18/09/17.
 */

public class Constants {

    //IP PUBLICA
    //public static String IPDAT="200.73.192.179";

    /*Data de Produccion*/
    public static final boolean CONSTANT_IS_PRODUCTION = false;

    //LOCAL moises
    public static String IPDAT="192.168.3.20";
    //LOCAL QA
    //public static String IPDAT = "192.168.3.140";

    //public static String LINK = "http://192.168.3.140:8080/ultima-1.0.6/formData.xhtml";
    public static String LINK = "http://200.73.192.178:8080/CardFormWeb/";


    public static final String RESUM_OPERATION_PHONE = "getResumOperationByPhoneNumberSender";
    public static final String RESUM_CLOSE_SESSION = "appCloseSession";
    public static final String RESUM_LAST_TRANSACTION = "getLastOperationByPhoneNumber";
    public static final boolean CONSTANT_IS_SECURE_PORT = false;
    public static final String CONSTANT_SECURE_PORT = "8181";
    public static final String CONSTANT_NAMESPACE_QA_REGISTRO_UNIFICADO = "http://ws.alodiga.ericsson.com/";
    //public static final String  CONSTANT_SECURE_URL_ALODIGA_WALLET = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";


    //QA
    //public static final String  CONSTANT_IP = "201.249.236.187";
    //public static final String  CONSTANT_URL_PROD = "http://201.249.236.187:8080/P2PWSServicesProviderService/P2PWSServicesProvider";
    //public static final String  CONSTANT_SECURE_URL = "https://201.249.236.187:8181/P2PWSServicesProviderService/P2PWSServicesProvider";
    public static final String REGISTRO_UNIFICADO = "RegistroUnificado";
    public static final String CONSTANT_NAMESPACE_QA_ALODIGA = "http://ws.wallet.alodiga.com/";
    public static final String ALODIGA = "AlodigaWallet";
    public static final String CONSTANT_NAMESPACE_ALODIGA_WALLET = "http://ws.wallet.alodiga.com/";
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
    public static final String WEB_SERVICES_RESPONSE_CODE_ENVIO_CORREO_FALLIDO = "09";
    public static final String WEB_SERVICES_RESPONSE_CODE_CORREO_YA_EXISTE = "10";
    public static final String WEB_SERVICES_RESPONSE_CODE_DATOS_NULOS = "11";
    public static final String WEB_SERVICES_RESPONSE_CODE_PRIMER_INGRESO = "12";
    public static final String WEB_SERVICES_RESPONSE_CODE_EIN_YA_EXISTE = "14";
    public static final String WEB_SERVICES_RESPONSE_CODE_FEDERAL_TAX_YA_EXISTE = "15";
    public static final String WEB_SERVICES_RESPONSE_CODE_CUENTA_BANCARIA_YA_EXISTE = "17";
    public static final String WEB_SERVICES_RESPONSE_CODE_GENERAR_CODIGO = "18";
    public static final String WEB_SERVICES_RESPONSE_CODE_TOKEN_EXPIRADO = "19";
    public static final String WEB_SERVICES_RESPONSE_CODE_SOLICITUD_TARJETA_ACTIVA = "20";
    public static final String WEB_SERVICES_RESPONSE_CODE_AFILIACIONES_MAXIMAS_ALCANZADAS = "21";
    public static final String WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_PHONE_NUMBER = "22";
    public static final String WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_TRANSACTIONS = "24";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_LOADING_CARDS = "28";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_AMOUNT_LIMIT = "30";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_ACCOUNT = "31";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_CUSTOMER = "32";
    public static final String WEB_SERVICES_RESPONSE_CODE_USER_HAS_NOT_BALANCE = "33";
    public static final String WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS = "50";
    public static final String WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE = "51";
    public static final String WEB_SERVICES_RESPONSE_CODE_THERE_IS_NO_SEARCH_RECORD = "52";
    public static final String WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH = "58";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_NUMBER_OF_ORDERS_ALLOWED_IS_EXCEEDED= "60";
    public static final String WEB_SERVICES_RESPONSE_CODE_APLICACION_NO_EXISTE = "94";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_SOSPECHOSO = "95";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_PENDIENTE = "96";
    public static final String WEB_SERVICES_RESPONSE_CODE_USUARIO_NO_EXISTE = "97";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_CREDENCIALES = "98";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_INTERNO = "99";


    public static final String WEB_SERVICES_RESPONSE_CODE_NON_EXISTENT_CARD = "100";
    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_MSISDN_OUT_OF_RANGE = "101";
    public static final String WEB_SERVICES_RESPONSE_CODE_EXPIRATION_DATE_DIFFERS = "102";
    public static final String WEB_SERVICES_RESPONSE_CODE_EXPIRED_CARD = "103";
    public static final String WEB_SERVICES_RESPONSE_CODE_LOCKED_CARD = "104";
    public static final String WEB_SERVICES_RESPONSE_CODE_BLOCKED_ACCOUNT = "105";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_ACCOUNT = "106";
    public static final String WEB_SERVICES_RESPONSE_CODE_INSUFFICIENT_BALANCE = "107";
    public static final String WEB_SERVICES_RESPONSE_CODE_INSUFFICIENT_LIMIT = "108";
    public static final String WEB_SERVICES_RESPONSE_CODE_CREDIT_LIMIT_0 = "109";
    public static final String WEB_SERVICES_RESPONSE_CODE_CREDIT_LIMIT_0_OF_THE_DESTINATION_ACCOUNT = "110";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_PROCESSING_THE_TRANSACTION = "111";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_TRANSACTION = "112";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATION_THE_TERMINAL = "113";
    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_CARD_LOCKED= "114";
    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_ACCOUNT_LOCKED = "115";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_DESTINATION_CARD = "116";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_DESTINATION_ACCOUNT = "117";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_MUST_BE_POSITIVE_AND_THE_AMOUNT_IS_REPORTED = "118";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_MUST_BE_NEGATIVE_AND_THE_AMOUNT_IS_REPORTED = "119";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_TRANSACTION_DATE = "120";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_TRANSACTION_TIME = "121";
    public static final String WEB_SERVICES_RESPONSE_CODE_SOURCE_OR_DESTINATION_ACCOUNT_IS_NOT_COMPATIBLE_WITH_THIS_OPERATION_NN = "122";
    public static final String WEB_SERVICES_RESPONSE_CODE_SOURCE_OR_DESTINATION_ACCOUNT_IS_NOT_COMPATIBLE_WITH_THIS_OPERATION_SN = "123";
    public static final String SOURCE_OR_DESTINATION_ACCOUNT_IS_NOT_COMPATIBLE_WITH_THIS_OPERATION_NS = "124";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRASACTION_BETWEEN_ACCOUNTS_NOT_ALLOWED = "125";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRADE_VALIDATON_ERROR = "126";
    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_CARD_DOES_NOT_SUPPORT_TRANSACTION = "127";
    public static final String WEB_SERVICES_RESPONSE_CODE_OPERATION_NOT_ENABLED_FOR_THE_DESTINATION_CARD = "128";
    public static final String WEB_SERVICES_RESPONSE_CODE_BIN_NOT_ALLOWED = "129";
    public static final String WEB_SERVICES_RESPONSE_CODE_STOCK_CARD = "130";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_ACCOUNT_EXCEEDS_THE_MONTHLY_LIMIT= "131";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_PAN_FIELD_IS_MANDATORY = "132";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_TO_BE_RECHARGE_IS_INCORRECT = "133";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_AMOUNT_MUST_BE_GREATER_THAN_0 = "134";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_ANSWERED = "135";
    public static final String WEB_SERVICES_RESPONSE_CODE_SUCCESSFUL_RECHARGE = "136";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_PIN = "137";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_CVC1 = "138";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_CVC2 = "139";
    public static final String WEB_SERVICES_RESPONSE_CODE_PIN_CHANGE_ERROR = "140";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_VALIDATING_THE_ITEM = "141";
    public static final String WEB_SERVICES_RESPONSE_CODE_INVALID_AMOUNT = "142";











    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_NOT_PREPAID = "204";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_TRANSACTION_TOP_UP = "205";
    public static final String WEB_SERVICES_RESPONSE_CODE_DENOMINATION_NOT_AVAILABLE = "301";
    public static final String WEB_SERVICES_METHOD_NAME_SAVE_USER = "guardarUsuarioAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS = "generarCodigoMovilSMS";
    public static final String WEB_SERVICES_METHOD_NAME_SEND_CODE_SMS_MOVIL = "generarCodigoMovilSMSAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_NAME_LOGIN_APP_MOBILE = "loginAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_NAME_SECRET_AMSWER = "getPreguntasSecretas";
    public static final String WEB_SERVICES_METHOD_NAME_GET_TRANSACTION_LIST = "getTransactionsByUserIdApp";
    public static final String WEB_SERVICES_METHOD_NAME_SEND_SECRET_AMSWER = "setPreguntasSecretasUsuarioAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_NAME_GET_BANK = "getBankByCountryApp";
    public static final String WEB_SERVICES_METHOD_NAME_GET_COUNTRIES = "getCountriesHasBank";
    public static final String WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_ = "getCountries";
    public static final String WEB_SERVICES_METHOD_NAME_GET_LANGUAJE_TOPUP = "getLanguage";
    public static final String WEB_SERVICES_METHOD_NAME_GET_PRODUCT_TOPUP = "getProductsPayTopUpByUserId";
    public static final String WEB_SERVICES_METHOD_NAME_GET_COUNTRIES_TOPUP = "getTopUpCountries";
    public static final String WEB_SERVICES_METHOD_NAME_GET_LIST_TOPUP = "topUpList";
    public static final String WEB_SERVICES_METHOD_NAME_SAVE_TOPUP = "saveRechargeTopUp";
    public static final String WEB_SERVICES_METHOD_NAME_GET_PRODUCT_EXCHANGE = "getProductsIsExchangeProductUserId";
    public static final String WEB_SERVICES_METHOD_NAME_GET_PREVIEW_EXCHANGE = "previewExchangeProduct";
    public static final String WEB_SERVICES_METHOD_NAME_GET_EXCHANGE = "exchangeProduct";
    public static final String WEB_SERVICES_METHOD_CHANGE_PASSWORD = "cambiarCredencialAplicacionMovil";
    public static final String WEB_SERVICES_METHOD_CHANGE_PASSWORD_FORGOT = "cambiarCredencialAplicacionMovilEmailOrPhone";
    public static final String WEB_SERVICES_METHOD_KYC_COLLECTION = "getValidateCollection";
    public static final String WEB_SERVICES_METHOD_KYC_PROCESS = "saveCumplimient";
    public static final String WEB_SERVICES_METHOD_ACTIVE_PROCESS = "activateCard";
    public static final String WEB_SERVICES_METHOD_DEACTIVE_PROCESS = "desactivateCard";
    public static final String WEB_SERVICES_METHOD_ACTIVE_DEACTIVE_STATUS = "checkStatusCard";
    public static final String WEB_SERVICES_METHOD_CHECK_BALANCE = "checkStatusAccount";
    public static final String WEB_SERVICES_METHOD_TRANSFERENCE_GET_CARD = "getCardByUserId";
    public static final String WEB_SERVICES_METHOD_TRANSFERENCE_CARD_TO_CARD = "transferCardToCardAutorization";














    public static final String WEB_SERVICES_METHOD_NAME_GET_PRODUCT = "getProductsByBankId";
    public static final String WEB_SERVICES_METHOD_NAME_PAYMENT_COMERCE = "savePaymentShop";
    public static final String WEB_SERVICES_METHOD_NAME_TRANSFERENCE = "saveTransferBetweenAccount";
    public static final String WEB_SERVICES_METHOD_NAME_VALID_CODE = "validarPin";
    public static final String WEB_SERVICES_METHOD_NAME_UPDATE_PRODUCT = "listadoProductosPorUsuario";
    public static final String WEB_SERVICES_METHOD_NAME_REMOVAL_MANUAL = "manualWithdrawals";
    public static final String WEB_SERVICES_METHOD_NAME_RECHARGE = "manualRecharge";
    public static final Integer MAX_RESULT_BY_TRANSACTION_OPERATION = 10;
    public static final String CONCEPT_TRANSACTION = "paymentshop";
    public static final String KEY_ENCRIPT_DESENCRIPT = "1nt3r4xt3l3ph0ny";
    public static final String SECRET_KEY = "1nt3r4xt3l3ph0nyDBWE";
    public static final String K2_ENCRIPT_DESENCRIPT = "DESede";
    public static final String VECTOR_ENCRIPT_DESENCRIPT = "0123456789ABCDEF";
    public static final String KEY_ENCRIPT_DESENCRIPT_QR = "alodigaPruebadellave";
    public static final String VALUE_KEY = "maria,d,tercerparametro,p";

    /*PRODUCCION*/
    //public static final String  CONSTANT_IP = "192.168.3.20";
    public static final String CONSTANT_IP = IPDAT;
    public static final String CONSTANT_URL_PROD = "http://" + IPDAT + ":8080/RegistroUnificado/APIRegistroUnificadoService";
    //public static final String  CONSTANT_URL_PROD = "http://192.168.3.20:8080/RegistroUnificado/APIRegistroUnificadoService";
    //public static final String  CONSTANT_SECURE_URL = "http://192.168.3.20:8080/RegistroUnificado/APIRegistroUnificadoService";
    public static final String CONSTANT_SECURE_URL = "http://" + IPDAT + ":8080/RegistroUnificado/APIRegistroUnificadoService";
    //Alodiga Eallet
    //public static final String  CONSTANT_URL_PROD_ALODIGA_WALLET = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";
    public static final String CONSTANT_URL_PROD_ALODIGA_WALLET = "http://" + IPDAT + ":8080/AlodigaWallet/APIAlodigaWalletService";
    public static final String CONSTANT_SECURE_URL_ALODIGA_WALLET = "http://" + IPDAT + ":8080/AlodigaWallet/APIAlodigaWalletService";
    /**
     * Data de QA
     */
    //public static final String CONSTANT_URL_QA_REGISTRO_UNIFICADO = "http://192.168.3.20:8080/RegistroUnificado/APIRegistroUnificadoService";
    public static final String CONSTANT_URL_QA_REGISTRO_UNIFICADO = "http://" + IPDAT + ":8080/RegistroUnificado/APIRegistroUnificadoService";
    /**
     *
     */
    //public static final String CONSTANT_URL_QA_ALODIGA = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";
    public static final String CONSTANT_URL_QA_ALODIGA = "http://" + IPDAT + ":8080/AlodigaWallet/APIAlodigaWalletService";
    /**
     * Alodiga WAllet
     */
    //public static final String CONSTANT_URL_QA_ALODIGA_WALLET  = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";
    public static final String CONSTANT_URL_QA_ALODIGA_WALLET = "http://" + IPDAT + ":8080/AlodigaWallet/APIAlodigaWalletService";


    public static final String MD5 ="MD5";
    public static final String INVALID_KEY_LONG="Longitud de clave invalida";

    // Definición del tipo de algoritmo a utilizar (AES, DES, RSA)
    public final static String ALG = "AES";
    // Definición del modo de cifrado a utilizar
    public final static String CI = "AES/CBC/PKCS5Padding";


    public static final String IS_SELECTED=" is selected";
    public static final String BANK= "Banco";
    public static final String MONEY="Moneda";
    public static final String PRODUCT= "Producto";
    public static final String ANSWER= "Pregunta de Seguridad";
    public static final String LOCATION= "Pais";
    public static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
    public static final String JPG =".jpg";
    public static final String IMAGE_JPG="image/jpeg";
    public static final String DIR= "dirrrrrr";
    public final static int QRcodeWidth = 500;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 500;
    public final static int HEIGHT = 500;
    public final static String TOPUP_DF = "DF";
    public final static String TOPUP_OR = "OR";
    public final static String TOPUP_PRODUCT_ID = "7";
    public final static String ACTIVE_STATUS_ACTIVE = "01";
    public final static String ACTIVE_STATUS_DEACTIVE = "24";




}
