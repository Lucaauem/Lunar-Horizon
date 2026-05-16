package engine.core.global;

import org.json.JSONArray;
import org.json.JSONObject;
import util.FileHandler;
import java.util.HashMap;

public class Global {
  private static final String FLAG_FILE_PATH = "src/main/assets/data/STORYFLAGS.json";

  private static final HashMap<String, Boolean> storyFlags = new HashMap<>();

  public static void init() {
    JSONObject flags = FileHandler.readJSON(FLAG_FILE_PATH);

    for (Object flag : flags.getJSONArray("flags")) {
      storyFlags.put((String) flag, false);
    }

    // Debug: Set flags in init
    for (Object debugFlag : flags.getJSONArray("debug_set")) {
      storyFlags.put((String) debugFlag, true);
    }
  }

  public static void setStoryFlag(String flag) {
    if (storyFlags.containsKey(flag)) {
      storyFlags.put(flag, true);
    }
  }

  private static boolean getStoryFlag(String flag) {
    return storyFlags.containsKey(flag) && storyFlags.get(flag);
  }

  public static boolean checkPreq(JSONObject preqs) {
    JSONArray trueFlags = preqs.has("true") ? preqs.getJSONArray("true") : new JSONArray();
    JSONArray falseFlags = preqs.has("false") ? preqs.getJSONArray("false") : new JSONArray();

    for (Object flag : trueFlags) {
      if (!Global.getStoryFlag((String) flag)) return false;
    }
    for (Object flag : falseFlags) {
      if (Global.getStoryFlag((String) flag)) return false;
    }

    return true;
  }
}
