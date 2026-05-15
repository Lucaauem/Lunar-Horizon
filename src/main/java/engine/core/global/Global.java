package engine.core.global;

import org.json.JSONObject;
import util.FileHandler;
import java.util.HashMap;

public class Global {
  private static final String FLAG_FILE_PATH = "src/main/assets/data/STORYFLAGS.json";

  private static final HashMap<String, Boolean> storyFlags = new HashMap<>();

  public static void init() {
    JSONObject flags = FileHandler.readJSON(FLAG_FILE_PATH);

    for (String key : flags.keySet()) {
      storyFlags.put(key, flags.getBoolean(key));
    }
  }

  public static void setStoryFlag(String flag) {
    if (storyFlags.containsKey(flag)) {
      storyFlags.put(flag, true);
    }
  }

  public static boolean getStoryFlag(String flag) {
    return storyFlags.containsKey(flag) && storyFlags.get(flag);
  }
}
