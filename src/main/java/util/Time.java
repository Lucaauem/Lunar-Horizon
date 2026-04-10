package util;

import engine.Game;

public class Time {
  private static long timeStarted;
  private static float lastTime;
  private static float deltaTime;

  public static void init() {
    timeStarted = System.nanoTime();
    lastTime = 0f;
  }

  public static boolean update() {
    float currentTime = getTime();
    Time.deltaTime = currentTime - Time.lastTime;

    if (Time.deltaTime < 1.0f / (float) Game.FPS_CAP) {
      return false;
    }

    Time.lastTime = currentTime;
    return true;
  }

  public static float deltaTime() {
    return Time.deltaTime;
  }

  private static float getTime() {
    return (float) ((System.nanoTime() - Time.timeStarted) * 1e-9);
  }
}