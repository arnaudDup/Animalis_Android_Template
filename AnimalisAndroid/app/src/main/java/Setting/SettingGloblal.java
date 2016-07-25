package Setting;

import android.util.Log;

/**
 * Created by arnauddupeyrat on 11/06/16.
 */
public class SettingGloblal {

    public static String URL = "http://localhost:";
    public static String LOCALHOSTEMUl = "http://10.0.2.2:";
    public static String PORT = "3000";
    public static String URLSERVER = URL+PORT;
    public static String URLSERVEREMUL = LOCALHOSTEMUl+PORT;


    /********************* Web service **************************/
    public static int DELETE = 4;
    public static int POST = 2;
    public static String RESPONSE = "response" ;
    public static String CODE = "HTTPcode";
    public static String MYURL = "url";
    public static final int NOUVELADHERENTS = 0;

    public static final String LEFT_FRAGMENT = "Left";
    public static final String RIGHT_FRAGMENT = "Rigth";

    /***********************  Dialog Error **********************/

    public static final String MESSAGEERROR = "message";
    public static final String MESSAGE_CONTROLER = "messageControler";


    /***********************  Dialog Error **********************/

    public static final String REMOVE_ACCOUNT = "removeAccount" ;
    public static final String ASK_FOR_PERMISSION = "askForPermission";

}
