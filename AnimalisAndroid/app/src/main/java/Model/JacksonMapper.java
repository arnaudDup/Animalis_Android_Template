package Model;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.DTO.ProfileUserDto;
import Setting.SettingGloblal;

/**
 * Created by arnauddupeyrat on 14/07/16.
 */
public class JacksonMapper {

    ObjectMapper mapper;

    /** private Constructor */
    private JacksonMapper(){
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    /** Unique instance */
    private static JacksonMapper INSTANCE = null;

    /** get the static instance */
    public static JacksonMapper getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new JacksonMapper();
        }
        return INSTANCE;
    }

    public Object StringToObject (Class currentClass, String objectInString) throws Exception{
            return mapper.readValue(objectInString, currentClass);
    }

}
