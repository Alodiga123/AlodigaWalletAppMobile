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
    public static String IPDAT = "192.168.3.20";
    //LOCAL QA
    //public static String IPDAT = "192.168.3.140";

    //public static String LINK = "http://192.168.3.140:8080/ultima-1.0.6/formData.xhtml";
    public static String LINK = "http://18.237.99.116:8080/CardRequestWeb/";


    public static final String RESUM_OPERATION_PHONE = "getResumOperationByPhoneNumberSender";
    public static final String RESUM_CLOSE_SESSION = "appCloseSession";
    public static final String RESUM_LAST_TRANSACTION = "getLastOperationByPhoneNumber";
    public static final boolean CONSTANT_IS_SECURE_PORT = false;
    public static final String CONSTANT_SECURE_PORT = "8181";
    public static final String CONSTANT_NAMESPACE_QA_REGISTRO_UNIFICADO = "http://ws.alodiga.ericsson.com/";
    public static final String CONSTANT_NAMESPACE_QA_REGISTRO_UNIFICADO_AW = "http://ws.alodiga.ericsson.com/";

    //public static final String  CONSTANT_SECURE_URL_ALODIGA_WALLET = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";


    //QA
    //public static final String  CONSTANT_IP = "201.249.236.187";
    //public static final String  CONSTANT_URL_PROD = "http://201.249.236.187:8080/P2PWSServicesProviderService/P2PWSServicesProvider";
    //public static final String  CONSTANT_SECURE_URL = "https://201.249.236.187:8181/P2PWSServicesProviderService/P2PWSServicesProvider";
    public static final String REGISTRO_UNIFICADO = "RegistroUnificado";
    public static final String REMITTANCE = "remittance";
    public static final String CONSTANT_NAMESPACE_QA_ALODIGA = "http://ws.wallet.alodiga.com/";
    public static final String CONSTANT_NAMESPACE_QA_ALODIGA_AW = "http://ws.wallet.alodiga.com/";

    public static final String CONSTANT_NAMESPACE_REMITTANCE = "http://services.remittance.ws.alodiga.com/";
    public static final String CONSTANT_NAMESPACE_REMITTANCE_AW = "http://services.remittance.ws.alodiga.com/";


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
    public static final String WEB_SERVICES_USUARIOWS_ = "userWS";
    public static final String WEB_SERVICES_PASSWORDWS = "passwordWS";
    public static final long SECONDS = 180; //In seconds
    public static final long STARTIME = SECONDS * 1000; //In miliseconds
    public static final long INTERVAL = 1000;
    public static final String WEB_SERVICES_RESPONSE_CODE_EXITO = "00";
    public static final String WEB_SERVICES_RESPONSE_CODE_EXITO_ = "0";
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
    public static final String WEB_SERVICES_RESPONSE_CODE_USER_NOT_HAS_CARD = "29";
    public static final String WEB_SERVICES_RESPONSE_CODE_DOES_NOT_HAVE_AN_ASSOCIATED_COMPANION_CARD = "30";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_AMOUNT_LIMIT = "30";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_ACCOUNT = "31";
    public static final String WEB_SERVICES_RESPONSE_CODE_TRANSACTION_MAX_NUMBER_BY_CUSTOMER = "32";
    public static final String WEB_SERVICES_RESPONSE_CODE_USER_HAS_NOT_BALANCE = "33";
    public static final String WEB_SERVICES_RESPONSE_CODE_CARD_NUMBER_EXISTS = "50";
    public static final String WEB_SERVICES_RESPONSE_CODE_NOT_ALLOWED_TO_CHANGE_STATE = "51";
    public static final String WEB_SERVICES_RESPONSE_CODE_THERE_IS_NO_SEARCH_RECORD = "52";
    public static final String WEB_SERVICES_RESPONSE_CODE_AUTHENTICALLY_IMPOSSIBLE = "54";
    public static final String WEB_SERVICES_RESPONSE_CODE_UNABLE_TO_ACCESS_DATA = "57";
    public static final String WEB_SERVICES_RESPONSE_CODE_THERE_ARE_NO_RECORDS_FOR_THE_REQUESTED_SEARCH = "58";
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_NUMBER_OF_ORDERS_ALLOWED_IS_EXCEEDED = "60";
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
    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_CARD_LOCKED = "114";
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
    public static final String WEB_SERVICES_RESPONSE_CODE_THE_ACCOUNT_EXCEEDS_THE_MONTHLY_LIMIT = "131";
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

    //resumen remesas
    public static final String WEB_SERVICES_RESPONSE_CODE_AUTHENTICATION_FAILED = "1";
    public static final String WEB_SERVICES_RESPONSE_CODE_MISSING_PARAMETERS = "2";
    public static final String WEB_SERVICES_RESPONSE_CODE_DISABLED_ACCOUNT = "3";
    public static final String WEB_SERVICES_RESPONSE_CODE_TOKEN_NOT_FOUND = "50";
    public static final String WEB_SERVICES_RESPONSE_CODE_RECORDS_NOT_FOUND = "28";
    public static final String WEB_SERVICES_RESPONSE_CODE_GENERAL_ERROR = "999";


    public static final String WEB_SERVICES_RESPONSE_CODE_DESTINATION_NOT_PREPAID = "204";
    public static final String WEB_SERVICES_RESPONSE_CODE_ERROR_TRANSACTION_TOP_UP = "205";
    public static final String WEB_SERVICES_RESPONSE_CODE_DENOMINATION_NOT_AVAILABLE = "301";
    public static final String WEB_SERVICES_RESPONSE_CODE_NOT_ASSOCIATED_PAYMENT_INFO = "220";

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
    public static final String WEB_SERVICES_METHOD_LIST_COMPANION_CARDS = "getCardsListByUserId";
    public static final String WEB_SERVICES_METHOD_LOGIN = "loginWS";
    public static final String WEB_SERVICES_METHOD_COUNTRIES = "getCountries";
    public static final String WEB_SERVICES_METHOD_PAYMENTBYCONTRY = "getPaymentNetworkByCountryId";
    public static final String WEB_SERVICES_METHOD_REMETTENCE_SUMARY = "getRemettenceSummary";
    public static final String WEB_SERVICES_METHOD_GETPRODUCTSREMETTENCEBYUSER = "getProductsRemettenceByUserId";
    public static final String WEB_SERVICES_METHOD_LOADDELIVERYFORMBYPAYMENTNETWORK = "getDeliveryFormByPamentNetwork";
    public static final String WEB_SERVICES_METHOD_GETSTATESBYCOUNTRY = "getStatesByCountry";
    public static final String WEB_SERVICES_METHOD_GETCITIESBYSTATE = "getCitiesByState";
    public static final String WEB_SERVICES_METHOD_PROCESSREMETTENCEACCOUNT = "processRemettenceAccount";
    public static final String WEB_SERVICES_METHOD_SAVEPAYMENTINFO = "savePaymentInfo";
    public static final String WEB_SERVICES_METHOD_GETPAYMENTINFO = "getPaymentInfo";
    public static final String WEB_SERVICES_METHOD_GETPRODUCTS_RECHARGE_PAYMENT_BY_USERID = "getProductsRechargePaymentByUserId";
    public static final String WEB_SERVICES_METHOD_GET_CREDIT_CARD_TYPE = "getCreditCardType";
    public static final String WEB_SERVICES_METHOD_CHANGE_STATUS_PAYMENT_INFO = "ChangeStatusPaymentInfo";
    public static final String WEB_SERVICES_METHOD_SAVE_RECHARGE_AFINITAS = "saveRechargeAfinitas";

    public static final String WEB_SERVICES_METHOD_CREDENTIALS_WS_INAVAILABLE="143";
    public static final String WEB_SERVICES_METHOD_CALL_ISSUER="150";
    public static final String WEB_SERVICES_METHOD_NOT_AUTHORIZED="151";
    public static final String WEB_SERVICES_METHOD_INVALID_TRADE="152";
    public static final String WEB_SERVICES_METHOD_RETAIN_CARD="153";
    public static final String WEB_SERVICES_METHOD_INVALID_TRANSACTION_AFINITAS="154";
    public static final String WEB_SERVICES_METHOD_RETRY_AFINITAS="155";
    public static final String WEB_SERVICES_METHOD_TRANSACTION_NOT_PERMITTED="156";
    public static final String WEB_SERVICES_METHOD_INVALID_CARD="157";
    public static final String WEB_SERVICES_METHOD_FORMAT_ERROR="158";
    public static final String WEB_SERVICES_METHOD_INSUFFICIENT_FUNDS="159";
    public static final String WEB_SERVICES_METHOD_EXPIRED_CARD_AFINITAS="160";
    public static final String WEB_SERVICES_METHOD_INVALID_PIN="161";
    public static final String WEB_SERVICES_METHOD_DEFERRED_PAYMENT_NOT_PERMITTED="162";
    public static final String WEB_SERVICES_METHOD_LIMIT_EXCEEDED="163";
    public static final String WEB_SERVICES_METHOD_TYPE_OF_PLAN_TERM_INVALID="164";
    public static final String WEB_SERVICES_METHOD_DUPLICATED_TRANSACTION="165";
    public static final String WEB_SERVICES_METHOD_EXCESSED_AUTHORIZATIONS="166";
    public static final String WEB_SERVICES_METHOD_CP_NOT_PERMITTED_BY_TH="167";
    public static final String WEB_SERVICES_METHOD_TERMINAL_ERROR="168";
    public static final String WEB_SERVICES_METHOD_UNACTIVATED_CARD="169";
    public static final String WEB_SERVICES_METHOD_INVALID_COIN="170";
    public static final String WEB_SERVICES_METHOD_CHIP_READING_ERROR="171";
    public static final String WEB_SERVICES_METHOD_INVALID_CHIP="172";
    public static final String WEB_SERVICES_METHOD_CHIP_NOT_SUPPORTED="173";
    public static final String WEB_SERVICES_METHOD_UNKNOWN="174";
    public static final String WEB_SERVICES_METHOD_THE_DEVICE_IS_NOT_ACTIVE="175";
    public static final String WEB_SERVICES_METHOD_THE_BRANCH_IS_NOT_ACTIVE="176";
    public static final String WEB_SERVICES_METHOD_TRADE_IS_NOT_ACTIVE="177";
    public static final String WEB_SERVICES_METHOD_THE_REQUEST_IS_EMPTY="178";
    public static final String WEB_SERVICES_METHOD_MISSING_PARAMETER_ON_REQUEST="179";
    public static final String WEB_SERVICES_METHOD_RESOURCE_NOT_FOUND="180";
    public static final String WEB_SERVICES_METHOD_ANSWER_EMPTY="181";
    public static final String WEB_SERVICES_METHOD_THE_TRANSACTION_EXCEEDS_THE_PERMITTED_AMOUNT="182";
    public static final String WEB_SERVICES_METHOD_TRANSACTION_EXCEEDS_THE_ALLOWED_DAILY_AMOUNT="183";
    public static final String WEB_SERVICES_METHOD_TRANSACTION_EXCEEDS_THE_MONTHLY_AMOUNT_ALLOWED="184";
    public static final String WEB_SERVICES_METHOD_NON_ACTIVE_PROMOTIONS="185";
    public static final String WEB_SERVICES_METHOD_PROMOTION_NOT_ACTIVE="186";
    public static final String WEB_SERVICES_METHOD_THE_TRANSACTION_IS_NOT_WITHIN_THE_PERMITTED_SCHEDULE="187";
    public static final String WEB_SERVICES_METHOD_THE_TRANSACTION_DOES_NOT_EXIST="188";
    public static final String WEB_SERVICES_METHOD_TRANSACTION_WITH_NOT_APPROVED_SOURCE="189";
    public static final String WEB_SERVICES_METHOD_INVALID_MEMBERSHIP="190";
    public static final String WEB_SERVICES_METHOD_TRANSACTION_CANCELED_PREVIOUSLY="191";
    public static final String WEB_SERVICES_METHOD_PREVIOUSLY_REVERSED_TRANSACTION="192";
    public static final String WEB_SERVICES_METHOD_EXCEED_PERMITTED_DAILY_TRANSACTIONS="193";
    public static final String WEB_SERVICES_METHOD_THE_CORPORATE_IS_NOT_ACTIVE="194";
    public static final String WEB_SERVICES_METHOD_ANSWER_NOT_FOUND="195";
    public static final String WEB_SERVICES_METHOD_TIME_EXCEEDED_TO_PERFORM_CANCELLATION="196";
    public static final String WEB_SERVICES_METHOD_SERVICE_NOT_AVAILABLE="144";


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

    //public static final String CONSTANT_URL_QA_REGISTRO_UNIFICADO_AW = "http://ec2-35-164-98-136.us-west-2.compute.amazonaws.com:8080/RegistroUnificado/APIRegistroUnificadoService?wsdl";
    //public static final String CONSTANT_URL_QA_REGISTRO_UNIFICADO_AW = "http://ec2-52-42-63-64.us-west-2.compute.amazonaws.com:8080/RegistroUnificado/APIRegistroUnificadoService?wsdl";
    //public static final String CONSTANT_URL_QA_REGISTRO_UNIFICADO_AW = "http://ec2-52-42-63-64.us-west-2.compute.amazonaws.com:8080/RegistroUnificado/APIRegistroUnificadoService?wsdl";
    public static final String CONSTANT_URL_QA_REGISTRO_UNIFICADO_AW = "http://ec2-52-42-63-64.us-west-2.compute.amazonaws.com:8080/RegistroUnificado/APIRegistroUnificadoService?wsdl";

    /**
     *
     */
    //public static final String CONSTANT_URL_QA_ALODIGA = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";
    public static final String CONSTANT_URL_QA_ALODIGA = "http://" + IPDAT + ":8080/AlodigaWallet/APIAlodigaWalletService";
    //public static final String CONSTANT_URL_QA_ALODIGA_AW = "http://ec2-35-164-98-136.us-west-2.compute.amazonaws.com:8080/AlodigaWallet/APIAlodigaWalletService?wsdl";
    public static final String CONSTANT_URL_QA_ALODIGA_AW ="http://54.70.31.158:8080/AlodigaWallet/APIAlodigaWalletService?wsdl";



    /**
     * Alodiga WAllet
     */
    //public static final String CONSTANT_URL_QA_ALODIGA_WALLET  = "http://192.168.3.20:8080/AlodigaWallet/APIAlodigaWalletService";
    public static final String CONSTANT_URL_QA_ALODIGA_WALLET = "http://" + IPDAT + ":8080/AlodigaWallet/APIAlodigaWalletService";

    public static final String CONSTANT_URL_REMITTENCE = "http://" + IPDAT + ":8080/WSRemittenceService/WSRemittence";
    public static final String CONSTANT_URL_REMITTENCE_PAYMENT_NETWORK = "http://" + IPDAT + ":8080/WSPaymentNetworkMethodService/WSPaymentNetworkMethod";
    public static final String CONSTANT_URL_REMITTENCE_MOBILE = "http://" + IPDAT + ":8080/WSRemittenceMobileService/WSRemittenceMobile";
    //public static final String CONSTANT_URL_REMITTENCE_MOBILE_AW = "http://ec2-35-164-98-136.us-west-2.compute.amazonaws.com:8080/WSRemittenceMobileService/WSRemittenceMobile?wsdl";
    public static final String CONSTANT_URL_REMITTENCE_MOBILE_AW ="http://ec2-35-162-209-241.us-west-2.compute.amazonaws.com:8080/WSRemittenceMobileService/WSRemittenceMobile?wsdl";

    public static final String CONSTANT_WSREMITTENCE= "WSRemittence";
    public static final String CONSTANT_WSPAYMENTNETWORK= "WSPaymentNetworkMethod";
    public static final String CONSTANT_WSREMITTENCESERVICES= "WSRemittenceServices";
    public static final String CONSTANT_WSREMITTENCEMOBILE= "WSRemittenceMobile";




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
    public final static String REMITTENCE_ID = "0";



    public final static String USUARIO = "alodiga";
    public final static String PASSWORD = "d6f80e647631bb4522392aff53370502";

    public static final int YEARPLUS = 70;
    public static final int YEARMINUS = 30;

    public static final int LONGITUD_MAXIMA_CVV = 4;
    public static final int LONGITUD_MINIMA_CVV = 3;
    public static final int LONGITUD_MAXIMA_TARJETA_CREDITO = 16;
    public static final int LONGITUD_MINIMA_TARJETA_CREDITO = 13;

    public static final String CURRENCY_INFO_PAYMENT = "840";



}
