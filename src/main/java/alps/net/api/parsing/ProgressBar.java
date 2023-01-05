package alps.net.api.parsing;

import java.util.Objects;

public class ProgressBar {
    private int currentProgress;
    private int maxProgress;
    private static final String progressFill = "###";
    private static final String restFill = "   ";

    public ProgressBar(int maxProgress) {
        this.currentProgress = 0;
        this.maxProgress = maxProgress;
        System.out.println("Progress: ");
        System.out.println("[" + getFill() + "]");
    }

    public void increaseProgress() {
        currentProgress++;
        System.out.println("[" + getFill() + "]");
    }

    private String getFill() {
        String fill = "";
        for (int i = 0; i < currentProgress; i++) {
            fill += progressFill;
        }
        for (int i = currentProgress; i < maxProgress; i++) {
            fill += restFill;
        }
        return fill;
    }
}

