package helab.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * JsonUtils
 *
 * @author <a href="mailto:jzchen@coremail.cn">jzchen</a>
 */
public class JsonUtils {

    public static JsonObject String2JsonObject(String httpEntity) {
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(httpEntity);
        return je.getAsJsonObject();
    }

}
