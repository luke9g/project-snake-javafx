public class Timer extends Thread {
    private boolean canUpdate;
    private boolean isFrozen;
    private int gameLoops;

    public Timer() {
        canUpdate = false;
        isFrozen = false;
    }

    public boolean canUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public void freeze() {
        isFrozen = true;
    }

    public void unfreeze() {
        isFrozen = false;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public int getGameLoops() {
        return gameLoops;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            while (!isFrozen) {
                gameLoops++;
                canUpdate = !canUpdate;
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
