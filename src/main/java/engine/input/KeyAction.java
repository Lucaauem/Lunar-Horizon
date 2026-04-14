package engine.input;

class KeyAction {
  private final Runnable action;
  private final boolean needReset;
  private boolean resetFlag = false;

  public KeyAction(Runnable action, boolean needReset) {
    this.action = action;
    this.needReset = needReset;
  }

  public void setResetFlag(boolean resetFlag) {
    this.resetFlag = resetFlag;
  }

  public void run() {
    if (!this.needReset || this.resetFlag) {
      this.action.run();
    }
  }
}
